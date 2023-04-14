package uk.gov.companieshouse.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import uk.gov.companieshouse.pageobjects.GlobalNavBar;
import uk.gov.companieshouse.pageobjects.pscpages.CreatePscPage;
import uk.gov.companieshouse.testdata.datamodel.Company;
import uk.gov.companieshouse.data.dbutil.DbUtil;
import uk.gov.companieshouse.enums.Form;
import uk.gov.companieshouse.pageobjects.ProcessStartOfDocumentPage;
import uk.gov.companieshouse.testdata.datamodel.PersonOfSignificantControl;
import uk.gov.companieshouse.testdata.enums.CompanyType;
import uk.gov.companieshouse.utils.TestContext;

public class PscStepDefs {

    private final TestContext testContext;
    private final DbUtil dbUtil;
    private final ProcessStartOfDocumentPage processStartOfDocumentPage;
    private final GlobalNavBar globalNavBar;
    private final CreatePscPage createPscPage;

    public PscStepDefs(TestContext testContext,
                       DbUtil dbUtil,
                       ProcessStartOfDocumentPage processStartOfDocumentPage,
                       GlobalNavBar globalNavBar,
                       CreatePscPage createPscPage) {
        this.testContext = testContext;
        this.dbUtil = dbUtil;
        this.processStartOfDocumentPage = processStartOfDocumentPage;
        this.globalNavBar = globalNavBar;
        this.createPscPage = createPscPage;
    }

    @And("a {string} that has not previously filed a PSC")
    public void processTheStartDocumentForForm(String companyType) {
        CompanyType type = CompanyType.getByType(companyType);
        Company company = dbUtil.cloneCompanyWithParameterInternal(type.getCompanySql(), type.getCorporateBodyId());
        testContext.getUser().setCompanyInContext(company);
    }

    @When("I complete the mandatory details for form {string}")
    public void iCompleteTheMandatoryDetailsForForm(String formType) {
        globalNavBar.clickProcessFormLabel();

        processStartOfDocumentPage.processForm(testContext.getUser().getCompanyInContext(),
                Form.getFormByType(formType));

        createPscPage.enterNewPscDetails(
                new PersonOfSignificantControl.Builder().defaultPscDetail().build());

    }
}
