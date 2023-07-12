package uk.gov.companieshouse.pageobjects;

import com.google.common.base.Verify;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.testdata.DocumentDetails;
import uk.gov.companieshouse.utils.TestContext;

public class EfTestHarnessResponsePage extends ChipsCommonPage<EfTestHarnessResponsePage> {

    public final TestContext testContext;
    public final DocumentDetails documentDetails;
    public static final Logger log = LoggerFactory.getLogger(EfTestHarnessResponsePage.class);

    /**
     * Required constructor for class.
     */
    public EfTestHarnessResponsePage(TestContext testContext, DocumentDetails documentDetails) {
        super(testContext);
        this.testContext = testContext;
        this.documentDetails = documentDetails;
        PageFactory.initElements(testContext.getWebDriver(), this);
    }

    @FindBy(how = How.ID, using = "form1:_id25")
    private WebElement submissionResponseField;
    @FindBy(how = How.CSS, using = "div[class='data'] > span[id='form1:formTrackerView:_id31']")
    private WebElement formTrackerDocStatus;


    public EfTestHarnessResponsePage verifyFormResponse() {
        Verify.verify(submissionResponseField.getText().equals("Response code is: 0"), "Response code is not 0");
        return this;
    }

    /**
     * Verify that the XML form submission has been accepted.
     */
    public void verifyFormAccepted() {
        int maxTries = 20;
        for (int i = 1; i <= maxTries; ++i) {
            log.info("Checking form has been accepted. Attempt {} of {} ", i, maxTries);
            try {
                waitForSpecificTextInElement(formTrackerDocStatus, "The form is in the transaction table with status: ACCEPTED.");
                break;
            }
            catch (TimeoutException exception) {
                log.info("Accepted form not found, retrying");
            }
            if (i == maxTries) {
                log.error("Unable to find accepted form submission");
                throw new RuntimeException("Unable to find accepted form submission");
            }
        }
        log.info("Accepted form found, continuing with test.");
    }

}
