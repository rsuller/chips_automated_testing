package uk.gov.companieshouse.pageobjects;

import com.google.common.base.Verify;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.utils.TestContext;

public class EfTestHarnessResponsePage extends ChipsCommonPage<EfTestHarnessResponsePage> {

    public final TestContext testContext;
    public static final Logger log = LoggerFactory.getLogger(EfTestHarnessResponsePage.class);

    /**
     * Required constructor for class.
     */
    public EfTestHarnessResponsePage(TestContext testContext) {
        super(testContext);
        this.testContext = testContext;
        PageFactory.initElements(testContext.getWebDriver(), this);
    }

    @FindBy(how = How.ID, using = "form1:_id25")
    private WebElement submissionResponseField;


    /**
     * Verify that the xml submission has returned the expected response.
     */
    public void verifyFormResponse() {
        waitUntilElementDisplayed(submissionResponseField);
        String submissionResponse = submissionResponseField.getText();
        Verify.verify(submissionResponse.equals("Response code is: 0"), "Received response: " + submissionResponse
                + ". Expected code should be 0. Check XML submission for errors");
    }

}
