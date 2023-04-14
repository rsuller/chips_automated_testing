package uk.gov.companieshouse.testdata.datamodel;

import static uk.gov.companieshouse.enums.TestDataForenames.getRandomTestForename;
import static uk.gov.companieshouse.enums.TestDataSurnames.getRandomTestSurname;

import java.util.Arrays;
import java.util.List;


public class Director {

    private final String titleField;
    private final String titleOtherField;
    private final String partialDobField;
    private final String forenameField;
    private final String middleNamesField;
    private final String surnameField;
    private final String countryUsuallyResidentField;
    private final String dobField;
    private final String countryField;
    private final String countryOtherField;
    private final String uraBarcodeField;
    private boolean uraBarcodeRequired;
    private boolean noChangeToUra;
    private final List<String> nationalityField;
    private final String occupationField;
    private final Address serviceAddressField;
    private final Address residentialAddressField;

    /**
     * Create a new director with default values.
     */
    private Director(DirectorBuilder directorBuilder) {
        this.titleField = directorBuilder.titleField;
        this.titleOtherField = directorBuilder.titleOtherField;
        this.partialDobField = directorBuilder.partialDobField;
        this.forenameField = directorBuilder.forenameField;
        this.middleNamesField = directorBuilder.middleNamesField;
        this.surnameField = directorBuilder.surnameField;
        this.countryUsuallyResidentField = directorBuilder.countryUsuallyResidentField;
        this.dobField = directorBuilder.dobField;
        this.countryField = directorBuilder.countryField;
        this.countryOtherField = directorBuilder.countryOtherField;
        this.uraBarcodeField = directorBuilder.uraBarcodeField;
        this.uraBarcodeRequired = directorBuilder.uraBarcodeRequired;
        this.noChangeToUra = directorBuilder.noChangeToUra;
        this.nationalityField = directorBuilder.nationalityField;
        this.occupationField = directorBuilder.occupationField;
        this.serviceAddressField = directorBuilder.serviceAddressField;
        this.residentialAddressField = directorBuilder.residentialAddressField;
    }

    public String getTitle() {
        return titleField;
    }

    public String getTitleOther() {
        return titleOtherField;
    }

    public String getPartialDob() {
        return partialDobField;
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

    public String getCountryUsuallyResident() {
        return countryUsuallyResidentField;
    }

    public String getDob() {
        return dobField;
    }

    public String getCountry() {
        return countryField;
    }

    public String getCountryOther() {
        return countryOtherField;
    }


    public String getUraBarcode() {
        return uraBarcodeField;
    }

    public boolean isUraBarcodeRequired() {
        return uraBarcodeRequired;
    }

    public void setUraBarcodeRequired(boolean uraBarcodeRequired) {
        this.uraBarcodeRequired = uraBarcodeRequired;
    }

    public boolean isNoChangeToUra() {
        return noChangeToUra;
    }

    public void setNoChangeToUra(boolean noChangeToUra) {
        this.noChangeToUra = noChangeToUra;
    }

    public List<String> getNationality() {
        return nationalityField;
    }

    public String getOccupation() {
        return occupationField;
    }

    public Address getServiceAddress() {
        return serviceAddressField;
    }

    public Address getResidentialAddress() {
        return residentialAddressField;
    }


    public static class DirectorBuilder {

        private String titleField;
        private String titleOtherField;
        private String partialDobField;
        private String forenameField;
        private String middleNamesField;
        private String surnameField;
        private String countryUsuallyResidentField;
        private String dobField;
        private String countryField;
        private String countryOtherField;
        private String uraBarcodeField;
        private boolean uraBarcodeRequired;
        private boolean noChangeToUra;
        private List<String> nationalityField;
        private String occupationField;
        private Address serviceAddressField;
        private Address residentialAddressField;

        public DirectorBuilder withTitle(final String title) {
            this.titleField = title;
            return this;
        }

        public DirectorBuilder withTitleOther(final String titleOther) {
            this.titleOtherField = titleOther;
            return this;
        }

        public DirectorBuilder withPatialDateOfBirth(final String partialDob) {
            this.partialDobField = partialDob;
            return this;
        }

        public DirectorBuilder withForename(final String forename) {
            this.forenameField = forename;
            return this;
        }

        public DirectorBuilder withMiddleNames(final String middleNames) {
            this.middleNamesField = middleNames;
            return this;
        }

        public DirectorBuilder withSurname(final String surname) {
            this.surnameField = surname;
            return this;
        }

        public DirectorBuilder withCountryUsuallyResident(final String usuallyResident) {
            this.countryUsuallyResidentField = usuallyResident;
            return this;
        }

        public DirectorBuilder withDateOfBirth(final String dob) {
            this.dobField = dob;
            return this;
        }

        public DirectorBuilder withCountry(final String country) {
            this.countryField = country;
            return this;
        }

        public DirectorBuilder withCountryOther(final String countryOther) {
            this.countryOtherField = countryOther;
            return this;
        }

        public DirectorBuilder withUraBarcode(final String uraBarcode) {
            this.uraBarcodeField = uraBarcode;
            return this;
        }

        public DirectorBuilder withNationalities(final List<String> nationalities) {
            this.nationalityField = nationalities;
            return this;
        }

        public DirectorBuilder withOccupation(final String occupation) {
            this.occupationField = occupation;
            return this;
        }

        public DirectorBuilder withServiceAddress(final Address address) {
            this.serviceAddressField = address;
            return this;
        }

        public DirectorBuilder withResidentialAddress(final Address address) {
            this.residentialAddressField = address;
            return this;
        }

        /**
         * Build a new Director that will be used with service address always ro checkbox ticked.
         * Therefore, service address is not necessary.
         */
        public DirectorBuilder createDefaultDirector() {
            final Address residentialAddress = new Address.AddressBuilder().welshAddress().build();
            final Address serviceAddress = new Address.AddressBuilder().englishAddress().build();
            withPatialDateOfBirth("01/1990");
            withTitle("DR");
            withForename(getRandomTestForename());
            withSurname(getRandomTestSurname());
            withServiceAddress(serviceAddress);
            withNationalities(Arrays.asList("Welsh", "Irish"));
            withOccupation("DIRECTOR");
            withDateOfBirth("01/01/1990");
            withCountryUsuallyResident(serviceAddress.getCountry());
            withResidentialAddress(residentialAddress);
            withCountry(residentialAddress.getCountry());
            return this;
        }

        public Director build() {
            return new Director(this);
        }

    }

}
