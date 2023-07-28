package uk.gov.companieshouse.data.datamodel;

import static uk.gov.companieshouse.enums.TestDataForenames.getRandomTestForename;
import static uk.gov.companieshouse.enums.TestDataSurnames.getRandomTestSurname;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("CPD-START")
public class PersonOfSignificantControl {

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
    private final Address residentialAddressField;
    private final String natureOfControlField;
    private final String natureOfControlCategoryField;


    /**
     * Create a new PSC with default values.
     */
    private PersonOfSignificantControl(PersonOfSignificantControlBuilder personOfSignificantControlBuilder) {
        this.titleField = personOfSignificantControlBuilder.titleField;
        this.titleOtherField = personOfSignificantControlBuilder.titleOtherField;
        this.partialDobField = personOfSignificantControlBuilder.partialDobField;
        this.forenameField = personOfSignificantControlBuilder.forenameField;
        this.middleNamesField = personOfSignificantControlBuilder.middleNamesField;
        this.surnameField = personOfSignificantControlBuilder.surnameField;
        this.countryUsuallyResidentField = personOfSignificantControlBuilder.countryUsuallyResidentField;
        this.dobField = personOfSignificantControlBuilder.dobField;
        this.countryField = personOfSignificantControlBuilder.countryField;
        this.countryOtherField = personOfSignificantControlBuilder.countryOtherField;
        this.uraBarcodeField = personOfSignificantControlBuilder.uraBarcodeField;
        this.uraBarcodeRequired = personOfSignificantControlBuilder.uraBarcodeRequired;
        this.noChangeToUra = personOfSignificantControlBuilder.noChangeToUra;
        this.nationalityField = personOfSignificantControlBuilder.nationalityField;
        this.residentialAddressField = personOfSignificantControlBuilder.residentialAddressField;
        this.natureOfControlField = personOfSignificantControlBuilder.natureOfControlField;
        this.natureOfControlCategoryField = personOfSignificantControlBuilder.natureOfControlCategoryField;
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

    public String getNatureOfControl() {
        return natureOfControlField;
    }

    public String getNatureOfControlCategory() {
        return natureOfControlCategoryField;
    }

    public Address getResidentialAddress() {
        return residentialAddressField;
    }


    public static class PersonOfSignificantControlBuilder {

        public String natureOfControlField;
        public String natureOfControlCategoryField;
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
        private Address residentialAddressField;

        public PersonOfSignificantControlBuilder withTitle(final String title) {
            this.titleField = title;
            return this;
        }

        public PersonOfSignificantControlBuilder withTitleOther(final String titleOther) {
            this.titleOtherField = titleOther;
            return this;
        }

        public PersonOfSignificantControlBuilder withPatialDateOfBirth(final String partialDob) {
            this.partialDobField = partialDob;
            return this;
        }

        public PersonOfSignificantControlBuilder withForename(final String forename) {
            this.forenameField = forename;
            return this;
        }

        public PersonOfSignificantControlBuilder withMiddleNames(final String middleNames) {
            this.middleNamesField = middleNames;
            return this;
        }

        public PersonOfSignificantControlBuilder withSurname(final String surname) {
            this.surnameField = surname;
            return this;
        }

        public PersonOfSignificantControlBuilder withCountryUsuallyResident(final String usuallyResident) {
            this.countryUsuallyResidentField = usuallyResident;
            return this;
        }

        public PersonOfSignificantControlBuilder withDateOfBirth(final String dob) {
            this.dobField = dob;
            return this;
        }

        public PersonOfSignificantControlBuilder withCountry(final String country) {
            this.countryField = country;
            return this;
        }

        public PersonOfSignificantControlBuilder withCountryOther(final String countryOther) {
            this.countryOtherField = countryOther;
            return this;
        }

        public PersonOfSignificantControlBuilder withUraBarcode(final String uraBarcode) {
            this.uraBarcodeField = uraBarcode;
            return this;
        }

        public PersonOfSignificantControlBuilder withNationalities(final List<String> nationalities) {
            this.nationalityField = nationalities;
            return this;
        }

        public PersonOfSignificantControlBuilder withNatureOfControl(final String natureOfControl) {
            this.natureOfControlField = natureOfControl;
            return this;
        }

        public PersonOfSignificantControlBuilder withNatureOfControlCategory(final String natureOfControlCategory) {
            this.natureOfControlCategoryField = natureOfControlCategory;
            return this;
        }

        public PersonOfSignificantControlBuilder withResidentialAddress(final Address address) {
            this.residentialAddressField = address;
            return this;
        }

        /**
         * Build a new default individual Person Of Significant Control. Appointment type 5007.
         */
        public PersonOfSignificantControlBuilder createDefaultPsc() {
            final Address residentialAddress = new Address.AddressBuilder().welshAddress().build();
            withPatialDateOfBirth("01/1990");
            withTitle("DR");
            withForename(getRandomTestForename());
            withSurname(getRandomTestSurname());
            withNationalities(Arrays.asList("Welsh", "Irish"));
            withDateOfBirth("01/01/1990");
            withNatureOfControl("75% or more of voting rights as a person");
            withNatureOfControlCategory("Ownership of voting rights as a person");
            withResidentialAddress(residentialAddress);
            withCountry(residentialAddress.getCountry());
            return this;
        }

        public PersonOfSignificantControl build() {
            return new PersonOfSignificantControl(this);
        }

    }

}
