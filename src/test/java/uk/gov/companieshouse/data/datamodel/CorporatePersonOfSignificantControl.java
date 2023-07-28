package uk.gov.companieshouse.data.datamodel;

import static uk.gov.companieshouse.utils.DateFormat.getDateAsString;

import java.util.Date;


public class CorporatePersonOfSignificantControl {

    private final String entityNameField;
    private final String companyNameField;
    private final Address addressField;
    private final String countryField;
    private final String legalFormField;
    private final String lawGovernedField;
    private final String registerRleField;
    private final String registrationNumberField;
    private final String natureOfControlField;
    private final String natureOfControlCategoryField;

    /**
     * Create a new PSC with default values.
     */
    private CorporatePersonOfSignificantControl(CorporatePersonOfSignificantControlBuilder corporatePersonOfSignificantControlBuilder) {

        this.countryField = corporatePersonOfSignificantControlBuilder.countryField;
        this.addressField = corporatePersonOfSignificantControlBuilder.addressField;
        this.natureOfControlField = corporatePersonOfSignificantControlBuilder.natureOfControlField;
        this.natureOfControlCategoryField = corporatePersonOfSignificantControlBuilder.natureOfControlCategoryField;
        this.entityNameField = corporatePersonOfSignificantControlBuilder.entityNameField;
        this.companyNameField = corporatePersonOfSignificantControlBuilder.companyNameField;
        this.legalFormField = corporatePersonOfSignificantControlBuilder.legalFormField;
        this.lawGovernedField = corporatePersonOfSignificantControlBuilder.lawGovernedField;
        this.registerRleField = corporatePersonOfSignificantControlBuilder.registerRleField;
        this.registrationNumberField = corporatePersonOfSignificantControlBuilder.registrationNumberField;
    }

    public String getRegistrationNumber() {
        return registrationNumberField;
    }

    public String getRegisterRle() {
        return registerRleField;
    }

    public String getLawGoverned() {
        return lawGovernedField;
    }

    public String getCompanyName() {
        return companyNameField;
    }

    public String getEntityName() {
        return entityNameField;
    }

    public String getCountry() {
        return countryField;
    }


    public String getNatureOfControl() {
        return natureOfControlField;
    }

    public String getNatureOfControlCategory() {
        return natureOfControlCategoryField;
    }

    public Address getAddress() {
        return addressField;
    }

    public String getLegalFormField() {
        return legalFormField;
    }


    public static class CorporatePersonOfSignificantControlBuilder {

        private String registrationNumberField;
        private String registerRleField;
        private String lawGovernedField;
        private String companyNameField;
        private String entityNameField;
        private Address addressField;
        private String natureOfControlField;
        private String natureOfControlCategoryField;
        private String legalFormField;
        private String countryField;


        public CorporatePersonOfSignificantControlBuilder withEntityName(final String entityName) {
            this.entityNameField = entityName;
            return this;
        }

        public CorporatePersonOfSignificantControlBuilder withCompanyName(final String companyName) {
            this.companyNameField = companyName;
            return this;
        }

        public CorporatePersonOfSignificantControlBuilder withCountry(final String country) {
            this.countryField = country;
            return this;
        }

        public CorporatePersonOfSignificantControlBuilder withNatureOfControl(final String natureOfControl) {
            this.natureOfControlField = natureOfControl;
            return this;
        }

        public CorporatePersonOfSignificantControlBuilder withNatureOfControlCategory(final String natureOfControlCategory) {
            this.natureOfControlCategoryField = natureOfControlCategory;
            return this;
        }

        public CorporatePersonOfSignificantControlBuilder withAddress(final Address address) {
            this.addressField = address;
            return this;
        }

        public CorporatePersonOfSignificantControlBuilder withLegalForm(final String legalForm) {
            this.legalFormField = legalForm;
            return this;
        }

        public CorporatePersonOfSignificantControlBuilder withLawGoverned(final String lawGoverned) {
            this.lawGovernedField = lawGoverned;
            return this;
        }

        public CorporatePersonOfSignificantControlBuilder withRegisterRle(final String registerRle) {
            this.registerRleField = registerRle;
            return this;
        }

        public CorporatePersonOfSignificantControlBuilder withRegistrationNumber(final String registrationNumber) {
            this.registrationNumberField = registrationNumber;
            return this;
        }

        /**
         * Build a new default Corporate Person Of Significant Control. AKA a relevant legal entity (RLE) or Other
         * Registrable Person (ORP).
         * Appointment type: 5008 or 5009.
         */
        public CorporatePersonOfSignificantControlBuilder createDefaultCorporatePsc() {
            final Address address = new Address.AddressBuilder().welshAddress().build();
            withEntityName("CORPORATE  PSC " + getDateAsString(new Date()));
            withCompanyName("COMPANY");
            withNatureOfControl("75% or more of voting rights as a relevant legal entity");
            withNatureOfControlCategory("Ownership of voting rights as a relevant legal entity");
            withAddress(address);
            withCountry(address.getCountry());
            withLegalForm("LEGAL FORM TEST DATA");
            withLawGoverned("LAW GOVERNED TEST DATA");
            withRegisterRle("REGISTER RLE TEST DATA");
            withRegistrationNumber("1234");
            return this;
        }

        public CorporatePersonOfSignificantControl build() {
            return new CorporatePersonOfSignificantControl(this);
        }
    }

}
