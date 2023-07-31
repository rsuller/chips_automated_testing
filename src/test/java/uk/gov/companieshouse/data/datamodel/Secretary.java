package uk.gov.companieshouse.data.datamodel;

import static uk.gov.companieshouse.enums.TestDataForenames.getRandomTestForename;
import static uk.gov.companieshouse.enums.TestDataSurnames.getRandomTestSurname;


public class Secretary {

    private final String titleField;
    private final String titleOtherField;
    private final String forenameField;
    private final String middleNamesField;
    private final String surnameField;
    private final Address serviceAddressField;
    private final String countryField;

    /**
     * Create a new secretary with default values.
     */
    private Secretary(SecretaryBuilder secretaryBuilder) {
        this.titleField = secretaryBuilder.titleField;
        this.titleOtherField = secretaryBuilder.titleOtherField;
        this.forenameField = secretaryBuilder.forenameField;
        this.middleNamesField = secretaryBuilder.middleNamesField;
        this.surnameField = secretaryBuilder.surnameField;
        this.serviceAddressField = secretaryBuilder.serviceAddressField;
        this.countryField = secretaryBuilder.countryField;


    }

    public String getTitle() {
        return titleField;
    }

    public String getTitleOther() {
        return titleOtherField;
    }

    public String getForename() {
        return forenameField;
    }

    public String getMiddleNames() {
        return middleNamesField;
    }

    public String getSurname() {
        return surnameField;
    }

    public Address getServiceAddress() {
        return serviceAddressField;
    }

    public String getCountry() {
        return countryField;
    }



    public static class SecretaryBuilder {

        private String titleField;
        private String titleOtherField;
        private String forenameField;
        private String middleNamesField;
        private String surnameField;
        private Address serviceAddressField;
        private String countryField;

        public SecretaryBuilder withTitle(final String title) {
            this.titleField = title;
            return this;
        }

        public SecretaryBuilder withTitleOther(final String titleOther) {
            this.titleOtherField = titleOther;
            return this;
        }

        public SecretaryBuilder withForename(final String forename) {
            this.forenameField = forename;
            return this;
        }

        public SecretaryBuilder withMiddleNames(final String middleNames) {
            this.middleNamesField = middleNames;
            return this;
        }

        public SecretaryBuilder withSurname(final String surname) {
            this.surnameField = surname;
            return this;
        }

        public SecretaryBuilder withServiceAddress(final Address address) {
            this.serviceAddressField = address;
            return this;
        }

        public SecretaryBuilder withCountry(final String country) {
            this.countryField = country;
            return this;
        }

        /**
         * Build a new default Secretary.
         */
        public SecretaryBuilder createDefaultSecretary() {
            final Address residentialAddress = new Address.AddressBuilder().welshAddress().build();
            final Address serviceAddress = new Address.AddressBuilder().englishAddress().build();
            withTitle("DR");
            withForename(getRandomTestForename());
            withSurname(getRandomTestSurname());
            withServiceAddress(serviceAddress);
            withCountry(residentialAddress.getCountry());
            return this;
        }

        public Secretary build() {
            return new Secretary(this);
        }

    }

}
