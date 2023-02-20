package uk.gov.companieshouse.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import uk.gov.companieshouse.pageobjects.ChangeOfficerPage;

public class ChangeDirectorDetailsStepDefs {
    private final ChangeOfficerPage changeOfficerPage;

    public ChangeDirectorDetailsStepDefs(ChangeOfficerPage changeOfficerPage) {
        this.changeOfficerPage = changeOfficerPage;
    }

    @And("^I select a current active appointment$")
    public void selectCurrentActiveAppointment() {
        // Select the first appointment from the list available
        changeOfficerPage.selectFirstOfficer();
    }

    @When("I change the {string} detail of the director")
    public void changeTheDetailOfTheDirector(String typeOfChange) {
        changeOfficerPage.directorDetailToChange(typeOfChange.toLowerCase());

    }

}
