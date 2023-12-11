package uk.gov.companieshouse.utils;

import com.google.common.base.Charsets;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.data.datamodel.Company;
import uk.gov.companieshouse.data.datamodel.CorporatePersonOfSignificantControl;
import uk.gov.companieshouse.data.datamodel.Director;
import uk.gov.companieshouse.data.datamodel.PersonOfSignificantControl;
import uk.gov.companieshouse.data.datamodel.Secretary;
import uk.gov.companieshouse.data.dbutil.DbUtil;
import uk.gov.companieshouse.testdata.DocumentDetails;

public class XmlHelper extends ElementInteraction {

    public String xml;
    private final DbUtil dbUtil;
    private final Director director = new Director.DirectorBuilder().createDefaultDirector().build();
    private final Secretary secretary = new Secretary.SecretaryBuilder().createDefaultSecretary().build();
    private final PersonOfSignificantControl individualPsc = new PersonOfSignificantControl
            .PersonOfSignificantControlBuilder().createDefaultPsc().build();
    private final CorporatePersonOfSignificantControl relevantLegalEntityPsc = new
            CorporatePersonOfSignificantControl.CorporatePersonOfSignificantControlBuilder().createDefaultCorporatePsc().build();
    private final CorporatePersonOfSignificantControl otherRegistrablePersonPsc = new
            CorporatePersonOfSignificantControl.CorporatePersonOfSignificantControlBuilder().createDefaultCorporatePsc().build();
    private final DocumentDetails documentDetails;


    private static final Logger LOG = LoggerFactory.getLogger(XmlHelper.class);

    /**
     * Required constructor for class.
     */
    public XmlHelper(TestContext testContext, DbUtil dbUtil, DocumentDetails documentDetails) {
        super(testContext);
        this.dbUtil = dbUtil;
        this.documentDetails = documentDetails;
    }


    /**
     * Locates xml file from folder, replace elements and return the new XML document.
     */
    public void modifyXml(String filename, Company company, String barcode, String xmlDate)
            throws IOException {
        // Load original XML file
        loadXml(filename);
        //Modify the XML elements
        xml = insertCompanyNumber(xml, company.getNumber());
        xml = insertCompanyName(xml, company.getName());
        xml = insertReceiptDate(xml, xmlDate);
        xml = insertBarcode(xml, barcode);
        xml = insertCorporateBodyId(xml, company.getCorporateBodyId());
        xml = insertAlphaKey(xml, company.getAlphaKey());
        xml = insertNewApplicationReference(xml);
        xml = insertNewDocumentNumber(xml);
        xml = insertNewSubmissionReference(xml);
        xml = insertConfirmationStatementDate(xml, company);
        xml = insertDirectorFirstName(xml, director.getForename());
        xml = insertDirectorSurname(xml, director.getSurname());
        xml = insertSecretaryFirstName(xml, secretary.getForename());
        xml = insertSecretarySurname(xml, secretary.getSurname());
        xml = insertExistingOfficerDetails(xml, company);
        xml = insertPscFirstName(xml, individualPsc.getForename());
        xml = insertPscSurname(xml, individualPsc.getSurname());
        xml = insertExistingPscName(xml, company);
        xml = insertExistingCorporatePscName(xml, company);
        xml = insertRlePscName(xml, relevantLegalEntityPsc.getEntityName());
        xml = insertOrpPscName(xml, otherRegistrablePersonPsc.getEntityName());
        xml = insertNewChargeNumber(xml);
        xml = insertPscStatement(xml, company);
    }

