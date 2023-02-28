package uk.gov.companieshouse.pageobjects;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.data.datamodel.Company;
import uk.gov.companieshouse.data.dbutil.DbUtil;
import uk.gov.companieshouse.enums.Forms.Form;
import uk.gov.companieshouse.testdata.CompanyDetails;
import uk.gov.companieshouse.testdata.DocumentDetails;
import uk.gov.companieshouse.testdata.SqlDetails;
import uk.gov.companieshouse.utils.BarcodeGenerator;
import uk.gov.companieshouse.utils.ElementInteraction;
import uk.gov.companieshouse.utils.TestContext;

public class ProcessStartOfDocumentPage extends ElementInteraction {

    public TestContext testContext;
    private BarcodeGenerator barcodeGenerator;
    private DbUtil dbUtil;
    public SqlDetails sqlDetails;
    public CompanyDetails companyDetails;
    public DocumentDetails documentDetails;

    public static final Logger log = LoggerFactory.getLogger(ProcessStartOfDocumentPage.class);

    public ProcessStartOfDocumentPage(TestContext testContext, BarcodeGenerator barcodeGenerator, DbUtil dbUtil,
                                      SqlDetails sqlDetails, CompanyDetails companyDetails, DocumentDetails documentDetails) {
        super(testContext);
        this.testContext = testContext;
        this.barcodeGenerator = barcodeGenerator;
        this.dbUtil = dbUtil;
        this.sqlDetails = sqlDetails;
        this.companyDetails = companyDetails;
        this.documentDetails = documentDetails;
        PageFactory.initElements(testContext.getWebDriver(), this);
    }

    @FindBy(how = How.ID, using = "form1:task_processStartOfDocumentValidator_startOfDocument_barcode:field")
    private WebElement elementBarcodeInputKey;
    @FindBy(how = How.ID, using = "form1:formType:field")
    private WebElement elementFormTypeSelectKey;
    @FindBy(how = How.ID, using = "form1:receiptDate:field")
    private WebElement elementReceiptDateInputKey;
    @FindBy(how = How.ID, using = "form1:task_proceed")
    private WebElement elementProceedLinkKey;
    @FindBy(how = How.ID, using = "form1:companyNumber:field")
    private WebElement elementCompanyNumberInputKey;
    @FindBy(how = How.ID, using = "form1:suppliedNumber:field")
    private WebElement elementSuppliedNumberInputKey;
    @FindBy(how = How.ID, using = "form1:_id7")
    private WebElement elementCompanyNumber1HiddenInputKey;
    @FindBy(how = How.ID, using = "form1:_id10")
    private WebElement elementCompanyNumber2HiddenInputKey;
    @FindBy(how = How.ID, using = "form1:hrCompanyNumber1:field")
    private WebElement elementCompanyNumber1InputKey;
    @FindBy(how = How.ID, using = "form1:hrCompanyNumber2:field")
    private WebElement elementCompanyNumber2InputKey;
    @FindBy(how = How.ID, using = "form1:_id8")
    private WebElement elementCompanyNumber1NameHiddenInputKey;
    @FindBy(how = How.ID, using = "form1:obscuredHrCompanyName2")
    private WebElement elementCompanyNumber2NameHiddenInputKey;
    @FindBy(how = How.ID, using = "form1:hrCompanyName1")
    private WebElement elementCompanyNumber1NameInputKey;
    @FindBy(how = How.ID, using = "form1:hrCompanyName2")
    private WebElement elementCompanyNumber2NameInputKey;
    @FindBy(how = How.ID, using = "coList")
    private WebElement elementCompanyListSelect;
    @FindBy(how = How.ID, using = "form1:checkCharacters:field")
    private WebElement elementCheckCharactersPrefixInputKey;
    @FindBy(how = How.ID, using = "form1:checkEndCharacters:field")
    private WebElement elementCheckCharactersSuffixInputKey;
    @FindBy(how = How.ID, using = "form1:suppliedName:field")
    private WebElement elementSuppliedCompanyNameKey;
    @FindBy(how = How.ID, using = "form1:task_startOfDocument_companyName__1:output")
    private WebElement elementCompanyNameOutput;
    @FindBy(how = How.ID, using = "div[id='form1:psodPopupDiv'] span[class='popTitle']")
    private WebElement elementErrorTitle;
    @FindBy(how = How.ID, using = "form1:task_processStartOfDocumentValidator_popUp_allBodyText")
    private WebElement elementErrorBody;
    @FindBy(how = How.ID, using = "form1:companyNumberHighRisk:field")
    private WebElement elementRejectCompanyNumberKey;
    @FindBy(how = How.ID, using = "form1:fieldGroup:rejectionCharacters:field")
    private WebElement elementRejectCheckCharactersPrefixKey;
    @FindBy(how = How.ID, using = "form1:fieldGroup:rejectionCheckEndCharacters:field")
    private WebElement elementRejectCheckCharactersSuffixKey;
    @FindBy(how = How.ID, using = "form1:fieldGroup:reject")
    private WebElement elementRejectLink;
    @FindBy(how = How.ID, using = "form1:task_reject")
    private WebElement elementPopupRejectLink;
    @FindBy(how = How.ID, using = "form1:task_startOfDocument_cutSuppliedCompanyName:field")
    private WebElement elementNameEndingMissingCheckbox;
    @FindBy(how = How.ID, using = "form1:psodPopupDiv:close")
    private WebElement elementPopupCloseLink;
    @FindBy(how = How.ID, using = "cff_page_message_div_messages")
    private WebElement messagePopup;
    @FindBy(how = How.ID, using = "a[class='messageClose']")
    private WebElement messageClose;

