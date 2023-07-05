package uk.gov.companieshouse.stepdefinitions;

import static uk.gov.companieshouse.utils.DateFormat.getDateAsString;

import io.cucumber.java.en.When;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.time.DateUtils;
import uk.gov.companieshouse.data.datamodel.Company;
import uk.gov.companieshouse.data.dbutil.DbUtil;
import uk.gov.companieshouse.data.dbutil.sql.CompanySql;
import uk.gov.companieshouse.testdata.CompanyDetails;
import uk.gov.companieshouse.testdata.DocumentDetails;
import uk.gov.companieshouse.utils.BarcodeGenerator;
import uk.gov.companieshouse.utils.TestContext;
import uk.gov.companieshouse.utils.XmlHelper;


public class ElectronicFilingStepDefs {

    public final TestContext context;
    private final DbUtil dbUtil;
    private final DocumentDetails documentDetails;
    private final BarcodeGenerator barcodeGenerator;
    private final XmlHelper xmlHelper;
    private final CompanyDetails companyDetails;


    /**
     * Required constructor for class.
     */
    public ElectronicFilingStepDefs(TestContext context, DbUtil dbUtil, DocumentDetails documentDetails,
                                    BarcodeGenerator barcodeGenerator, XmlHelper xmlHelper,
                                    CompanyDetails companyDetails) {
        this.context = context;
        this.dbUtil = dbUtil;
        this.documentDetails = documentDetails;
        this.barcodeGenerator = barcodeGenerator;
        this.xmlHelper = xmlHelper;
        this.companyDetails = companyDetails;
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
                        company.getName(), company.getAlphaKey(), todayAsXmlDateString, barcode, xmlDateNextCsDue)
                .submitXmlRequest(filename);
    }

}