    /**
     * Load the xml file from the resources folder.
     *
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
     * @return xml that was provided with CONFIRMATION_STATEMENT_DATE replaced with the next CS date due.
     */
    private String insertConfirmationStatementDate(final String xml, Company company) {
        if (xml.contains("${CONFIRMATION_STATEMENT_DATE}")) {
            SimpleDateFormat xmlDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            Date lastCs01Date = dbUtil.getLastConfirmationStatementDate(company.getCorporateBodyId());
            Date nextCsDue = DateUtils.addYears(lastCs01Date, 1);
            LOG.info("Replacing ${CONFIRMATION_STATEMENT_DATE} with: " + nextCsDue);
            String xmlDateNextCsDue = xmlDateFormatter.format(nextCsDue);
            return xml.replaceAll("\\$\\{CONFIRMATION_STATEMENT_DATE}", xmlDateNextCsDue);
        } else {
            return xml;
        }
    }

    /**
     * Changes any instances of ${PSC_STATEMENT} in the xml with the PSC statement returned from the DB.
     *
     * @param xml xml to be transformed
     * @return xml that was provided with PSC_STATEMENT replaced with the correct PSC statement.
     */
    private String insertPscStatement(final String xml, Company company) {
        if (xml.contains("${PSC_STATEMENT}")) {
            String pscStatement = dbUtil.getPscStatement(company.getCorporateBodyId());
            LOG.info("Replacing ${PSC_STATEMENT} with: " + pscStatement);
            return xml.replaceAll("\\$\\{PSC_STATEMENT}", pscStatement);
        } else {
            return xml;
        }
    }

    /**
     * Changes any instances of ${DIRECTOR_FIRST_NAME} in the xml with the first name.
     *
     * @param xml xml to be transformed
     * @return xml that was provided with DIRECTOR_FIRST_NAME replaced with firstName
     */
    private String insertDirectorFirstName(final String xml, String firstName) {
        if (xml.contains("${DIRECTOR_FIRST_NAME}")) {
            LOG.info("Replacing ${DIRECTOR_FIRST_NAME} with: " + firstName);
            return xml.replaceAll("\\$\\{DIRECTOR_FIRST_NAME}", firstName);
        } else {
            return xml;
        }
    }

    /**
     * Changes any instances of ${DIRECTOR_SURNAME} in the xml with the surname.
     *
     * @param xml xml to be transformed
     * @return xml that was provided with DIRECTOR_SURNAME replaced with surname
     */
    private String insertDirectorSurname(final String xml, String surname) {
        if (xml.contains("${DIRECTOR_SURNAME}")) {
            LOG.info("Replacing ${DIRECTOR_SURNAME} with: " + surname);
            return xml.replaceAll("\\$\\{DIRECTOR_SURNAME}", surname);
        } else {
            return xml;
        }
    }

    /**
     * Changes any instances of ${EXISTING_OFFICER_FIRST_NAME}, ${EXISTING_OFFICER_SURNAME} and ${EXISTING_OFFICER_DOB}
     * in the xml with the details of the director selected from the database. Needed for electronic change forms
     *
     * @param xml     xml to be transformed.
     * @param company the corporate body that used to select the existing director from the database.
     * @return xml that was provided with EXISTING_OFFICER_FIRST_NAME replaced with first name, EXISTING_OFFICER_SURNAME
     *     replaced with surname and EXISTING_OFFICER_DOB replaced with the date of birth in XML format.
     */
    private String insertExistingOfficerDetails(final String xml, Company company) {
        if (xml.contains("${EXISTING_OFFICER_FIRST_NAME}") || xml.contains("${EXISTING_OFFICER_SURNAME}")) {
            // Switch the officer to retrieve based on the form type.
            String officerType;
            String formType = documentDetails.getFormType();
            officerType = switch (formType) {
                case "TM01", "CH01" -> "director";
                case "CH04" -> "corporate secretary";
                case "LLCH01" -> "LLP Member";
                default -> "secretary";
            };
            Date formattedDob;
            String xmlDobDate = null;
            SimpleDateFormat officerDobFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat xmlDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            List<String> officerDetails = dbUtil.getOfficerAppointment(company.getCorporateBodyId(), officerType);
            String officerFirstName = officerDetails.get(0);
            String officerSurname = officerDetails.get(1);
            String officerDobDateFromDb = officerDetails.get(2);
            if (officerDobDateFromDb != null) {
                try {
                    formattedDob = officerDobFormatter.parse(officerDobDateFromDb);
                } catch (ParseException exception) {
                    throw new RuntimeException(exception);
                }
                xmlDobDate = xmlDateFormatter.format(formattedDob);
                LOG.info("Replacing ${EXISTING_OFFICER_DOB} with: " + xmlDobDate);
            }
             if (officerFirstName != null) {
                 LOG.info("Replacing ${EXISTING_OFFICER_FIRST_NAME} with: " + officerFirstName);
             }
            LOG.info("Replacing ${EXISTING_OFFICER_SURNAME} with: " + officerSurname);
            return xml
                    .replaceAll("\\$\\{EXISTING_OFFICER_FIRST_NAME}", officerFirstName)
                    .replaceAll("\\$\\{EXISTING_OFFICER_SURNAME}", officerSurname)
                    .replaceAll("\\$\\{EXISTING_OFFICER_DOB}", xmlDobDate);
        } else {
            return xml;
        }
    }

