package uk.gov.companieshouse.pageobjects;

/**
 * Interface for address page interactions.The most common use case for this is to be able to enter an address and save.
 */
public interface AddressPageInterface {

    // Abstract methods for address page interactions
    AddressPageInterface enterHouseNumber(String houseNumber);

    AddressPageInterface enterPostCode(String postCode);

    AddressPageInterface clickLookUp();

    AddressPageInterface waitUntilStreetPopulated();

    void saveForm();
}


