package uk.gov.companieshouse.stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.pageObjects.ChipsHomePage;
import uk.gov.companieshouse.pageObjects.CompanySearchPage;
import uk.gov.companieshouse.pageObjects.OrgUnitPage;
import uk.gov.companieshouse.testData.User;
import uk.gov.companieshouse.utils.ConfigReader;
import uk.gov.companieshouse.utils.TestContext;

public class StepDefinitions {

    public static Logger log = LoggerFactory.getLogger(StepDefinitions.class);

    public TestContext context;
    public ChipsHomePage chipsHomePage;
    public ConfigReader configReader;
    public CompanySearchPage companySearchPage;
    public OrgUnitPage orgUnitPage;

    public StepDefinitions(TestContext context,
                           ChipsHomePage chipsHomePage,
                           ConfigReader configReader,
                           CompanySearchPage companySearchPage,
                           OrgUnitPage orgUnitPage) {
        this.context = context;
        this.chipsHomePage = chipsHomePage;
        this.configReader = configReader;
        this.companySearchPage = companySearchPage;
        this.orgUnitPage = orgUnitPage;
    }

    @Given("I am logged in as a user in the {string} organisational unit")
    public void iAmLoggedInAsAUserInTheOrganisationalUnit(String orgUnit) {
        // Setup user
        User user = new User();
        user.setUser(configReader.getConfigProperty("username"),
                configReader.getConfigProperty("password"));
        context.setUpUser(user);
        // Login user and open Chips
        context.getUser().setOrgUnit(orgUnit);
        chipsHomePage.logInUser(context.getUser().getUsername(),
                context.getUser().getPassword());
        context.getWebDriver().manage().window().maximize();
        context.getWebDriver().navigate().to(
                configReader.getConfigProperty("chips_url")
                        + "/menu/changeOrgUnitAssignment");
        //Select Org. unit
        orgUnitPage.selectOrgUnit(context.getUser().getOrgUnit());
    }


    @Then("I will be able to search for company {string}")
    public void iWillBeAbleToSearchForCompany(String companyNumber) {
        log.info("Searching for company number: {}", companyNumber);
        context.getWebDriver().navigate().to(
                configReader.getConfigProperty("chips_url") +"/menu/companySearch");
        companySearchPage.searchForCompanyNumber(companyNumber);

    }

}