    // elements for the attachments pop up
    @FindBy(how = How.ID, using = "form1:task_startOfDocument_attachments:field:add")
    private WebElement elementAddAttachmentsLink;
    @FindBy(how = How.ID, using = "table[id='form1:task_startOfDocument_attachments:field:dataTable'] tbody tr")
    private WebElement elementListOfAttachments;
    @FindBy(how = How.ID, using = "form1:task_startOfDocument_attachments:field:save")
    private WebElement elementSaveAttachmentsLink;

    // elements for the confirmation pop up
    @FindBy(how = How.ID, using = "form1:task_yes")
    private WebElement elementPopupYesLink;
    @FindBy(how = How.ID, using = "form1:task_no")
    private WebElement elementPopupNoLink;


    /**
     * Set the barcode.
     *
     * @param barcode the barcode
     */
    public ProcessStartOfDocumentPage setBarcodeField(String barcode) {
        waitUntilElementDisplayed(elementBarcodeInputKey);
        elementBarcodeInputKey.sendKeys(barcode);
        return waitUntilReceiptDatePopulated();
    }


    public String getGeneratedReceiptDate() {
        waitElementTextNotEmpty(elementReceiptDateInputKey);
        return elementReceiptDateInputKey.getAttribute("value");
    }


    private ProcessStartOfDocumentPage waitUntilReceiptDatePopulated() {
        if (elementReceiptDateInputKey.getText() == null) {
            throw new RuntimeException("The receipt date was not populated.");
        }
        return this;
    }

    public String generateBarcode(Date date) {
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        int maxBarcodeRetries = 5;
        Date barcodeDate = (date == null) ? today : date;
        String barcode = "";
        for (int i = 1; i <= maxBarcodeRetries; i++) {
            barcode = generateAndEnterBarcode(barcodeDate);
            String originalDateString = sdf.format(barcodeDate);
            String generatedDateString = getGeneratedReceiptDate();

            if (generatedDateString.equals(originalDateString)) {
                log.info("Barcode {} generated for date: {}", barcode, generatedDateString);
                documentDetails.setBarcode(barcode);
                documentDetails.setReceivedDate(generatedDateString);
                break;
            }

            if (i == maxBarcodeRetries) {
                log.error(
                        "Tried {} barcodes for date {}, all incorrect.",
                        maxBarcodeRetries, originalDateString
                );
                throw new RuntimeException("Unable to generate correct barcode");
            }
            log.info("Generated barcode {} has incorrect date - retrying", barcode);
            clearBarcodeAndDate();
        }
        return barcode;
    }

    public void clearBarcodeAndDate() {
        if ("The date on the barcode is over 25 days old.".equals(messagePopup.getText())) {
            messageClose.click();
        }
        elementReceiptDateInputKey.clear();
        elementBarcodeInputKey.clear();
    }

    public String generateAndEnterBarcode(Date date) {
        String barcode = barcodeGenerator.generateNewStyleBarcode(date);
        setBarcodeField(barcode);
        return barcode;
    }

    /**
     * Wait until company name populated after CHIPS lookup.
     * returns the company name, or a blank string if not populated.
     */
    public String getPopulatedCompanyName() {
        //Wait for company name to be populated
        getWebDriverWait(1000);
        return elementCompanyNameOutput.getText();
    }

    public ProcessStartOfDocumentPage clickProceedLink() {
        elementProceedLinkKey.click();
        return this;
    }

    /**
     * Wait until the process start of document page is displayed.
     * Log an error if the barcode field on PSOD cannot be found.
     */
    public ProcessStartOfDocumentPage waitUntilDisplayed() {
            waitUntilElementDisplayed(elementBarcodeInputKey);
            log.info("Process start of document page displayed successfully.");
        return this;
    }

    /**
     * Complete the company identification fields on the process start of document screen.
     *
     * @param company      the company object containing the company details to fill in
     * @param highRiskForm flag identifying whether to complete high risk or low risk form fields
     *                     (triple keying or not)
     */
    public ProcessStartOfDocumentPage processForm(Company company, String formType, boolean highRiskForm) {
        Date today = new Date();
        waitUntilDisplayed().generateBarcode(today);
        fillInPsodFields(company, highRiskForm, formType);
        clickProceedLink();
        return this;
    }

    /**
     * Complete the Process Start Of Document screen for FES document.
     *
     * @param form           the form to select
     * @param company        the pertinent company
     * @param twoCompanyForm flags form as 'high risk' that requires double entry of company
     *                       name and number
     * @return the domain reached
     */
    public ProcessStartOfDocumentPage processFesForm(
            String form,
            Company company,
            boolean twoCompanyForm
    ) {
        if (company != null) {
            setCompanyContext(company);
        }
        fillInPsodFields(company, twoCompanyForm, form);
        documentDetails.setReceivedDate(getGeneratedReceiptDate());
        clickProceedLink();
        clickPsodPopUpYesLink();
        return this;
    }

