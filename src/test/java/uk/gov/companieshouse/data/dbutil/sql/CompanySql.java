package uk.gov.companieshouse.data.dbutil.sql;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

public enum CompanySql {

    ACCOUNTS_DUE_SQL_PRIVATE_LTD_COMPANY_ENG_WALES(
            "accounts/private_limited_company_with_accounts_due.sql"),
    ACCOUNTS_DUE_SQL_PUBLIC_LTD_COMPANY_ENG_WALES(
            "accounts/public_limited_company_with_accounts_due.sql"),
    BASE_SQL_PRIVATE_LIMITED_COMPANY_ENG_WALES_ID(
            "base/private_limited_company_eng_wales_id.sql"),
    DISSOLUTION_COMPANY_NO_PREV_DISS_REQUEST_FILED(
            "dissolution/company_no_prev_diss_request_filed.sql"),
    BASE_SQL_PRIVATE_LIMITED_COMPANY_RO_LOCATION_UNSPECIFIED(
            "base/private_limited_company_ro_location_unspecified.sql"),
    BASE_SQL_LTD_COMPANY_WITH_ACTIVE_DIRECTOR("base/ltd_company_with_active_director.sql"),
    BASE_SQL_lTD_WITH_ACTIVE_CORPORATE_DIRECTOR("base/ltd_company_with_active_corporate_director.sql"),
    CS_SQL_LTD_COMPANY_WITH_CS_DUE(
            "confirmation_statement/private_limited_company_eng_wales_with_cs_due.sql"),
    INS_PRIVATE_LTD_COMPANY_ENG_WALES_WITH_MVL_CASE_ACTION_CODE(
            "insolvency/private_limited_company_eng_wales_with_mvl.sql"),
    INS_PRIVATE_LTD_COMPANY_ENG_WALES_WITH_CVL_CASE_ACTION_CODE(
            "insolvency/private_limited_company_eng_wales_with_cvl.sql"),
    INS_PRIVATE_LTD_COMPANY_WITH_RECEIVER_MANAGER_ACTION_CODE(
            "insolvency/private_limited_company_engwales_with_receiver_manager.sql"),
    RESTORATION_SQL_PRIVATE_LIMITED_COMPANY_VOLUNTARY_DISSOLVED_IN_LAST_6_YEARS(
            "restoration/private_ltd_engwales_voluntary_dissolved_in_last_6_years.sql");

    private String sql;

    CompanySql(String sql) {
        if (sql.endsWith(".sql")) {
            loadFromFile(sql);
        } else {
            this.sql = sql;
        }
    }

    /**
     * Loads the SQL from a file in the resource directory.
     *
     * @param resourceIn the file path from resources/sql
     */
    private void loadFromFile(String resourceIn) {
        try {
            String resource = resourceIn.replaceAll("^/+", "");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            Objects.requireNonNull(getClass().getResourceAsStream("/uk/gov/companieshouse/sql/" + resource))));
            // split string by new lines
            String[] rawSql = IOUtils.toString(reader).split("(\r\n|\r|\n)");

            // remove comments and indentation and build up a new string list
            final List<String> lines = new ArrayList<>();
            for (String line : rawSql) {
                // skip comments
                if (line.startsWith("---")) {
                    continue;
                }
                lines.add(line.replaceAll("^\\s+", ""));
            }

            // join the strings again and remove any semicolons that may exist
            // at the end
            this.sql = StringUtils.join(lines, " ").replaceAll(";+$", "");
        } catch (IOException ex) {
            throw new RuntimeException("Unable to load the SQL file src/test/resources/sql"
                    + resourceIn + ". Is the path incorrect?");
        }
    }

    public String getSql() {
        return sql;
    }
}
