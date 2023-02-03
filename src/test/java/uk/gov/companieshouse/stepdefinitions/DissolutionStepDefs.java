package uk.gov.companieshouse.stepdefinitions;

import static uk.gov.companieshouse.data.dbclone.sql.CompanySql.DISSOLUTION_COMPANY_NO_PREV_DISS_REQUEST_FILED;

import io.cucumber.java.en.When;
import uk.gov.companieshouse.data.datamodel.Address;
import uk.gov.companieshouse.data.datamodel.Company;
import uk.gov.companieshouse.data.dbclone.DbClone;
import uk.gov.companieshouse.enums.Forms.Form;
import uk.gov.companieshouse.pageobjects.DissolutionPage;
import uk.gov.companieshouse.pageobjects.ProcessStartOfDocumentPage;
import uk.gov.companieshouse.utils.GlobalNavBar;
import uk.gov.companieshouse.utils.TestContext;


public class DissolutionStepDefs {

    public TestContext context;
    public GlobalNavBar globalNavBar;
    public DbClone dbClone;
    public ProcessStartOfDocumentPage processStartOfDocumentPage;
    public DissolutionPage dissolutionPage;

    public DissolutionStepDefs(TestContext context, GlobalNavBar globalNavBar, DbClone dbClone,
                               ProcessStartOfDocumentPage processStartOfDocumentPage, DissolutionPage dissolutionPage) {
        this.context = context;
        this.globalNavBar = globalNavBar;
        this.dbClone = dbClone;
        this.processStartOfDocumentPage = processStartOfDocumentPage;
        this.dissolutionPage = dissolutionPage;
    }

    @When("I process the start document for form DS01")
    public void processTheStartDocumentForFormDs01() {
        globalNavBar.clickProcessFormLabel();
        Company company = dbClone.cloneCompanyWithParameterInternal(DISSOLUTION_COMPANY_NO_PREV_DISS_REQUEST_FILED, null);
        processStartOfDocumentPage.processForm(company, "DS01", Form.DS01.isHighRiskForm());
    }

    @When("I complete mandatory details to strike off the company from the register")
    public void completeMandatoryDetailsToStrikeOffTheCompanyFromTheRegister() {
        Address address = new Address.AddressBuilder().welshAddress().build();
        dissolutionPage
                .waitUntilFormDisplayed(Form.DS01.getTitle())
                .enterSignatureDate()
                .enterHouseNumber(address.getHouseNumber())
                .enterPostcode(address.getPostcode())
                .clickPostcodeLookup()
                .saveForm();

    }


}
