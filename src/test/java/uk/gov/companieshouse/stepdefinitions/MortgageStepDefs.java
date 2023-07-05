package uk.gov.companieshouse.stepdefinitions;

import io.cucumber.java.en.When;
import uk.gov.companieshouse.enums.Form;
import uk.gov.companieshouse.pageobjects.mortgage.MortgageStatementOfSatisfactionPage;


public class MortgageStepDefs {
    private final MortgageStatementOfSatisfactionPage mortgageStatementOfSatisfactionPage;

    public MortgageStepDefs(MortgageStatementOfSatisfactionPage mortgageStatementOfSatisfactionPage) {
        this.mortgageStatementOfSatisfactionPage = mortgageStatementOfSatisfactionPage;
    }

    /**
     * Complete all necessary actions to process a statement of satisfaction against a current charge.
     */
    @When("I complete mandatory details to process a statement of satisfaction of a charge")
    public void processStatementOfSatisfaction() {
        mortgageStatementOfSatisfactionPage
                .waitUntilFormDisplayed(Form.MR04)
                .selectSatisfactionType()
                .clickSearch()
                .clickProcessStmntOfSatisfaction()
                .saveForm();
    }

}
