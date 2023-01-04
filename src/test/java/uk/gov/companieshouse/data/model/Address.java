package uk.gov.companieshouse.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import uk.gov.ch.taf.common.builder.Builder;
import uk.gov.ch.taf.common.field.StringField;

import java.text.ParseException;
import java.util.List;
import java.util.Map;


public class Address extends Builder<Address> {

    @SerializedName("houseNumber")
    @Expose
    private StringField houseNumberField;
    @SerializedName("street")
    @Expose
    private StringField streetField;
    @SerializedName("area")
    @Expose
    private StringField areaField;
    @SerializedName("postTown")
    @Expose
    private StringField postTownField;
    @SerializedName("region")
    @Expose
    private StringField regionField;
    @SerializedName("postcode")
    @Expose
    private StringField postcodeField;
    @SerializedName("country")
    @Expose
    private StringField countryField;
    @SerializedName("companyName")
    @Expose
    private StringField companyNameField;
    @SerializedName("situationOfRo")
    @Expose
    private StringField situationOfRoField;

    /**
     * Create a new address with default values.
     */
    public Address() {
        this.houseNumberField = new StringField("", "House Number", fieldKeys);
        this.streetField = new StringField("", "Street", fieldKeys);
        this.areaField = new StringField("", "Area", fieldKeys);
        this.postTownField = new StringField("", "Post Town", fieldKeys);
        this.regionField = new StringField("", "Region", fieldKeys);
        this.postcodeField = new StringField("", "Postcode", fieldKeys);
        this.countryField = new StringField("", "Country", fieldKeys);
        this.companyNameField = new StringField("", "Company Name", fieldKeys);
        this.situationOfRoField = new StringField("", "Situation of RO", fieldKeys);
    }

    public String getHouseNumber() {
        return houseNumberField.getValue();
    }

    public String getStreet() {
        return streetField.getValue();
    }

    public String getArea() {
        return areaField.getValue();
    }

    public String getPostTown() {
        return postTownField.getValue();
    }

    public String getRegion() {
        return regionField.getValue();
    }

    public String getPostcode() {
        return postcodeField.getValue();
    }

    public String getCountry() {
        return countryField.getValue();
    }

    public String getCompanyName() {
        return companyNameField.getValue();
    }

    public String getSituationOfRo() {
        return situationOfRoField.getValue();
    }

    public Address withHouseNumber(final String houseNumber) {
        this.houseNumberField.setValue(houseNumber);
        return this;
    }

    public Address withStreet(final String street) {
        this.streetField.setValue(street);
        return this;
    }

    public Address withArea(final String area) {
        this.areaField.setValue(area);
        return this;
    }

    public Address withPostTown(final String postTown) {
        this.postTownField.setValue(postTown);
        return this;
    }

    public Address withRegion(final String region) {
        this.regionField.setValue(region);
        return this;
    }

    public Address withPostcode(final String postcode) {
        this.postcodeField.setValue(postcode);
        return this;
    }

    public Address withCountry(final String country) {
        this.countryField.setValue(country);
        return this;
    }

    public Address withCompanyName(final String companyName) {
        this.companyNameField.setValue(companyName);
        return this;
    }

    public Address withSituationOfRo(final String situationOfRo) {
        this.situationOfRoField.setValue(situationOfRo);
        return this;
    }

    private static Address build() {
        return new Address();
    }

    public static Address build(List<Map<String, String>> rows) throws ParseException {
        return rows.isEmpty() ? build() : build(rows.get(0));
    }

    private static Address build(Map<String, String> parameters) throws ParseException {
        return parameters.isEmpty() ? build() : new Address().usingParameters(parameters);
    }

    /**
     * Create a new address with default values for English address.
     */
    public static Address newEngland() {
        return build()
                .withHouseNumber("16")
                .withStreet("SMITH BARRY CRESCENT")
                .withArea("UPPER RISSINGTON")
                .withPostTown("CHELTENHAM")
                .withRegion("GLOUCESTERSHIRE")
                .withPostcode("GL54 2NG")
                .withCountry("ENGLAND")
                .withSituationOfRo("England/Wales");
    }

    /**
     * Create a new address with default values for Northen Irish address.
     */
    public static Address newNorthernIreland() {
        return build()
                .withHouseNumber("11E")
                .withStreet("GLENMACHAN PLACE")
                .withArea("")
                .withPostTown("BELFAST")
                .withRegion("COUNTY ANTRIM")
                .withPostcode("BT12 6QH")
                .withCountry("NORTHERN IRELAND")
                .withSituationOfRo("Northern Ireland");
    }

    /**
     * Create a new address with default values for Scottish address.
     */
    public static Address newScotland() {
        return build()
                .withHouseNumber("82")
                .withStreet("LAURISTON PLACE")
                .withArea("")
                .withPostTown("EDINBURGH")
                .withRegion("")
                .withPostcode("EH3 9DG")
                .withCountry("SCOTLAND")
                .withSituationOfRo("Scotland");
    }

    /**
     * A Scottish border address.
     *
     * @return Address
     */
    public static Address newScottishBorder() {
        return build()
                .withHouseNumber("33")
                .withStreet("")
                .withArea("")
                .withPostTown("")
                .withRegion("")
                .withPostcode("TD15 1DQ")
                .withCountry("ENGLAND")
                .withSituationOfRo("England/Wales");
    }

    /**
     * Create a new address with default values for Welsh address.
     */
    public static Address newWales() {
        return build()
                .withHouseNumber("26")
                .withStreet("OAKLANDS CLOSE")
                .withArea("ST. MELLONS")
                .withPostTown("CARDIFF")
                .withRegion("SOUTH GLAMORGAN")
                .withPostcode("CF3 0AD")
                .withCountry("WALES")
                .withSituationOfRo("Wales");
    }

    /**
     * Create a valid French address.
     */
    public static Address newFrance() {
        return build()
                .withHouseNumber("27")
                .withStreet("RUE PASTEUR")
                .withPostTown("CABOURG")
                .withPostcode("14390")
                .withCountry("FRANCE");
    }

    /**
     * Create a new address linked to disqualified officer.
     */
    public static Address newDiqualifiedOfficer() {
        return build()
                .withHouseNumber("156")
                .withPostcode("SA6 8NY")
                .withCountry("WALES");
    }

}
