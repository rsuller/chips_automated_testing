package uk.gov.companieshouse.stepdefinitions;

import static uk.gov.companieshouse.utils.DateFormat.getDateAsString;

import io.cucumber.java.en.When;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import uk.gov.companieshouse.data.datamodel.Company;
import uk.gov.companieshouse.data.dbutil.DbUtil;
import uk.gov.companieshouse.data.dbutil.sql.CompanySql;
import uk.gov.companieshouse.pageobjects.EfTestHarnessPage;
import uk.gov.companieshouse.pageobjects.EfTestHarnessResponsePage;
import uk.gov.companieshouse.pageobjects.GlobalNavBar;
import uk.gov.companieshouse.testdata.CompanyDetails;
import uk.gov.companieshouse.testdata.DocumentDetails;
import uk.gov.companieshouse.utils.BarcodeGenerator;
import uk.gov.companieshouse.utils.DocumentProcessor;
import uk.gov.companieshouse.utils.TestContext;
import uk.gov.companieshouse.utils.XmlHelper;


public class ElectronicFilingStepDefs {

    public final TestContext testContext;
    private final DbUtil dbUtil;
    private final DocumentDetails documentDetails;
    private final BarcodeGenerator barcodeGenerator;
    private final XmlHelper xmlHelper;
    private final CompanyDetails companyDetails;
    private final EfTestHarnessPage efTestHarnessPage;
    private final EfTestHarnessResponsePage efTestHarnessResponsePage;
    private final DocumentProcessor documentProcessor;
    private final GlobalNavBar globalNavBar;


    /**
     * Required constructor for class.
     */
    public ElectronicFilingStepDefs(TestContext testContext, DbUtil dbUtil, DocumentDetails documentDetails,
                                    BarcodeGenerator barcodeGenerator, XmlHelper xmlHelper,
                                    CompanyDetails companyDetails, EfTestHarnessPage efTestHarnessPage,
                                    EfTestHarnessResponsePage efTestHarnessResponsePage, DocumentProcessor documentProcessor,
                                    GlobalNavBar globalNavBar) {
        this.testContext = testContext;
        this.dbUtil = dbUtil;
        this.documentDetails = documentDetails;
        this.barcodeGenerator = barcodeGenerator;
        this.xmlHelper = xmlHelper;
        this.companyDetails = companyDetails;
        this.efTestHarnessPage = efTestHarnessPage;
        this.efTestHarnessResponsePage = efTestHarnessResponsePage;
        this.documentProcessor = documentProcessor;
        this.globalNavBar = globalNavBar;
    }


