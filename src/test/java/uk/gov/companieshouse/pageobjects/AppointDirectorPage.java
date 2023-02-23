package uk.gov.companieshouse.pageobjects;

import static org.openqa.selenium.support.ui.ExpectedConditions.attributeToBeNotEmpty;

import java.util.Date;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.data.datamodel.Director;
import uk.gov.companieshouse.utils.DateFormat;
import uk.gov.companieshouse.utils.ElementInteraction;
import uk.gov.companieshouse.utils.TestContext;


public class AppointDirectorPage extends ElementInteraction {

    public TestContext testContext;
    public static final Logger log = LoggerFactory.getLogger(AppointDirectorPage.class);

    public AppointDirectorPage(TestContext testContext) {
        super(testContext);
        this.testContext = testContext;
        PageFactory.initElements(testContext.getWebDriver(), this);
    }

    @FindBy(how = How.ID, using = "form1:task_save")
    private WebElement elementSave;
    @FindBy(how = How.CSS, using = "div[class='subheader'] > span[class='subtitle']")
    private WebElement elementPageSubTitle;
    @FindBy(how = How.ID, using = "form1:officerDetailsTabSubView:officer_actionDate:field")
    private WebElement elementDateOfAppointment;
    @FindBy(how = How.ID, using = "form1:officerDetailsTabSubView:doc_corporateBody_appointments_"
            + "officers0_personName_title:field")
    private WebElement elementTitle;
    @FindBy(how = How.ID, using = "form1:officerDetailsTabSubView:partialBirthDate:field")
    private WebElement elementPartialDob;
    @FindBy(how = How.ID, using = "form1:officerDetailsTabSubView:doc_corporateBody_appointments_"
            + "officers0_personName_title:other:field")
    private WebElement elementTitleOther;
    @FindBy(how = How.ID, using = "form1:officerDetailsTabSubView:officer_personName_forename:field")
    private WebElement elementForename;
    @FindBy(how = How.ID, using = "form1:officerDetailsTabSubView:officer_personName_middlenames:field")
    private WebElement elementMiddlename;
    @FindBy(how = How.ID, using = "form1:officerDetailsTabSubView:officer_personName_surname:field")
    private WebElement elementSurname;
    @FindBy(how = How.ID, using = "form1:officerDetailsTabSubView:serviceAddress:address:houseNumber:field")
    private WebElement elementHouseNumber;
    @FindBy(how = How.ID, using = "form1:officerDetailsTabSubView:serviceAddress:address:postcodefg:postcode:field")
    private WebElement elementPostcode;
    @FindBy(how = How.ID, using = "form1:officerDetailsTabSubView:serviceAddress:address:postcodefg:postcodeLookup")
    private WebElement elementLookup;
    @FindBy(how = How.ID, using = "form1:officerDetailsTabSubView:nationalitiesDropDownId:field")
    private WebElement elementNationality;
    @FindBy(how = How.ID, using = "form1:officerDetailsTabSubView:officer_businessOccupation:field")
    private WebElement elementBusinessOccupation;
    @FindBy(how = How.ID, using = "form1:officerDetailsTabSubView:officer_usualResidence:field")
    private WebElement elementCountryUsually;
    @FindBy(how = How.ID, using = "form1:task_tabBookTabHeader:3")
    private WebElement elementRoTab;
    @FindBy(how = How.ID, using = "form1:residentialAddressTabSubView:task_birthDate:field")
    private WebElement elementRoDob;
    @FindBy(how = How.ID, using = "form1:residentialAddressTabSubView:residentialAddress:address:houseNumber:field")
    private WebElement elementRoHouseNumber;
    @FindBy(how = How.ID, using = "form1:residentialAddressTabSubView:residentialAddress:address:"
            + "postcodefg:postcode:field")
    private WebElement elementRoPostCode;
    @FindBy(how = How.ID, using = "form1:residentialAddressTabSubView:residentialAddress:address:"
            + "postcodefg:postcodeLookup")
    private WebElement elementRoLookup;
    @FindBy(how = How.ID, using = "form1:residentialAddressTabSubView:residentialAddress:address:street:field")
    private WebElement elementRoStreet;
    @FindBy(how = How.ID, using = "form1:residentialAddressTabSubView:residentialAddress:address:country:field")
    private WebElement elementRoCountry;
    @FindBy(how = How.ID, using = "form1:residentialAddressTabSubView:residentialAddress"
            + ":address:country:other:field")
    private WebElement elementInvalidUraCountry;
    @FindBy(how = How.ID, using = "form1:officerDetailsTabSubView:serviceAddress:address:street:field")
    private WebElement elementSaStreet;
    @FindBy(how = How.ID, using = "form1:officerDetailsTabSubView:serviceAddress:address:town:field")
    private WebElement elementSaTown;
    @FindBy(how = How.ID, using = "form1:officerDetailsTabSubView:serviceAddress:address:country:field")
    private WebElement elementSaCountry;
    @FindBy(how = How.ID, using = "form1:officerDetailsTabSubView:officer_personName_surname:field")
    private WebElement elementCompanyName;
    @FindBy(how = How.ID, using = "form1:officerDetailsTabSubView:selectedNationalityList:field")
    private WebElement elementSelectedNationality;


