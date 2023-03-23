package uk.gov.companieshouse.pageobjects;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.enums.Form;
import uk.gov.companieshouse.utils.ElementInteraction;
import uk.gov.companieshouse.utils.TestContext;

public class ChipsCommonPage<T extends ChipsCommonPage<?>> extends ElementInteraction {

    private static final String URA_BARCODE = "7777788888";
    TestContext testContext;
    public static final Logger log = LoggerFactory.getLogger(ChipsCommonPage.class);

    /**
     * Required constructor for class.
     */
    public ChipsCommonPage(TestContext testContext) {
        super(testContext);
        this.testContext = testContext;
        PageFactory.initElements(testContext.getWebDriver(),this);
    }

    @FindBy(how = How.ID, using = "form1:task_tabBookTabHeader:2")
    private WebElement residentialAddressLink;
    @FindBy(how = How.ID, using = "form1:residentialAddressTabSubView:officer_residentialAddress_uraBarcode:field")
    private WebElement uraBarcodeInputField;
    @FindBy(how = How.ID, using = "form1:task_save")
    private WebElement globalSaveFormElement;
    @FindBy(how = How.ID, using = "form1:task_submit")
    private WebElement globalSubmitFormElement;


    /**
     * Enter the URA barcode.
     */
    public T enterUraBarcode() {
        residentialAddressLink.click();
        uraBarcodeInputField.sendKeys(URA_BARCODE);
        return (T) this;
    }

    @FindBy(how = How.CSS, using = "div[class='subheader'] > span[class='subtitle']")
    private WebElement elementPageSubTitle;

    /**
     * Common method to wait until the form tiutle is displayed before continuing with test.
     * @param form the form title to expect.
     */
    public T waitUntilFormDisplayed(Form form) {
        String pageSubTitle = form.getTitle();
        waitForSpecificTextInElement(elementPageSubTitle, pageSubTitle);
        return (T) this;
    }

    /**
     * Common method for saving a form using one of two selectors. Try using one global selector if that isn't
     * present use the other one.
     */
    public void saveForm() {
        try {
            globalSaveFormElement.click();
        } catch (NoSuchElementException exception) {
            globalSubmitFormElement.click();
        }
        log.info("Submitting form...");
    }
}
