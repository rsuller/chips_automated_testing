package uk.gov.companieshouse.pageobjects;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.enums.Form;
import uk.gov.companieshouse.utils.ElementInteraction;
import uk.gov.companieshouse.utils.TestContext;


@SuppressWarnings("unchecked")
public class ChipsCommonPage<T extends ChipsCommonPage<?>> extends ElementInteraction {

    private static final String URA_BARCODE = "7777788888";
    TestContext testContext;
    public static final Logger log = LoggerFactory.getLogger(ChipsCommonPage.class);

    /**
     * Required constructor for class.
     */
    public ChipsCommonPage(TestContext testContext) {
        super(testContext);
        this.testContext = testContext;
        PageFactory.initElements(testContext.getWebDriver(),this);
    }

    @FindBy(how = How.ID, using = "form1:task_save")
    private WebElement globalSaveFormElement;
    @FindBy(how = How.ID, using = "form1:task_submit")
    private WebElement globalSubmitFormElement;
    @FindBy(how = How.CSS, using = "[id='form1:viewCorporateBodyAppointments:corporateAppointments:"
            + "corporateBodyAppointments_corporateAppointmentList'] > tbody > tr")
    private List<WebElement> elementCorporateOfficerTable;
    @FindBy(how = How.ID, using = "form1:task_viewCorporateAppointments")
    private WebElement viewAllAppointmentsLink;
    @FindBy(how = How.ID, using = "form1:task_appointments:0:surname")
    private WebElement surnameTableElement;


    @FindBy(how = How.CSS, using = "div[class='subheader'] > span[class='subtitle']")
    private WebElement elementPageSubTitle;

    /**
     * Common method to wait until the form tiutle is displayed before continuing with test.
     * @param form the form title to expect.
     */
    public T waitUntilFormDisplayed(Form form) {
        String pageSubTitle = form.getTitle();
        waitForSpecificTextInElement(elementPageSubTitle, pageSubTitle);
        return (T) this;
    }

    /**
     * Common method for saving a form using one of two selectors. Try using one global selector if that isn't
     * present use the other one.
     */
    public void saveForm() {
        try {
            globalSaveFormElement.click();
        } catch (NoSuchElementException exception) {
            globalSubmitFormElement.click();
        }
        log.info("Submitting form...");
    }

    /**
     * Select an active corporate officer appointment from the table.
     * @param officerType the type of officer to select.
     */
    public void selectActiveCorporateDirector(String officerType) {
        String expectedOfficerType;
        String expectedAppointmentType;

        if ("corporate director".equals(officerType)) {
            expectedOfficerType = "Corporate Officer";
            expectedAppointmentType = "DIRECTOR";
        } else {
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
     * Wait for the form to be loaded and click the first officer in the appointments table.
     */
    public void selectFirstOfficer(String formType) {
        waitUntilFormDisplayed(Form.getFormByType(formType));
        // Select the officer and wait until fields populated before continuing
        surnameTableElement.click();
        log.info("Selected Officer Surname: {}", surnameTableElement.getText());
    }

    private String returnOfficerType(WebElement officer) {
        WebElement officerType = officer.findElement(
                By.cssSelector("[id^='" + officer.getAttribute("id")
                        + ":officerHeaderCorporateImageView']"));
        return officerType.getAttribute("title");
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

}
