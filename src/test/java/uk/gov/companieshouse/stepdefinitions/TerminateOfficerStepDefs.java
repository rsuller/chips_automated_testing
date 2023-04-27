package uk.gov.companieshouse.stepdefinitions;

import io.cucumber.java.en.When;
import uk.gov.companieshouse.pageobjects.TerminateOfficerPage;


public class TerminateOfficerStepDefs {
    private final TerminateOfficerPage terminateOfficerPage;

    public TerminateOfficerStepDefs(TerminateOfficerPage terminateOfficerPage) {
        this.terminateOfficerPage = terminateOfficerPage;
    }

    @When("I complete mandatory details to terminate a director")
    public void terminateDirectorAppointment() {
        terminateOfficerPage.enterDateOfTermination().saveForm();
    }

}
