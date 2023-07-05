package uk.gov.companieshouse.pageobjects.workitems;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.pageobjects.GlobalNavBar;
import uk.gov.companieshouse.utils.ElementInteraction;
import uk.gov.companieshouse.utils.TestContext;


public class MyWorkPage extends ElementInteraction {

    @FindBy(how = How.CSS, using = "[id='form1:task_workObjects'] > tbody > tr")
    private WebElement elementMyWorkTableRows;
    @FindBy(how = How.CSS, using = "*[class='subtitle']")
    private WebElement elementMwPageTitle;
    @FindBy(how = How.ID, using = "form1:task_workObjects:task_sortByStartDate")
    private WebElement elementSortByDate;
    @FindBy(how = How.ID, using = "form1:task_workObjects:receiptCreationDateColumn:sortIcon")
    private WebElement elementSortDateIndicator;
    @FindBy(how = How.ID, using = "form1:task_workObjects:0:documentContactTypeColumn")
    private WebElement elementDocumentType;

    private static final Logger LOG = LoggerFactory.getLogger(MyWorkPage.class);
    public final TestContext testContext;
    private final GlobalNavBar globalNavBar;

    /**
     * Required constructor for class.
     */
    public MyWorkPage(TestContext testContext, GlobalNavBar globalNavBar) {
        super(testContext);
        PageFactory.initElements(testContext.getWebDriver(), this);
        this.globalNavBar = globalNavBar;
        this.testContext = testContext;

    }

    /**
     * Open my work page from the main menu.
     */
    public MyWorkPage goToMyWorkPage() {
        globalNavBar.clickMenuItem("My Work");
        waitUntilOpened().sortDateDescending();
        return this;
    }

    /**
     * Opens a work item table entry based on the parameters passed. Iterates through the list of work items until
     * the relevant one is found.
     *
     * @param columnName  e.g. "company number"
     * @param columnValue e.g. the company name
     * @param documentType e.g. CS01
     */
    public void openWorkItem(String columnName, String columnValue,
                              String documentType) {
        final List<WebElement> allRows = testContext.getWebDriver().findElements(By.cssSelector("table[id='form1:task_workObjects'] tbody tr"));
        final String typeFormat = "form1:task_workObjects:%d:documentContactTypeColumn";
        final String columnValueFormat = "form1:task_workObjects:%d:%s";
        final String docStatusFormat = "form1:task_workObjects:%d:statusColumn";
        WebElement webElement;

        for (int i = 0; i < allRows.size(); i++) {
            webElement = allRows.get(i);
            final String colCoNum
                    = webElement.findElement(By.id(String.format(columnValueFormat, i, columnName))).getText().trim();
            final String colType
                    = webElement.findElement(By.id(String.format(typeFormat, i))).getText().trim();
            final String columnStatus
                    = webElement.findElement(By.id(String.format(docStatusFormat, i))).getText().trim();
            if (colCoNum.equals(columnValue) && colType.equals(documentType)  && columnStatus.equals("REALLOCATED")) {
                doubleClick(webElement);
                LOG.info("Selecting document {} for company {} from My Work...", documentType, columnValue);
                break;
            }
        }
    }

    public MyWorkPage waitUntilOpened() {
        waitForSpecificTextInElement(elementMwPageTitle, "My Work");
        return this;
    }

    /**
     * Sort work items by date.
     */
    private MyWorkPage sortDateDescending() {
        elementSortByDate.click();
        if (isSortedAscending()) {
            sortDateDescending();
        }
        return this;
    }

    private boolean isSortedAscending() {
        return elementSortDateIndicator.getAttribute("class").contains("sortForward");
    }


}
