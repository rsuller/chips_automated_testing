package uk.gov.companieshouse.pageobjects.officers;

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

public class TerminateOfficerPage extends ChipsCommonPage<TerminateOfficerPage> {

    public final TestContext testContext;
    public static final Logger log = LoggerFactory.getLogger(TerminateOfficerPage.class);

    /**
     * Required constructor for class.
     */
    public TerminateOfficerPage(TestContext testContext) {
        super(testContext);
        this.testContext = testContext;
        PageFactory.initElements(testContext.getWebDriver(), this);
    }

    @FindBy(how = How.ID, using = "form1:terminationDate")
    private WebElement elementEnterTerminationDate;

    /**
     * Enter the date of termination as today minus one day.
     */
    public TerminateOfficerPage enterDateOfTermination() {
        waitUntilElementDisplayed(elementEnterTerminationDate);
        log.info("Entering yesterday as termination date...");
        elementEnterTerminationDate.sendKeys(DateFormat.getDateAsString(DateUtils.addDays(new Date(),-1)));
        return this;

    }

}

