package uk.gov.companieshouse.pageobjects;

import java.util.List;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.data.datamodel.Director;
import uk.gov.companieshouse.enums.Forms.Form;
import uk.gov.companieshouse.utils.ElementInteraction;
import uk.gov.companieshouse.utils.TestContext;




public class AppointDirectorPage extends ElementInteraction {

    public TestContext testContext;
    public static final Logger log = LoggerFactory.getLogger(AppointDirectorPage.class);

    public AppointDirectorPage(TestContext testContext) {
        super(testContext);
        this.testContext = testContext;
        PageFactory.initElements(testContext.getWebDriver(),this);
    }


    @FindBy(how = How.ID, using = "form1:task_submit")
    private WebElement elementSave;
    @FindBy(how = How.CSS, using = "div[class='subheader'] > span[class='subtitle']")
    private WebElement elementPageSubTitle;

    public AppointDirectorPage waitUntilFormDisplayed(String pageSubTitle) {
        getWebDriverWait(10).until(ExpectedConditions.textToBePresentInElement(
                elementPageSubTitle, pageSubTitle));
        return this;
    }

    /**
     * Enter form details with default information and multiple nationalities.
     */
    public AppointDirectorPage enterMandatoryFormDetails(Director director, Form form) {
        completeAppointmentDetailsTab(director, form);
        completeResidentialAddressTab(director);
        memory.commit(KeysCh.newOfficerSurname, director.getSurname());
        memory.commit(KeysCh.officerNationality, director.getNationality());
        appointDirectorPage.clickSave();
        return this;
    }

    private AppointDirectorPage completeAppointmentDetailsTab(Director director, Form form) {
        appointDirectorPage
                .enterDateOfAppointment(director.getDateOfAppointment())
                .enterPartialDob(director.getPartialDateOfBirth())
                .enterTitle(director.getTitle())
                .enterTitleOther(director.getTitleOther())
                .enterForename(director.getForename())
                .enterMiddleName(director.getMiddleNames())
                .enterSurname(director.getSurname())
                .enterHouseNumber(director.getAddress().getHouseNumber())
                .enterPostCode(director.getAddress().getPostcode())
                .clickLookup()
                .selectSaCountry(director.getAddress().getCountry())
                .enterCountryUsuallyResident(director.getCountryUsuallyResident());
        if (Form.LLAP01 != form) {
            appointDirectorPage.enterNationality(director.getNationality())
                    .enterBusinessOccupation(director.getOccupation());
        }
        return this;
    }

    private AppointDirectorPage completeResidentialAddressTab(Director director) {
        appointDirectorPage
                .clickResidentialAddressTab()
                .enterDateOfBirth(director.getDobField())
                .enterRoHouseNumber(director.getResidentialAddress().getHouseNumber())
                .enterRoPostcode(director.getResidentialAddress().getPostcode())
                .clickRoLookup()
                .selectRoCountry(director.getResidentialAddress().getCountry());
        if (director.getUraBarcodeRequired()) {
            appointDirectorPage.enterRoUra(director.getUraBarcode());
        }
        return this;
    }

    /**
     * Enter nationalities. Can be single or multiple.
     */
    private AppointDirectorPage enterNationality(List<String> nationalities) {
        for (String nationality : nationalities) {
            selectByVisibleText(elementNationality, nationality);
        }
        return this;
    }

    /**
     * Selects the country from a pre-populated list.
     *
     * @param country country to be selected. If country is not in list it will enter in
     *                other country field.
     */
    private AppointDirectorPage selectSaCountry(String country) {
        try {
            selectByVisibleText(elementSaCountry, country);
        } catch (NoSuchElementException ex) {
            // If country is not in list, enter the country in other country field
            enterOtherSaCountry(country);
        }
        return this;
    }

    private AppointDirectorPage enterOtherSaCountry(String country) {
        return type(elementInvalidSaCountry, country);
    }

    private AppointDirectorPage enterBusinessOccupation(String string) {
        return type(elementBusinessOccupation, string);
    }

    private AppointDirectorPage enterCountryUsuallyResident(String string) {
        return type(elementCountryUsually, string);
    }

    private AppointDirectorPage enterInvalidCountryUsuallyResident(String country) {
        return type(elementCountryUsually, country);
    }

    private AppointDirectorPage clickResidentialAddressTab() {
        return click(elementRoTab);
    }

    private AppointDirectorPage enterPartialDob(String string) {
        return type(elementPartialDob, string);
    }

    private AppointDirectorPage enterDateOfBirth(DateVariant date) {
        return typeDate(elementRoDob, date, ChipsDateFormat.ddMMyyyy);
    }

    private AppointDirectorPage enterRoHouseNumber(String string) {
        return type(elementRoHouseNumber, string);
    }

    private AppointDirectorPage enterRoPostcode(String string) {
        return type(elementRoPostCode, string);
    }

    private AppointDirectorPage clickRoLookup() {
        return click(elementRoLookup);
    }

    /**
     * Selects the country from a pre-populated list.
     *
     * @param country country to be selected. If country is not in list it will enter in
     *                other country field.
     */
    private AppointDirectorPage selectRoCountry(String country) {
        try {
            selectByVisibleText(elementRoCountry, country);
        } catch (NoSuchElementException ex) {
            // If country is not in list, enter the country in other country field
            enterOtherUraCountry(country);
        }
        return this;
    }

    private AppointDirectorPage enterOtherUraCountry(String country) {
        return type(elementInvalidUraCountry, country);
    }

    private AppointDirectorPage enterRoUra(String string) {
        return type(elementRoUra, string);
    }


}

