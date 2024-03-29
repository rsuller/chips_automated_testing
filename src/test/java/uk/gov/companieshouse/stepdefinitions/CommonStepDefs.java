package uk.gov.companieshouse.stepdefinitions;

import static uk.gov.companieshouse.data.dbutil.sql.CompanySql.BASE_SQL_PRIVATE_LIMITED_COMPANY_ENG_WALES_ID;
import static uk.gov.companieshouse.data.dbutil.sql.CompanySql.BASE_SQL_lTD_WITH_ACTIVE_CORPORATE_DIRECTOR;
import static uk.gov.companieshouse.data.dbutil.sql.CompanySql.CS_SQL_LTD_COMPANY_WITH_CS_DUE;
import static uk.gov.companieshouse.data.dbutil.sql.CompanySql.DISSOLUTION_COMPANY_NO_PREV_DISS_REQUEST_FILED;
import static uk.gov.companieshouse.data.dbutil.sql.CompanySql.RESTORATION_SQL_PRIVATE_LIMITED_COMPANY_VOLUNTARY_DISSOLVED_IN_LAST_6_YEARS;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.data.datamodel.Company;
import uk.gov.companieshouse.data.dbutil.DbUtil;

import uk.gov.companieshouse.enums.Form;
import uk.gov.companieshouse.pageobjects.ChipsHomePage;
import uk.gov.companieshouse.pageobjects.GlobalNavBar;
import uk.gov.companieshouse.pageobjects.OrgUnitPage;
import uk.gov.companieshouse.pageobjects.ProcessStartOfDocumentPage;
import uk.gov.companieshouse.pageobjects.companysearch.CompanyDetailsScreen;
import uk.gov.companieshouse.pageobjects.companysearch.CompanySearchPage;
import uk.gov.companieshouse.testdata.CompanyDetails;
import uk.gov.companieshouse.testdata.DocumentDetails;
import uk.gov.companieshouse.testdata.User;
import uk.gov.companieshouse.utils.TestContext;

public class CommonStepDefs {

    public static final Logger log = LoggerFactory.getLogger(CommonStepDefs.class);

    public final TestContext context;
    public final ChipsHomePage chipsHomePage;
    public final CompanyDetailsScreen companyDetailsScreen;
    public final OrgUnitPage orgUnitPage;
    public final ProcessStartOfDocumentPage processStartOfDocumentPage;
    public final GlobalNavBar globalNavBar;
    public final DbUtil dbUtil;
    public final DocumentDetails documentDetails;
    public final CompanySearchPage companySearchPage;
    public final CompanyDetails companyDetails;

    /**
     * Required constructor for class.
     */
    public CommonStepDefs(TestContext context, ChipsHomePage chipsHomePage, CompanyDetailsScreen companyDetailsScreen,
                          OrgUnitPage orgUnitPage, ProcessStartOfDocumentPage processStartOfDocumentPage,
                          GlobalNavBar globalNavBar, DbUtil dbUtil, DocumentDetails documentDetails,
                          CompanySearchPage companySearchPage, CompanyDetails companyDetails) {
        this.context = context;
        this.chipsHomePage = chipsHomePage;
        this.companyDetailsScreen = companyDetailsScreen;
        this.orgUnitPage = orgUnitPage;
        this.processStartOfDocumentPage = processStartOfDocumentPage;
        this.globalNavBar = globalNavBar;
        this.dbUtil = dbUtil;
        this.documentDetails = documentDetails;
        this.companySearchPage = companySearchPage;
        this.companyDetails = companyDetails;
    }

    /**
     * Common step definition to login to chips and switch to the organisational unit required.
     *
     * @param orgUnit the org unit to switch to.
     */
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

    /**
     * Common step definition to check the company transaction history for a particular form.
     *
     * @param formName the form name and details to look for.
     */
    @Then("^company history information is updated with the accepted (.*) transaction$")
    public void companyHistoryInformationIsUpdated(String formName) {
        log.info("Checking company history information is updated with the accepted {} transaction",
                formName);
        Form form = Form.getFormByType(formName);
        String descriptionToCheck;
        companySearchPage
                .findCompanyByNameFromMenu()
                .openCompanyDetails();
        if (formName.equals("AA")) {
            SimpleDateFormat enquiryScreenFormatter = new SimpleDateFormat("dd/MM/yy");
            String storedAccountsDate = documentDetails.getAccountsMud();
            Date date;
            String formattedDate;
            try {
                date = enquiryScreenFormatter.parse(storedAccountsDate);
                formattedDate = enquiryScreenFormatter.format(date);
            } catch (ParseException exception) {
                throw new RuntimeException(exception);
            }
            descriptionToCheck = formattedDate + " " + documentDetails.getAccountsType().toUpperCase();
        } else if (formName.equalsIgnoreCase("RT01")) {
            String restorationDate = documentDetails.getReceivedDate();
            descriptionToCheck = form.getTransactionHistoryPartialDescription() + " " + restorationDate;
        }
        else {
            descriptionToCheck = form.getTransactionHistoryPartialDescription();
        }
        companyDetailsScreen
                .waitUntilDisplayed()
                .getExpectedTransactionFromHistory(form.getType(), documentDetails.getReceivedDate(), "ACCEPTED",
                        descriptionToCheck);
    }

    /**
     * Clone company using SQL based on the form and process this form with data selected.
     *
     * @param formType the form to be processed.
     */
    @And("I process the start document for form {string}")
    public void processTheStartDocumentForForm(String formType) {
        globalNavBar.clickProcessFormLabel();
        Company company = null;
        switch (formType) {
            case "CH02":
                company = dbUtil.cloneCompany(BASE_SQL_lTD_WITH_ACTIVE_CORPORATE_DIRECTOR);
                break;
            case "AD01":
            case "CONNOT":
            case "AD02":
                company = dbUtil.cloneCompany(BASE_SQL_PRIVATE_LIMITED_COMPANY_ENG_WALES_ID);
                break;
            case "DS01":
                company = dbUtil.cloneCompany(DISSOLUTION_COMPANY_NO_PREV_DISS_REQUEST_FILED);
                break;
            case "RT01":
                company = dbUtil.cloneCompany(RESTORATION_SQL_PRIVATE_LIMITED_COMPANY_VOLUNTARY_DISSOLVED_IN_LAST_6_YEARS);
                break;
            case "CS01":
                company = dbUtil.cloneCompany(CS_SQL_LTD_COMPANY_WITH_CS_DUE);
                break;
            case "RES15":
                company = companyDetails.getCompanyObject();
                break;
            default:
                log.error("There is no current option for form {}", formType);
                break;

        }
        processStartOfDocumentPage.processForm(company, Form.getFormByType(formType));
        documentDetails.setFormType(formType);

    }

    @And("^I select a current active appointment$")
    public void selectCurrentActiveAppointment() {
        // Select the first appointment from the list available
        chipsHomePage.selectFirstOfficer(documentDetails.getFormType());
    }


}
