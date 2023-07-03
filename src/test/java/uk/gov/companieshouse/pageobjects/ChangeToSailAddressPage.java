package uk.gov.companieshouse.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import uk.gov.companieshouse.utils.TestContext;

public class ChangeToSailAddressPage extends ChipsCommonPage<ChangeToSailAddressPage> implements AddressPageInterface {

    @FindBy(how = How.ID, using = "form1:changeOfAddressScreenTabSheet:changeOfAddressScreen:sailAddress:address"
            + ":houseNumber:field")
    private WebElement elementRoHouseNumber;
    @FindBy(how = How.ID, using = "form1:changeOfAddressScreenTabSheet:changeOfAddressScreen:sailAddress:"
            + "address:postcodefg:postcode:field")
    private WebElement elementRoPostCode;
    @FindBy(how = How.ID, using = "form1:changeOfAddressScreenTabSheet:changeOfAddressScreen:sailAddress:"
            + "address:postcodefg:postcodeLookup")
    private WebElement elementRoLookUp;
    @FindBy(how = How.ID, using = "form1:changeOfAddressScreenTabSheet:changeOfAddressScreen:sailAddress:address"
            + ":street:field")
    private WebElement elementStreetName;

    /**
     * Required constructor for class.
     *
     * @param testContext common test context
     */
    public ChangeToSailAddressPage(TestContext testContext) {
        super(testContext);
    }

    @Override
    public AddressPageInterface enterHouseNumber(String houseNumber) {
        elementRoHouseNumber.sendKeys(houseNumber);
        return this;
    }

    @Override
    public AddressPageInterface enterPostCode(String postCode) {
        elementRoPostCode.sendKeys(postCode);
        return this;
    }

    @Override
    public AddressPageInterface clickLookUp() {
        elementRoLookUp.click();
        return this;
    }

    @Override
    public AddressPageInterface waitUntilStreetPopulated() {
        waitElementTextNotEmpty(elementStreetName);
        return this;
    }

}