    /**
     * Simulate a submission of a form that has been filed through electronic filing.
     */
    @When("I process an e-filed {string} form for a private limited company")
    public void processElectronicFiledForm(String formType) throws IOException {
        testContext.getWebDriver().get(
                testContext.getEnv().config.getString("ef-test-harness"));
        String filename;
        Company company;
        boolean autoAccepted;
        switch (formType) {
            case "AP01":
                filename = "ap01_successful_submission.xml";
                company = dbUtil.cloneCompany(CompanySql.BASE_SQL_PRIVATE_LIMITED_COMPANY_ENG_WALES_ID);
                autoAccepted = true;
                break;
            case "CS01":
                filename = "confirmation_statement_no_updates.xml";
                company = dbUtil.cloneCompany(CompanySql.CS_SQL_LTD_COMPANY_WITH_CS_DUE);
                autoAccepted = true;
                break;
            case "IN01":
                filename = "in01_successful_limited_by_shares.xml";
                company = new Company.CompanyBuilder().createDefaultCompany().build();
                companyDetails.setCompanyObject(company);
                autoAccepted = true;
                break;
            case "MR04":
                filename = "mr04_successful_submission.xml";
                company = dbUtil.cloneCompany(CompanySql.MORTGAGE_SQL_PRIVATE_LIMITED_COMPANY_ID_WITH_MORTGAGES);
                autoAccepted = false;
                break;
            case "SH01":
                filename = "sh01_successful_submission.xml";
                company = dbUtil.cloneCompany(CompanySql.SHARE_CAPITAL_SQL_COMPANY_WITH_SoC_PRESENT);
                autoAccepted = true;
                break;
            case "PSC01":
                filename = "psc01_successful_submission.xml";
                company = dbUtil.cloneCompany(CompanySql.PSC_SQL_LTD_PSC_NOT_PREV_FILED_EW);
                autoAccepted = true;
                break;
            case "PSC02":
                filename = "psc02_successful_submission.xml";
                company = dbUtil.cloneCompany(CompanySql.PSC_SQL_LTD_PSC_NOT_PREV_FILED_EW);
                autoAccepted = true;
                break;
            case "PSC04":
                filename = "psc04_successful_submission.xml";
                company = dbUtil.cloneCompany(CompanySql.PSC_SQL_LTD_COMPANY_WITH_PSC);
                autoAccepted = true;
                break;
            case "PSC05":
                filename = "psc05_successful_submission.xml";
                company = dbUtil.cloneCompany(CompanySql.PSC_SQL_LTD_COMPANY_WITH_RLE_PSC);
                autoAccepted = true;
                break;
            case "PSC07":
                filename = "psc07_successful_submission.xml";
                company = dbUtil.cloneCompany(CompanySql.PSC_SQL_LTD_COMPANY_WITH_PSC);
                autoAccepted = true;
                break;
            case "PSC08":
                filename = "psc08_successful_submission.xml";
                company = dbUtil.cloneCompany(CompanySql.PSC_SQL_LTD_PSC_NOT_PREV_FILED_EW);
                autoAccepted = true;
                break;
            case "PSC09":
                filename = "psc09_successful_submission.xml";
                company = dbUtil.cloneCompany(CompanySql.PSC_SQL_LTD_COMPANY_WITH_PSC_STATEMENT);
                autoAccepted = true;
                break;
            case "LLPSC04":
                filename = "llpsc04_successful_submission.xml";
                company = dbUtil.cloneCompany(CompanySql.PSC_SQL_LLP_WITH_PSC);
                autoAccepted = true;
                break;
            case "TM01":
                filename = "tm01_successful_submission.xml";
                company = dbUtil.cloneCompany(CompanySql.BASE_SQL_LTD_COMPANY_WITH_ACTIVE_DIRECTOR);
                autoAccepted = true;
                break;
            case "TM02":
                filename = "tm02_successful_submission.xml";
                company = dbUtil.cloneCompany(CompanySql.BASE_SQL_LTD_COMPANY_WITH_ACTIVE_SECRETARY);
                autoAccepted = true;
                break;
            case "CH01":
                filename = "ch01_successful_submission.xml";
                company = dbUtil.cloneCompany(CompanySql.BASE_SQL_LTD_COMPANY_WITH_ACTIVE_DIRECTOR);
                autoAccepted = true;
                break;
            case "CH03":
                filename = "ch03_successful_submission.xml";
                company = dbUtil.cloneCompany(CompanySql.BASE_SQL_LTD_COMPANY_WITH_ACTIVE_SECRETARY);
                autoAccepted = true;
                break;
            case "CH04":
                filename = "ch04_successful_submission.xml";
                company = dbUtil.cloneCompany(CompanySql.BASE_SQL_LTD_COMPANY_WITH_ACTIVE_CORPORATE_SECRETARY);
                autoAccepted = true;
                break;
            case "LLCH01":
                filename = "llch01_successful_submission.xml";
                company = dbUtil.cloneCompany(CompanySql.BASE_SQL_LLP_WITH_ACTIVE_MEMBER_EW);
                autoAccepted = true;
                break;
            case "LLCS01":
                filename = "llp_confirmation_statement_no_updates.xml";
                company = dbUtil.cloneCompany(CompanySql.CS_SQL_LLP_WITH_CS_DUE);
                autoAccepted = true;
                break;
            case "LLTM01":
                filename = "lltm01_successful_submission.xml";
                company = dbUtil.cloneCompany(CompanySql.BASE_SQL_LLP_WITH_ACTIVE_MEMBER_EW);
                autoAccepted = true;
                break;
            default:
                throw new RuntimeException("Unable to find SQL for specified form type");
        }
        modifyXml(formType, filename, company);
        String xml = xmlHelper.xml;
        efTestHarnessPage
                .enterFormXml(xml)
                .clickDisableFormTracker()
                .saveForm();
        efTestHarnessResponsePage.verifyFormResponse();
        documentProcessor.checkDocumentSubmission();
        // If the form is not auto-accepted, then the document will be allocated to the user through teamwork/My work queues.
        if (!autoAccepted) {
            globalNavBar.clickSubMenuItem("Work...", "My Work");
            documentProcessor.allocateWorkAndPsod(documentDetails.getFormType(), company);
        }

    }

    private void modifyXml(String formType, String filename, Company company) throws IOException {
        SimpleDateFormat xmlDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        String barcode = barcodeGenerator.generateNewStyleBarcode(today);
        final String todayAsXmlDateString = xmlDateFormatter.format(today);
        final String todayAsChipsDateString = getDateAsString(today);
        documentDetails.setBarcode(barcode);
        documentDetails.setReceivedDate(todayAsChipsDateString);
        documentDetails.setFormType(formType);
        companyDetails.setCompanyObject(company);
        xmlHelper.modifyXml(filename, company, barcode, todayAsXmlDateString);
    }

}
