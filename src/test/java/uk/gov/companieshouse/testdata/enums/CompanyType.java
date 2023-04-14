package uk.gov.companieshouse.testdata.enums;

import uk.gov.companieshouse.data.dbutil.sql.CompanySql;

public enum CompanyType {

    PRIVATE_LIMITED("private_limited_company", CompanySql.BASE_SQL_PRIVATE_LIMITED_COMPANY_ID, 0),
    PRIVATE_LIMITED_PSC("private limited company", CompanySql.PSC_SQL_COMPANY_PSC_NOT_PREV_FILED, 2);
    private final String type;
    private final CompanySql companySql;
    private final int corporateBodyId;

    CompanyType(String type, CompanySql companySql, int corporateBodyId) {
        this.type = type;
        this.companySql = companySql;
        this.corporateBodyId = corporateBodyId;
    }

    /**
     * Return the Company type.
     */
    public static CompanyType getByType(String type) {
        for (CompanyType companyType : values()) {
            if (companyType.type.equals(type)) {
                return companyType;
            }
        }
        throw new RuntimeException("There is no option for: "  + type);
    }

    @Override
    public String toString() {
        return type;
    }

    public CompanySql getCompanySql() {
        return companySql;
    }

    public int getCorporateBodyId() {
        return corporateBodyId;
    }
}
