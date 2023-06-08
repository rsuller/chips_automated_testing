package uk.gov.companieshouse.pageobjects;

import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.enums.Form;
import uk.gov.companieshouse.testdata.DocumentDetails;
import uk.gov.companieshouse.utils.TestContext;


public class InsolvencyLandingScreen extends ChipsCommonPage<InsolvencyLandingScreen> {

    public final TestContext testContext;
    public final DocumentDetails documentDetails;
    public static final Logger log = LoggerFactory.getLogger(InsolvencyLandingScreen.class);

    /**
     * Required constructor for class.
     */
    public InsolvencyLandingScreen(TestContext testContext, DocumentDetails documentDetails) {
        super(testContext);
        this.testContext = testContext;
        this.documentDetails = documentDetails;
        PageFactory.initElements(testContext.getWebDriver(), this);
    }

    @FindBy(how = How.ID, using = "form1:task_cvlCreateCase")
    private WebElement createCvlCaseLink;
    @FindBy(how = How.ID, using = "form1:task_mvlCreateCase")
    private WebElement createMvlCaseLink;
    @FindBy(how = How.LINK_TEXT, using = "Add to Case")
    private WebElement addToCaseLink;
    @FindBy(how = How.ID, using = "form1:allocateIP:task_unceasedDataModel:"
            + "0:appointment_insolvencyAppointmentEffDate__1:field")
    private WebElement elementIpApptDate;
    @FindBy(how = How.CSS, using = "input[type='radio'][value='pass']")
    private WebElement elementLiveCaseRadioButton;

    @FindBy(how = How.CSS, using = "table[id='form1:viewExistingCases:viewExistingCasesId:"
            + "task_casesDataModel'] tbody tr")
    private List<WebElement> elementCases;
    @FindBy(how = How.ID, using = "form1:task_processStatement")
    private WebElement elementProcessStatementLink;
    @FindBy(how = How.ID, using = "form1:task_cessationIp")
    private WebElement elementCeaseIpLink;
    @FindBy(how = How.ID, using = "form1:task_convertMvlToCvlCase")
    private WebElement elementConvertMvlToCvlLink;

    public InsolvencyLandingScreen waitUntilFormDisplayed() {
        waitUntilFormDisplayed(Form.getFormByType(documentDetails.getFormType()));
        return this;
    }

    /**
     * Click the create insolvency case link.
     */
    public InsolvencyLandingScreen createInsolvencyCase(String caseType) {
            if (caseType.equals("MVL")) {
                createMvlCaseLink.click();
            } else {
                createCvlCaseLink.click();
            }
        return this;
    }

    /**
     * Wait for the IP appointment date field to be displayed then enter the date.
     *
     * @param date the specified data to enter.
     */
    public InsolvencyLandingScreen enterIpApptDate(String date) {
        waitUntilElementDisplayed(elementIpApptDate);
        typeText(elementIpApptDate, date);
        return this;
    }

    /**
     * click live case radio button.
     */
    public InsolvencyLandingScreen clickLiveCaseRadioButton() {
        elementLiveCaseRadioButton.click();
        return this;
    }

    public InsolvencyLandingScreen selectFirstCase() {
        elementCases.get(0).click();
        return this;
    }

    public InsolvencyLandingScreen clickProcessStatementLink() {
        elementProcessStatementLink.click();
        return this;
    }

    public InsolvencyLandingScreen clickCeaseIpLink() {
        elementCeaseIpLink.click();
        return this;
    }

    public InsolvencyLandingScreen clickConvertMvlToCvlLink() {
        elementConvertMvlToCvlLink.click();
        return this;
    }

    public InsolvencyLandingScreen clickAddToCase() {
        addToCaseLink.click();
        return this;
    }

}

