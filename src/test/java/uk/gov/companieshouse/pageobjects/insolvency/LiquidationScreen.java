package uk.gov.companieshouse.pageobjects.insolvency;

import java.util.Date;
import org.apache.commons.lang3.time.DateUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.pageobjects.ChipsCommonPage;
import uk.gov.companieshouse.utils.DateFormat;
import uk.gov.companieshouse.utils.TestContext;


public class LiquidationScreen extends ChipsCommonPage<LiquidationScreen> {

    public final TestContext testContext;
    public static final Logger log = LoggerFactory.getLogger(LiquidationScreen.class);

    /**
     * Required constructor for class.
     */
    public LiquidationScreen(TestContext testContext) {
        super(testContext);
        this.testContext = testContext;
        PageFactory.initElements(testContext.getWebDriver(), this);
    }

    @FindBy(how = How.ID, using = "form1:doc_liquidation_decSolSwornDate:field")
    private WebElement solvencyDeclarationDateEntry;
    @FindBy(how = How.ID, using = "form1:doc_liquidation_caseDate:field")
    private WebElement caseStartDate;
    @FindBy(how = How.ID, using = "form1:doc_liquidation_broughtDownDate:field")
    private WebElement statementPeriodEndDate;
    @FindBy(how = How.ID, using = "form1:task_processStatement")
    private WebElement saveProcessStatement;

    /**
     * Wait for the field to be displayed then enter the date solvency is sworn on.
     */
    public LiquidationScreen enterDateSolvencySworn() {
        waitUntilElementDisplayed(solvencyDeclarationDateEntry);
        log.info("Entering date solvency is sworn...");
        solvencyDeclarationDateEntry.sendKeys(DateFormat.getDateAsString(new Date()));
        return this;
    }

    /**
     * Wait for the field to be displayed then enter the liquidation case start date.
     */
    public LiquidationScreen enterCaseStartDate() {
        waitUntilElementDisplayed(caseStartDate);
        log.info("Entering case start date...");
        caseStartDate.sendKeys(DateFormat.getDateAsString(new Date()));
        return this;
    }

    /**
     * Wait for the field to be displayed then enter the period end date of the statement.
     * Date must be at least 1 day before current date.
     */
    public LiquidationScreen enterYesterdayAsPeriodEndDate() {
        waitUntilElementDisplayed(statementPeriodEndDate);
        log.info("Entering yesterday as period end date...");
        statementPeriodEndDate.sendKeys(DateFormat.getDateAsString(DateUtils.addDays(new Date(),-1)));
        return this;
    }

    public void saveProcessStatement() {
        log.info("Saving statement...");
        saveProcessStatement.click();
    }

}

