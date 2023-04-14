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
    BASE_SQL_PRIVATE_LIMITED_COMPANY_ID(
            "base/private_limited_company_id.sql"),
    DISSOLUTION_COMPANY_NO_PREV_DISS_REQUEST_FILED(
            "dissolution/company_no_prev_diss_request_filed.sql"),
    BASE_SQL_PRIVATE_LIMITED_COMPANY_RO_LOCATION_UNSPECIFIED(
            "base/private_limited_company_ro_location_unspecified.sql"),
    BASE_SQL_LTD_COMPANY_WITH_ACTIVE_DIRECTOR("base/ltd_company_with_active_director.sql"),
    BASE_SQL_lTD_WITH_ACTIVE_CORPORATE_DIRECTOR("base/ltd_company_with_active_corporate_director.sql"),
    PSC_SQL_COMPANY_PSC_NOT_PREV_FILED("psc/company_psc_not_prev_filed.sql");

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
                            Objects.requireNonNull(getClass().getResourceAsStream("/sql/" + resource))));
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
