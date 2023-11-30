package uk.gov.companieshouse.data.dbutil;

import com.typesafe.config.ConfigException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.data.datamodel.Company;
import uk.gov.companieshouse.data.dbutil.sql.CompanySql;
import uk.gov.companieshouse.testdata.SqlDetails;
import uk.gov.companieshouse.utils.TestContext;

public class DbUtil {

    public final TestContext testContext;
    public final SqlDetails sqlDetails;
    private static final Logger LOG = LoggerFactory.getLogger(DbUtil.class);

    /**
     * Required constructor for class.
     */
    public DbUtil(TestContext testContext, SqlDetails sqlDetails) {
        this.testContext = testContext;
        this.sqlDetails = sqlDetails;
        PageFactory.initElements(testContext.getWebDriver(), this);
    }

    public Company cloneCompany(CompanySql sql) {
        return cloneCompanyWithParameterInternal(sql, null);
    }

    /**
     * Clone a company with parameter specified added to the QL file specified.
     *
     * @param sql       the SQL file to use.
     * @param parameter the parameter to be added the SQL statement.
     */
    public Company cloneCompanyWithParameterInternal(CompanySql sql, Object parameter) {
        boolean disableCloning;
        String env = testContext.getEnv().config.getString("chips-db-user");
        try {
            disableCloning = testContext.getEnv().config.getBoolean("disable-company-cloning");
        } catch (ConfigException ex) {
            LOG.warn("No config found for: disable-company-cloning. Setting to false.");
            disableCloning = false;
        }
        LOG.info("Attempting to use SQL: {}", sql);
        if (disableCloning) {
            try {
                String companyNumber = dbQueryCriteriaCompanyId(sql.getSql(), parameter);
                Company company = dbGetCompanyFromDb(companyNumber);
                String message = String.format("Company selected from DB: %s %s", companyNumber, company.getName());
                //writeToScenario(message);
                LOG.info(message);
                sqlDetails.setCompanySql(sql);
                sqlDetails.setSqlParameter(parameter);
                return company;
            } catch (SQLException | InstantiationException | IllegalAccessException ex) {
                String message = "No company data found for the given query in environment: " + env;
                //writeToScenario(message);
                LOG.error(message, ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException("Could not select a company using sql: " + sql.getSql());
        }

        String companyNumberToClone = null;
        int retryCount = 5;

        while (retryCount > 0) {
            try {
                companyNumberToClone = dbQueryCriteriaCompanyId(sql.getSql(), parameter);
                String companyNumberCloned = dbCloneCompany(companyNumberToClone);
                Company company = dbGetCompanyFromDb(companyNumberCloned);
                final String message = "Environment: " + env + " - Successfully cloned company " + companyNumberToClone
                        + " to new company " + companyNumberCloned + " [" + company.getName() + "]";
                sqlDetails.setCompanySql(sql);
                //writeToScenario(message);
                LOG.info(message);
                return company;
            } catch (Exception ex) {
                String message = "Environment: " + env + " - Error attempting clone for company number " + companyNumberToClone
                        + ":\n " + ex.getMessage();
                //writeToScenario(message);
                LOG.error(message, ex);
                retryCount--;
            }
        }
        throw new RuntimeException("Could not clone company using sql: " + sql.getSql());
    }

    /**
     * get transaction documentID.
     */
    public String getDocumentId(final String barcode) {
        final String sql = "select input_document_id "
                + "from transaction_doc_xml "
                + "inner join transaction "
                + "using (transaction_id) "
                + "where form_barcode = ?";
        int maxTries = 50;
        String docId = null;
        for (int i = 1; i <= maxTries; ++i) {
            LOG.info("Polling DB looking for the Document ID of barcode {}. Attempt {} of {} ",
                    barcode, i, maxTries);
            try (Connection connection = dbGetConnection();
                 PreparedStatement stmt = createPreparedStatement(connection, sql, barcode);
                 ResultSet rs = stmt.executeQuery()) {
                rs.next();
                docId = rs.getString("input_document_id");
                LOG.info("Document ID found: {}.  Continuing with test", docId);
                break;
            } catch (SQLException  exception) {
                if (i == maxTries) {
                    LOG.error("Error attempting to find document ID: {}", exception.getMessage());
                    return null;
                }
                LOG.info("Document ID not found, retrying");
            }
        }
        return docId;
    }

    /**
     * Use the corporateBodyId stored in memory to get the latest confirmation statement date for that company.
     * @param corporateBodyId the ID of the company used to search the DB for.
     */
    public Date getLastConfirmationStatementDate(String corporateBodyId) {
        final String sql = "select * from confirmation_statement cs where corporate_body_id = ?"
                + "order by CONFIRM_STMT_MADE_UP_DATE desc";

        try (Connection conn = dbGetConnection();
            PreparedStatement preparedStatement = createPreparedStatement(conn, sql, corporateBodyId);
            ResultSet rs = preparedStatement.executeQuery()) {
            rs.next();
            Date confirmationStatementDate = rs.getDate("CONFIRM_STMT_MADE_UP_DATE");
            conn.close();
            LOG.info("Last confirmation statement filed on {}", confirmationStatementDate);
            return confirmationStatementDate;
        } catch (SQLException  exception) {
            throw new RuntimeException("Unable to get confirmation statement date from DB", exception);
        }

    }

    /**
     * Use the corporateBodyId stored in memory to return the PSC statement(s) for that company.
     * @param corporateBodyId the ID of the company used to search the DB for.
     */
    public String getPscStatement(String corporateBodyId) {
        final String sql = "select * "
                + "from psc_statement_type pst "
                + "join psc_statement ps on ps.psc_statement_type_id = pst.psc_statement_type_id "
                + "where corporate_body_id = ?";

        try (Connection conn = dbGetConnection();
             PreparedStatement preparedStatement = createPreparedStatement(conn, sql, corporateBodyId);
             ResultSet rs = preparedStatement.executeQuery()) {
            rs.next();
            String pscStatement = rs.getString("PSC_STATEMENT_TYPE_DESC");
            String xmlPscStatement = rs.getString("LABEL_KEY");
            conn.close();
            LOG.info("PSC Statement found: {}", pscStatement);
            LOG.info("XML format PSC statement: {}", xmlPscStatement);
            return xmlPscStatement;
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to get PSC statement from DB", exception);
        }

    }

    /**
     * Use the corporateBodyId stored in memory to return the individual PSC appointment details for that company.
     * @param corporateBodyId the ID of the company used to search the DB for.
     */
    public List<String> getIndividualPscAppointmentName(String corporateBodyId) {
        final String sql = "select * "
                + "from corporate_body_appointment "
                + "where corporate_body_id = ? "
                + "AND APPOINTMENT_TYPE_ID = 5007 "
                + "AND resignation_ind = 'N' ";

        try (Connection conn = dbGetConnection();
             PreparedStatement preparedStatement = createPreparedStatement(conn, sql, corporateBodyId);
             ResultSet rs = preparedStatement.executeQuery()) {
            rs.next();
            String pscForename = rs.getString("OFFICER_FORENAME_1");
            String pscSurname = rs.getString("OFFICER_SURNAME");
            List<String> pscFullName = new ArrayList<>();
            pscFullName.add(pscForename);
            pscFullName.add(pscSurname);
            conn.close();
            LOG.info("PSC found: {} {}", pscForename, pscSurname);
            return pscFullName;
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to get Individual PSC appointment from DB", exception);
        }

    }

    /**
     * Use the corporateBodyId stored in memory to return the director appointment details for that company.
     * @param corporateBodyId the ID of the company used to search the DB for.
     */
    public List<String> getDirectorAppointment(String corporateBodyId) {
        final String sql = "select cba.officer_forename_1, cba.officer_surname, od.officer_date_of_birth "
                + "from corporate_body_appointment cba "
                + "join officer_detail od on cba.officer_id = od.officer_id "
                + "where corporate_body_id = ? "
                + "AND cba.APPOINTMENT_TYPE_ID = 2 "
                + "AND cba.resignation_ind = 'N'";

        try (Connection conn = dbGetConnection();
             PreparedStatement preparedStatement = createPreparedStatement(conn, sql, corporateBodyId);
             ResultSet rs = preparedStatement.executeQuery()) {
            rs.next();
            String directorForename = rs.getString("OFFICER_FORENAME_1");
            String directorSurname = rs.getString("OFFICER_SURNAME");
            String directorDob = rs.getString("OFFICER_DATE_OF_BIRTH");
            List<String> directorFullDetails = new ArrayList<>();
            directorFullDetails.add(directorForename);
            directorFullDetails.add(directorSurname);
            directorFullDetails.add(directorDob);
            conn.close();
            LOG.info("Director found: {} {}", directorForename, directorSurname);
            return directorFullDetails;
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to get director appointment from DB", exception);
        }

    }

    /**
     * Use the corporateBodyId stored in memory to return the director appointment details for that company.
     * @param corporateBodyId the ID of the company used to search the DB for.
     */
    public List<String> getSecretaryAppointment(String corporateBodyId) {
        final String sql = "select * "
                + "from corporate_body_appointment "
                + "where corporate_body_id = ? "
                + "AND APPOINTMENT_TYPE_ID = 1 "
                + "AND resignation_ind = 'N' ";

        try (Connection conn = dbGetConnection();
             PreparedStatement preparedStatement = createPreparedStatement(conn, sql, corporateBodyId);
             ResultSet rs = preparedStatement.executeQuery()) {
            rs.next();
            String directorForename = rs.getString("OFFICER_FORENAME_1");
            String directorSurname = rs.getString("OFFICER_SURNAME");
            List<String> directorFullName = new ArrayList<>();
            directorFullName.add(directorForename);
            directorFullName.add(directorSurname);
            conn.close();
            LOG.info("Director found: {} {}", directorForename, directorSurname);
            return directorFullName;
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to get secretary appointment from DB", exception);
        }

    }

    /**
     * Use the corporateBodyId stored in memory to return the corporate PSC appointment details for that company.
     * @param corporateBodyId the ID of the company used to search the DB for.
     */
    public String getCorporatePscAppointmentName(String corporateBodyId) {
        final String sql = "select * "
                + "from corporate_body_appointment "
                + "where corporate_body_id = ? "
                + "AND APPOINTMENT_TYPE_ID = 5008 "
                + "AND resignation_ind = 'N' ";

        try (Connection conn = dbGetConnection();
             PreparedStatement preparedStatement = createPreparedStatement(conn, sql, corporateBodyId);
             ResultSet rs = preparedStatement.executeQuery()) {
            rs.next();
            String corporatePscName = rs.getString("OFFICER_SURNAME");
            conn.close();
            LOG.info("Corporate PSC found: {}", corporatePscName);
            return corporatePscName;
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to get PSC appointment from DB", exception);
        }


    }
  
    private PreparedStatement createPreparedStatement(Connection conn, String sql, Object... params) throws SQLException {
        final PreparedStatement ps = conn.prepareStatement(sql);

        for (int i = 0; i < params.length; ++i) {
            if (params[i] != null) {
                ps.setObject(i + 1, params[i]);
            }
        }

        return ps;
    }

    private String dbQueryCriteriaCompanyId(String sql, Object intParameter) throws SQLException {
        String companyNumber = null;
        Connection conn;
        conn = dbGetConnection();
        do {
            PreparedStatement preparedStatement = createPreparedStatement(conn, sql, intParameter);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                companyNumber = resultSet.getString("incorporation_number");
            }
        } while (companyNumber == null);

        conn.close();
        return companyNumber;
    }

    private String dbCloneCompany(String companyNumber) throws SQLException {
        final String sql = "{ ? = call tedium_pkg_generic.clonecorporate_body (?) }";

        Connection conn = dbGetConnection();
        CallableStatement cstmt = conn.prepareCall(sql);
        {
            cstmt.setQueryTimeout(1800);
            cstmt.registerOutParameter(1, Types.VARCHAR);
            cstmt.setString(2, companyNumber);
            cstmt.executeUpdate();
            return cstmt.getNString(1);
        }
    }

    private Company dbGetCompanyFromDb(String companyNumber) throws SQLException,
            ClassNotFoundException, InstantiationException, IllegalAccessException {
        final String sql = "select * from corporate_body cb where cb.incorporation_number = ?";
        Connection conn = dbGetConnection();
        PreparedStatement preparedStatement = createPreparedStatement(conn, sql, companyNumber);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            String alphaKey = rs.getString("corporate_body_name_alphakey");
            String corporateBodyId = rs.getString("corporate_body_id");
            String companyName = rs.getString("corporate_body_short_name").replace("(cloned)", "");
            String nameEnding = rs.getString("corporate_body_name").replace(companyName, "").trim();
            Date incorporationDate = rs.getDate("incorporation_date");
            conn.close();
            return new Company.CompanyBuilder().createDefaultCompany()
                    .withNumber(companyNumber)
                    .withName(companyName)
                    .withNameEnding(nameEnding)
                    .withIncorporationDate(incorporationDate)
                    .withCorporateBodyId(corporateBodyId)
                    .withAlphaKey(alphaKey)
                    .build();
        } else {
            throw new RuntimeException("Unable to get company from DB");
        }
    }

    private Connection dbGetConnection() throws SQLException {
        String user = testContext.getEnv().config.getString("chips-db-user");
        String pass = testContext.getEnv().config.getString("chips-db-pass");
        String url = testContext.getEnv().config.getString("chips-jdbc");

        Properties prop = new Properties();
        prop.setProperty("user", user);
        prop.setProperty("password", pass);
        return DriverManager.getConnection(url, prop);
    }


}
