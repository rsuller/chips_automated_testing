package uk.gov.companieshouse.data.dbclone;

import com.typesafe.config.ConfigException;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.data.dataModel.Company;
import uk.gov.companieshouse.data.dbclone.sql.CompanySql;
import uk.gov.companieshouse.utils.TestContext;

import javax.sql.DataSource;
import java.sql.*;
import java.text.ParseException;
import java.util.Properties;

public class DbClone {

    public TestContext testContext;
    private static final Logger LOG = LoggerFactory.getLogger(DbClone.class);

    private volatile DataSource dataSource;


    public DbClone(TestContext testContext) {
        this.testContext = testContext;
        PageFactory.initElements(testContext.getWebDriver(), this);
    }

    public Company cloneCompanyWithParameterInternal(CompanySql sql, Object parameter) {
        boolean disableCloning;
        String env = testContext.getEnv().config.getString("chips-db-user");
        try {
            disableCloning = testContext.getEnv().config.getBoolean("disable-company-cloning");
        } catch (ConfigException ex) {
            LOG.warn("No config found for: disable-company-cloning. Setting to false.");
            disableCloning = false;
        }
        LOG.debug("Attempting to use SQL: {}", sql);
        if (disableCloning) {
            try {
                String companyNumber = dbQueryCriteriaCompanyId(sql.getSql(), parameter);
                Company company = dbGetCompanyFromDb(companyNumber);
                String message = String.format("Company selected from DB: %s %s", companyNumber, company.getName());
                //writeToScenario(message);
                LOG.info(message);
                return company;
            } catch (SQLException | InstantiationException | IllegalAccessException ex) {
                String message = "No company data found for the given query in environment: " + env;
                //writeToScenario(message);
                LOG.error(message, ex);
            } catch (ParseException | ClassNotFoundException ex) {
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
                final String message = "Environment: " + env + " - Successfully cloned company " + companyNumberToClone + " to new company " + companyNumberCloned + " [" + company.getName() + "]";

                //writeToScenario(message);
                LOG.info(message);
                return company;
            } catch (Exception ex) {
                String message = "Environment: " + env + " - Error attempting clone for company number " + companyNumberToClone + ":\n " + ex.getMessage();
                //writeToScenario(message);
                LOG.error(message, ex);
                retryCount--;
            }
        }
        throw new RuntimeException("Could not clone company using sql: " + sql.getSql());
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
        try {
            conn = dbGetConnection();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
        do {
            PreparedStatement pStm = createPreparedStatement(conn, sql, intParameter);
            ResultSet resultSet = pStm.executeQuery();
            resultSet.next();
            companyNumber = resultSet.getString("incorporation_number");
        } while (companyNumber == null);

        return companyNumber;
    }

    private String dbCloneCompany(String companyNumber) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
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

    private Company dbGetCompanyFromDb(String companyNumber) throws SQLException, ParseException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        final String sql = "select * from corporate_body cb where cb.incorporation_number = ?";

        Connection conn = dbGetConnection();
        PreparedStatement pStm = createPreparedStatement(conn, sql, companyNumber);
        ResultSet rs = pStm.executeQuery();
        {
            rs.next();
            String alphaKey = rs.getString("corporate_body_name_alphakey");
            String corporateBodyId = rs.getString("corporate_body_id");
            String companyName = rs.getString("corporate_body_short_name").replace("(cloned)", "");
            String nameEnding = rs.getString("corporate_body_name").replace(companyName, "").trim();
            Date incorporationDate = rs.getDate("incorporation_date");

            return new Company.CompanyBuilder().createDefaultCompany().withNumber(companyNumber).withName(companyName).withNameEnding(nameEnding).withIncorporationDate(incorporationDate).withCorporateBodyId(corporateBodyId).withAlphaKey(alphaKey).build();
        }
    }

    private Connection dbGetConnection() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
        String user = testContext.getEnv().config.getString("chips-db-user");
        String pass = testContext.getEnv().config.getString("chips-db-pass");
        String url = testContext.getEnv().config.getString("chips-jdbc");

        Properties prop = new Properties();
        prop.setProperty("user", user);
        prop.setProperty("password", pass);
        return DriverManager.getConnection(url, prop);
    }
}
