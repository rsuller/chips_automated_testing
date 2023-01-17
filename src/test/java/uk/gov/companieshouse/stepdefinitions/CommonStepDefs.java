package uk.gov.companieshouse.stepdefinitions;

import static uk.gov.companieshouse.data.dbclone.sql.CompanySql.BASE_SQL_PRIVATE_LIMITED_COMPANY_ID;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.data.datamodel.Company;
import uk.gov.companieshouse.data.dbclone.DbClone;
import uk.gov.companieshouse.enums.Forms.Form;
import uk.gov.companieshouse.pageobjects.ChipsHomePage;
import uk.gov.companieshouse.pageobjects.CompanySearchPage;
import uk.gov.companieshouse.pageobjects.OrgUnitPage;
import uk.gov.companieshouse.pageobjects.ProcessStartOfDocumentPage;
import uk.gov.companieshouse.testdata.User;
import uk.gov.companieshouse.utils.GlobalNavBar;
import uk.gov.companieshouse.utils.TestContext;


public class CommonStepDefs {

    public static Logger log = LoggerFactory.getLogger(CommonStepDefs.class);

    public TestContext context;
    public ChipsHomePage chipsHomePage;
    public CompanySearchPage companySearchPage;
    public OrgUnitPage orgUnitPage;
    public ProcessStartOfDocumentPage processStartOfDocumentPage;
    public GlobalNavBar globalNavBar;
    public DbClone dbClone;


    public CommonStepDefs(TestContext context, ChipsHomePage chipsHomePage, CompanySearchPage companySearchPage,
                          OrgUnitPage orgUnitPage, ProcessStartOfDocumentPage processStartOfDocumentPage,
                          GlobalNavBar globalNavBar, DbClone dbClone) {
        this.context = context;
        this.chipsHomePage = chipsHomePage;
        this.companySearchPage = companySearchPage;
        this.orgUnitPage = orgUnitPage;
        this.processStartOfDocumentPage = processStartOfDocumentPage;
        this.globalNavBar = globalNavBar;
        this.dbClone = dbClone;
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

    @Then("I will be able to search for company {string}")
    public void willBeAbleToSearchForCompany(String companyNumber) {
        log.info("Searching for company number: {}", companyNumber);
        context.getWebDriver().navigate().to(context.getEnv().config.getString("chips_url") + "/menu/companySearch");
        companySearchPage.searchForCompanyNumber(companyNumber);

    }

    @When("I process the start document for form {string}")
    public void processTheStartDocumentForForm(String formType) {
        Date today = new Date();
        globalNavBar.clickProcessFormLabel();
        Company company = dbClone.cloneCompanyWithParameterInternal(BASE_SQL_PRIVATE_LIMITED_COMPANY_ID, null);
        processStartOfDocumentPage
                .waitUntilDisplayed()
                .generateBarcode(today);
        processStartOfDocumentPage.selectFormType(Form.getFormByType(formType));

        do {
            processStartOfDocumentPage.setCompanyNumberField(company.getNumber())
                    .setCheckCharactersPrefixField(company.getPrefix())
                    .setCheckCharactersSuffixField(company.getSuffix());
        } while (!retryCloneIfCompanyNameNotPopulated());
        processStartOfDocumentPage.clickProceedLink();
    }

    @Then("the form is submitted without rules fired")
    public void theFormIsSubmittedWithoutFailedRules() {
        processStartOfDocumentPage.waitUntilDisplayed();
    }

    /**
     * Check if the company name is populated correctly on PSOD.
     * Retry company cloning using the SQL initially used if it is not.
     */
    private boolean retryCloneIfCompanyNameNotPopulated() {
        if (processStartOfDocumentPage.getPopulatedCompanyName().equals("")) {
            dbClone.cloneCompanyWithParameterInternal(BASE_SQL_PRIVATE_LIMITED_COMPANY_ID, null);
            return false;
        } else {
            return true;
        }
    }

}
