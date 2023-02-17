package uk.gov.companieshouse.pageobjects;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.utils.ElementInteraction;
import uk.gov.companieshouse.utils.TestContext;


public class Logout extends ElementInteraction {

    public static final Logger log = LoggerFactory.getLogger(Logout.class);

    public TestContext testContext;

    @FindBy(how = How.ID, using = "form1:task_ignoreOpenBatches")
    private WebElement elementIgnoreOpenBatches;

    public Logout(TestContext testContext) {
        super(testContext);
        this.testContext = testContext;
        PageFactory.initElements(testContext.getWebDriver(), this);
    }

    /**
     * Clicks ignore batches if present.
     * Logs exception if no batches have been opened.
     */
    public Logout clickIgnoreOpenBatches() {
        try {
            getWebDriverWait(10).until(visibilityOf(elementIgnoreOpenBatches));
            elementIgnoreOpenBatches.click();
        } catch (NoSuchElementException exception) {
            log.info("There are no batches to be closed.");
        }
        return this;
    }

}