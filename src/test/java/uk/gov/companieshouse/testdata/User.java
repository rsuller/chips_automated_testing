package uk.gov.companieshouse.testdata;

import uk.gov.companieshouse.testdata.datamodel.Company;

/**
 * This is the CHIPS user. A CHIPS user will have a username and password to access the service.
 * The CHIPS user will have access to various organisational units with specific privileges, based on access level.
 * The user will also have the company it is filing for.
 */
public class User {

    String username;
    String password;
    String orgUnit;
    Company company;

    public void setOrgUnit(String orgUnit) {
        this.orgUnit = orgUnit;
    }

    /**
     * Get org unit or use the default option if not available.
     */
    public String getOrgUnit() {
        if (null == orgUnit) {
            // Return a default org unit
            orgUnit = "EF Registration Team";
        }
        return orgUnit;
    }


    public void setUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setCompanyInContext(Company company) {
        this.company = company;
    }

    public Company getCompanyInContext() {
        return company;
    }
}
