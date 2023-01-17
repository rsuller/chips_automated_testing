package uk.gov.companieshouse.stepdefinitions;

import io.cucumber.java.en.Given;
import uk.gov.companieshouse.data.datamodel.Address;
import uk.gov.companieshouse.pageobjects.ChangeToRoPage;
import uk.gov.companieshouse.utils.TestContext;


public class ChangeOfAddressStepDefs {

    public TestContext context;
    public ChangeToRoPage changeToRoPage;

    public ChangeOfAddressStepDefs(TestContext context, ChangeToRoPage changeToRoPage) {
        this.context = context;
        this.changeToRoPage = changeToRoPage;
    }

    /**
     * Complete the mandatory details for the registered office or SAIL address.
     */
    @Given("I complete mandatory details to change a registered office address")
    public void completeMandatoryDetailsToChangeARegisteredOfficeAddress() {
        Address address = new Address.AddressBuilder().welshAddress().build();
        changeToRoPage
                .waitUntilAd01Displayed()
                .enterHouseNumber(address.getHouseNumber())
                .enterPostCode(address.getPostcode())
                .clickLookup()
                .waitUntilStreetPopulated()
                .saveForm();
    }

}
