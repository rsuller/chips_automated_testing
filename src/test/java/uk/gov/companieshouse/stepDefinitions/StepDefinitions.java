package uk.gov.companieshouse.stepDefinitions;

import io.cucumber.java.en.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.pageObjects.ChipsHomePage;
import uk.gov.companieshouse.pageObjects.CompanySearchPage;
import uk.gov.companieshouse.pageObjects.OrgUnitPage;
import uk.gov.companieshouse.testData.User;
import uk.gov.companieshouse.utils.FileReader;
import uk.gov.companieshouse.utils.TestContext;

public class StepDefinitions {

    public static Logger log = LoggerFactory.getLogger(StepDefinitions.class);

    public TestContext context;
    public ChipsHomePage chipsHomePage;
    public FileReader fileReader;
    public CompanySearchPage companySearchPage;
    public OrgUnitPage orgUnitPage;

    public StepDefinitions(TestContext context,
                           ChipsHomePage chipsHomePage,
                           FileReader fileReader,
                           CompanySearchPage companySearchPage,
                           OrgUnitPage orgUnitPage) {
        this.context = context;
        this.chipsHomePage = chipsHomePage;
        this.fileReader = fileReader;
        this.companySearchPage = companySearchPage;
        this.orgUnitPage = orgUnitPage;
    }

    @Given("I am an authorised user")
    public void iAmAnAuthorisedUser() {
        User user = new User();
        user.setUser(fileReader.getConfigProperty("username"),
                fileReader.getConfigProperty("password"));
        context.setUpUser(user);

    }

    @When("I access CHIPS")
    public void iAccessCHIPS() {
        context.getWebDriver().get(
                fileReader.getConfigProperty("chips_url"));
        chipsHomePage.logInUser(context.getUser().getUsername(),
                context.getUser().getPassword());
    }

    @Then("I will be able to search for company {string}")
    public void iWillBeAbleToSearchForCompany(String companyNumber) {
        log.info("Searching for company number: {}", companyNumber);
        context.getWebDriver().navigate().to(
                fileReader.getConfigProperty("chips_url") +"/menu/companySearch");
        companySearchPage.searchForCompanyNumber(companyNumber);

    }

    @And("I am a user of the {string} department")
    public void iAmAUserOfTheDepartment(String orgUnit) {
        context.getUser().setOrgUnit(orgUnit);
    }

    @Then("I will be able to file for the correct department")
    public void iWillBeAbleToFileForTheCorrectDepartment() {
        context.getWebDriver().navigate().to(
                fileReader.getConfigProperty("chips_url")
                        + "menu/uam/changeOrgUnitAssignment");

        orgUnitPage.selectOrgUnit(context.getUser().getOrgUnit());
    }
}
