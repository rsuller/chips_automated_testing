package uk.gov.companieshouse.testdata;

public class DocumentDetails {

    String barcode;
    String receivedDate;
    String accountsMud;
    String accountsType;
    String formType;

    public void setReceivedDate(String receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getReceivedDate() {
        return receivedDate;
    }


    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setAccountsMud(String accountsMud) {
        this.accountsMud = accountsMud;
    }

    public String getAccountsMud() {
        return accountsMud;
    }

    public void setAccountsType(String accountsType) {
        this.accountsType = accountsType;
    }

    public String getAccountsType() {
        return accountsType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public String getFormType() {
        return formType;
    }
}
