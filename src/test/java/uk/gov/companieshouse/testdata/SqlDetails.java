package uk.gov.companieshouse.testdata;

import uk.gov.companieshouse.data.dbclone.sql.CompanySql;

public class SqlDetails {

    CompanySql companySql;
    Object sqlParameter;

    public void setCompanySql(CompanySql companySql) {
        this.companySql = companySql;
    }

    public CompanySql getCompanySql() {
        return companySql;
    }

    public void setSqlParameter(Object sqlParameter) {
        this.sqlParameter = sqlParameter;
    }

    public Object getSqlParameter() {
        return sqlParameter;
    }

}
