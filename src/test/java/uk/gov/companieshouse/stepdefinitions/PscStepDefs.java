package uk.gov.companieshouse.stepdefinitions;

import io.cucumber.java.en.And;
import uk.gov.companieshouse.data.datamodel.Company;
import uk.gov.companieshouse.data.dbutil.DbUtil;
import uk.gov.companieshouse.enums.Form;
import uk.gov.companieshouse.pageobjects.ProcessStartOfDocumentPage;
import uk.gov.companieshouse.testdata.enums.CompanyType;
import uk.gov.companieshouse.utils.TestContext;

public class PscStepDefs {

    private TestContext testContext;
    private DbUtil dbUtil;
    private ProcessStartOfDocumentPage processStartOfDocumentPage;

    public PscStepDefs(TestContext testContext,
                       DbUtil dbUtil,
                       ProcessStartOfDocumentPage processStartOfDocumentPage) {
        this.testContext = testContext;
        this.dbUtil = dbUtil;
        this.processStartOfDocumentPage = processStartOfDocumentPage;
    }

    @And("a {string} that has not previously filed a PSC")
    public void processTheStartDocumentForForm(String companyType) {
        CompanyType type = CompanyType.getByType(companyType);
        Company company = dbUtil.cloneCompanyWithParameterInternal(type.getCompanySql(), type.getCorporateBodyId());
        testContext.getUser().setCompanyInContext(company);
    }

    @And("I complete the mandatory details for form {string}")
    public void iCompleteTheMandatoryDetailsForForm(String formType) {
        processStartOfDocumentPage.processForm(testContext.getUser().getCompanyInContext(),
                Form.getFormByType(formType));
    }
}
