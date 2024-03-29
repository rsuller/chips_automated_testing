package uk.gov.companieshouse.stepdefinitions;

import io.cucumber.java.en.When;
import uk.gov.companieshouse.data.datamodel.Director;
import uk.gov.companieshouse.pageobjects.officers.AppointDirectorPage;
import uk.gov.companieshouse.pageobjects.workitems.MyWorkPage;
import uk.gov.companieshouse.utils.TestContext;


public class OfficerAppointmentsStepDefs {

    public final TestContext context;
    public final AppointDirectorPage appointDirectorPage;
    public final MyWorkPage myWorkPage;


    /**
     * Required constructor for class.
     */
    public OfficerAppointmentsStepDefs(TestContext context, AppointDirectorPage appointDirectorPage, MyWorkPage myWorkPage) {
        this.context = context;
        this.appointDirectorPage = appointDirectorPage;
        this.myWorkPage = myWorkPage;
    }

    /**
     * Build a default director object and use this data to fill in mandatory fields.
     */
    @When("I complete mandatory details to appoint a FES'd director")
    public void completeMandatoryDetailsToAppointFesDirector() {
        Director director = new Director.DirectorBuilder().createDefaultDirector().build();
        appointDirectorPage.enterMandatoryFormDetails(director);
        myWorkPage.waitUntilOpened();
    }

}
