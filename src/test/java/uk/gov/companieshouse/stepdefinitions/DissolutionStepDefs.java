package uk.gov.companieshouse.stepdefinitions;

import io.cucumber.java.en.When;
import uk.gov.companieshouse.data.datamodel.Address;
import uk.gov.companieshouse.enums.Form;
import uk.gov.companieshouse.pageobjects.dissolution.DissolutionPage;
import uk.gov.companieshouse.utils.TestContext;


public class DissolutionStepDefs {

    public final TestContext context;
    public final DissolutionPage dissolutionPage;

    /**
     * Required constructor for class.
     */
    public DissolutionStepDefs(TestContext context, DissolutionPage dissolutionPage) {
        this.context = context;
        this.dissolutionPage = dissolutionPage;
    }

    /**
     * Complete all fields to complete the DS01 form striking off the company.
     */
    @When("I complete mandatory details to strike off the company from the register")
    public void completeMandatoryDetailsToStrikeOffTheCompanyFromTheRegister() {
        Address address = new Address.AddressBuilder().welshAddress().build();
        dissolutionPage.waitUntilFormDisplayed(Form.DS01);
        dissolutionPage
                .waitUntilFormDisplayed(Form.DS01)
                .enterSignatureDate()
                .enterHouseNumber(address.getHouseNumber())
                .enterPostCode(address.getPostcode())
                .clickLookUp()
                .saveForm();

    }


}
