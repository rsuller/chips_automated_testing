package uk.gov.companieshouse.pageobjects;

import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.enums.Forms;
import uk.gov.companieshouse.utils.DateFormat;
import uk.gov.companieshouse.utils.TestContext;

public class ChangeOfficerPage extends ChipsCommonPage<ChangeOfficerPage> {
    TestContext testContext;
    public static final Logger log = LoggerFactory.getLogger(ChangeOfficerPage.class);

    /**
     * Required constructor for class.
     */
    public ChangeOfficerPage(TestContext testContext) {
        super(testContext);
        this.testContext = testContext;
        PageFactory.initElements(testContext.getWebDriver(), this);
    }

    @FindBy(how = How.ID, using = "form1:ukSubView:ukDetails_nonUk_registrationNumber:field")
    private WebElement elementNonUkRegistrationNumber;

    @FindBy(how = How.ID, using = "form1:ukSubView:ukDetails_nonUk_registerLocation:field")
    private WebElement elementNonUkRegisterLocation;

    @FindBy(how = How.ID, using = "form1:ukSubView:ukDetails_nonUk_governingLaw:field")
    private WebElement elementNonUkLawGoverned;

    @FindBy(how = How.ID, using = "form1:ukSubView:ukDetails_nonUk_legalForm:field")
    private WebElement elementNonUkLegalForm;

    @FindBy(how = How.ID, using = "form1:ukSubView:ukDetails_uk_registrationNumber:field")
    private WebElement elementUkRegistrationNumber;

    @FindBy(how = How.ID, using = "form1:task_tabBookTabHeader:1")
    private WebElement corporateInfoTab;

    @FindBy(how = How.CSS, using = "[id='form1:viewCorporateBodyAppointments:corporateAppointments:"
            + "corporateBodyAppointments_corporateAppointmentList'] > tbody > tr")
    private List<WebElement> elementCorporateOfficerTable;

    @FindBy(how = How.CSS, using = "[id^='form1:viewCorporateBodyAppointments:corporateAppointments:"
            + "corporateBodyAppointments_corporateAppointmentList']")
    private WebElement appointmentTypeColumn;

    @FindBy(how = How.CSS, using = "[id='form1:viewCorporateBodyAppointments:corporateAppointments:"
            + "corporateBodyAppointments_corporateAppointmentList'] > tbody > tr")
    private List<WebElement> corporateTableList;

    @FindBy(how = How.ID, using = "form1:task_viewCorporateAppointments")
    private WebElement viewAllAppointmentsLink;

    @FindBy(how = How.ID, using = "form1:changeOfficerDetailsTabSubView:appointmentChangeModifiedOfficer_officer_actionDate:field")
    private WebElement dateField;
    @FindBy(how = How.ID, using = "form1:changeOfficerDetailsTabSubView:forenames:appointmentChangeModifiedOfficer_"
            + "officer_personName_middlenames:field")
    private WebElement middleNameField;
    @FindBy(how = How.ID, using = "form1:task_appointments:0:surname")
    private WebElement surnameTableElement;
    @FindBy(how = How.ID, using = "form1:changeOfficerDetailsTabSubView:appointmentChangeModifiedOfficer_officer"
            + "_businessOccupation:field")
    private WebElement businessOccupationField;

    /**
     * Wait for the form to be loaded and click the first officer in the appointments table.
     */
    public void selectFirstOfficer() {
        waitUntilFormDisplayed(Forms.Form.CH01);
        // Select the officer and wait until fields populated before continuing
        surnameTableElement.click();
        waitElementTextNotEmpty(businessOccupationField);
        log.info("Selected Officer Surname: {}", surnameTableElement.getText());
    }

    /**
     * Enter the details of the director that need changing.
     * @param typeOfChange the particular detail to change.
     */
    public void directorDetailToChange(String typeOfChange) {
        enterChangeDateAsToday();

        // Appreciate this doesn't need to be a switch case right now, but leaving it in case of more examples required.
        switch (typeOfChange) {
            case "middle name":
                if (!middleNameField.getAttribute("value").isEmpty()) {
                    clearField(middleNameField);
                }
                // Currently this name is not causing an issue but may need to review at another time
                middleNameField.sendKeys("Sam");
                break;
            default:
                throw new RuntimeException("There is no option for " + typeOfChange + " please amend.");
        }
        saveForm();
    }

    private void enterChangeDateAsToday() {
        waitUntilElementDisplayed(dateField);
        dateField.sendKeys(DateFormat.getDateAsString(new Date()));
    }

    /**
     * Select an active corporate officer appointment from the table.
     * @param officerType the ype of officer to select.
     */
    public void selectActiveCorporateDirector(String officerType) {
        String expectedOfficerType;
        String expectedAppointmentType;

        switch (officerType) {
            case "corporate director":
                expectedOfficerType = "Corporate Officer";
                expectedAppointmentType = "DIRECTOR";
                break;
            default:
                throw new RuntimeException("There is no known option for: " + officerType);
        }

        viewAllAppointmentsLink.click();

        List<WebElement> corporateOfficerTable = elementCorporateOfficerTable;

        for (WebElement officer : corporateOfficerTable) {
            if (returnOfficerType(officer).equals(expectedOfficerType)
                    && returnAppointmentType(officer).equals(expectedAppointmentType)) {
                doubleClick(officer);
                break;
            }
        }

    }

    /**
     * Get the appointment type from the table.
     */
    private String returnAppointmentType(WebElement officer) {
        WebElement officerType = officer.findElement(
                By.cssSelector("[id^='" + officer.getAttribute("id")
                        + ":_id']"));
        return officerType.getText();
    }

    private String returnOfficerType(WebElement officer) {
        WebElement officerType = officer.findElement(
                By.cssSelector("[id^='" + officer.getAttribute("id")
                        + ":officerHeaderCorporateImageView']"));
        return officerType.getAttribute("title");
    }

    /**
     * Change the UL registration number of the corporate officer.
     */
    public void changeDetailOfCorporateOfficer() {
        enterChangeDateAsToday();
        corporateInfoTab.click();
        clearCorporateInfoFields();
        elementUkRegistrationNumber.sendKeys("49874532");
        saveForm();
    }

    private void clearCorporateInfoFields() {
        clearField(elementUkRegistrationNumber);
        clearField(elementNonUkLegalForm);
        clearField(elementNonUkLawGoverned);
        clearField(elementNonUkRegisterLocation);
        clearField(elementNonUkRegistrationNumber);
    }
}
