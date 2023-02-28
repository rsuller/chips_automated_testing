package uk.gov.companieshouse.pageobjects;

import java.util.Date;
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

    public ChangeOfficerPage(TestContext testContext) {
        super(testContext);
        this.testContext = testContext;
        PageFactory.initElements(testContext.getWebDriver(), this);
    }

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

    public void selectFirstOfficer() {
        waitUntilFormDisplayed(Forms.Form.CH01);
        // Select the officer and wait until fields populated before continuing
        surnameTableElement.click();
        waitElementTextNotEmpty(businessOccupationField);
        log.info("Selected Officer Surname: {}", surnameTableElement.getText());
    }

    public void directorDetailToChange(String typeOfChange) {
        dateField.sendKeys(DateFormat.getDateAsString(new Date()));

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
        // Enter URA barcode as mandatory field
        enterUraBarcode();
        saveForm();
    }

}
