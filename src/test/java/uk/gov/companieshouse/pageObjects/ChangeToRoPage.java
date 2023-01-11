package uk.gov.companieshouse.pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import uk.gov.companieshouse.utils.ElementInteraction;
import uk.gov.companieshouse.utils.TestContext;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class ChangeToRoPage extends ElementInteraction {

    public TestContext testContext;

    public ChangeToRoPage(TestContext testContext) {
        super(testContext);
        this.testContext = testContext;
        PageFactory.initElements(testContext.getWebDriver(),this);
    }

    @FindBy(how = How.ID, using = "form1:changeOfAddressScreenTabSheet:changeOfAddressScreen:registeredOffice:"
            + "address:houseNumber:field")
    private WebElement elementRoHouseNumber;
    @FindBy(how = How.ID, using ="form1:changeOfAddressScreenTabSheet:changeOfAddressScreen"
            + ":registeredOffice:address:country:field")
    private WebElement elementAd01Country;
    @FindBy(how = How.ID, using ="form1:changeOfAddressScreenTabSheet:changeOfAddressScreen"
            + ":registeredOffice:address:country:other:field")
    private WebElement elementAd01InvalidCountry;
    @FindBy(how = How.ID, using ="form1:changeOfAddressScreenTabSheet:changeOfAddressScreen:registeredOffice:"
            + "address:postcodefg:postcode:field")
    private WebElement elementRoPostCode;
    @FindBy(how = How.ID, using = "form1:changeOfAddressScreenTabSheet:changeOfAddressScreen:registeredOffice:"
            + "address:postcodefg:postcodeLookup")
    private WebElement elementRoLookUp;
    @FindBy(how = How.ID, using ="form1:changeOfAddressScreenTabSheet:changeOfAddressScreen:registeredOffice:"
            + "address:street:field")
    private WebElement elementRoStreet;
    @FindBy(how = How.ID, using = "form1:changeOfAddressScreenTabSheet:changeOfAddressScreen:registeredOffice:"
            + "address:country:field")
    private WebElement elementRoCountry;
    @FindBy(how = How.ID, using = "form1:changeOfAddressScreenTabSheet:changeOfAddressScreen:registeredOffice:"
            + "address:careOfFieldGroup:address_careOf:field")
    private WebElement elementCareOfField;
    @FindBy(how = How.ID, using = "form1:changeOfAddressScreenTabSheet:changeOfAddressScreen:registeredOffice:"
            + "address:careOfFieldGroup:address_poBox:field")
    private WebElement elementPoBoxField;
    @FindBy(how = How.ID, using = "form1:doc_corporateBody_registers:0:register_registerSelected:field")
    private WebElement elementRegisterOfMembers;
    @FindBy(how = How.ID, using = "form1:doc_corporateBody_registers:1:register_registerSelected:field")
    private WebElement elementRegisterOfDirectors;

    @FindBy(how = How.ID, using = "form1:task_submit")
    private WebElement elementSave;


    public ChangeToRoPage waitUntilAd01Displayed() {
        getWebDriverWait(10).until(visibilityOf(elementRoHouseNumber));
        return this;
    }

    public ChangeToRoPage waitUntilStreetPopulated() {
        getWebDriverWait(5).until(attributeToBeNotEmpty(elementRoStreet, "value"));
        return this;
    }

    public ChangeToRoPage enterHouseNumber(String string) {
        elementRoHouseNumber.sendKeys(string);
        return this;
    }

    public ChangeToRoPage selectRegisterOfMembers() {
        elementRegisterOfMembers.click();
        return this;
    }

    public ChangeToRoPage selectRegisterOfDirectors() {
        elementRegisterOfDirectors.click();
        return this;
    }

    public ChangeToRoPage enterAd01Country(String value) {
        selectByText(elementAd01Country, value);
        return this;
    }

    /**
     * Enter a country that is not listed in the predefined list.
     *
     * @param country to be entered.
     */
    public ChangeToRoPage enterAd01OtherCountry(String country) {
        selectByText(elementAd01Country, "Other");
        elementAd01InvalidCountry.sendKeys(country);
        return this;
    }

    public ChangeToRoPage enterPostCode(String string) {
        elementRoPostCode.sendKeys(string);
        return this;
    }


    public ChangeToRoPage clickLookup() {
        elementRoLookUp.click();
        return this;
    }

    public ChangeToRoPage enterStreetManually(String string) {
        elementRoStreet.sendKeys(string);
        return this;
    }

    public ChangeToRoPage selectCountry(String string) {
        selectByText(elementRoCountry, string);
        return this;
    }

    public ChangeToRoPage saveForm() {
        elementSave.click();
        return this;
    }

}

