package uk.gov.companieshouse.stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.data.dataModel.Company;
import uk.gov.companieshouse.data.dbclone.DbClone;
import uk.gov.companieshouse.pageObjects.ChipsHomePage;
import uk.gov.companieshouse.pageObjects.CompanySearchPage;
import uk.gov.companieshouse.pageObjects.OrgUnitPage;
import uk.gov.companieshouse.pageObjects.ProcessStartOfDocumentPage;
import uk.gov.companieshouse.testData.User;
import uk.gov.companieshouse.utils.GlobalNavBar;
import uk.gov.companieshouse.utils.TestContext;

import java.util.Date;

public class StepDefinitions {

    public static Logger log = LoggerFactory.getLogger(StepDefinitions.class);

    public TestContext context;
    public ChipsHomePage chipsHomePage;
    public CompanySearchPage companySearchPage;
    public OrgUnitPage orgUnitPage;
    public ProcessStartOfDocumentPage processStartOfDocumentPage;
    public GlobalNavBar globalNavBar;
    public DbClone dbClone;

    public StepDefinitions(TestContext context, ChipsHomePage chipsHomePage, CompanySearchPage companySearchPage, OrgUnitPage orgUnitPage, ProcessStartOfDocumentPage processStartOfDocumentPage, GlobalNavBar globalNavBar, DbClone dbClone) {
        this.context = context;
        this.chipsHomePage = chipsHomePage;
        this.companySearchPage = companySearchPage;
        this.orgUnitPage = orgUnitPage;
        this.processStartOfDocumentPage = processStartOfDocumentPage;
        this.globalNavBar = globalNavBar;
        this.dbClone = dbClone;
    }

    @Given("I am logged in as a user in the {string} organisational unit")
    public void iAmLoggedInAsAUserInTheOrganisationalUnit(String orgUnit) {
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


    @Then("I will be able to search for company {string}")
    public void iWillBeAbleToSearchForCompany(String companyNumber) {
        log.info("Searching for company number: {}", companyNumber);
        context.getWebDriver().navigate().to(context.getEnv().config.getString("chips_url") + "/menu/companySearch");
        companySearchPage.searchForCompanyNumber(companyNumber);

    }

    @When("I process the start document for form AD01")
    public void iProcessTheStartDocumentForFormAd01() {
        Date today = new Date();
        globalNavBar.clickProcessFormLabel();
        processStartOfDocumentPage.generateBarcode(today);
        Company company = dbClone.cloneCompanyWithParameterInternal("---\n" + "--- Private Limited Company with No sight required or Super secure PSC indicator\n" + "---\n" + "SELECT *\n" + "FROM corporate_body cb\n" + "             WHERE cb.incorporation_number not like 'c'\n" + "             AND cb.action_code_type_id = 0\n" + "             AND cb.trading_status_type_id = 1\n" + "             AND cb.corporate_body_type_id = 2\n" + "             AND cb.PROOF_TYPE_ID = 0\n" + "             AND cb.language_type_id = 1\n" + "             AND cb.register_location_type_id = 1\n" + "             AND cb.SUPER_SECURE_PSC_IND = 'N'\n" + "             AND cb.corporate_body_short_name NOT LIKE '%(cloned)%'\n" + "             AND (SELECT COUNT (1) FROM sight_required_request sr WHERE sr.corporate_body_id = cb.corporate_body_id)=0\n" + "             AND rownum <= 30\n" + "             ORDER BY dbms_random.value", null);
    }

}
