package uk.gov.companieshouse.stepdefinitions;

import static uk.gov.companieshouse.utils.DateFormat.getDateAsString;
import static uk.gov.companieshouse.utils.RandomStringCreator.randomAlphaNumeric;

import io.cucumber.java.en.When;
import java.util.Date;

import uk.gov.companieshouse.data.datamodel.Company;
import uk.gov.companieshouse.enums.Form;
import uk.gov.companieshouse.pageobjects.ConfirmCompanyIdentityPopup;
import uk.gov.companieshouse.pageobjects.companyname.ChangeCompanyNamePage;
import uk.gov.companieshouse.pageobjects.companysearch.CompanyDetailsScreen;
import uk.gov.companieshouse.testdata.CompanyDetails;
import uk.gov.companieshouse.testdata.DocumentDetails;


public class ChangeCompanyNameStepDefs {
    private final ChangeCompanyNamePage changeCompanyNamePage;
    private final ConfirmCompanyIdentityPopup confirmCompanyIdentityPopup;
    private final CompanyDetails companyDetails;
    private final CompanyDetailsScreen companyDetailsScreen;
    private final DocumentDetails documentDetails;

    private final String fullProposedName = "COMPANY NAME CHANGE " + randomAlphaNumeric(6).toUpperCase()
            + " " + getDateAsString(new Date());
    private final Company company = new Company.CompanyBuilder()
            .createDefaultCompany()
            .withName(fullProposedName)
            .build();


    /**
     * Required constructor for class.
     */
    public ChangeCompanyNameStepDefs(ChangeCompanyNamePage changeCompanyNamePage, CompanyDetails companyDetails,
                                     ConfirmCompanyIdentityPopup confirmCompanyIdentityPopup, CompanyDetailsScreen
                                             companyDetailsScreen, DocumentDetails documentDetails) {
        this.changeCompanyNamePage = changeCompanyNamePage;
        this.companyDetails = companyDetails;
        this.confirmCompanyIdentityPopup = confirmCompanyIdentityPopup;
        this.companyDetailsScreen = companyDetailsScreen;
        this.documentDetails = documentDetails;
    }

    /**
     * Complete all mandatory fields on the Change of Name notice screen in order to change company name by special
     * resolution (NM01).
     */
    @When("I complete details giving notice of a change of company name")
    public void giveNoticeOfCompanyNameChange() {
        changeCompanyNamePage
                .waitUntilFormDisplayed(Form.CONNOT)
                .enterProposedName(company.getName())
                .reKeyProposedName(company.getName())
                .selectNameEnding(company.getNameEnding())
                .selectNm01MethodOfChange()
                .saveForm();
        confirmCompanyIdentityPopup.clickYesLink();
    }

    /**
     * Complete fields on the change of name notice screen required to execute the change of name change after
     * processing the RES15 form.
     */
    @When("I confirm the change of name details by special resolution")
    public void confirmChangeOfNameBySpecialResolution() {
        changeCompanyNamePage
                .waitUntilFormDisplayed(Form.RES15)
                .enterProposedName(company.getName())
                .reKeyProposedName(company.getName())
                .selectNameEnding(company.getNameEnding())
                .enterMeetingDateAsToday()
                .executeChangeOfName()
                .saveForm();
        // set the new company details to be stored memory for company search
        companyDetails.setCompanyObject(company);
        confirmCompanyIdentityPopup.clickYesLink();
        changeCompanyNamePage.useRoAddressAsPresenter();
    }

    /**
     * Check the company enquiry screen for the RES15 transaction and the company name has been changed successfully.
     */
    @When("the resolution is displayed and company name changed")
    public void companyNameIsChangedAndResolutionDisplayed() {
        Form form = Form.RES15;
        companyDetailsScreen
                .verifyNewCompanyName(company.getName() + " " + company.getNameEnding())
                .getExpectedTransactionFromHistory(form.getType(), documentDetails.getReceivedDate(),
                "ACCEPTED", form.getTransactionHistoryPartialDescription());
    }

}
