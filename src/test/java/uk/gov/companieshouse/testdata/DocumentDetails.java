package uk.gov.companieshouse.testdata;

public class DocumentDetails {

    String barcode;
    String receivedDate;
    String accountsMud;
    String accountsType;
    String formType;
    String liquidationType;
    String allocatedIpNumber;
    String filingMethod;

    public void setFilingMethod(String filingMethod) {
        this.filingMethod = filingMethod;
    }

    public void setAllocatedIpNumber(String allocatedIpNumber) {
        this.allocatedIpNumber = allocatedIpNumber;
    }

    public String getAllocatedIpNumber() {
        return allocatedIpNumber;
    }

    public void setLiquidationType(String liquidationType) {
        this.liquidationType = liquidationType;
    }

    public String getLiquidationType() {
        return liquidationType;
    }

    public void setReceivedDate(String receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getFilingMethod() {
        return filingMethod;
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
