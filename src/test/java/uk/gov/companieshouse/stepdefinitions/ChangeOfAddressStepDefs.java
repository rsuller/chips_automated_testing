package uk.gov.companieshouse.stepdefinitions;

import io.cucumber.java.en.Given;
import uk.gov.companieshouse.data.datamodel.Address;
import uk.gov.companieshouse.enums.Form;
import uk.gov.companieshouse.pageobjects.companyaddresses.ChangeToRoPage;

public class ChangeOfAddressStepDefs {
    public final ChangeToRoPage changeToRoPage;

    /**
     * Required constructor for class.
     */
    public ChangeOfAddressStepDefs(ChangeToRoPage changeToRoPage) {
        this.changeToRoPage = changeToRoPage;
    }

    /**
     * Complete the mandatory details to change the registered office of the company.
     */
    @Given("I complete mandatory details to change a registered office address")
    public void completeMandatoryDetailsToChangeARegisteredOfficeAddress() {
        Address address = new Address.AddressBuilder().welshAddress().build();
        changeToRoPage
                .waitUntilFormDisplayed(Form.AD01)
                .enterHouseNumber(address.getHouseNumber())
                .enterPostCode(address.getPostcode())
                .clickLookUp()
                .waitUntilStreetPopulated()
                .saveForm();
    }

}
