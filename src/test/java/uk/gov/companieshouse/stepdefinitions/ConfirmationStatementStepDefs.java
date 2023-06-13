package uk.gov.companieshouse.stepdefinitions;

import io.cucumber.java.en.When;
import uk.gov.companieshouse.pageobjects.ConfirmationStatementPage;
import uk.gov.companieshouse.utils.TestContext;


public class ConfirmationStatementStepDefs {

    public final TestContext context;
    public final ConfirmationStatementPage confirmationStatementPage;


    /**
     * Required constructor for class.
     */
    public ConfirmationStatementStepDefs(TestContext context, ConfirmationStatementPage confirmationStatementPage) {
        this.context = context;
        this.confirmationStatementPage = confirmationStatementPage;
    }

    /**
     * Complete mandatory fields to process a no update confirmation statement.
     */
    @When("I complete mandatory details process a no update confirmation statement")
    public void completeMandatoryDetailsNoUpdateConfirmationStatement() {
        confirmationStatementPage.completeNoUpdateCs01();
    }

    /**
     * Complete mandatory fields to process a no update confirmation statement.
     */
    @When("I complete mandatory details to file a paper confirmation statement")
    public void completeMandatoryDetailsPaperConfirmationStatement() {
        confirmationStatementPage.completePaperCs01();
    }

}
