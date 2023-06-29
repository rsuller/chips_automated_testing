package uk.gov.companieshouse.stepdefinitions;

import io.cucumber.java.en.When;
import uk.gov.companieshouse.enums.Form;
import uk.gov.companieshouse.pageobjects.ChangeCompanyNamePage;
import uk.gov.companieshouse.pageobjects.ConfirmCompanyIdentityPopup;
import uk.gov.companieshouse.testdata.CompanyDetails;

import java.util.Date;

import static uk.gov.companieshouse.utils.DateFormat.getDateAsString;
import static uk.gov.companieshouse.utils.RandomStringCreator.randomAlphaNumeric;

public class ChangeCompanyNameStepDefs {
    private final ChangeCompanyNamePage changeCompanyNamePage;
    private final CompanyDetails companyDetails;
    private final ConfirmCompanyIdentityPopup confirmCompanyIdentityPopup;

    public ChangeCompanyNameStepDefs(ChangeCompanyNamePage changeCompanyNamePage, CompanyDetails companyDetails,
                                     ConfirmCompanyIdentityPopup confirmCompanyIdentityPopup) {
        this.changeCompanyNamePage = changeCompanyNamePage;
        this.companyDetails = companyDetails;
        this.confirmCompanyIdentityPopup = confirmCompanyIdentityPopup;
    }

    @When("I complete details giving notice of a change of company name")
    public void giveNoticeOfCompanyNameChange() {
        String fullProposedName = "COMPANY " + randomAlphaNumeric(6) + " " + getDateAsString(new Date());
        changeCompanyNamePage
                .waitUntilFormDisplayed(Form.CONNOT)
                .enterProposedName(fullProposedName)
                .reKeyProposedName(fullProposedName)
                .selectNameEnding(companyDetails.getCompanyObject().getNameEnding().trim())
                .selectMethodOfChange("NM01 - Resolution")
                .saveForm();
        confirmCompanyIdentityPopup.clickYesLink();
    }

    @When("I confirm the change of name details by special resolution")
    public void confirmChangeOfNameBySpecialResolution() {


    }

    @When("the resolution is displayed and company name changed")
    public void comanyNameIsChangedAndResolutionDisplayed() {


    }

}
