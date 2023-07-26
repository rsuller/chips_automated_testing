package uk.gov.companieshouse.stepdefinitions;

import static uk.gov.companieshouse.utils.DateFormat.getDateAsString;

import io.cucumber.java.en.When;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.time.DateUtils;
import uk.gov.companieshouse.data.datamodel.Company;
import uk.gov.companieshouse.data.datamodel.Director;
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
            case "MR04":
                filename = "mr04_successful_submission.xml";
                company = dbUtil.cloneCompany(CompanySql.MORTGAGE_SQL_PRIVATE_LIMITED_COMPANY_ID_WITH_MORTGAGES);
                autoAccepted = false;
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
        // If the form is not auto-accepted, then the document will be allocated to the user through teamwork/My work queues
        if (!autoAccepted) {
            globalNavBar.clickSubMenuItem("Work...", "My Work");
            documentProcessor.allocateWorkAndPsod(documentDetails.getFormType(), company);
        }

    }

    private void modifyXml(String formType, String filename, Company company) throws IOException {
        SimpleDateFormat xmlDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        String barcode = barcodeGenerator.generateNewStyleBarcode(today);
        String xmlDateNextCsDue;
        final String todayAsXmlDateString = xmlDateFormatter.format(today);
        final String todayAsChipsDateString = getDateAsString(today);
        documentDetails.setBarcode(barcode);
        documentDetails.setReceivedDate(todayAsChipsDateString);
        documentDetails.setFormType(formType);
        companyDetails.setCompanyObject(company);
        // CS01 forms require the Next CS Due date to be set
        // all other forms do not.
        if (formType.equals("CS01")) {
            Date lastCs01Date = dbUtil.getLastConfirmationStatementDate(company.getCorporateBodyId());
            Date nextCsDue = DateUtils.addYears(lastCs01Date, 1);
            xmlDateNextCsDue = xmlDateFormatter.format(nextCsDue);
        } else {
            xmlDateNextCsDue = null;
        }
        Director director = new Director.DirectorBuilder().createDefaultDirector().build();
        xmlHelper.modifyXml(filename, company.getCorporateBodyId(), company.getNumber(),
                company.getName(), company.getAlphaKey(), todayAsXmlDateString, barcode, xmlDateNextCsDue, director.getForename(),
                director.getSurname());
    }

}
