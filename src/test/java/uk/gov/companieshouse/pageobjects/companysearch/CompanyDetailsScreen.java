package uk.gov.companieshouse.pageobjects.companysearch;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.utils.ElementInteraction;
import uk.gov.companieshouse.utils.TestContext;


public class CompanyDetailsScreen extends ElementInteraction {

    public static final Logger log = LoggerFactory.getLogger(CompanyDetailsScreen.class);

    public final TestContext testContext;


    /**
     * Required constructor for class.
     */
    public CompanyDetailsScreen(TestContext testContext) {
        super(testContext);
        this.testContext = testContext;
        PageFactory.initElements(testContext.getWebDriver(), this);
    }

    @FindBy(how = How.CSS, using = "#header > .pageheader > .titleLabel")
    private WebElement elementViewCompanyTitle;
    @FindBy(how = How.CSS, using = "a[title='Show next page']")
    private WebElement nextPage;
    @FindBy(how = How.ID, using = "form1:companySearchTabs:companySearchHeaderAndTabs:corporateBody:corporateBody:"
            + "corporateBody_actionCode__1:output")
    private WebElement elementActionCode;
    @FindBy(how = How.ID, using = "form1:companySearchTabs:companySearchHeaderAndTabs:corporateBody:"
            + "corporateBody:corporateBody_name:output")
    private WebElement elementCompanyName;

    /**
     * Locates specific transaction from the filing history table.
     */
    public CompanyDetailsScreen getExpectedTransactionFromHistory(String formName,
                                                                  String docReceivedDate,
                                                                  String transactionStatus,
                                                                  String transactionDescription) {
        Map<String, String> transactionToFind = new HashMap<>();
        transactionToFind.put("Form", formName.toUpperCase());
        transactionToFind.put("Received date", docReceivedDate);
        transactionToFind.put("Transaction status", transactionStatus);
        transactionToFind.put("Transaction description", transactionDescription);

        String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        WebElement transaction;
        final String formToFind = transactionToFind.get("Form");

        log.debug("Looking for transaction: {}", transactionToFind);
        while (true) {
            transaction = checkHistoryTableRow(transactionToFind);
            if (transaction != null) {
                log.info("Transaction history entry found: {}", transaction.getText());
                break;
            }

            if (docReceivedDate.equals(todayDate)) {
                log.error("{} transaction not found on today's date.", formToFind);
                throw new RuntimeException("The expected " + formToFind + " transaction was not found on today's date");
            } else {
                try {
                    nextPage.click();
                    log.debug("Checking next page for {} transaction.", formToFind);
                } catch (NoSuchElementException noSuchElementException) {
                    log.error("{} transaction not found, no more pages to check. Error: {}",
                            formToFind, noSuchElementException.getMessage());
                    throw new RuntimeException("The expected " + formToFind + " transaction was not found");
                }
            }
        }
        return this;
    }

    public CompanyDetailsScreen waitUntilDisplayed() {
        waitForSpecificTextInElement(elementViewCompanyTitle, "View Company Details");
        return this;
    }

    /**
     * verify action code.
     */
    public CompanyDetailsScreen verifyActionCode(String actionCodeDesc) {
        waitForSpecificTextInElement(elementActionCode, actionCodeDesc);
        return this;
    }

    /**
     * Verify the new company name after a name change.
     *
     * @param companyName the company name to find.
     */
    public CompanyDetailsScreen verifyNewCompanyName(String companyName) {
        log.info("Checking for new company name: {}", companyName);
        waitForSpecificTextInElement(elementCompanyName, companyName.toUpperCase());
        return this;
    }

    private WebElement checkHistoryTableRow(Map<String, String> transactionToFind) {
        // Get all transaction rows in a list
        final List<WebElement> allTransactionsList = testContext.getWebDriver().findElements(By.cssSelector(
                "[id='form1:viewTCL:viewTHCL:_THCLDataTableUsed:THCLDataTableFrag:"
                        + "transactionHistoryAndContactLog_unfilteredDataModel'] > tbody > tr"));
        WebElement webElement;
        //The ID's of columns to check with placeholders for the row index.
        final String formTypeColumn = "form1:viewTCL:viewTHCL:_THCLDataTableUsed:THCLDataTableFrag:"
                + "transactionHistoryAndContactLog_unfilteredDataModel:%d:type";
        final String docReceivedDateColumn = "form1:viewTCL:viewTHCL:_THCLDataTableUsed:THCLDataTable"
                + "Frag:transactionHistoryAndContactLog_unfilteredDataModel:%d:datereceived";
        final String docStatusColumn = "form1:viewTCL:viewTHCL:_THCLDataTableUsed:THCLDataTableFrag:"
                + "transactionHistoryAndContactLog_unfilteredDataModel:%d:status";
        final String docDescriptionColumn = "form1:viewTCL:viewTHCL:_THCLDataTableUsed:THCLDataTable"
                + "Frag:transactionHistoryAndContactLog_unfilteredDataModel:%d:description";
        for (int i = 0; i < allTransactionsList.size(); i++) {
            // Iterate through each individual transaction entry
            webElement = allTransactionsList.get(i);
            String formType = String.valueOf(webElement.findElement(By.id(String
                    .format(formTypeColumn, i))).getText());
            String docReceivedDate = String.valueOf(webElement.findElement(By.id(String
                    .format(docReceivedDateColumn, i))).getText());
            String docStatus = String.valueOf(webElement.findElement(By.id(String
                    .format(docStatusColumn, i))).getText());
            String docDescription = String.valueOf(webElement.findElement(By.id(String
                    .format(docDescriptionColumn, i))).getText());

            // If the form type matches what we are looking for
            // Check the rest of the row found for what we are looking for
            if (formType.equals(transactionToFind.get("Form"))
                    && docReceivedDate.equals(transactionToFind.get("Received date"))
                    && docStatus.equals(transactionToFind.get("Transaction status"))
                    && docDescription.contains(transactionToFind.get("Transaction "
                    + "description"))) {
                // If all is equal to what we are looking for then return the webElement
                return webElement;
            }

        }
        // If not then return null and the method will be called again to check the next row
        return null;
    }


}
