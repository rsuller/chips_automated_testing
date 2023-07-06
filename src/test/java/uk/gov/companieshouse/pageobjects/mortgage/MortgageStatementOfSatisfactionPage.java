package uk.gov.companieshouse.pageobjects.mortgage;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.pageobjects.ChipsCommonPage;
import uk.gov.companieshouse.utils.TestContext;


public class MortgageStatementOfSatisfactionPage extends ChipsCommonPage<MortgageStatementOfSatisfactionPage> {

    public final TestContext testContext;
    public static final Logger log = LoggerFactory.getLogger(MortgageStatementOfSatisfactionPage.class);

    /**
     * Required constructor for class.
     */
    public MortgageStatementOfSatisfactionPage(TestContext testContext) {
        super(testContext);
        this.testContext = testContext;
        PageFactory.initElements(testContext.getWebDriver(), this);
    }

    @FindBy(how = How.ID, using = "searchAndSelectCharge:searchAndSelectChargeBaseId:"
            + "searchAndSelectChargeBase:satisfactionTypes:field")
    private WebElement elementMortgageSatisfactionTypeAction;
    @FindBy(how = How.ID, using = "searchAndSelectCharge:task_search")
    private WebElement elementSearch;
    @FindBy(how = How.ID, using = "form1:task_processStmntOfSatisfaction")
    private WebElement elementProcessStmntOfSatisfaction;

    public MortgageStatementOfSatisfactionPage selectSatisfactionType() {
        selectByText(elementMortgageSatisfactionTypeAction, "unsatisfied");
        return this;
    }

    public MortgageStatementOfSatisfactionPage clickSearch() {
        elementSearch.click();
        return this;
    }

    /**
     * Wait for the process statement of satisfaction link to be displayed then click it.
     */
    public MortgageStatementOfSatisfactionPage clickProcessStmntOfSatisfaction() {
        waitUntilElementDisplayed(elementProcessStmntOfSatisfaction);
        log.info("Processing statement of satisfaction...");
        elementProcessStmntOfSatisfaction.click();
        return this;
    }


}

