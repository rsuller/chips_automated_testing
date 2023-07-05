package uk.gov.companieshouse.pageobjects.dissolution;

import static uk.gov.companieshouse.utils.DateFormat.getDateAsString;

import java.util.Date;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.pageobjects.AddressPageInterface;
import uk.gov.companieshouse.pageobjects.ChipsCommonPage;
import uk.gov.companieshouse.utils.TestContext;

public class DissolutionPage extends ChipsCommonPage<DissolutionPage> implements AddressPageInterface {

    public final TestContext testContext;
    public static final Logger log = LoggerFactory.getLogger(DissolutionPage.class);

    /**
     * Required constructor for class.
     */
    public DissolutionPage(TestContext testContext) {
        super(testContext);
        this.testContext = testContext;
        PageFactory.initElements(testContext.getWebDriver(), this);
    }

    @FindBy(how = How.ID, using = "form1:doc_filingDetails_signDate:field")
    private WebElement elementSignatureDate;
    @FindBy(how = How.ID, using = "form1:address:address:houseNumber:field")
    private WebElement elementHouseNumber;
    @FindBy(how = How.ID, using = "form1:address:address:street:field")
    private WebElement elementStreetName;
    @FindBy(how = How.ID, using = "form1:address:address:postcodefg:postcode:field")
    private WebElement elementPostcode;
    @FindBy(how = How.ID, using = "form1:address:address:postcodefg:postcodeLookup")
    private WebElement elementPostcodeLookup;

    // TODO: Some of these elements are not used in the test, so they should be removed.
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

    @Override
    public DissolutionPage enterHouseNumber(String houseNumber) {
        typeText(elementHouseNumber, houseNumber);
        return this;
    }

    @Override
    public AddressPageInterface enterPostCode(String postCode) {
        typeText(elementPostcode, postCode);
        return this;
    }

    @Override
    public AddressPageInterface clickLookUp() {
        elementPostcodeLookup.click();
        return this;
    }

    @Override
    public AddressPageInterface waitUntilStreetPopulated() {
        waitElementTextNotEmpty(elementStreetName);
        return this;
    }

}