package uk.gov.companieshouse.pageobjects;

import java.util.Date;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.testdata.DocumentDetails;
import uk.gov.companieshouse.utils.DateFormat;
import uk.gov.companieshouse.utils.TestContext;


public class LiquidationScreen extends ChipsCommonPage<LiquidationScreen> {

    public TestContext testContext;
    public DocumentDetails documentDetails;
    public static final Logger log = LoggerFactory.getLogger(LiquidationScreen.class);

    /**
     * Required constructor for class.
     */
    public LiquidationScreen(TestContext testContext, DocumentDetails documentDetails) {
        super(testContext);
        this.testContext = testContext;
        this.documentDetails = documentDetails;
        PageFactory.initElements(testContext.getWebDriver(), this);
    }

    @FindBy(how = How.ID, using = "form1:doc_liquidation_decSolSwornDate:field")
    private WebElement solvencyDeclarationDateEntry;
    @FindBy(how = How.ID, using = "form1:allocateIP:task_allocateIP")
    private WebElement elementAllocateIpLink;

    /**
     * Wait for the field to be displayed then enter the date solvency is sworn on.
     */
    public LiquidationScreen enterDateSolvencySworn() {
        waitUntilElementDisplayed(solvencyDeclarationDateEntry);
        solvencyDeclarationDateEntry.sendKeys(DateFormat.getDateAsString(new Date()));
        return this;
    }

}

