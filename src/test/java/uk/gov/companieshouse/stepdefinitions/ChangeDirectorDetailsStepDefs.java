package uk.gov.companieshouse.stepdefinitions;

import io.cucumber.java.en.When;
import uk.gov.companieshouse.pageobjects.officers.ChangeOfficerPage;

public class ChangeDirectorDetailsStepDefs {
    private final ChangeOfficerPage changeOfficerPage;

    public ChangeDirectorDetailsStepDefs(ChangeOfficerPage changeOfficerPage) {
        this.changeOfficerPage = changeOfficerPage;
    }

    @When("I change the {string} detail of the director")
    public void changeTheDetailOfTheDirector(String typeOfChange) {
        changeOfficerPage.directorDetailToChange(typeOfChange.toLowerCase());

    }

}