    public AppointDirectorPage waitUntilFormDisplayed(String pageSubTitle) {
        getWebDriverWait(10).until(ExpectedConditions.textToBePresentInElement(
                elementPageSubTitle, pageSubTitle));
        return this;
    }

    /**
     * Enter form details with default information and multiple nationalities.
     */
    public AppointDirectorPage enterMandatoryFormDetails(Director director) {
        completeAppointmentDetailsTab(director);
        completeResidentialAddressTab(director);
        clickSave();
        return this;
    }

    private AppointDirectorPage completeAppointmentDetailsTab(Director director) {
        enterDateOfAppointment(DateFormat.getDateAsString(new Date()))
                .enterPartialDob(director.getPartialDob())
                .selectTitle(director.getTitle())
                .enterForename(director.getForename())
                .enterSurname(director.getSurname())
                .enterSaHouseNumber(director.getServiceAddress().getHouseNumber())
                .enterSaPostCode(director.getServiceAddress().getPostcode())
                .clickSaLookup()
                .enterNationality(director.getNationality())
                .enterBusinessOccupation(director.getOccupation())
                .enterCountryUsuallyResident(director.getCountryUsuallyResident());
        return this;
    }

    private AppointDirectorPage completeResidentialAddressTab(Director director) {
        clickResidentialAddressTab()
                .enterDateOfBirth(director.getDob())
                .enterRoHouseNumber(director.getResidentialAddress().getHouseNumber())
                .enterRoPostcode(director.getResidentialAddress().getPostcode())
                .clickRoLookup()
                .waitUntilRoStreetPopulated()
                .selectRoCountry(director.getResidentialAddress().getCountry());
        return this;
    }

    private AppointDirectorPage enterDateOfAppointment(String appointmentDate) {
        typeText(elementDateOfAppointment, appointmentDate);
        return this;
    }

    private AppointDirectorPage enterPartialDob(String string) {
        typeText(elementPartialDob, string);
        return this;
    }

    private AppointDirectorPage enterForename(String string) {
        typeText(elementForename, string);
        return this;
    }

    private AppointDirectorPage enterSurname(String string) {
        typeText(elementSurname, string);
        return this;
    }

    private AppointDirectorPage enterSaHouseNumber(String string) {
        typeText(elementHouseNumber, string);
        return this;
    }

    private AppointDirectorPage enterSaPostCode(String string) {
        typeText(elementPostcode, string);
        return this;
    }

    private AppointDirectorPage clickSaLookup() {
        elementLookup.click();
        waitUntilSaStreetPopulated();
        return this;
    }

    private AppointDirectorPage waitUntilSaStreetPopulated() {
        getWebDriverWait(5).until(attributeToBeNotEmpty(elementSaStreet, "value"));
        log.info("Postcode lookup completed successfully for service address");
        return this;
    }

    /**
     * Enter nationalities. Can be single or multiple. Has to sendKeys.Enter on the field after selecting using
     * the dropdown due to known issues Selenium 4 where after using the necessary select by text method
     * the screen does not refresh and won't show the selected nationality.
     */
    private AppointDirectorPage enterNationality(List<String> nationalities) {
        for (String nationality : nationalities) {
            selectByText(elementNationality, nationality);
            elementNationality.sendKeys(Keys.ENTER);
        }
        return this;
    }

    private AppointDirectorPage enterBusinessOccupation(String string) {
        typeText(elementBusinessOccupation, string);
        return this;
    }

    private AppointDirectorPage enterCountryUsuallyResident(String string) {
        typeText(elementCountryUsually, string);
        return this;
    }

    private AppointDirectorPage clickResidentialAddressTab() {
        elementRoTab.click();
        return this;
    }

    private AppointDirectorPage selectTitle(String string) {
        selectByText(elementTitle, string);
        return this;
    }

    private AppointDirectorPage enterDateOfBirth(String date) {
        typeText(elementRoDob, date);
        return this;
    }

    private AppointDirectorPage enterRoHouseNumber(String string) {
        typeText(elementRoHouseNumber, string);
        return this;
    }

    private AppointDirectorPage enterRoPostcode(String string) {
        typeText(elementRoPostCode, string);
        return this;
    }

    private AppointDirectorPage clickRoLookup() {
        elementRoLookup.click();
        return this;
    }

    private AppointDirectorPage waitUntilRoStreetPopulated() {
        getWebDriverWait(5).until(attributeToBeNotEmpty(elementRoStreet, "value"));
        log.info("Postcode lookup completed successfully for residential address");
        return this;
    }

    /**
     * Selects the country from a pre-populated list.
     *
     * @param country country to be selected. If country is not in list it will enter in
     *                other country field.
     */
    private AppointDirectorPage selectRoCountry(String country) {
        try {
            selectByText(elementRoCountry, country);
        } catch (NoSuchElementException ex) {
            // If country is not in list, enter the country in other country field
            enterOtherUraCountry(country);
        }
        return this;
    }

    private AppointDirectorPage enterOtherUraCountry(String country) {
        typeText(elementInvalidUraCountry, country);
        return this;
    }

    private AppointDirectorPage clickSave() {
        elementSave.click();
        log.info("Submitting form...");
        return this;
    }

}

