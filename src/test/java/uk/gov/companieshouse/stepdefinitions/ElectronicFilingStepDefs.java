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
import uk.gov.companieshouse.testdata.CompanyDetails;
import uk.gov.companieshouse.testdata.DocumentDetails;
import uk.gov.companieshouse.utils.BarcodeGenerator;
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


    /**
     * Required constructor for class.
     */
    public ElectronicFilingStepDefs(TestContext testContext, DbUtil dbUtil, DocumentDetails documentDetails,
                                    BarcodeGenerator barcodeGenerator, XmlHelper xmlHelper,
                                    CompanyDetails companyDetails, EfTestHarnessPage efTestHarnessPage,
                                    EfTestHarnessResponsePage efTestHarnessResponsePage) {
        this.testContext = testContext;
        this.dbUtil = dbUtil;
        this.documentDetails = documentDetails;
        this.barcodeGenerator = barcodeGenerator;
        this.xmlHelper = xmlHelper;
        this.companyDetails = companyDetails;
        this.efTestHarnessPage = efTestHarnessPage;
        this.efTestHarnessResponsePage = efTestHarnessResponsePage;
    }


    /**
     * Simulate a submission of a CS01 form that has been filed through electronic filing.
     */
    @When("I process a no update e-filed CS01 form for a private limited company")
    public void processElectronicFiledCS01Form() throws IOException {
        final String filename = "confirmation_statement_no_updates.xml";
        SimpleDateFormat xmlDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        String barcode = barcodeGenerator.generateNewStyleBarcode(today);
        final String todayAsXmlDateString = xmlDateFormatter.format(today);
        final String todayAsChipsDateString = getDateAsString(today);
        final Company company = dbUtil.cloneCompany(CompanySql.CS_SQL_LTD_COMPANY_WITH_CS_DUE);
        documentDetails.setBarcode(barcode);
        documentDetails.setReceivedDate(todayAsChipsDateString);
        companyDetails.setCompanyObject(company);
        Date lastCs01Date = dbUtil.getLastConfirmationStatementDate(company.getCorporateBodyId());
        Date nextCsDue = DateUtils.addYears(lastCs01Date, 1);
        String xmlDateNextCsDue = xmlDateFormatter.format(nextCsDue);

        xmlHelper
                .modifyXml(filename, company.getCorporateBodyId(), company.getNumber(),
                        company.getName(), company.getAlphaKey(), todayAsXmlDateString, barcode, xmlDateNextCsDue,
                        null, null)
                .submitXmlRequest(filename);
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
        switch (formType) {
            case "AP01":
                filename = "ap01_successful_submission.xml";
                company = dbUtil.cloneCompany(CompanySql.BASE_SQL_PRIVATE_LIMITED_COMPANY_ENG_WALES_ID);
                break;
            case "MR04":
                filename = "mr04_successful_submission.xml";
                company = dbUtil.cloneCompany(CompanySql.MORTGAGE_SQL_PRIVATE_LIMITED_COMPANY_ID_WITH_MORTGAGES);
                break;
            default:
                throw new RuntimeException("Unable to find SQL for specified form type");

        }
        SimpleDateFormat xmlDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        String barcode = barcodeGenerator.generateNewStyleBarcode(today);
        final String todayAsXmlDateString = xmlDateFormatter.format(today);
        final String todayAsChipsDateString = getDateAsString(today);
        documentDetails.setBarcode(barcode);
        documentDetails.setReceivedDate(todayAsChipsDateString);
        companyDetails.setCompanyObject(company);
        Director director = new Director.DirectorBuilder().createDefaultDirector().build();
        xmlHelper.modifyXml(filename, company.getCorporateBodyId(), company.getNumber(),
                company.getName(), company.getAlphaKey(), todayAsXmlDateString, barcode, null, director.getForename(),
                director.getSurname());
        String xml = xmlHelper.xml;
        efTestHarnessPage
                .enterFormXml(xml)
                .saveForm();
        efTestHarnessResponsePage
                .verifyFormResponse()
                .verifyFormInTransDocXmlTable();
    }

    @When("I allocate and select the work item for processing")
    public void allocateAndSelectWorkItem() {
        efTestHarnessResponsePage
                .allocateWorkItem()
                .selectWorkItem();
    }


}
