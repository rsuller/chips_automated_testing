package uk.gov.companieshouse.stepdefinitions;

import io.cucumber.java.en.When;
import uk.gov.companieshouse.data.datamodel.Company;
import uk.gov.companieshouse.data.dbutil.DbUtil;
import uk.gov.companieshouse.data.dbutil.sql.CompanySql;
import uk.gov.companieshouse.enums.Form;
import uk.gov.companieshouse.pageobjects.ChipsHomePage;
import uk.gov.companieshouse.pageobjects.CompanyDetailsScreen;
import uk.gov.companieshouse.pageobjects.CompanySearchPage;
import uk.gov.companieshouse.pageobjects.GlobalNavBar;
import uk.gov.companieshouse.pageobjects.OrgUnitPage;
import uk.gov.companieshouse.pageobjects.ProcessStartOfDocumentPage;
import uk.gov.companieshouse.testdata.DocumentDetails;
import uk.gov.companieshouse.utils.FesProcessor;
import uk.gov.companieshouse.utils.TestContext;


public class FesProcessingStepDefs {

    public TestContext context;
    public ChipsHomePage chipsHomePage;
    public CompanyDetailsScreen companyDetailsScreen;
    public OrgUnitPage orgUnitPage;
    public ProcessStartOfDocumentPage processStartOfDocumentPage;
    public GlobalNavBar globalNavBar;
    public DbUtil dbUtil;
    public DocumentDetails documentDetails;
    public CompanySearchPage companySearchPage;
    public FesProcessor fesProcessor;


    /**
     * Required constructor for class.
     */
    public FesProcessingStepDefs(TestContext context, ChipsHomePage chipsHomePage, CompanyDetailsScreen companyDetailsScreen,
                                 OrgUnitPage orgUnitPage, ProcessStartOfDocumentPage processStartOfDocumentPage,
                                 GlobalNavBar globalNavBar, DbUtil dbUtil, DocumentDetails documentDetails,
                                 CompanySearchPage companySearchPage, FesProcessor fesProcessor) {
        this.context = context;
        this.chipsHomePage = chipsHomePage;
        this.companyDetailsScreen = companyDetailsScreen;
        this.orgUnitPage = orgUnitPage;
        this.processStartOfDocumentPage = processStartOfDocumentPage;
        this.globalNavBar = globalNavBar;
        this.dbUtil = dbUtil;
        this.documentDetails = documentDetails;
        this.companySearchPage = companySearchPage;
        this.fesProcessor = fesProcessor;
    }


    /**
     * Process a FES'd AP01 form switching the SQL chosen to run based on the jurisdiction under test.
     */
    @When("I process a FES {string} for a company registered in {string}")
    public void processFesForm(String formType, String registerLocation) throws Throwable {
        Company company;
        Form.getFormByType(formType);
        int registerLocationTypeId = 0;
        if ("Eng/Wales".equals(registerLocation)) {
            registerLocationTypeId = 1;
        } else if ("Scotland".equals(registerLocation)) {
            registerLocationTypeId = 2;
        } else if ("Northern Ireland".equals(registerLocation)) {
            registerLocationTypeId = 3;
        }
        company = dbUtil.cloneCompanyWithParameterInternal(
                CompanySql.BASE_SQL_PRIVATE_LIMITED_COMPANY_RO_LOCATION_UNSPECIFIED, registerLocationTypeId);
        fesProcessor
                .processFesDocumentForLocation(registerLocationTypeId, formType, company.getNumber(), company.getName())
                .allocateWorkAndPsodFes(formType, company);
    }

}
