package uk.gov.companieshouse.stepdefinitions;

import static uk.gov.companieshouse.data.dbutil.sql.CompanySql.BASE_SQL_LTD_COMPANY_WITH_ACTIVE_DIRECTOR;
import static uk.gov.companieshouse.data.dbutil.sql.CompanySql.BASE_SQL_PRIVATE_LIMITED_COMPANY_ENG_WALES_ID;
import static uk.gov.companieshouse.data.dbutil.sql.CompanySql.CS_SQL_LTD_COMPANY_WITH_CS_DUE;
import static uk.gov.companieshouse.data.dbutil.sql.CompanySql.INS_PRIVATE_LTD_COMPANY_ENG_WALES_WITH_MVL_CASE_ACTION_CODE;

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
     * Process a FES form, switching the SQL chosen to run based on the jurisdiction and form type under test.
     */
    @When("I process a FES {string} for a {string} registered in {string}")
    public void processFesForm(String formType, String companyType, String registerLocation) throws Throwable {
        Company company;
        Form.getFormByType(formType);
        int registerLocationTypeId;
        if ("Eng/Wales".equals(registerLocation)) {
            registerLocationTypeId = 1;
        } else if ("Scotland".equals(registerLocation)) {
            registerLocationTypeId = 2;
        } else if ("Northern Ireland".equals(registerLocation)) {
            registerLocationTypeId = 3;
        } else {
            throw new RuntimeException("Register location type " + registerLocation
                    + "specified in the feature file is unrecognised");
        }
        switch (formType) {
            case "AA":
                if (companyType.equals("PLC")) {
                    company = dbUtil.cloneCompany(
                            CompanySql.ACCOUNTS_DUE_SQL_PUBLIC_LTD_COMPANY_ENG_WALES);
            } else {
                company = dbUtil.cloneCompany(
                        CompanySql.ACCOUNTS_DUE_SQL_PRIVATE_LTD_COMPANY_ENG_WALES);
            }
                break;
            case "AP01":
                company = dbUtil.cloneCompanyWithParameterInternal(
                        CompanySql.BASE_SQL_PRIVATE_LIMITED_COMPANY_RO_LOCATION_UNSPECIFIED, registerLocationTypeId);
                break;
            case "CS01":
                company = dbUtil.cloneCompany(CS_SQL_LTD_COMPANY_WITH_CS_DUE);
                break;
            case "CH01":
                company = dbUtil.cloneCompany(BASE_SQL_LTD_COMPANY_WITH_ACTIVE_DIRECTOR);
                break;
            case "TM01":
                company = dbUtil.cloneCompany(BASE_SQL_LTD_COMPANY_WITH_ACTIVE_DIRECTOR);
                break;
            case "AD01":
                company = dbUtil.cloneCompany(BASE_SQL_PRIVATE_LIMITED_COMPANY_ENG_WALES_ID);
                break;
            case "LIQ01":
                company = dbUtil.cloneCompany(INS_PRIVATE_LTD_COMPANY_ENG_WALES_WITH_MVL_CASE_ACTION_CODE);
                break;
            default:
                throw new RuntimeException("Unable to find SQL for specified form type");
        }
        documentDetails.setFormType(formType);
        fesProcessor
                .processFesDocumentForLocation(registerLocationTypeId, formType, company.getNumber(), company.getName())
                .allocateWorkAndPsodFes(formType, company);
    }

}
