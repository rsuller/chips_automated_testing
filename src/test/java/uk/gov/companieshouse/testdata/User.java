package uk.gov.companieshouse.testdata;

public class User {

    String username;
    String password;
    String orgUnit;

    public void setOrgUnit(String orgUnit) {
        this.orgUnit = orgUnit;
    }

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
}
