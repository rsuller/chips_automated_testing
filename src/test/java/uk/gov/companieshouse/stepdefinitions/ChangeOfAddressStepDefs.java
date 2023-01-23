package uk.gov.companieshouse.stepdefinitions;

import static uk.gov.companieshouse.data.dbclone.sql.CompanySql.BASE_SQL_PRIVATE_LIMITED_COMPANY_ID;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import uk.gov.companieshouse.data.datamodel.Address;
import uk.gov.companieshouse.data.datamodel.Company;
import uk.gov.companieshouse.data.dbclone.DbClone;
import uk.gov.companieshouse.enums.Forms.Form;
import uk.gov.companieshouse.pageobjects.ChangeToRoPage;
import uk.gov.companieshouse.pageobjects.ProcessStartOfDocumentPage;
import uk.gov.companieshouse.utils.GlobalNavBar;
import uk.gov.companieshouse.utils.TestContext;


public class ChangeOfAddressStepDefs {

    public TestContext context;
    public ChangeToRoPage changeToRoPage;
    public GlobalNavBar globalNavBar;
    public DbClone dbClone;
    public ProcessStartOfDocumentPage processStartOfDocumentPage;

    public ChangeOfAddressStepDefs(TestContext context, ChangeToRoPage changeToRoPage, GlobalNavBar globalNavBar, DbClone dbClone,
                                   ProcessStartOfDocumentPage processStartOfDocumentPage) {
        this.context = context;
        this.changeToRoPage = changeToRoPage;
        this.globalNavBar = globalNavBar;
        this.dbClone = dbClone;
        this.processStartOfDocumentPage = processStartOfDocumentPage;
    }

    @When("I process the start document for form AD01")
    public void processTheStartDocumentForFormAD01() {
        globalNavBar.clickProcessFormLabel();
        Company company = dbClone.cloneCompanyWithParameterInternal(BASE_SQL_PRIVATE_LIMITED_COMPANY_ID, null);
        processStartOfDocumentPage.processForm(company, "AD01", Form.AD01.isHighRiskForm());
    }

    /**
     * Complete the mandatory details for the registered office or SAIL address.
     */
    @Given("I complete mandatory details to change a registered office address")
    public void completeMandatoryDetailsToChangeARegisteredOfficeAddress() {
        Address address = new Address.AddressBuilder().welshAddress().build();
        changeToRoPage
                .waitUntilAd01Displayed()
                .enterHouseNumber(address.getHouseNumber())
                .enterPostCode(address.getPostcode())
                .clickLookup()
                .waitUntilStreetPopulated()
                .saveForm();
    }

}