package uk.gov.companieshouse.pageobjects.pscpages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import uk.gov.companieshouse.testdata.datamodel.PersonOfSignificantControl;
import uk.gov.companieshouse.utils.TestContext;

public class PscUraTab extends PscAddressDetails {

    TestContext testContext;
    @FindBy(how = How.ID, using = "form1:residentialAddressTabSubView:psc_birthDate:field")
    private WebElement dateOfBirthField;
    @FindBy(how = How.LINK_TEXT, using = "Residential Address")
    private WebElement residentialAddressLink;
    @FindBy(how = How.ID, using =
            "form1:residentialAddressTabSubView:psc_residentialAddress_isResidentialAddressSA:field")
    private WebElement uraSameAsServiceAddress;

    public PscUraTab(TestContext testContext) {
        super(testContext);
        this.testContext = testContext;
        PageFactory.initElements(testContext.getWebDriver(), this);
    }

    public void enterUraDetails(PersonOfSignificantControl psc) {
        residentialAddressLink.click();
        enterDateOfBirth(psc.getDateOfBirth());
        selectUraAsServiceAddress();
        //enterAddress(psc.getAddress());
        enterUraBarcode();
    }

    private void selectUraAsServiceAddress() {
        uraSameAsServiceAddress.click();
    }

    private void enterDateOfBirth(String dateOfBirth) {
        waitUntilElementDisplayed(dateOfBirthField);
        dateOfBirthField.sendKeys(dateOfBirth);
    }
}