    /**
     * Complete the company identification fields on the process start of document screen
     * extracted out to a utility method as it is used in multiple locations.
     *
     * @param company        the company object containing the company details to fill in
     * @param twoCompanyForm flag identifying whether to complete high risk or low risk form fields
     *                       (double keying or not)
     */
    private void fillInPsodFields(Company company, boolean twoCompanyForm, String formType) {
        // Attempt to fill in the fields using the Company data from the DB
        // Call the retry method if not populated correctly.
        selectFormType(Form.getFormByType(formType));

        do {
            if (company != null) {
                if (!twoCompanyForm) {
                    waitUntilElementDisplayed(elementCompanyNumberInputKey);
                    log.info("Successfully selected low risk form: {}", formType);
                    setCompanyNumberField(company.getNumber())
                            .setCheckCharactersPrefixField(company.getPrefix())
                            .setCheckCharactersSuffixField(company.getSuffix());
                } else {
                    log.info("Successfully selected low risk form: {}", formType);
                    setCompanyNumber1Field(company.getNumber())
                            .setCompanyNumber1NameField(company.getName())
                            .setCompanyNumber2Field(company.getNumber())
                            .setCompanyNumber2NameField(company.getName())
                            .setCompanySelect(company.getName(), company.getNameEnding());
                }
            }
            assert company != null;
            setCompanyContext(company);
        } while (!retryCloneIfCompanyNameNotPopulated());
    }

    private void selectFormType(Form form) {
        String formName = form.getType();
        selectByText(elementFormTypeSelectKey, formName);
        // There is a known issue with Selenium 4 where after using the necessary select method above, the screen
        // does not refresh and hidden company name/number fields are not displayed. The following methods moving
        // the cursor up and down results in the correct form being selected and required fields are displayed.
        elementFormTypeSelectKey.sendKeys(Keys.UP);
        elementFormTypeSelectKey.sendKeys(Keys.DOWN);
    }

    public ProcessStartOfDocumentPage clickPsodPopUpYesLink() {
        elementPopupYesLink.click();
        return this;
    }

    /**
     * Check if the company name is populated correctly on PSOD.
     * Retry company cloning using the SQL initially used if it is not.
     */
    private boolean retryCloneIfCompanyNameNotPopulated() {
        if (getPopulatedCompanyName().equals("")) {
            dbUtil.cloneCompanyWithParameterInternal(sqlDetails.getCompanySql(),
                    sqlDetails.getSqlParameter());
            return false;
        } else {
            return true;
        }
    }

    private ProcessStartOfDocumentPage setCompanyNumberField(String companyNumber) {
        elementCompanyNumberInputKey.sendKeys(companyNumber);
        return this;
    }

    private ProcessStartOfDocumentPage setCheckCharactersPrefixField(final String checkPrefix) {
        elementCheckCharactersPrefixInputKey.sendKeys(checkPrefix);
        return this;
    }

    private ProcessStartOfDocumentPage setCheckCharactersSuffixField(final String checkSuffix) {
        elementCheckCharactersSuffixInputKey.sendKeys(checkSuffix);
        return this;
    }

    private ProcessStartOfDocumentPage setCompanyNumber1Field(String companyNumber) {
        elementCompanyNumber1HiddenInputKey.click();
        typeText(elementCompanyNumber1InputKey, companyNumber);
        return this;
    }

    private ProcessStartOfDocumentPage setCompanyNumber1NameField(String companyName) {
        elementCompanyNumber1NameHiddenInputKey.click();
        typeText(elementCompanyNumber1NameInputKey, companyName.trim());
        return this;
    }

    private ProcessStartOfDocumentPage setCompanyNumber2Field(String companyNumber) {
        elementCompanyNumber2HiddenInputKey.click();
        typeText(elementCompanyNumber2InputKey, companyNumber);
        return this;
    }

    private ProcessStartOfDocumentPage setCompanyNumber2NameField(String companyName) {
        elementCompanyNumber2NameHiddenInputKey.click();
        typeText(elementCompanyNumber2NameInputKey, companyName);
        // move the cursor to make the hidden company select appear
        elementCompanyNumber2NameInputKey.sendKeys(Keys.LEFT);
        return this;
    }

    /**
     * Select the company from the dropdown list.
     *
     * @param companyName the company name
     * @param nameEnding  its name ending
     */
    private ProcessStartOfDocumentPage setCompanySelect(String companyName, String nameEnding) {
        selectByText(
                elementCompanyListSelect,
                (companyName.trim() + " " + nameEnding).trim()
        );
        elementCompanyListSelect.sendKeys(Keys.RETURN);
        return this;
    }

    private ProcessStartOfDocumentPage setCompanyContext(Company company) {
        companyDetails.setCompanyNumber(company.getNumber());
        companyDetails.setCompanyName(company.getName());
        return this;
    }

}
