package uk.gov.companieshouse.utils;

import com.google.common.base.Charsets;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Random;
import java.util.UUID;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlHelper extends ElementInteraction {

    public String xml;
    private final TestContext testContext;

    private static final Logger LOG = LoggerFactory.getLogger(XmlHelper.class);

    /**
     * Required constructor for class.
     */
    public XmlHelper(TestContext testContext) {
        super(testContext);
        this.testContext = testContext;
    }


    /**
     * Locates xml file from folder, replace elements and return the new XML document.
     */
    public XmlHelper modifyXml(String filename, String corporateBodyId, String companyNumber,
                               String companyName, String alphaKey, String receiptDate,
                               String barcode, String csDate, String firstName, String surname)
            throws IOException {
        // Load original XML file
        loadXml(filename);
        //Modify the XML elements
        xml = insertCompanyNumber(xml, companyNumber);
        xml = insertCompanyName(xml, companyName);
        xml = insertReceiptDate(xml, receiptDate);
        xml = insertBarcode(xml, barcode);
        xml = insertCorporateBodyId(xml, corporateBodyId);
        xml = insertAlphaKey(xml, alphaKey);
        xml = insertNewApplicationReference(xml);
        xml = insertNewDocumentNumber(xml);
        xml = insertNewSubmissionReference(xml);
        xml = insertConfirmationStatementDate(xml, csDate);
        xml = insertFirstName(xml, firstName);
        xml = insertSurname(xml, surname);
        return this;
    }

    /**
     * Submits the modified XML to the Chips REST interface.
     *
     * @param filename the name of the filename that has been modified and submitted.
     */
    public void submitXmlRequest(String filename) throws IOException {
        LOG.info("Processing XML submission for updated file: {}", filename);
        HttpPost request = new HttpPost(testContext.getEnv().config.getString("ef-submission-api"));
        StringEntity params = new StringEntity(xml);
        params.setContentType("application/xml");
        request.setEntity(params);
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        String responseEntity = EntityUtils.toString(response.getEntity());
        int responseCode = response.getStatusLine().getStatusCode();

        if (202 != responseCode) {
            throw new RuntimeException("CHIPS REST API failed to return correct response. Code: "
                    + responseCode + ", " + responseEntity);
        }
        LOG.info("Successful request to CHIPS REST API. Proceeding with test");
    }

    /**
     * Load the xml file from the resources folder.
     * @param xmlFile the file to load.
     */
    public void loadXml(String xmlFile) throws IOException {
        // Load original XML file
        LOG.info("Loading XML file " + xmlFile);
        File file = new File("src/test/resources/uk/gov/companieshouse/xml_documents/" + xmlFile);
        BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(file.toPath()));
        xml = new String(IOUtils.toByteArray(bis), Charsets.UTF_8);
    }

    /**
     * Changes any instances of ${RECEIPT_DATE} in the xml with specified date.
     *
     * @param xml xml to be transformed
     * @return xml that was provided with RECEIPT_DATE replaced with date
     */
    private String insertReceiptDate(final String xml, String date) {
        if (xml.contains("${RECEIPT_DATE}")) {
            LOG.info("Replacing ${RECEIPT_DATE} with: " + date);
            return xml.replaceAll("\\$\\{RECEIPT_DATE}", date);
        } else {
            return xml;
        }
    }

    /**
     * Changes any instances of ${COMPANY_NUMBER} in the xml with specified company number.
     *
     * @param xml xml to be transformed
     * @return xml that was provided with COMPANY_NUMBER replaced with companyNumber
     */
    private String insertCompanyNumber(final String xml, String companyNumber) {
        if (xml.contains("${COMPANY_NUMBER}")) {
            LOG.info("Replacing ${COMPANY_NUMBER} with: " + companyNumber);
            return xml.replaceAll("\\$\\{COMPANY_NUMBER}", companyNumber);
        } else {
            return xml;
        }
    }

    /**
     * Changes any instances of ${COMPANY_NAME} in the xml with specified company name.
     *
     * @param xml xml to be transformed
     * @return xml that was provided with COMPANY_NAME replaced with companyName
     */
    private String insertCompanyName(final String xml, String companyName) {
        String newCompanyName;
        if (companyName.contains("&")) {
            newCompanyName = companyName.replaceAll("&", "&amp;");
        } else {
            newCompanyName = companyName;
        }
        if (xml.contains("${COMPANY_NAME}")) {
            LOG.info("Replacing ${COMPANY_NAME} with: " + newCompanyName);
            return xml.replaceAll("\\$\\{COMPANY_NAME}", newCompanyName);
        } else {
            return xml;
        }
    }

    /**
     * Changes any instances of ${BARCODE} in the xml with specified barcode.
     *
     * @param xml xml to be transformed
     * @return xml that was provided with BARCODE replaced with barcode
     */
    private String insertBarcode(final String xml, String barcode) {
        if (xml.contains("${BARCODE}")) {
            LOG.info("Replacing ${BARCODE} with: " + barcode);
            return xml.replaceAll("\\$\\{BARCODE}", barcode);
        } else {
            return xml;
        }
    }

    /**
     * Changes any instances of ${CORPORATE_BODY_ID} in the xml with the ID of company
     * selected from the DB.
     *
     * @param xml xml to be transformed
     * @return xml that was provided with CORPORATE_BODY_ID replaced with corpBodyId
     */
    private String insertCorporateBodyId(final String xml, String corpBodyId) {
        if (xml.contains("${CORPORATE_BODY_ID}")) {
            LOG.info("Replacing ${CORPORATE_BODY_ID} with: " + corpBodyId);
            return xml.replaceAll("\\$\\{CORPORATE_BODY_ID}", corpBodyId);
        } else {
            return xml;
        }
    }

    /**
     * Changes any instances of ${ALPHA_KEY} in the xml with the alphakey of company
     * selected from the DB.
     *
     * @param xml xml to be transformed
     * @return xml that was provided with ALPHA_KEY replaced with alphakey
     */
    private String insertAlphaKey(final String xml, String alphaKey) {
        if (xml.contains("${ALPHA_KEY}")) {
            LOG.info("Replacing ${ALPHA_KEY} with: " + alphaKey);
            return xml.replaceAll("\\$\\{ALPHA_KEY}", alphaKey);
        } else {
            return xml;
        }
    }

    /**
     * Changes any instances of ${APPLICATION_REFERENCE} in the xml with randomly generated
     * information.
     *
     * @param xml xml to be transformed
     * @return xml provided with APPLICATION_REFERENCE replaced with randomApplicationReference
     */
    private String insertNewApplicationReference(final String xml) {
        String randomApplicationReference = UUID.randomUUID().toString();
        if (xml.contains("${APPLICATION_REFERENCE}")) {
            LOG.info("Replacing ${APPLICATION_REFERENCE} with: " + randomApplicationReference);
            return xml.replaceAll("\\$\\{APPLICATION_REFERENCE}", randomApplicationReference);
        } else {
            return xml;
        }
    }

    /**
     * Changes any instances of ${DOCUMENT_NUMBER}
     * in the xml with randomly generated information.
     *
     * @param xml xml to be transformed
     * @return xml provided with DOCUMENT_NUMBER replaced with randomDocNumber
     */
    private String insertNewDocumentNumber(final String xml) {
        if (!xml.contains("${DOCUMENT_NUMBER}")) {
            return xml;
        }
        Random random = new Random();
        String randomDocNumber = String.valueOf(random.nextInt(9999));
        LOG.info("Replacing ${DOCUMENT_NUMBER} with: " + randomDocNumber);
        return xml.replaceAll("\\$\\{DOCUMENT_NUMBER}", randomDocNumber);

    }

    /**
     * Changes any instances of ${SUBMISSION_REFERENCE} in the xml with a randomly generated
     * submission reference.
     *
     * @param xml xml to be transformed
     * @return xml provided with SUBMISSION_REFERENCE replaced with randomSubmissionReference
     */
    private String insertNewSubmissionReference(final String xml) {
        if (!xml.contains("${SUBMISSION_REFERENCE}")) {
            return xml;
        }
        Random random = new Random();
        String num = String.valueOf(random.nextLong());
        // In the case of a negative number being generated, remove the -
        String removeNegative = num.replaceAll("-", "");
        // Remove the digits of the long that are not required.
        String submissionNum = removeNegative.replace(num.subSequence(14, removeNegative.length()),
                "");
        // Insert hyphens to create a submission number.
        String randomSubmissionReference = submissionNum.replaceFirst("(\\d{5})(\\d{5})(\\d+)",
                "$1-$2-$3");
        LOG.info("Replacing ${SUBMISSION_REFERENCE} with: " + randomSubmissionReference);
        return xml.replaceAll("\\$\\{SUBMISSION_REFERENCE}", randomSubmissionReference);
    }

    /**
     * Changes any instances of ${CONFIRMATION_STATEMENT_DATE} in the xml with the CS01 date of company
     * selected from the DB.
     *
     * @param xml xml to be transformed
     * @return xml that was provided with CONFIRMATION_STATEMENT_DATE replaced with confirmationStmtDate
     */
    private String insertConfirmationStatementDate(final String xml, String confirmationStmtDate) {
        if (xml.contains("${CONFIRMATION_STATEMENT_DATE}")) {
            LOG.info("Replacing ${CONFIRMATION_STATEMENT_DATE} with: " + confirmationStmtDate);
            return xml.replaceAll("\\$\\{CONFIRMATION_STATEMENT_DATE}", confirmationStmtDate);
        } else {
            return xml;
        }
    }

    /**
     * Changes any instances of ${FIRST_NAME} in the xml with the first name.
     * @param xml xml to be transformed
     * @return xml that was provided with FIRST_NAME replaced with firstName
     */
    private String insertFirstName(final String xml, String firstName) {
        if (xml.contains("${FIRST_NAME}")) {
            LOG.info("Replacing ${FIRST_NAME} with: " + firstName);
            return xml.replaceAll("\\$\\{FIRST_NAME}", firstName);
        } else {
            return xml;
        }
    }

    /**
     * Changes any instances of ${SURNAME} in the xml with the surname.
     * @param xml xml to be transformed
     * @return xml that was provided with SURNAME replaced with surname
     */
    private String insertSurname(final String xml, String surname) {
        if (xml.contains("${SURNAME}")) {
            LOG.info("Replacing ${SURNAME} with: " + surname);
            return xml.replaceAll("\\$\\{SURNAME}", surname);
        } else {
            return xml;
        }
    }

}
