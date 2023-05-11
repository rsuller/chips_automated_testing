package uk.gov.companieshouse.stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.pageobjects.CompanyDetailsScreen;
import uk.gov.companieshouse.pageobjects.InsolvencyPage;
import uk.gov.companieshouse.pageobjects.ViewInsolvencyCaseDetailsPage;
import uk.gov.companieshouse.utils.TestContext;


public class InsolvencyStepDefs {

    public TestContext context;
    public InsolvencyPage insolvencyPage;
    public ViewInsolvencyCaseDetailsPage viewInsolvencyCaseDetailsPage;
    public CompanyDetailsScreen companyDetailsScreen;

    public static final Logger log = LoggerFactory.getLogger(InsolvencyStepDefs.class);


    /**
     * Required constructor for class.
     */
    public InsolvencyStepDefs(TestContext context, InsolvencyPage insolvencyPage, ViewInsolvencyCaseDetailsPage
            viewInsolvencyCaseDetailsPage, CompanyDetailsScreen companyDetailsScreen) {
        this.context = context;
        this.insolvencyPage = insolvencyPage;
        this.viewInsolvencyCaseDetailsPage = viewInsolvencyCaseDetailsPage;
        this.companyDetailsScreen = companyDetailsScreen;
    }

    /**
     * Perform the required actions to create an insolvency case.
     */
    @When("I create an insolvency case")
    public void createInsolvencyCase() {
        insolvencyPage
                .clickCreateInsolvencyCase()
                .enterDateSolvencySworn()
                .saveForm();
        viewInsolvencyCaseDetailsPage.waitUntilCaseDetailsPageDisplayed();
    }

    @Then("the company action code remains {string}")
    public void theCompanyActionCodeIsChangedToOrRemains(String actionCodeDesc) {
        companyDetailsScreen.verifyActionCode(actionCodeDesc);

    }


}
