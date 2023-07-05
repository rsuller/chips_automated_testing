package uk.gov.companieshouse.stepdefinitions;

import io.cucumber.java.en.When;
import uk.gov.companieshouse.pageobjects.restoration.Rt01Page;


public class RestorationStepDefs {
    private final Rt01Page rt01Page;

    public RestorationStepDefs(Rt01Page rt01Page) {
        this.rt01Page = rt01Page;
    }

    /**
     * Process administrative restoration for current company.
     */
    @When("^I complete the mandatory details for an administrative restoration$")
    public void processAdministrativeRestoration() {
        rt01Page
                .enterYourReference()
                .saveForm();
    }

}
