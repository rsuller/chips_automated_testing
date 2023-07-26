package uk.gov.companieshouse.pageobjects.insolvency;

import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.enums.Form;
import uk.gov.companieshouse.pageobjects.ChipsCommonPage;
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
    @FindBy(how = How.LINK_TEXT, using = "Cease Case")
    private WebElement elementCessationLiveCaseLink;

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
                log.info("Creating an MVL case");
            } else {
                createCvlCaseLink.click();
                log.info("Creating a CVL case");
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
        log.info("Entering IP appointment date {}", date);
        return this;
    }

    /**
     * click live case radio button.
     */
    public InsolvencyLandingScreen clickLiveCaseRadioButton() {
        log.info("Selectign live case button...");
        elementLiveCaseRadioButton.click();
        return this;
    }

    /**
     * Click on the first insolvency case displayed.
     */
    public InsolvencyLandingScreen selectFirstCase() {
        log.info("Selecting first case");
        elementCases.get(0).click();
        return this;
    }

    /**
     * Click on the last insolvency case displayed.
     */
    public InsolvencyLandingScreen selectLastCase() {
        log.info("Selecting last case");
        elementCases.get(elementCases.size() - 1).click();
        return this;
    }

    /**
     * Click the process statement link.
     */
    public InsolvencyLandingScreen clickProcessStatementLink() {
        log.info("Processing statement...");
        elementProcessStatementLink.click();
        return this;
    }

    /**
     * Click the cease IP link.
     */
    public InsolvencyLandingScreen clickCeaseIpLink() {
        log.info("Ceasing IP...");
        elementCeaseIpLink.click();
        return this;
    }

    /**
     * Click the convert MVL to CVL link.
     */
    public InsolvencyLandingScreen clickConvertMvlToCvlLink() {
        log.info("Converting MVL to CVL...");
        elementConvertMvlToCvlLink.click();
        return this;
    }

    /**
     * Click the add to case link.
     */
    public InsolvencyLandingScreen clickAddToCase() {
        log.info("Adding to case..");
        addToCaseLink.click();
        return this;
    }

    /**
     * Click the cease case link.
     */
    public InsolvencyLandingScreen clickCeaseLiveCase() {
        log.info("Ceasing a live case...");
        elementCessationLiveCaseLink.click();
        return this;
    }

}

