package uk.gov.companieshouse.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import uk.gov.ch.alphakey.SameAsService;
import uk.gov.ch.alphakey.SameAsServiceImpl;
import uk.gov.companieshouse.data.field.AddressField;
import uk.gov.ch.taf.common.builder.Builder;
import uk.gov.ch.taf.common.field.DateVariantField;
import uk.gov.ch.taf.common.field.StringField;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import static uk.gov.companieshouse.utils.RandomStringCreator.randomAlphaString;

public class Company extends Builder<Company> {

    @SerializedName("alphaKey")
    @Expose
    private final StringField alphaKeyField;
    @SerializedName("corporateBodyId")
    @Expose
    private final StringField corporateBodyIdField;
    @SerializedName("typeShort")
    @Expose
    private final StringField typeShortField;
    @SerializedName("typeLong")
    @Expose
    private final StringField typeLongField;
    @SerializedName("number")
    @Expose
    private final StringField numberField;
    @SerializedName("suppliedCompanyNumber")
    @Expose
    private final StringField suppliedCompanyNumField;
    @SerializedName("name")
    @Expose
    private final StringField nameField;
    @SerializedName("prefix")
    @Expose
    private final StringField prefixField;
    @SerializedName("suffix")
    @Expose
    private final StringField suffixField;
    @SerializedName("nameEnding")
    @Expose
    private final StringField nameEndingField;
    @SerializedName("sicCode")
    @Expose
    private final StringField sicCodeField;
    @SerializedName("sicDescription")
    @Expose
    private final StringField sicDescriptionField;
    @SerializedName("supplyCompanyName")
    @Expose
    private final StringField supplyCompanyNameField;
    @SerializedName("address")
    @Expose
    private final AddressField addressField;
    @SerializedName("incorporationDate")
    @Expose
    private final DateVariantField incorporationDateField;
    @SerializedName("memberState")
    @Expose
    private final StringField memberStateField;

    /**
     * Create a company with default values.
     */
    public Company() {
        this.alphaKeyField = new StringField("", "Alpha Key", fieldKeys);
        this.corporateBodyIdField = new StringField("12345678", "Corporate Body ID", fieldKeys);
        this.typeShortField = new StringField("LTD", "Type Short", fieldKeys);
        this.typeLongField = new StringField("Private Limited", "Type Long", fieldKeys);
        this.numberField = new StringField("", "Company Number", fieldKeys);
        this.suppliedCompanyNumField = new StringField("12345678",
                "supplied company number", fieldKeys);
        this.nameField = new StringField("COMPANY " + randomAlphaString(6),
                "Company Name", fieldKeys);
        this.prefixField = new StringField("", "Prefix", fieldKeys);
        this.suffixField = new StringField("", "Suffix", fieldKeys);
        this.nameEndingField = new StringField("LTD", "Name Ending", fieldKeys);
        this.sicCodeField = new StringField("03110", "Sic Code", fieldKeys);
        this.supplyCompanyNameField = new StringField("INVALID COMPANY",
                "Supply Company Name", fieldKeys);
        this.sicDescriptionField = new StringField("MARINE FISHING", "Sic Description", fieldKeys);
        this.addressField = new AddressField(Address.newEngland());
        try {
            this.incorporationDateField = new DateVariantField(String.valueOf(new Date()),
                    "Incorporation Date", fieldKeys);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
        this.memberStateField = new StringField("", "", fieldKeys);
    }

    public static Company build(Map<String, String> parameters) throws ParseException {
        return parameters.isEmpty() ? build() : new Company().usingParameters(parameters);
    }

    public static Company build() {
        return new Company();
    }

    public String getCorporateBodyId() {
        return corporateBodyIdField.getValue();
    }

    public String getAlphaKey() {
        return alphaKeyField.getValue();
    }

    public String getTypeShort() {
        return typeShortField.getValue();
    }

    public String getTypeLong() {
        return typeLongField.getValue();
    }

    public String getNumber() {
        return numberField.getValue();
    }

    public String getSuppliedNumber() {
        return suppliedCompanyNumField.getValue();
    }

    public String getName() {
        return nameField.getValue();
    }

    public String getPrefix() {
        return prefixField.getValue();
    }

    public String getSuffix() {
        return suffixField.getValue();
    }

    public String getNameEnding() {
        return nameEndingField.getValue();
    }

    public String getSicCode() {
        return sicCodeField.getValue();
    }

    public String getSuppliedCompanyName() {
        return supplyCompanyNameField.getValue();
    }

    public String getSicDescription() {
        return sicDescriptionField.getValue();
    }

    public String getMemberState() {
        return memberStateField.getValue();
    }

    public Address getAddress() {
        return addressField.getValue();
    }

    public Company withTypeShort(String typeShort) {
        this.typeShortField.setValue(typeShort);
        return this;
    }

    public Company withAlphaKey(String alphaKey) {
        this.alphaKeyField.setValue(alphaKey);
        return this;
    }

    public Company withCorporateBodyId(String corporateBodyId) {
        this.corporateBodyIdField.setValue(corporateBodyId);
        return this;
    }

    public Company withTypeLong(final String typeLong) {
        this.typeLongField.setValue(typeLong);
        return this;
    }

    public Company withNumber(final String number) {
        this.numberField.setValue(number);
        return this;
    }

    /**
     * Create a new company with specified name.
     */
    public Company withName(String name) {
        this.nameField.setValue(name);

        SameAsService sas = new SameAsServiceImpl();
        String checkKey = sas.deriveCheckDigitAlphakeyFromCorporateBodyFullName(name);

        if (checkKey.length() > 4) {
            withPrefix(checkKey.substring(0, 4));
            withSuffix(checkKey.substring(checkKey.length() - 4, checkKey.length()));
        } else {
            withPrefix(checkKey);
            withSuffix(checkKey);
        }
        return this;
    }

    public Company withPrefix(final String prefix) {
        this.prefixField.setValue(prefix);
        return this;
    }

    public Company withSuffix(final String suffix) {
        this.suffixField.setValue(suffix);
        return this;
    }

    public Company withNameEnding(final String nameEnding) {
        this.nameEndingField.setValue(nameEnding);
        return this;
    }

    public Company withSicCode(final String sicCode) {
        this.sicCodeField.setValue(sicCode);
        return this;
    }

    public Company withSicDescription(final String sicDescription) {
        this.sicDescriptionField.setValue(sicDescription);
        return this;
    }

    public Company withAddress(final Address address) {
        this.addressField.setValue(address);
        return this;
    }

    public Company withIncorporationDate(final Date incDate) throws ParseException {
        this.incorporationDateField.setValue(String.valueOf(incDate));
        return this;
    }

    public Company withMemberState(final String memberState) {
        this.memberStateField.setValue(memberState);
        return this;
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

}