    /**
     * Changes any instances of ${SECRETARY_FIRST_NAME} in the xml with the first name.
     *
     * @param xml xml to be transformed
     * @return xml that was provided with SECRETARY_FIRST_NAME replaced with firstName
     */
    private String insertSecretaryFirstName(final String xml, String firstName) {
        if (xml.contains("${SECRETARY_FIRST_NAME}")) {
            LOG.info("Replacing ${SECRETARY_FIRST_NAME} with: " + firstName);
            return xml.replaceAll("\\$\\{SECRETARY_FIRST_NAME}", firstName);
        } else {
            return xml;
        }
    }

    /**
     * Changes any instances of ${SECRETARY_SURNAME} in the xml with the surname.
     *
     * @param xml xml to be transformed
     * @return xml that was provided with SECRETARY_SURNAME replaced with surname
     */
    private String insertSecretarySurname(final String xml, String surname) {
        if (xml.contains("${SECRETARY_SURNAME}")) {
            LOG.info("Replacing ${SECRETARY_SURNAME} with: " + surname);
            return xml.replaceAll("\\$\\{SECRETARY_SURNAME}", surname);
        } else {
            return xml;
        }
    }

    /**
     * Changes any instances of ${PSC_FIRST_NAME} in the xml with the firstName.
     *
     * @param xml xml to be transformed
     * @return xml that was provided with PSC_FIRST_NAME replaced with firstName
     */
    private String insertPscFirstName(final String xml, String firstName) {
        if (xml.contains("${PSC_FIRST_NAME}")) {
            LOG.info("Replacing ${PSC_FIRST_NAME} with: " + firstName);
            return xml.replaceAll("\\$\\{PSC_FIRST_NAME}", firstName);
        } else {
            return xml;
        }
    }

    /**
     * Changes any instances of ${PSC_SURNAME} in the xml with the surname.
     *
     * @param xml xml to be transformed
     * @return xml that was provided with PSC_SURNAME replaced with surname
     */
    private String insertPscSurname(final String xml, String surname) {
        if (xml.contains("${PSC_SURNAME}")) {
            LOG.info("Replacing ${PSC_SURNAME} with: " + surname);
            return xml.replaceAll("\\$\\{PSC_SURNAME}", surname);
        } else {
            return xml;
        }
    }

