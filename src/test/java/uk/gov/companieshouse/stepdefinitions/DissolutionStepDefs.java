package uk.gov.companieshouse.stepdefinitions;

import io.cucumber.java.en.When;
import uk.gov.companieshouse.data.datamodel.Address;
import uk.gov.companieshouse.data.dbutil.DbUtil;
import uk.gov.companieshouse.enums.Forms.Form;
import uk.gov.companieshouse.pageobjects.DissolutionPage;
import uk.gov.companieshouse.pageobjects.GlobalNavBar;
import uk.gov.companieshouse.pageobjects.ProcessStartOfDocumentPage;
import uk.gov.companieshouse.utils.TestContext;


public class DissolutionStepDefs {

    public TestContext context;
    public GlobalNavBar globalNavBar;
    public DbUtil dbUtil;
    public ProcessStartOfDocumentPage processStartOfDocumentPage;
    public DissolutionPage dissolutionPage;

    /**
     * Required constructor for class.
     */
    public DissolutionStepDefs(TestContext context, GlobalNavBar globalNavBar, DbUtil dbUtil,
                               ProcessStartOfDocumentPage processStartOfDocumentPage, DissolutionPage dissolutionPage) {
        this.context = context;
        this.globalNavBar = globalNavBar;
        this.dbUtil = dbUtil;
        this.processStartOfDocumentPage = processStartOfDocumentPage;
        this.dissolutionPage = dissolutionPage;
    }

    /**
     * Complete all fields to complete the DS01 form striking off the company.
     */
    @When("I complete mandatory details to strike off the company from the register")
    public void completeMandatoryDetailsToStrikeOffTheCompanyFromTheRegister() {
        Address address = new Address.AddressBuilder().welshAddress().build();
        dissolutionPage.waitUntilFormDisplayed(Form.DS01);
        dissolutionPage
                .waitUntilFormDisplayed(Form.DS01)
                .enterSignatureDate()
                .enterHouseNumber(address.getHouseNumber())
                .enterPostcode(address.getPostcode())
                .clickPostcodeLookup()
                .saveForm();

    }


}
