package uk.gov.companieshouse.pageobjects.companyaddresses;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.pageobjects.ChipsCommonPage;
import uk.gov.companieshouse.utils.TestContext;


public class ChangeToRoPage extends ChipsCommonPage<ChangeToRoPage> implements AddressPageInterface {

    public final TestContext testContext;
    public static final Logger log = LoggerFactory.getLogger(ChangeToRoPage.class);

    /**
     * Required constructor for class.
     */
    public ChangeToRoPage(TestContext testContext) {
        super(testContext);
        this.testContext = testContext;
        PageFactory.initElements(testContext.getWebDriver(),this);
    }

    @FindBy(how = How.ID, using = "form1:changeOfAddressScreenTabSheet:changeOfAddressScreen:registeredOffice:"
            + "address:houseNumber:field")
    private WebElement elementRoHouseNumber;
    @FindBy(how = How.ID, using = "form1:changeOfAddressScreenTabSheet:changeOfAddressScreen:registeredOffice:"
            + "address:postcodefg:postcode:field")
    private WebElement elementRoPostCode;
    @FindBy(how = How.ID, using = "form1:changeOfAddressScreenTabSheet:changeOfAddressScreen:registeredOffice:"
            + "address:postcodefg:postcodeLookup")
    private WebElement elementRoLookUp;
    @FindBy(how = How.ID, using = "form1:changeOfAddressScreenTabSheet:changeOfAddressScreen:registeredOffice:"
            + "address:street:field")
    private WebElement elementRoStreet;


    /**
     * Wait until the address fields are populated after postcode lookup.
     */
    @Override
    public ChangeToRoPage waitUntilStreetPopulated() {
        waitElementTextNotEmpty(elementRoStreet);
        log.info("Postcode lookup completed successfully for address");
        return this;
    }


    @Override
    public ChangeToRoPage enterHouseNumber(String string) {
        elementRoHouseNumber.sendKeys(string);
        return this;
    }

    @Override
    public ChangeToRoPage enterPostCode(String string) {
        elementRoPostCode.sendKeys(string);
        return this;
    }

    @Override
    public AddressPageInterface clickLookUp() {
        elementRoLookUp.click();
        return this;
    }

}

