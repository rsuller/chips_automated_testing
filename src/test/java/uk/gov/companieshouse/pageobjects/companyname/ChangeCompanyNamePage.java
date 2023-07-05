package uk.gov.companieshouse.pageobjects.companyname;

import static uk.gov.companieshouse.utils.DateFormat.getDateAsString;

import java.util.Date;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.pageobjects.ChipsCommonPage;
import uk.gov.companieshouse.utils.TestContext;


public class ChangeCompanyNamePage extends ChipsCommonPage<ChangeCompanyNamePage> {
    final TestContext testContext;
    public static final Logger log = LoggerFactory.getLogger(ChangeCompanyNamePage.class);

    /**
     * Required constructor for class.
     */
    public ChangeCompanyNamePage(TestContext testContext) {
        super(testContext);
        this.testContext = testContext;
        PageFactory.initElements(testContext.getWebDriver(), this);
    }

    @FindBy(how = How.CSS, using = "textarea[name='form1:proposedName:proposedName:_id58']")
    private WebElement elementProposedName;
    @FindBy(how = How.ID, using = "form1:proposedName:proposedName:_id62")
    private WebElement elementRekeyProposedName;
    @FindBy(how = How.ID, using = "form1:proposedName:proposedName:_id65")
    private WebElement elementNameEnding;
    @FindBy(how = How.ID, using = "form1:doc_general_meetingDate:field")
    private WebElement elementMeetingDate;
    @FindBy(how = How.ID, using = "form1:methodOfChange:checkBoxes:methodOfChangeId:field")
    private WebElement elementChangeMethod;
    @FindBy(how = How.ID, using = "form1:checkboxes:checkBoxes:_id69")
    private WebElement elementExecuteNameChange;
    @FindBy(how = How.ID, using = "form1:task_useROAddress")
    private WebElement elementUseRoAddress;


    /**
     * Enter the proposed company name and tab from the field as a workaround for the issue in the method below.
     * @param proposedName the proposed name to enter.
     */
    public ChangeCompanyNamePage enterProposedName(String proposedName) {
        elementProposedName.sendKeys(proposedName);
        elementProposedName.sendKeys(Keys.TAB);
        return this;
    }

    /**
     * Re-key the proposed company name. The Selenium sendKeys method does not work here it results in
     * ElementNotInteractableException. Our typeText workaround in ElementInteraction.java appears to work but then on
     * saving the form the text field appears blank. Therefore, Selenium Actions class is used here.
     * @param proposedName the proposed name to enter.
     */
    public ChangeCompanyNamePage reKeyProposedName(String proposedName) {
        Actions actions = new Actions(testContext.getWebDriver());
        actions.moveToElement(elementRekeyProposedName).sendKeys(elementRekeyProposedName, proposedName).perform();
        return this;
    }

    public ChangeCompanyNamePage selectNameEnding(String nameEnding) {
        selectByText(elementNameEnding, nameEnding);
        return this;
    }

    public ChangeCompanyNamePage enterMeetingDateAsToday() {
        typeText(elementMeetingDate, getDateAsString(new Date()));
        return this;
    }

    public ChangeCompanyNamePage selectNm01MethodOfChange() {
        selectByText(elementChangeMethod, "NM01 - Resolution");
        return this;
    }

    public ChangeCompanyNamePage executeChangeOfName() {
        elementExecuteNameChange.click();
        return this;
    }

    /**
     * Wait until the use RO address link is displayed and click it.
     */
    public ChangeCompanyNamePage useRoAddressAsPresenter() {
        waitUntilElementDisplayed(elementUseRoAddress);
        elementUseRoAddress.click();
        return this;
    }


}
