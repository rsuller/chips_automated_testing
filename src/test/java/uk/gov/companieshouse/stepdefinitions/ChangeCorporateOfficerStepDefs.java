package uk.gov.companieshouse.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import uk.gov.companieshouse.pageobjects.officers.ChangeOfficerPage;

public class ChangeCorporateOfficerStepDefs {
    private final ChangeOfficerPage changeOfficerPage;

    public ChangeCorporateOfficerStepDefs(ChangeOfficerPage changeOfficerPage) {
        this.changeOfficerPage = changeOfficerPage;
    }

    @And("I select a {string} to change")
    public void selectACorporateDirectorToChange(String officerType) {
        changeOfficerPage.selectActiveCorporateDirector(officerType);
    }

    @When("I change the corporate officer's details")
    public void changeTheCorporateOfficerDetails() {
        changeOfficerPage.changeDetailOfCorporateOfficer();
    }

}
