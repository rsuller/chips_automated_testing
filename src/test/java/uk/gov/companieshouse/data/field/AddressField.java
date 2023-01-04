package uk.gov.companieshouse.data.field;

import uk.gov.companieshouse.data.model.Address;
import uk.gov.ch.taf.common.field.FieldKey;


public class AddressField extends FieldKey<Address> {

    public AddressField(Address value) {
        super(value);
    }

    public void setValue(Address value) {
        this.value = value;
    }

    @Override
    public void setValue(String value) {
        // this.setValue(null);
    }
}
