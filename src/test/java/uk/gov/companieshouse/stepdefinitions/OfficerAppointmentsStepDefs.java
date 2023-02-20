package uk.gov.companieshouse.stepdefinitions;

import io.cucumber.java.en.When;
import uk.gov.companieshouse.data.dbutil.DbUtil;
import uk.gov.companieshouse.pageobjects.ChangeToRoPage;
import uk.gov.companieshouse.pageobjects.GlobalNavBar;
import uk.gov.companieshouse.pageobjects.ProcessStartOfDocumentPage;
import uk.gov.companieshouse.utils.TestContext;


public class OfficerAppointmentsStepDefs {

    public TestContext context;
    public ChangeToRoPage changeToRoPage;
    public GlobalNavBar globalNavBar;
    public DbUtil dbUtil;
    public ProcessStartOfDocumentPage processStartOfDocumentPage;

    public OfficerAppointmentsStepDefs(TestContext context, ChangeToRoPage changeToRoPage, GlobalNavBar globalNavBar, DbUtil dbUtil,
                                       ProcessStartOfDocumentPage processStartOfDocumentPage) {
        this.context = context;
        this.changeToRoPage = changeToRoPage;
        this.globalNavBar = globalNavBar;
        this.dbUtil = dbUtil;
        this.processStartOfDocumentPage = processStartOfDocumentPage;
    }

    @When("I complete mandatory details to appoint a FES'd director")
    public void completeMandatoryDetailsToAppointFesDirector() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

}
