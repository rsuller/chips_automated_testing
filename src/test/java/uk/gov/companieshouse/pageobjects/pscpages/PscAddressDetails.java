package uk.gov.companieshouse.pageobjects.pscpages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import uk.gov.companieshouse.pageobjects.ChipsCommonPage;
import uk.gov.companieshouse.testdata.datamodel.Address;
import uk.gov.companieshouse.utils.TestContext;

public class PscAddressDetails extends ChipsCommonPage<PscAddressDetails> {

    @FindBy(how = How.CSS, using = "input[id$='address:houseNumber:field']")
    // TODO Fix this as not sure the CSS selector works
    // form1:residentialAddressTabSubView:residentialAddress:address:houseNumber:field
    private WebElement houseNameOrNumber;

    @FindBy(how = How.ID, using =
            "form1:personOfControlDetailsTabSubView:serviceAddress:address:postcodefg:postcode:field")
    private WebElement postcode;

    @FindBy(how = How.LINK_TEXT, using = "lookup")
    private WebElement lookup;

    @FindBy(how = How.ID, using = "form1:personOfControlDetailsTabSubView:serviceAddress:address:street:field")
    private WebElement street;

    @FindBy(how = How.ID, using = "form1:personOfControlDetailsTabSubView:serviceAddress:address:town:field")
    private WebElement postTown;

    @FindBy(how = How.ID, using = "form1:personOfControlDetailsTabSubView:serviceAddress:address:county:field")
    private WebElement county;

    @FindBy(how = How.ID, using = "form1:personOfControlDetailsTabSubView:serviceAddress:address:country:field")
    private WebElement country;

    TestContext testContext;

    /**
     * Required constructor for class.
     *
     * @param testContext required for shared state.
     */
    public PscAddressDetails(TestContext testContext) {
        super(testContext);
        this.testContext = testContext;
        PageFactory.initElements(testContext.getWebDriver(), this);
    }

    public void enterHouseNameOrNumber(String houseNameOrNumberText) {
        houseNameOrNumber.sendKeys(houseNameOrNumberText);
    }

    public void enterStreet(String streetText) {
        street.sendKeys(streetText);
    }

    public void enterPostCode(String postcodeText) {
        postcode.sendKeys(postcodeText);
    }

    private void enterRegion(String regionText) {
        county.sendKeys(regionText);
    }

    private void selectCountry(String countryText) {
        selectByText(country, countryText, true);
    }

    protected void enterAddress(Address address) {
        if (address.getHouseNumber() != null && address.getPostcode() != null) {
            enterHouseNameOrNumber(address.getHouseNumber());
            enterPostCode(address.getPostcode());
            lookup.click();
        } else {
            enterHouseNameOrNumber(address.getHouseNumber());
            enterStreet(address.getStreet());
            enterRegion(address.getRegion());
        }
        selectCountry(address.getCountry());
    }
}
