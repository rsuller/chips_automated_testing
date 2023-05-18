package uk.gov.companieshouse.pageobjects;

import static uk.gov.companieshouse.utils.DateFormat.getDateAsString;

import java.util.Date;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.utils.TestContext;


public class DissolutionPage extends ChipsCommonPage<DissolutionPage> {

    public final TestContext testContext;
    public static final Logger log = LoggerFactory.getLogger(DissolutionPage.class);

    /**
     * Required constructor for class.
     */
    public DissolutionPage(TestContext testContext) {
        super(testContext);
        this.testContext = testContext;
        PageFactory.initElements(testContext.getWebDriver(),this);
    }

    @FindBy(how = How.ID, using = "form1:doc_filingDetails_signDate:field")
    private WebElement elementSignatureDate;
    @FindBy(how = How.ID, using = "form1:address:address:houseNumber:field")
    private WebElement elementHouseNumber;
    @FindBy(how = How.ID, using = "form1:address:address:postcodefg:postcode:field")
    private WebElement elementPostcode;
    @FindBy(how = How.ID, using = "form1:address:address:postcodefg:postcodeLookup")
    private WebElement elementPostcodeLookup;
    @FindBy(how = How.ID, using = "form1:address:address:country:other:field")
    private WebElement elementInvalidDs01Country;
    @FindBy(how = How.ID, using = "form1:doc_filingDetails_presenterDocumentReference:field")
    private WebElement elementPresenterReference;
    @FindBy(how = How.ID, using = "form1:task_pend")
    private WebElement elementPendLink;

    public DissolutionPage enterSignatureDate() {
        typeText(elementSignatureDate, getDateAsString(new Date()));
        return this;
    }

    public DissolutionPage enterHouseNumber(String houseNumber) {
        typeText(elementHouseNumber, houseNumber);
        return this;
    }

    public DissolutionPage enterPostcode(String postcode) {
        typeText(elementPostcode, postcode);
        return  this;
    }

    public DissolutionPage clickPostcodeLookup() {
        elementPostcodeLookup.click();
        return this;
    }

}