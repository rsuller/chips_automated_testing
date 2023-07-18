package uk.gov.companieshouse.pageobjects;

import com.google.common.base.Verify;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.utils.DocumentProcessor;
import uk.gov.companieshouse.utils.TestContext;

public class EfTestHarnessResponsePage extends ChipsCommonPage<EfTestHarnessResponsePage> {

    public final TestContext testContext;
    public final DocumentProcessor documentProcessor;
    public static final Logger log = LoggerFactory.getLogger(EfTestHarnessResponsePage.class);

    /**
     * Required constructor for class.
     */
    public EfTestHarnessResponsePage(TestContext testContext, DocumentProcessor documentProcessor) {
        super(testContext);
        this.testContext = testContext;
        this.documentProcessor = documentProcessor;
        PageFactory.initElements(testContext.getWebDriver(), this);
    }

    @FindBy(how = How.ID, using = "form1:_id25")
    private WebElement submissionResponseField;
    @FindBy(how = How.CSS, using = "div[class='data'] > span[id='form1:formTrackerView:_id32']")
    private WebElement transDocStatusMessage;
    @FindBy(how = How.ID, using = "form1:formTrackerView:formTrackerView_scriptVar_allocateToCurrentUserreturnfalsealloc"
            + "ateworkitemtocurrentuser")
    private WebElement allocateWorkItemToCurrentUserLink;
    @FindBy(how = How.CSS, using = "span[id='form1:formTrackerView:_id48']")
    private WebElement workItemAllocatedMessage;
    @FindBy(how = How.CSS, using = "a[title='select work item']")
    private WebElement selectWorkItemLink;


    /**
     * Verify that the xml submission has returned the expected response.
     */
    public EfTestHarnessResponsePage verifyFormResponse() {
        String submissionResponse = submissionResponseField.getText();
        Verify.verify(submissionResponse.equals("Response code is: 0"), "Received response: " + submissionResponse
                + ". Expected code should be 0. Check XML submission for errors");
        return this;
    }

    /**
     * Verify that the XML form submission has gone through to the transaction_doc_xml table for processing.
     */
    public void verifyFormInTransDocXmlTable() {
        String docId = documentProcessor.checkDocumentSubmission();
        int maxTries = 10;
        for (int i = 1; i <= maxTries; ++i) {
            log.info("Checking form is in transaction_doc_xml table. Attempt {} of {} ", i, maxTries);
            try {
                waitForSpecificTextInElement(transDocStatusMessage, "The form is in the transaction_doc_xml table "
                        + "with document ID: " + docId);
                break;
            } catch (TimeoutException exception) {
                log.info("Form not in transaction_doc_xml table, retrying");
            }
            if (i == maxTries) {
                log.error("Unable to find form in transaction_doc_xml table after {} attempts", maxTries);
                throw new RuntimeException("Unable to find form in transaction_doc_xml table after " + maxTries + " attempts");
            }
        }
        log.info("Form in transaction_doc_xml table, continuing with test.");
    }

    /**
     * Allocate the work item to the current user by clicking the link.
     */
    public EfTestHarnessResponsePage allocateWorkItem() {
        waitUntilElementClickable(allocateWorkItemToCurrentUserLink);
        allocateWorkItemToCurrentUserLink.click();
        return this;
    }

    /**
     * Select the work item to open up the form by clicking the link.
     */
    public void selectWorkItem() {
        waitForSpecificTextInElement(workItemAllocatedMessage, "The form has been allocated to the current user.");
        waitUntilElementClickable(selectWorkItemLink);
        selectWorkItemLink.click();
    }

}
