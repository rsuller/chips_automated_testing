package uk.gov.companieshouse.pageobjects;

import static org.openqa.selenium.support.ui.ExpectedConditions.attributeToBeNotEmpty;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.enums.Forms.Form;
import uk.gov.companieshouse.utils.BarcodeGenerator;
import uk.gov.companieshouse.utils.ElementInteraction;
import uk.gov.companieshouse.utils.TestContext;


public class ProcessStartOfDocumentPage extends ElementInteraction {

    public TestContext testContext;
    private BarcodeGenerator barcodeGenerator;
    public static final Logger log = LoggerFactory.getLogger(ProcessStartOfDocumentPage.class);

    public ProcessStartOfDocumentPage(TestContext testContext) {
        super(testContext);
        this.testContext = testContext;
        this.barcodeGenerator = new BarcodeGenerator();
        PageFactory.initElements(testContext.getWebDriver(),this);
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

    // elements for the liquidation confirmation pop up
    @FindBy(how = How.ID, using = "form1:task_yes")
    private WebElement elementLiquidationYesLink;
    @FindBy(how = How.ID, using = "form1:task_no")
    private WebElement elementLiquidationNoLink;


    /**
     * Set the barcode.
     *
     * @param barcode the barcode
     */
    public ProcessStartOfDocumentPage setBarcodeField(String barcode) {
        getWebDriverWait(5).until(visibilityOf(elementBarcodeInputKey));
        elementBarcodeInputKey.sendKeys(barcode);
        return waitUntilReceiptDatePopulated();
    }


    public String getGeneratedReceiptDate() {
        getWebDriverWait(5).until(attributeToBeNotEmpty(elementReceiptDateInputKey, "value"));
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
                testContext.storeDocumentDetails(barcode, generatedDateString);
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

    public void selectFormType(Form form) {
        String formName = form.getType();
        selectByText(elementFormTypeSelectKey, formName);
        // There is a known issue with Selenium 4 where after using the necessary select method above, the screen
        // does not refresh and hidden company name/number fields are not displayed. The following methods moving
        // the cursor up and down results in the correct form being selected and required fields are displayed.
        elementFormTypeSelectKey.sendKeys(Keys.UP);
        elementFormTypeSelectKey.sendKeys(Keys.DOWN);
        elementFormTypeSelectKey.sendKeys(Keys.TAB);
        //log.info("Successfully selected form: {}", formName);
        getWebDriverWait(5).until(visibilityOf(elementCompanyNumberInputKey));
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

    public ProcessStartOfDocumentPage setCompanyNumberField(String companyNumber) {
        elementCompanyNumberInputKey.sendKeys(companyNumber);
        return this;
    }

    public ProcessStartOfDocumentPage setCheckCharactersPrefixField(final String checkPrefix) {
        elementCheckCharactersPrefixInputKey.sendKeys(checkPrefix);
        return this;
    }

    public ProcessStartOfDocumentPage setCheckCharactersSuffixField(final String checkSuffix) {
        elementCheckCharactersSuffixInputKey.sendKeys(checkSuffix);
        return this;
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
        try {
            getWebDriverWait(5).until(visibilityOf(elementBarcodeInputKey));
            log.info("Process start of document page displayed successfully.");
        } catch (NoSuchElementException exception) {
            log.error("Process start of document page was not displayed.");
            throw new NoSuchElementException("Process start of document page "
                    + "was not displayed", exception);
        }
        return this;
    }


}
