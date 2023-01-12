package uk.gov.companieshouse.pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.utils.ElementInteraction;
import uk.gov.companieshouse.utils.TestContext;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class ChangeToRoPage extends ElementInteraction {

    public TestContext testContext;
    public static final Logger log = LoggerFactory.getLogger(ChangeToRoPage.class);

    public ChangeToRoPage(TestContext testContext) {
        super(testContext);
        this.testContext = testContext;
        PageFactory.initElements(testContext.getWebDriver(),this);
    }

    @FindBy(how = How.ID, using = "form1:changeOfAddressScreenTabSheet:changeOfAddressScreen:registeredOffice:"
            + "address:houseNumber:field")
    private WebElement elementRoHouseNumber;
    @FindBy(how = How.ID, using ="form1:changeOfAddressScreenTabSheet:changeOfAddressScreen:registeredOffice:"
            + "address:postcodefg:postcode:field")
    private WebElement elementRoPostCode;
    @FindBy(how = How.ID, using = "form1:changeOfAddressScreenTabSheet:changeOfAddressScreen:registeredOffice:"
            + "address:postcodefg:postcodeLookup")
    private WebElement elementRoLookUp;
    @FindBy(how = How.ID, using ="form1:changeOfAddressScreenTabSheet:changeOfAddressScreen:registeredOffice:"
            + "address:street:field")
    private WebElement elementRoStreet;

    @FindBy(how = How.ID, using = "form1:task_submit")
    private WebElement elementSave;


    public ChangeToRoPage waitUntilAd01Displayed() {
        getWebDriverWait(10).until(visibilityOf(elementRoHouseNumber));
        return this;
    }

    public ChangeToRoPage waitUntilStreetPopulated() {
        getWebDriverWait(5).until(attributeToBeNotEmpty(elementRoStreet, "value"));
        log.info("Postcode lookup completed successfully for address");
        return this;
    }

    public ChangeToRoPage enterHouseNumber(String string) {
        elementRoHouseNumber.sendKeys(string);
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

    public ChangeToRoPage saveForm() {
        log.info("Submitting form...");
        elementSave.click();
        return this;
    }

}

