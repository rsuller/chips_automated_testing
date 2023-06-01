package uk.gov.companieshouse.stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.pageobjects.CompanyDetailsScreen;
import uk.gov.companieshouse.pageobjects.InsolvencyLandingScreen;
import uk.gov.companieshouse.pageobjects.InsolvencyPractitionerDetailsScreen;
import uk.gov.companieshouse.pageobjects.InsolvencyPractitionerSearchScreen;
import uk.gov.companieshouse.pageobjects.LiquidationScreen;
import uk.gov.companieshouse.pageobjects.ViewInsolvencyCaseDetailsPage;
import uk.gov.companieshouse.pageobjects.ViewInsolvencyPractitionerAddressPage;
import uk.gov.companieshouse.testdata.DocumentDetails;
import uk.gov.companieshouse.utils.TestContext;


public class InsolvencyStepDefs {

    public final TestContext context;
    public final InsolvencyLandingScreen insolvencyLandingScreen;
    public final ViewInsolvencyCaseDetailsPage viewInsolvencyCaseDetailsPage;
    public final CompanyDetailsScreen companyDetailsScreen;
    public final DocumentDetails documentDetails;
    public final LiquidationScreen liquidationScreen;
    public final InsolvencyPractitionerSearchScreen insolvencyPractitionerSearchScreen;
    public final InsolvencyPractitionerDetailsScreen insolvencyPractitionerDetailsScreen;
    public final ViewInsolvencyPractitionerAddressPage insolvencyPractitionerAddressPage;

    public static final Logger log = LoggerFactory.getLogger(InsolvencyStepDefs.class);


    /**
     * Required constructor for class.
     */
    public InsolvencyStepDefs(TestContext context, InsolvencyLandingScreen insolvencyLandingScreen, ViewInsolvencyCaseDetailsPage
            viewInsolvencyCaseDetailsPage, CompanyDetailsScreen companyDetailsScreen, DocumentDetails documentDetails,
                              LiquidationScreen liquidationScreen, InsolvencyPractitionerSearchScreen
                                      insolvencyPractitionerSearchScreen, InsolvencyPractitionerDetailsScreen insolvencyPractitionerDetailsScreen,
                              ViewInsolvencyPractitionerAddressPage insolvencyPractitionerAddressPage) {
        this.context = context;
        this.insolvencyLandingScreen = insolvencyLandingScreen;
        this.viewInsolvencyCaseDetailsPage = viewInsolvencyCaseDetailsPage;
        this.companyDetailsScreen = companyDetailsScreen;
        this.documentDetails = documentDetails;
        this.liquidationScreen = liquidationScreen;
        this.insolvencyPractitionerSearchScreen = insolvencyPractitionerSearchScreen;
        this.insolvencyPractitionerDetailsScreen = insolvencyPractitionerDetailsScreen;
        this.insolvencyPractitionerAddressPage = insolvencyPractitionerAddressPage;
    }

    /**
     * Perform the required actions to create an insolvency case.
     */
    @When("I create an insolvency case")
    public void createInsolvencyCase() {
        String insolvencyType = documentDetails.getLiquidationType();
        String formType = documentDetails.getFormType();
        insolvencyLandingScreen
                .waitUntilFormDisplayed()
                .createInsolvencyCase(insolvencyType);
        if (formType.equalsIgnoreCase("LIQ01")) {
            liquidationScreen.enterDateSolvencySworn();
        } else {
            liquidationScreen.enterCaseStartDate();
        }
        liquidationScreen.saveForm();
        viewInsolvencyCaseDetailsPage.waitUntilCaseDetailsPageDisplayed();
    }

    /**
     * Create a liquidation case selecting an existing IP.
     */
    @When("I create a voluntary liquidation case using a selected IP")
    public void createVoluntaryLiquidationCase() {
        insolvencyLandingScreen.waitUntilFormDisplayed();
        String liquidationType = documentDetails.getLiquidationType();
        log.info("Liquidation type is {}", liquidationType);
        insolvencyLandingScreen.createInsolvencyCase(liquidationType);
        insolvencyPractitionerSearchScreen.searchAndSelectIp();
        insolvencyPractitionerDetailsScreen.clickFirstCompleteAddressRow();
        insolvencyPractitionerAddressPage.clickSelectIpLink();
        insolvencyLandingScreen
                .enterIpApptDate(documentDetails.getReceivedDate())
                .saveForm();
        insolvencyLandingScreen.clickLiveCaseRadioButton().saveForm();
    }

    @Then("the company action code remains {string}")
    public void theCompanyActionCodeIsChangedToOrRemains(String actionCodeDesc) {
        companyDetailsScreen.verifyActionCode(actionCodeDesc);

    }

    /**
     * Select a case and enter yesterday for period end date.
     */
    @When("I select a case and enter a period end date")
    public void selectACaseAndEnterAPeriodEndDate() {
        insolvencyLandingScreen
                .waitUntilFormDisplayed()
                .selectFirstCase()
                .clickProcessStatementLink();
        liquidationScreen
                .enterYesterdayAsPeriodEndDate()
                .saveProcessStatement();
        viewInsolvencyCaseDetailsPage.waitUntilCaseDetailsPageDisplayed();
    }

    /**
     * Select a case and cease an IP appointment.
     */
    @When("I cease an Insolvency Practitioner")
    public void ceaseIpAppointment() {
        insolvencyLandingScreen
                .waitUntilFormDisplayed()
                .selectFirstCase()
                .clickCeaseIpLink();
        insolvencyPractitionerDetailsScreen
                .clickFirstIp()
                .enterIpCeaseDate()
                .saveForm();
    }

}
