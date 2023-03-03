package uk.gov.companieshouse.stepdefinitions;

import static uk.gov.companieshouse.data.dbutil.sql.CompanySql.BASE_SQL_LTD_COMPANY_WITH_ACTIVE_DIRECTOR;
import static uk.gov.companieshouse.data.dbutil.sql.CompanySql.BASE_SQL_PRIVATE_LIMITED_COMPANY_ID;
import static uk.gov.companieshouse.data.dbutil.sql.CompanySql.BASE_SQL_lTD_WITH_ACTIVE_CORPORATE_DIRECTOR;
import static uk.gov.companieshouse.data.dbutil.sql.CompanySql.DISSOLUTION_COMPANY_NO_PREV_DISS_REQUEST_FILED;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.data.datamodel.Company;
import uk.gov.companieshouse.data.dbutil.DbUtil;
import uk.gov.companieshouse.enums.Forms.Form;
import uk.gov.companieshouse.pageobjects.ChipsHomePage;
import uk.gov.companieshouse.pageobjects.CompanyDetailsScreen;
import uk.gov.companieshouse.pageobjects.CompanySearchPage;
import uk.gov.companieshouse.pageobjects.GlobalNavBar;
import uk.gov.companieshouse.pageobjects.OrgUnitPage;
import uk.gov.companieshouse.pageobjects.ProcessStartOfDocumentPage;
import uk.gov.companieshouse.testdata.DocumentDetails;
import uk.gov.companieshouse.testdata.User;
import uk.gov.companieshouse.utils.TestContext;

public class CommonStepDefs {

    public static Logger log = LoggerFactory.getLogger(CommonStepDefs.class);

    public TestContext context;
    public ChipsHomePage chipsHomePage;
    public CompanyDetailsScreen companyDetailsScreen;
    public OrgUnitPage orgUnitPage;
    public ProcessStartOfDocumentPage processStartOfDocumentPage;
    public GlobalNavBar globalNavBar;
    public DbUtil dbUtil;
    public DocumentDetails documentDetails;
    public CompanySearchPage companySearchPage;

    public CommonStepDefs(TestContext context, ChipsHomePage chipsHomePage, CompanyDetailsScreen companyDetailsScreen,
                          OrgUnitPage orgUnitPage, ProcessStartOfDocumentPage processStartOfDocumentPage,
                          GlobalNavBar globalNavBar, DbUtil dbUtil, DocumentDetails documentDetails,
                          CompanySearchPage companySearchPage) {
        this.context = context;
        this.chipsHomePage = chipsHomePage;
        this.companyDetailsScreen = companyDetailsScreen;
        this.orgUnitPage = orgUnitPage;
        this.processStartOfDocumentPage = processStartOfDocumentPage;
        this.globalNavBar = globalNavBar;
        this.dbUtil = dbUtil;
        this.documentDetails = documentDetails;
        this.companySearchPage = companySearchPage;
    }

    @Given("I am logged in as a user in the {string} organisational unit")
    public void imLoggedInAsAUserInTheOrganisationalUnit(String orgUnit) {
        // Setup user
        User user = new User();
        user.setUser(context.getEnv().config.getString("username"), context.getEnv().config.getString("password"));
        context.setUpUser(user);
        // Login user and open Chips
        context.getUser().setOrgUnit(orgUnit);
        chipsHomePage.logInUser(context.getUser().getUsername(), context.getUser().getPassword());
        context.getWebDriver().manage().window().maximize();
        context.getWebDriver().navigate().to(context.getEnv().config.getString("chips_url") + "/menu/changeOrgUnitAssignment");
        //Select Org. unit
        orgUnitPage.selectOrgUnit(context.getUser().getOrgUnit());
    }

    @Then("the form is submitted without rules fired")
    public void theFormIsSubmittedWithoutFailedRules() {
        processStartOfDocumentPage.waitUntilDisplayed();
    }

    @Then("^company history information is updated with the accepted (.*) transaction$")
    public void companyHistoryInformationIsUpdated(String formName) {
        Form form = Form.getFormByType(formName);
        companySearchPage
                .findCompanyByNumberFromMenu()
                .openCompanyDetails();
        companyDetailsScreen
                .waitUntilDisplayed()
                .getExpectedTransactionFromHistory(form.getType(), documentDetails.getReceivedDate(), "ACCEPTED",
                        form.getTransactionHistoryPartialDescription());
    }

    @And("I process the start document for form {string}")
    public void processTheStartDocumentForForm(String formType) {
        globalNavBar.clickProcessFormLabel();
        Company company = null;
        switch (formType) {
            case "CH01":
                company = dbUtil.cloneCompany(BASE_SQL_LTD_COMPANY_WITH_ACTIVE_DIRECTOR);
                break;
            case "CH02":
                company = dbUtil.cloneCompany(BASE_SQL_lTD_WITH_ACTIVE_CORPORATE_DIRECTOR);
                break;
            case "AD01":
                company = dbUtil.cloneCompany(BASE_SQL_PRIVATE_LIMITED_COMPANY_ID);
                break;
            case "DS01":
                company = dbUtil.cloneCompany(DISSOLUTION_COMPANY_NO_PREV_DISS_REQUEST_FILED);
                break;
            default:
                log.error("There is no current option for form {}", formType);
                break;

        }
        processStartOfDocumentPage.processForm(company, Form.getFormByType(formType));

    }
}
