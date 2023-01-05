package uk.gov.companieshouse.data.dataModel;

import uk.gov.ch.alphakey.SameAsService;
import uk.gov.ch.alphakey.SameAsServiceImpl;

import java.text.ParseException;
import java.util.Date;

import static uk.gov.companieshouse.utils.RandomStringCreator.randomAlphaString;

public class Company {

    private final String alphaKeyField;
    private final String corporateBodyIdField;
    private final String typeShortField;
    private final String typeLongField;
    private final String numberField;
    private final String suppliedCompanyNumField;
    private final String nameField;
    private final String prefixField;
    private final String suffixField;
    private final String nameEndingField;
    private final String sicCodeField;
    private final String sicDescriptionField;
    private final String supplyCompanyNameField;
    private final Address addressField;
    private final String incorporationDateField;
    private final String memberStateField;

    private Company(CompanyBuilder companyBuilder) {
        this.alphaKeyField = companyBuilder.alphaKeyField;
        this.corporateBodyIdField = companyBuilder.corporateBodyIdField;
        this.typeShortField = companyBuilder.typeShortField;
        this.typeLongField = companyBuilder.typeLongField;
        this.numberField = companyBuilder.numberField;
        this.suppliedCompanyNumField = companyBuilder.suppliedCompanyNumField;
        this.nameField = companyBuilder.nameField;
        this.prefixField = companyBuilder.prefixField;
        this.suffixField = companyBuilder.suffixField;
        this.nameEndingField = companyBuilder.nameEndingField;
        this.sicCodeField = companyBuilder.sicCodeField;
        this.supplyCompanyNameField = companyBuilder.supplyCompanyNameField;
        this.sicDescriptionField = companyBuilder.sicDescriptionField;
        this.addressField = companyBuilder.addressField;
        this.incorporationDateField = companyBuilder.incorporationDateField;
        this.memberStateField = companyBuilder.memberStateField;
    }
    public String getCorporateBodyId() {
        return corporateBodyIdField;
    }

    public String getAlphaKey() {
        return alphaKeyField;
    }

    public String getTypeShort() {
        return typeShortField;
    }

    public String getTypeLong() {
        return typeLongField;
    }

    public String getNumber() {
        return numberField;
    }

    public String getSuppliedNumber() {
        return suppliedCompanyNumField;
    }

    public String getName() {
        return nameField;
    }

    public String getPrefix() {
        return prefixField;
    }

    public String getSuffix() {
        return suffixField;
    }

    public String getNameEnding() {
        return nameEndingField;
    }

    public String getSicCode() {
        return sicCodeField;
    }

    public String getSuppliedCompanyName() {
        return supplyCompanyNameField;
    }

    public String getSicDescription() {
        return sicDescriptionField;
    }

    public String getMemberState() {
        return memberStateField;
    }

    public String getIncorporationDate() {
        return incorporationDateField;
    }

    public Address getAddress() {
        return addressField;
    }

    public String toString() {
        return String.format("Company Details: \n Alpha Key: %s \n Corporate Body ID: %s \n Type Short: %s \n Type Long: %s \n" +
                " Number: %s \n Supplied Number: %s \n Name: %s \n Prefix: %s \n Suffix: %s \n Name Ending: %s \n " +
                "Sic Code: %s \n Sic Code Description: %s \n Address: %s \n Incorporation Date: %s \n Member State: %s",
                getAlphaKey(),
                getCorporateBodyId(),
                getTypeShort(),
                getTypeLong(),
                getNumber(),
                getSuppliedNumber(),
                getName(),
                getPrefix(),
                getSuffix(),
                getNameEnding(),
                getSicCode(),
                getSicDescription(),
                getAddress(),
                getIncorporationDate(),
                getMemberState());
    }

