package uk.gov.companieshouse.stepdefinitions;

import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.companieshouse.data.datamodel.Address;
import uk.gov.companieshouse.enums.Form;
import uk.gov.companieshouse.pageobjects.ChangeToSailAddressPage;

public class SailAddressStepDefs {

    public static final Logger log = LoggerFactory.getLogger(SailAddressStepDefs.class);
    private ChangeToSailAddressPage changeToSailAddressPage;

    public SailAddressStepDefs(ChangeToSailAddressPage changeToSailAddressPage) {
        this.changeToSailAddressPage = changeToSailAddressPage;
    }

    /**
     * Change the SAIL address details of the company.
     */
    @When("^I change the SAIL address details of the company$")
    public void changeTheSailAddressDetailsOfTheCompany() {
        log.info("I change the SAIL address details of the company");

        Address address = new Address.AddressBuilder().welshAddress().build();
        changeToSailAddressPage
                .waitUntilFormDisplayed(Form.AD02)
                .enterHouseNumber(address.getHouseNumber())
                .enterPostCode(address.getPostcode())
                .clickLookUp()
                .waitUntilStreetPopulated()
                .saveForm();
    }

}