    /**
     * Changes any instances of ${EXISTING_PSC_FIRST_NAME} and ${EXISTING_PSC_SURNAME} in the xml with the name of the PSC selected
     * from the Database. Needed for electronic change forms
     *
     * @param xml     xml to be transformed.
     * @param company the corporate body that used to select the existing PSC from the database.
     * @return xml that was provided with EXISTING_PSC_FIRST_NAME replaced with first name and EXISTING_PSC_SURNAME replaced with surname
     */
    private String insertExistingPscName(final String xml, Company company) {
        if (xml.contains("${EXISTING_PSC_FIRST_NAME}")) {
            List<String> pscFullName = dbUtil.getIndividualPscAppointmentName(company.getCorporateBodyId());
            String pscFirstName = pscFullName.get(0);
            String pscSurname = pscFullName.get(1);
            LOG.info("Replacing ${EXISTING_PSC_FIRST_NAME} with: " + pscFirstName);
            LOG.info("Replacing ${EXISTING_PSC_SURNAME} with: " + pscSurname);
            return xml.replaceAll("\\$\\{EXISTING_PSC_FIRST_NAME}", pscFirstName).replaceAll("\\$\\{EXISTING_PSC_SURNAME}", pscSurname);
        } else {
            return xml;
        }
    }

    /**
     * Changes any instances of ${EXISTING_CORPORATE_PSC_NAME}in the xml with the name of the PSC selected
     * from the Database. Needed for electronic change forms
     *
     * @param xml     xml to be transformed.
     * @param company the corporate body that used to select the existing corporate PSC from the database.
     * @return xml that was provided with EXISTING_CORPORATE_PSC_NAME replaced with corporate PSC name
     */
    private String insertExistingCorporatePscName(final String xml, Company company) {
        if (xml.contains("${EXISTING_CORPORATE_PSC_NAME}")) {
            String corporatePscName = dbUtil.getCorporatePscAppointmentName(company.getCorporateBodyId());
            LOG.info("Replacing ${EXISTING_CORPORATE_PSC_NAME} with: " + corporatePscName);
            return xml.replaceAll("\\$\\{EXISTING_CORPORATE_PSC_NAME}", corporatePscName).replaceAll("\\$\\{EXISTING_PSC_SURNAME}", corporatePscName);
        } else {
            return xml;
        }
    }

    /**
     * Changes any instances of ${RLE_PSC_NAME} in the xml with the entity name.
     *
     * @param xml xml to be transformed
     * @return xml that was provided with RLE_PSC_NAME replaced with entityName
     */
    private String insertRlePscName(final String xml, String entityName) {
        if (xml.contains("${RLE_PSC_NAME}")) {
            LOG.info("Replacing ${RLE_PSC_NAME} with: " + entityName);
            return xml.replaceAll("\\$\\{RLE_PSC_NAME}", entityName);
        } else {
            return xml;
        }
    }

    /**
     * Changes any instances of ${ORP_PSC_NAME} in the xml with the entity name.
     *
     * @param xml xml to be transformed
     * @return xml that was provided with ORP_PSC_NAME replaced with entityName
     */
    private String insertOrpPscName(final String xml, String entityName) {
        if (xml.contains("${ORP_PSC_NAME}")) {
            LOG.info("Replacing ${ORP_PSC_NAME} with: " + entityName);
            return xml.replaceAll("\\$\\{ORP_PSC_NAME}", entityName);
        } else {
            return xml;
        }
    }

    /**
     * Changes any instances of ${CHARGE_NUMBER}
     * in the xml with randomly generated information.
     *
     * @param xml xml to be transformed
     * @return xml provided with CHARGE_NUMBER replaced with randomChargeNumber
     */
    private String insertNewChargeNumber(final String xml) {
        if (!xml.contains("${CHARGE_NUMBER}")) {
            return xml;
        }
        int length = 12;
        Random random = new Random();
        char[] digits = new char[length];
        digits[0] = (char) (random.nextInt(9) + '1');
        for (int i = 1; i < length; i++) {
            digits[i] = (char) (random.nextInt(10) + '0');
        }
        String randomChargeNumber = String.valueOf(Long.parseLong(new String(digits)));
        LOG.info("Replacing ${CHARGE_NUMBER} with: " + randomChargeNumber);
        return xml.replaceAll("\\$\\{CHARGE_NUMBER}", randomChargeNumber);
    }

}