package uk.gov.companieshouse.pageobjects.pscpages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import uk.gov.companieshouse.testdata.datamodel.PersonOfSignificantControl;
import uk.gov.companieshouse.utils.DateFormat;
import uk.gov.companieshouse.utils.TestContext;

public class PscDetailsTab extends PscAddressDetails {

    @FindBy(how = How.ID, using = "form1:personOfControlDetailsTabSubView:psc_actionDate:field")
    private WebElement dateOfNotification;

    @FindBy(how = How.ID, using = "form1:personOfControlDetailsTabSubView:psc_personName_title:field")
    private WebElement title;

    @FindBy(how = How.ID, using = "form1:personOfControlDetailsTabSubView:psc_personName_forename:field")
    private WebElement forename;

    @FindBy(how = How.ID, using = "form1:personOfControlDetailsTabSubView:psc_personName_middlenames:field")
    private WebElement middleName;

    @FindBy(how = How.ID, using = "form1:personOfControlDetailsTabSubView:psc_personName_surname:field")
    private WebElement surname;

    @FindBy(how = How.ID, using = "form1:personOfControlDetailsTabSubView:psc_usualResidence:field")
    private WebElement countryStateUsualResident;

    @FindBy(how = How.ID, using = "form1:personOfControlDetailsTabSubView:nationalitiesDropDownId:field")
    private WebElement nationality;

    @FindBy(how = How.ID, using =
            "form1:personOfControlDetailsTabSubView:task_personOfControlDetails_partialBirthDate:field")
    private WebElement partialDob;

    @FindBy(how = How.ID, using = "form1:personOfControlDetailsTabSubView:secureIndicator:field")
    private WebElement section790Exception;

    @FindBy(how = How.ID, using = "form1:personOfControlDetailsTabSubView:psc_serviceAddress_isServiceAddressROA:field")
    private WebElement serviceAddressAlwaysRo;

    @FindBy(how = How.ID, using = "form1:personOfControlDetailsTabSubView:foreignAddressIndicator:field")
    private WebElement foreignAddress;

    @FindBy(how = How.ID, using =
            "form1:personOfControlDetailsTabSubView:serviceAddress:address:careOfFieldGroup:address_careOf:field")
    private WebElement careOf;

    @FindBy(how = How.ID, using =
            "form1:personOfControlDetailsTabSubView:serviceAddress:address:careOfFieldGroup:address_poBox:field")
    private WebElement poBox;

    @FindBy(how = How.ID,
            using = "form1:residentialAddressTabSubView:psc_residentialAddress_isResidentialAddressSA:field")
    private WebElement serviceAddressSameAsRoCheckBox;
    TestContext testContext;

    public PscDetailsTab(TestContext testContext) {
        super(testContext);
        this.testContext = testContext;
        PageFactory.initElements(testContext.getWebDriver(), this);
    }
    public void enterDateOfNotification(String date) {
        dateOfNotification.sendKeys(date);
    }

    public void selectTitle(String titleText) {
        selectByText(title, titleText);
    }

    public void enterForename(String forenameText) {
        forename.sendKeys(forenameText);
    }

    public void enterMiddleName(String middleNameText) {
        middleName.sendKeys(middleNameText);
    }

    public void enterSurname(String surnameText) {
        surname.sendKeys(surnameText);
    }

    public void enterCountryStateUsualResident(String countryStateUsualResidentText) {
        countryStateUsualResident.sendKeys(countryStateUsualResidentText);
    }

    public void selectNationality(String nationalityText) {
        selectByText(nationality, nationalityText);
    }

    public void enterPartialDob(String partialDobText) {
        partialDob.sendKeys(partialDobText);
    }

    public void selectSection790Exception(boolean section790ExceptionOption) {
        if (section790ExceptionOption) {
            section790Exception.click();
        }
    }

    public void enterCareOf(String careOfText) {
        careOf.sendKeys(careOfText);
    }

    public void enterPoBox(String poBoxText) {
        poBox.sendKeys(poBoxText);
    }

    public void selectServiceAddressAlwaysRo(boolean serviceAddressAlwaysRoOption) {
        if (serviceAddressAlwaysRoOption) {
            serviceAddressAlwaysRo.click();
        }
    }

    public void selectForeignAddress(boolean foreignAddressOption) {
        if (foreignAddressOption) {
            foreignAddress.click();
        }
    }

    private void enterServiceAddress(PersonOfSignificantControl personOfSignificantControl) {
        if (personOfSignificantControl.getServiceAddressSameAsRo()) {
            // Click same as check box
            serviceAddressSameAsRoCheckBox.click();
        } else {
            // Lazy to enter no detail in these fields, but we will see errors if there are changes to UI
            enterCareOf("");
            enterPoBox("");
            enterAddress(personOfSignificantControl.getAddress());
        }
    }

    public void enterPscDetails(PersonOfSignificantControl personOfSignificantControl) {
        enterDateOfNotification(DateFormat.getDateAsString(personOfSignificantControl.getDateOfNotification()));
        selectTitle(personOfSignificantControl.getTitle());
        enterForename(personOfSignificantControl.getForename());
        enterMiddleName(personOfSignificantControl.getMiddleName());
        enterSurname(personOfSignificantControl.getSurname());
        enterCountryStateUsualResident(personOfSignificantControl.getCountryOfUsualResidence());
        selectNationality(personOfSignificantControl.getNationality());
        enterPartialDob(personOfSignificantControl.getDateOfBirth());
        selectSection790Exception(personOfSignificantControl.getZfException());
        selectForeignAddress(personOfSignificantControl.getForeignAddress());
        enterServiceAddress(personOfSignificantControl);
    }


}

