package uk.gov.companieshouse.utils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.minidev.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.data.datamodel.Company;
import uk.gov.companieshouse.data.dbutil.DbUtil;
import uk.gov.companieshouse.enums.Form;
import uk.gov.companieshouse.pageobjects.ChipsCommonPage;
import uk.gov.companieshouse.pageobjects.GlobalNavBar;
import uk.gov.companieshouse.pageobjects.ProcessStartOfDocumentPage;
import uk.gov.companieshouse.pageobjects.workitems.MyWorkPage;
import uk.gov.companieshouse.pageobjects.workitems.TeamWorkPage;
import uk.gov.companieshouse.testdata.DocumentDetails;


public class DocumentProcessor extends ChipsCommonPage<DocumentProcessor> {

    public final TestContext testContextContext;
    public final ProcessStartOfDocumentPage processStartOfDocumentPage;
    public final GlobalNavBar globalNavBar;
    public final DbUtil dbUtil;
    public final DocumentDetails documentDetails;
    public final BarcodeGenerator barcodeGenerator;
    public final TeamWorkPage teamWorkPage;
    public final MyWorkPage myWorkPage;

    public static final Logger log = LoggerFactory.getLogger(DocumentProcessor.class);


    /**
     * Required constructor for class.
     */
    public DocumentProcessor(TestContext testContext, ProcessStartOfDocumentPage processStartOfDocumentPage,
                             GlobalNavBar globalNavBar, DbUtil dbUtil, DocumentDetails documentDetails,
                             BarcodeGenerator barcodeGenerator, TeamWorkPage teamWorkPage, MyWorkPage myWorkPage) {
        super(testContext);
        this.testContextContext = testContext;
        this.processStartOfDocumentPage = processStartOfDocumentPage;
        this.globalNavBar = globalNavBar;
        this.dbUtil = dbUtil;
        this.documentDetails = documentDetails;
        this.barcodeGenerator = barcodeGenerator;
        this.teamWorkPage = teamWorkPage;
        this.myWorkPage = myWorkPage;
    }

    /**
     * Json builder specific to fes forms scanned from the location specified in the feature file.
     */
    public DocumentProcessor processFesDocumentForLocation(int locationTypeId, String formType, String companyNumber,
                                                           String companyName) throws IOException {
        String batchName = null;
        String batchNumber = null;
        String envelopeId = null;
        if (locationTypeId == 1) {
            batchName = "ENW_140711_0006";
            batchNumber = "66446";
            envelopeId = "9293";

        } else if (locationTypeId == 2) {
            batchName = "SC_140711_0006";
            batchNumber = "66447";
            envelopeId = "9294";

        } else if (locationTypeId == 3) {
            batchName = "NI_140711_0008";
            batchNumber = "66448";
            envelopeId = "9295";
        }
        JSONObject json = new JSONObject();

        json.put("formType", formType);
        json.put("batchName", batchName);
        json.put("batchNumber", batchNumber);
        json.put("envelopeId", envelopeId);
        json.put("paymentInd", "N");
        json.put("companyNumber", companyNumber);
        json.put("scannedLocation", locationTypeId);
        json.put("companyName", companyName);

        postToRestApi(json);
        checkDocumentSubmission();
        return this;
    }

    /**
     * Allocate work item to "My Work" and complete PSOD for the item.
     * Choose different method if the item is an Incorporation document.
     *
     * @param formType The form type to allocate.
     * @param company  The company to allocate.
     */
    public DocumentProcessor allocateWorkAndPsod(String formType, Company company) {
        globalNavBar.waitUntilDisplayed();
        teamWorkPage
                .filterTeamWorkSummaryByCompanyNumberAndFormType(company.getNumber(), formType)
                .allocateMostRecentWorkObjectSelected(formType, "weblogic");
        myWorkPage
                .goToMyWorkPage()
                .openWorkItem("companyNumberColumn", company.getNumber(), formType);
        if (formType.equalsIgnoreCase("NEWINC")) {
            //No need to fill in PSOD company details for an incorporation
            processStartOfDocumentPage.clickProceedLink();
        } else if (documentDetails.getFilingMethod().equals("front-end scanned")) {
            processStartOfDocumentPage.processFesForm(formType, company, Form.getFormByType(formType).isHighRiskForm());
        } else if (documentDetails.getFilingMethod().equals("electronic filing")) {
            // EF forms go straight to the processing screen
            waitUntilFormDisplayed(Form.getFormByType(formType));
        }
        return this;
    }

    private void postToRestApi(JSONObject json) throws IOException {

        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String todayFormatted = sdf.format(today);
        Date postDate;
        try {
            postDate = sdf.parse(todayFormatted);
        } catch (ParseException exception) {
            throw new RuntimeException(exception);
        }
        String barcode = barcodeGenerator.generateNewStyleBarcode(postDate);
        json.put("barcode", barcode);
        documentDetails.setBarcode(barcode);
        log.info("Processing API submission: {}", json);
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        try {
            HttpPost request = new HttpPost(testContextContext.getEnv().config.getString("chips-rest-api"));
            StringEntity params = new StringEntity(json.toString());
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);

            ResponseHandler<String> handler = new BasicResponseHandler();
            String body = handler.handleResponse(response);
            if (!body.equals("0")) {
                throw new Exception("CHIPS REST API didn’t come back with successful response: "
                        + body);
            }
            log.info("Successful request to CHIPS REST API. Proceeding with test");
        } catch (Exception ex) {
            log.error("Submission returned an exception: {}, {}.", ex.getStackTrace(),
                    ex.getMessage());
            ex.printStackTrace();
            httpClient.close();
            throw new RuntimeException("API submission returned an exception: {}.", ex);
        }
    }

    /**
     * Check the document ID exists in the database for the form barcode.
     */
    public String checkDocumentSubmission() {
        String barcode = documentDetails.getBarcode();
        String documentId = dbUtil.getDocumentId(barcode);
        if (documentId == null) {
            throw new RuntimeException(
                    String.format("Document Id not found for barcode %s.", barcode));
        }
        return documentId;
    }

}
