package uk.gov.companieshouse.testdata;

import uk.gov.companieshouse.data.datamodel.Company;

public class CompanyDetails {

    Company company;

    public void setCompanyObject(Company company) {
        this.company = company;
    }

    public Company getCompanyObject() {
        return company;
    }

}
