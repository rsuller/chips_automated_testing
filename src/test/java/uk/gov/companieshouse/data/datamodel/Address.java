package uk.gov.companieshouse.data.datamodel;

public class Address {

    private final String houseNumberField;
    private final String streetField;
    private final String areaField;
    private final String postTownField;
    private final String regionField;
    private final String postcodeField;
    private final String countryField;

    /**
     * Create a new address with default values.
     */
    private Address(AddressBuilder addressBuilder) {
        this.houseNumberField = addressBuilder.houseNumberField;
        this.streetField = addressBuilder.streetField;
        this.areaField = addressBuilder.areaField;
        this.postTownField = addressBuilder.postTownField;
        this.regionField = addressBuilder.regionField;
        this.postcodeField = addressBuilder.postcodeField;
        this.countryField = addressBuilder.countryField;

    }

    public String getHouseNumber() {
        return houseNumberField;
    }

    public String getStreet() {
        return streetField;
    }

    public String getArea() {
        return areaField;
    }

    public String getPostTown() {
        return postTownField;
    }

    public String getRegion() {
        return regionField;
    }

    public String getPostcode() {
        return postcodeField;
    }

    public String getCountry() {
        return countryField;
    }

    @Override
    public String toString() {
        return String.format("House number: %s \n Street: %s \n Area: %s \n PostTown: %s \n Region: %s \n "
                        + "Postcode: %s \n Country: %s",
                getHouseNumber(),
                getStreet(),
                getArea(),
                getPostTown(),
                getRegion(),
                getPostcode(),
                getCountry());
    }


    public static class AddressBuilder {
        private String houseNumberField;
        private String streetField;
        private String areaField;
        private String postTownField;
        private String regionField;
        private String postcodeField;
        private String countryField;

        public AddressBuilder withHouseNumber(final String houseNumber) {
            this.houseNumberField = houseNumber;
            return this;
        }

        public AddressBuilder withStreet(final String street) {
            this.streetField = street;
            return this;
        }

        public AddressBuilder withArea(final String area) {
            this.areaField = area;
            return this;
        }

        public AddressBuilder withPostTown(final String postTown) {
            this.postTownField = postTown;
            return this;
        }

        public AddressBuilder withRegion(final String region) {
            this.regionField = region;
            return this;
        }

        public AddressBuilder withPostcode(final String postcode) {
            this.postcodeField = postcode;
            return this;
        }

        public AddressBuilder withCountry(final String country) {
            this.countryField = country;
            return this;
        }

        /**
         * Create a new address with default values for an address based in Wales.
         */
        public AddressBuilder welshAddress() {
            withHouseNumber("1");
            withStreet("Heol Fawr");
            withArea("Nelson");
            withPostTown("Treharris");
            withPostcode("CF46 6NW");
            withCountry("Wales");
            return this;
        }

        /**
         * Create a new address with default values for an address based in England.
         */
        public AddressBuilder englishAddress() {
            withHouseNumber("16");
            withStreet("Smith Barry Cresent");
            withArea("Upper Rissington");
            withPostTown("Cheltenham");
            withRegion("Gloucestershire");
            withPostcode("GL54 2NG");
            withCountry("England");
            return this;
        }

        public Address build() {
            return new Address(this);
        }

    }

}
