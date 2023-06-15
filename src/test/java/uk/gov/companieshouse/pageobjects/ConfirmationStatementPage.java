package uk.gov.companieshouse.pageobjects;

import java.util.Date;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.enums.Form;
import uk.gov.companieshouse.utils.DateFormat;
import uk.gov.companieshouse.utils.TestContext;


public class ConfirmationStatementPage extends ChipsCommonPage<ConfirmationStatementPage> {

    public final TestContext testContext;
    public static final Logger log = LoggerFactory.getLogger(ConfirmationStatementPage.class);

    /**
     * Required constructor for class.
     */
    public ConfirmationStatementPage(TestContext testContext) {
        super(testContext);
        this.testContext = testContext;
        PageFactory.initElements(testContext.getWebDriver(), this);
    }

    @FindBy(how = How.ID, using = "form1:confirmationstatement_date:csMUD:field")
    private WebElement elementCsDateInput;
    @FindBy(how = How.ID, using = "form1:confirmationstatement_payment:task_selectedPaymentReceivedValue:field1")
    private WebElement elementPaymentReceivedYes;
    @FindBy(how = How.ID, using = "form1:confirmationstatement_date:_id61")
    private WebElement elementNoUpdatesCheckbox;
    @FindBy(how = How.ID, using = "form1:capitalTab:indicator")
    private WebElement elementCapitalTab;
    @FindBy(how = How.ID, using = "form1:task_tabBookUITabHeader:0")
    private WebElement elementDatesTab;
    @FindBy(how = How.ID, using = "form1:confirmationstatement_capital:_id66")
    private WebElement elementSocSelector;
    @FindBy(how = How.ID, using = "form1:statementOfCapital_shareValuesElement:0:shareValue_totalAmountUnpaid:field")
    private WebElement elementTotalUnpaid;
    @FindBy(how = How.ID, using = "form1:corporateBody:corporateBody_formattedNextCSDueDate:output")
    private WebElement elementNextCsDue;


    /**
     * Complete mandatory fields to file a no update ConfirmationStatement. Alert is fired when checkbox is clicked
     * so accept that and continue with test.
     */
    public ConfirmationStatementPage completeNoUpdateCs01() {
        waitUntilFormDisplayed(Form.CS01);
        elementCsDateInput.sendKeys(DateFormat.getDateAsString(new Date()));
        elementPaymentReceivedYes.click();
        elementNoUpdatesCheckbox.click();
        acceptAlert();
        saveForm();
        return this;
    }

    /**
     * Complete mandatory fields to file a no update ConfirmationStatement. Alert is fired when checkbox is clicked
     * so accept that and continue with test.
     */
    public ConfirmationStatementPage completePaperCs01() {
        waitUntilFormDisplayed(Form.CS01);
        String nextCsDue = elementNextCsDue.getText();
        elementCsDateInput.sendKeys(nextCsDue);
        elementPaymentReceivedYes.click();
        completeCapitalTab();
        saveForm();
        return this;
    }

    /**
     * Open up the capital tab and complete the madatory fields required to file the form.
     */
    private ConfirmationStatementPage completeCapitalTab() {
        elementCapitalTab.click();
        waitUntilElementDisplayed(elementSocSelector);
        typeText(elementTotalUnpaid, "0");
        selectByText(elementSocSelector, "YES");
        return this;
    }

}

