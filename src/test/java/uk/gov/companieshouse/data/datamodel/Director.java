package uk.gov.companieshouse.data.datamodel;

import java.util.Date;
import java.util.List;

public class Director {

    private final Date dateOfAppointmentField;
    private final String titleField;
    private final String titleOtherField;
    private final String forenameField;
    private final String middleNamesField;
    private final String surnameField;
    private final String countryUsuallyResidentField;
    private final Date dobField;
    private final String countryField;
    private final String countryOtherField;
    private final String uraBarcodeField;
    private boolean uraBarcodeRequired;
    private boolean noChangeToUra;
    private final List<String> nationalityField;
    private final String occupationField;
    private final Address serviceAddressField;
    private final String  serviceAddressHouseNumberField;
    private final String  residentialAddressHouseNumberField;
    private final String residentialAddressPostCodeField;
    private final Address residentialAddressField;

    /**
     * Create a new director with default values.
     */
    private Director(DirectorBuilder directorBuilder) {
        this.dateOfAppointmentField = directorBuilder.dateOfAppointmentField;
        this.titleField = directorBuilder.titleField;
        this.titleOtherField = directorBuilder.titleOtherField;
        this.forenameField = directorBuilder.forenameField;
        this.middleNamesField = directorBuilder.middleNamesField;
        this.surnameField = directorBuilder.surnameField;
        this.countryUsuallyResidentField = directorBuilder.countryUsuallyResidentField;;
        this.dobField = directorBuilder.dobField;
        this.countryField = directorBuilder.countryField;
        this.countryOtherField = directorBuilder.countryOtherField;
        this.uraBarcodeField = directorBuilder.uraBarcodeField;
        this.uraBarcodeRequired = directorBuilder.uraBarcodeRequired;
        this.noChangeToUra = directorBuilder.noChangeToUra;
        this.nationalityField = directorBuilder.nationalityField;
        this.occupationField = directorBuilder.occupationField;
        this.serviceAddressField = directorBuilder.serviceAddressField;
        this.serviceAddressHouseNumberField = directorBuilder.serviceAddressHouseNumberField;
        this.residentialAddressHouseNumberField = directorBuilder.residentialAddressHouseNumberField;
        this.residentialAddressPostCodeField = directorBuilder.residentialAddressPostCodeField;
        this.residentialAddressField = directorBuilder.residentialAddressField;
    }

    public Date getDateOfAppointmentField() {
        return dateOfAppointmentField;
    }

    public String getTitleField() {
        return titleField;
    }

    public String getTitleOtherField() {
        return titleOtherField;
    }

    public String getForenameField() {
        return forenameField;
    }

    public String getMiddleNamesField() {
        return middleNamesField;
    }

    public String getSurnameField() {
        return surnameField;
    }

    public String getCountryUsuallyResidentField() {
        return countryUsuallyResidentField;
    }

    public Date getDobField() {
        return dobField;
    }

    public String getCountryField() {
        return countryField;
    }

    public String getCountryOtherField() {
        return countryOtherField;
    }

    public String getUraBarcodeField() {
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

    public List<String> getNationalityField() {
        return nationalityField;
    }

    public String getOccupationField() {
        return occupationField;
    }

    public Address getServiceAddressField() {
        return serviceAddressField;
    }

    public String getServiceAddressHouseNumberField() {
        return serviceAddressHouseNumberField;
    }

    public String getResidentialAddressHouseNumberField() {
        return residentialAddressHouseNumberField;
    }

    public String getResidentialAddressPostCodeField() {
        return residentialAddressPostCodeField;
    }

    public Address getResidentialAddressField() {
        return residentialAddressField;
    }


    public static class DirectorBuilder {
        private Date dateOfAppointmentField;

        private String titleField;
        private String titleOtherField;
        private String forenameField;
        private String middleNamesField;
        private String surnameField;
        private String countryUsuallyResidentField;
        private Date dobField;
        private String countryField;
        private String countryOtherField;
        private String uraBarcodeField;
        private boolean uraBarcodeRequired;
        private boolean noChangeToUra;
        private List<String> nationalityField;
        private String occupationField;
        private Address serviceAddressField;
        private String  serviceAddressHouseNumberField;
        private String  residentialAddressHouseNumberField;
        private String residentialAddressPostCodeField;
        private Address residentialAddressField;

        public DirectorBuilder withDateOfAppointment(final Date dateOfAppointment) {
            this.dateOfAppointmentField = dateOfAppointment;
            return this;
        }

        public DirectorBuilder withTitle(final String title) {
            this.titleField = title;
            return this;
        }

        public DirectorBuilder withTitleOther(final String titleOther) {
            this.titleOtherField = titleOther;
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

        public DirectorBuilder withDateOfBirth(final Date dob) {
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

        public DirectorBuilder withAddress(final Address address) {
            this.serviceAddressField = address;
            return this;
        }

        public DirectorBuilder withResidentialAddress(final Address address) {
            this.residentialAddressField = address;
            return this;
        }

        public Director build() {
            return new Director(this);
        }

    }

    public static void main(String[] args) {
        // Build a default Welsh Address


    }

}
