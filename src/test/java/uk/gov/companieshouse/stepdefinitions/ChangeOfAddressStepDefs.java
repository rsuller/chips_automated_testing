package uk.gov.companieshouse.stepdefinitions;

import io.cucumber.java.en.Given;
import uk.gov.companieshouse.data.datamodel.Address;
import uk.gov.companieshouse.data.dbutil.DbUtil;
import uk.gov.companieshouse.enums.Forms.Form;
import uk.gov.companieshouse.pageobjects.ChangeToRoPage;
import uk.gov.companieshouse.pageobjects.GlobalNavBar;
import uk.gov.companieshouse.pageobjects.ProcessStartOfDocumentPage;
import uk.gov.companieshouse.utils.TestContext;


public class ChangeOfAddressStepDefs {

    public TestContext context;
    public ChangeToRoPage changeToRoPage;
    public GlobalNavBar globalNavBar;
    public DbUtil dbUtil;
    public ProcessStartOfDocumentPage processStartOfDocumentPage;

    /**
     * Required constructor for class.
     */
    public ChangeOfAddressStepDefs(TestContext context, ChangeToRoPage changeToRoPage, GlobalNavBar globalNavBar, DbUtil dbUtil,
                                   ProcessStartOfDocumentPage processStartOfDocumentPage) {
        this.context = context;
        this.changeToRoPage = changeToRoPage;
        this.globalNavBar = globalNavBar;
        this.dbUtil = dbUtil;
        this.processStartOfDocumentPage = processStartOfDocumentPage;
    }

    /**
     * Complete the mandatory details to change the registered office of the company.
     */
    @Given("I complete mandatory details to change a registered office address")
    public void completeMandatoryDetailsToChangeARegisteredOfficeAddress() {
        Address address = new Address.AddressBuilder().welshAddress().build();
        changeToRoPage
                .waitUntilFormDisplayed(Form.AD01)
                .enterHouseNumber(address.getHouseNumber())
                .enterPostCode(address.getPostcode())
                .clickLookup()
                .waitUntilStreetPopulated()
                .saveForm();
    }

}
