package uk.gov.companieshouse.pageobjects;

public interface AddressPageInterface {

    // Abstract methods for address page interactions
    AddressPageInterface enterHouseNumber(String houseNumber);

    AddressPageInterface enterPostCode(String postCode);

    AddressPageInterface clickLookUp();

    AddressPageInterface waitUntilStreetPopulated();

    void saveForm();
}