    public static class CompanyBuilder {
        private  String alphaKeyField;
        private  String corporateBodyIdField;
        private  String typeShortField;
        private  String typeLongField;
        private  String numberField;
        private  String suppliedCompanyNumField;
        private  String nameField;
        private  String prefixField;
        private  String suffixField;
        private  String nameEndingField;
        private  String sicCodeField;
        private  String sicDescriptionField;
        private  String supplyCompanyNameField;
        private  Address addressField;
        private  String incorporationDateField;
        private  String memberStateField;

        public CompanyBuilder withTypeShort(String typeShort) {
            this.typeShortField = typeShort;
            return this;
        }

        public CompanyBuilder withAlphaKey(String alphaKey) {
            this.alphaKeyField = alphaKey;
            return this;
        }

        public CompanyBuilder withCorporateBodyId(String corporateBodyId) {
            this.corporateBodyIdField = corporateBodyId;
            return this;
        }

        public CompanyBuilder withTypeLong(final String typeLong) {
            this.typeLongField = typeLong;
            return this;
        }

        public CompanyBuilder withNumber(final String number) {
            this.numberField = number;
            return this;
        }

        public CompanyBuilder withSuppliedCompanyNumber(final String suppliedCompanyNumber) {
            this.suppliedCompanyNumField = suppliedCompanyNumber;
            return this;
        }

        /**
         * Create a new company with specified name.
         */
        public CompanyBuilder withName(String name) {
            this.nameField = name;

            SameAsService sas = new SameAsServiceImpl();
            String checkKey = sas.deriveCheckDigitAlphakeyFromCorporateBodyFullName(name);

            if (checkKey.length() > 4) {
                withPrefix(checkKey.substring(0, 4));
                withSuffix(checkKey.substring(checkKey.length() - 4));
            } else {
                withPrefix(checkKey);
                withSuffix(checkKey);
            }
            return this;
        }

        public CompanyBuilder withPrefix(String prefix) {
            this.prefixField = prefix;
            return this;
        }

        public CompanyBuilder withSuffix(String suffix) {
            this.suffixField = suffix;
            return this;
        }

        public CompanyBuilder withNameEnding(String nameEnding) {
            this.nameEndingField = nameEnding;
            return this;
        }

        public CompanyBuilder withSicCode(String sicCode) {
            this.sicCodeField = sicCode;
            return this;
        }

        public CompanyBuilder withSicDescription(String sicDescription) {
            this.sicDescriptionField = sicDescription;
            return this;
        }

        public CompanyBuilder withAddress(Address address) {
            this.addressField = address;
            return this;
        }

        public CompanyBuilder withIncorporationDate(Date incDate) {
            this.incorporationDateField = String.valueOf(incDate);
            return this;
        }

        public CompanyBuilder withMemberState(final String memberState) {
            this.memberStateField = memberState;
            return this;
        }

        public CompanyBuilder createDefaultCompany() throws ParseException {
            withAlphaKey("");
            withCorporateBodyId("12345678");
            withTypeShort("LTD");
            withTypeLong("Private Limited");
            withNumber("");
            withSuppliedCompanyNumber("12345678");
            withName("COMPANY " + randomAlphaString(6));
            withPrefix("");
            withSuffix("");
            withNameEnding("LTD");
            withSicCode("03110");
            withSicDescription("MARINE FISHING");
            withAddress(new Address.AddressBuilder().welshAddress().build());
            withIncorporationDate(new Date());
            withMemberState("");
            return this;

        }

        public Company build() {
            return new Company(this);
        }

    }


    /**
     * Check if corporate body is an LLP.
     *
     * @return boolean
     */
    public boolean isLlp() {
        String companyNumber = getNumber();
        String prefix = companyNumber.substring(0, 2);
        return prefix.equals("OC") || prefix.equals("SO") || prefix.equals("NC");
    }

    public static void main(String[] args) throws ParseException {
        // Create default company
        Company company = new Company.CompanyBuilder().createDefaultCompany().build();

        System.out.println(company);

        // Create company which specified name
        Company company1 = new Company.CompanyBuilder().createDefaultCompany().withName("This is a Test").build();
        System.out.println(company1);
    }

}
