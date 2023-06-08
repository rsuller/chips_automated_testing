package uk.gov.companieshouse.stepdefinitions;

import static uk.gov.companieshouse.data.dbutil.sql.CompanySql.BASE_SQL_LTD_COMPANY_WITH_ACTIVE_DIRECTOR;
import static uk.gov.companieshouse.data.dbutil.sql.CompanySql.BASE_SQL_PRIVATE_LIMITED_COMPANY_ENG_WALES_ID;
import static uk.gov.companieshouse.data.dbutil.sql.CompanySql.CS_SQL_LTD_COMPANY_WITH_CS_DUE;
import static uk.gov.companieshouse.data.dbutil.sql.CompanySql.INS_PRIVATE_LTD_COMPANY_ENG_WALES_WITH_CVL_CASE_ACTION_CODE;
import static uk.gov.companieshouse.data.dbutil.sql.CompanySql.INS_PRIVATE_LTD_COMPANY_ENG_WALES_WITH_MVL_CASE_ACTION_CODE;
import static uk.gov.companieshouse.data.dbutil.sql.CompanySql.INS_PRIVATE_LTD_COMPANY_WITH_RECEIVER_MANAGER_ACTION_CODE;

import io.cucumber.java.en.When;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public final TestContext context;
    public final ChipsHomePage chipsHomePage;
    public final CompanyDetailsScreen companyDetailsScreen;
    public final OrgUnitPage orgUnitPage;
    public final ProcessStartOfDocumentPage processStartOfDocumentPage;
    public final GlobalNavBar globalNavBar;
    public final DbUtil dbUtil;
    public final DocumentDetails documentDetails;
    public final CompanySearchPage companySearchPage;
    public final FesProcessor fesProcessor;

    public static final Logger log = LoggerFactory.getLogger(FesProcessingStepDefs.class);


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
            case "TM01":
                company = dbUtil.cloneCompany(BASE_SQL_LTD_COMPANY_WITH_ACTIVE_DIRECTOR);
                break;
            case "AD01":
                company = dbUtil.cloneCompany(BASE_SQL_PRIVATE_LIMITED_COMPANY_ENG_WALES_ID);
                break;
            case "LIQ01":
            case "LIQ02":
            case "LIQ03":
            case "LIQ13":
                company = dbUtil.cloneCompany(INS_PRIVATE_LTD_COMPANY_ENG_WALES_WITH_MVL_CASE_ACTION_CODE);
                documentDetails.setLiquidationType("MVL");
                break;
            case "LIQ14":
                company = dbUtil.cloneCompany(INS_PRIVATE_LTD_COMPANY_ENG_WALES_WITH_CVL_CASE_ACTION_CODE);
                break;
            case "LRESEX":
                company = dbUtil.cloneCompany(BASE_SQL_PRIVATE_LIMITED_COMPANY_ENG_WALES_ID);
                documentDetails.setLiquidationType("CVL");
                break;
            case "LRESSP":
                company = dbUtil.cloneCompany(BASE_SQL_PRIVATE_LIMITED_COMPANY_ENG_WALES_ID);
                documentDetails.setLiquidationType("MVL");
                break;
            case "NDISC":
                company = dbUtil.cloneCompany(INS_PRIVATE_LTD_COMPANY_WITH_RECEIVER_MANAGER_ACTION_CODE);
                break;
            case "600":
            case "LIQ10":
                // Select an MVL or CVL case type at random and continue processing the 600 form for this case type
                List<String> liquidationTypes = new ArrayList<>();
                liquidationTypes.add("MVL");
                liquidationTypes.add("CVL");
                int index = new Random().nextInt(liquidationTypes.size());
                String caseTypeToCreate = liquidationTypes.get(index);
                if (caseTypeToCreate.equals("MVL")) {
                    company = dbUtil.cloneCompany(INS_PRIVATE_LTD_COMPANY_ENG_WALES_WITH_MVL_CASE_ACTION_CODE);
                    log.info("Processing a form {} with MVL case", formType);
                } else {
                    company = dbUtil.cloneCompany(
                            INS_PRIVATE_LTD_COMPANY_ENG_WALES_WITH_CVL_CASE_ACTION_CODE);
                    log.info("Processing a form {} with CVL case", formType);
                }
                documentDetails.setLiquidationType(caseTypeToCreate);
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
