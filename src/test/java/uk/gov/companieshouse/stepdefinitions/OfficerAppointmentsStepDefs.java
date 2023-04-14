package uk.gov.companieshouse.stepdefinitions;

import io.cucumber.java.en.When;
import uk.gov.companieshouse.testdata.datamodel.Director;
import uk.gov.companieshouse.data.dbutil.DbUtil;
import uk.gov.companieshouse.pageobjects.AppointDirectorPage;
import uk.gov.companieshouse.pageobjects.ChangeToRoPage;
import uk.gov.companieshouse.pageobjects.GlobalNavBar;
import uk.gov.companieshouse.pageobjects.MyWorkPage;
import uk.gov.companieshouse.pageobjects.ProcessStartOfDocumentPage;
import uk.gov.companieshouse.utils.TestContext;


public class OfficerAppointmentsStepDefs {

    public TestContext context;
    public ChangeToRoPage changeToRoPage;
    public GlobalNavBar globalNavBar;
    public DbUtil dbUtil;
    public ProcessStartOfDocumentPage processStartOfDocumentPage;
    public AppointDirectorPage appointDirectorPage;
    public MyWorkPage myWorkPage;


    /**
     * Required constructor for class.
     */
    public OfficerAppointmentsStepDefs(TestContext context, ChangeToRoPage changeToRoPage, GlobalNavBar globalNavBar, DbUtil dbUtil,
                                       ProcessStartOfDocumentPage processStartOfDocumentPage, AppointDirectorPage appointDirectorPage,
                                       MyWorkPage myWorkPage) {
        this.context = context;
        this.changeToRoPage = changeToRoPage;
        this.globalNavBar = globalNavBar;
        this.dbUtil = dbUtil;
        this.processStartOfDocumentPage = processStartOfDocumentPage;
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
