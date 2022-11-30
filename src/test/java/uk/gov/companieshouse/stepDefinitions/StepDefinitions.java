package uk.gov.companieshouse.stepDefinitions;

import io.cucumber.java.en.*;

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

    @Given("I am an authorised user")
    public void iAmAnAuthorisedUser() {
        User user = new User();
        user.setUser(configReader.getConfigProperty("username"),
                configReader.getConfigProperty("password"));
        context.setUpUser(user);

    }

    @When("I access CHIPS")
    public void iAccessCHIPS() {
        chipsHomePage.logInUser(context.getUser().getUsername(),
                context.getUser().getPassword());
        context.getWebDriver().manage().window().maximize();
    }

    @Then("I will be able to search for company {string}")
    public void iWillBeAbleToSearchForCompany(String companyNumber) {
        log.info("Searching for company number: {}", companyNumber);
        context.getWebDriver().navigate().to(
                configReader.getConfigProperty("chips_url") +"/menu/companySearch");
        companySearchPage.searchForCompanyNumber(companyNumber);

    }

    @And("I am a user of the {string} department")
    public void iAmAUserOfTheDepartment(String orgUnit) {
        context.getUser().setOrgUnit(orgUnit);
    }

    @Then("I will be able to file for the correct department")
    public void iWillBeAbleToFileForTheCorrectDepartment() {
        context.getWebDriver().navigate().to(
                configReader.getConfigProperty("chips_url")
                        + "/menu/changeOrgUnitAssignment");

        orgUnitPage.selectOrgUnit(context.getUser().getOrgUnit());
    }
}
