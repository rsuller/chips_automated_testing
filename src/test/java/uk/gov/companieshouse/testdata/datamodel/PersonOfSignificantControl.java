package uk.gov.companieshouse.testdata.datamodel;

import javafx.util.Pair;

import java.util.Date;

public class PersonOfSignificantControl {
    private final Date dateOfNotification;
    private final String title;
    private final String forename;
    private final String middleName;
    private final String surname;
    private final String dateOfBirth;
    private final Address address;
    private final String nationality;
    private final boolean zfException;
    private final boolean foreignAddress;
    private final String countryOfUsualResidence;
    private final boolean serviceAddressSameAsRo;
    private final Pair<String, String> natureOfControl;

    private PersonOfSignificantControl(Builder builder) {
        this.dateOfNotification = builder.dateOfNotification;
        this.title = builder.title;
        this.forename = builder.forename;
        this.middleName = builder.middleName;
        this.surname = builder.surname;
        this.dateOfBirth = builder.dateOfBirth;
        this.address = builder.address;
        this.foreignAddress = builder.foreignAddress;
        this.nationality = builder.nationality;
        this.zfException = builder.zfException;
        this.countryOfUsualResidence = builder.countryOfUsualResidence;
        this.serviceAddressSameAsRo = builder.serviceAddressSameAsRo;
        this.natureOfControl = builder.natureOfControl;
    }

    public Date getDateOfNotification() {
        return dateOfNotification;
    }

    public String getTitle() {
        return title;
    }

    public String getForename() {
        return forename;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getSurname() {
        return surname;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public Address getAddress() {
        return address;
    }

    public boolean getForeignAddress() {
        return foreignAddress;
    }

    public String getNationality() {
        return nationality;
    }

    public boolean getZfException() {
        return zfException;
    }

    public String getCountryOfUsualResidence() {
        return countryOfUsualResidence;
    }

    public boolean getServiceAddressSameAsRo() {
        return serviceAddressSameAsRo;
    }

    public Pair<String, String> getNatureOfControl() {
        return natureOfControl;
    }

    public static class Builder {

        private Date dateOfNotification;
        private String title;
        private String forename;
        private String middleName;
        private String surname;
        private String dateOfBirth;
        private Address address;
        private boolean foreignAddress;
        public boolean serviceAddressSameAsRo;
        private String nationality;
        private String countryOfUsualResidence;
        private boolean zfException;
        public Pair<String, String> natureOfControl;
        public Builder() {
        }

        public Builder withDateOfNotification(Date dateOfNotification) {
            this.dateOfNotification = dateOfNotification;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }
        public Builder withForename(String forename) {
            this.forename = forename;
            return this;
        }

        public Builder withMiddleName(String middleName) {
            this.middleName = middleName;
            return this;
        }

        public Builder withSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public Builder withDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Builder withAddress(Address address) {
            this.address = address;
            return this;
        }

        public Builder withServiceAddressSameAsRo(boolean serviceAddressSameAsRo) {
            this.serviceAddressSameAsRo = serviceAddressSameAsRo;
            return this;
        }

        public Builder withForeignAddress(boolean foreignAddress) {
            this.foreignAddress = foreignAddress;
            return this;
        }

        public Builder withNationality(String nationality) {
            this.nationality = nationality;
            return this;
        }

        public Builder withCountryOfUsualResidence(String countryOfUsualResidence) {
            this.countryOfUsualResidence = countryOfUsualResidence;
            return this;
        }

        public Builder withZfException(boolean zfException) {
            this.zfException = zfException;
            return this;
        }

        private Builder withNatureOfControl(String natureOfControlCategory, String natureOfControl) {
            this.natureOfControl = new Pair<>(natureOfControlCategory, natureOfControl);
            return this;
        }

        public Builder defaultPscDetail() {
            withDateOfNotification(new Date());
            withTitle("Mr");
            withForename("John");
            withMiddleName("");
            withSurname("Doe");
            withDateOfBirth("01/07/1979");
            withAddress(new Address.AddressBuilder().welshAddress().build());
            withServiceAddressSameAsRo(true);
            withForeignAddress(false);
            withNationality("British");
            withCountryOfUsualResidence("United Kingdom");
            withZfException(false);
            withNatureOfControl("Ownership of shares as a person",
                    "More than 25% but not more than 50% of shares held as a person");
            return this;
        }

        public PersonOfSignificantControl build() {
            return new PersonOfSignificantControl(this);
        }
    }
}

