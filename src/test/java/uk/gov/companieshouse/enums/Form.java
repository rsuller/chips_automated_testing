package uk.gov.companieshouse.enums;

public enum Form {

        // The transaction history description for ACCOUNTS is blank as it is custom depending on the accounts type and
        // date used within each specific test
        ACCOUNTS("AA","Accounts Filing", false, ""),
        AD01("AD01", "AD01: Change to registered office address", false, "REGISTERED OFFICE CHANGED"),
        AP01("AP01", "Officers", false, "DIRECTOR APPOINTED"),
        CH01("CH01", "Change Officers", false, "DIRECTOR'S CHANGE OF PARTICULA..."),
        CH02("CH02", "Change Officers", false, "CORPORATE DIRECTOR'S CHANGE OF..."),
        CS01("CS01", "Confirmation Statement", false, "CONFIRMATION STATEMENT"),
        DS01("DS01", "Process Voluntary Dissolution Form DS01/LLDS01", true, "APPLICATION FOR STRIKING-OFF"),
        LIQ01("LIQ01", "Process Options - LIQ01", true, "NOTICE OF STATUTORY DECL"),
        TM01("TM01", "Terminate Officers", false, "APPOINTMENT TERMINATED"),
        INS_600("600", "Process Options - 600", true, "NOTICE OF APPOINTMENT OF LIQ"),
        LREXEX("LRESEX", "Process Options - LRESEX", true, "EXTRAORDINARY RESOLUTION"),
        LRESSP("LRESSP", "Process Options - LRESSP", true, "SPECIAL RESOLUTION");

        private final String type;
        private final String title;
        private final boolean highRiskForm;
        private final String transactionHistoryPartialDescription;

        Form(final String type, final String title, final boolean highRiskForm, final String transactionHistoryPartialDescription) {
            this.type = type;
            this.title = title;
            this.highRiskForm = highRiskForm;
            this.transactionHistoryPartialDescription = transactionHistoryPartialDescription;
        }

        public String getType() {
            return type;
        }

        public String getTitle() {
            return title;
        }

        public boolean isHighRiskForm() {
            return highRiskForm;
        }

        public String getTransactionHistoryPartialDescription() {
            return transactionHistoryPartialDescription;
        }

        /**
         * Determine {@link Form} value from title string.
         * @param title the form title
         * @return the {@link Form} enum value
         */
        public static Form getFormByType(final String title) {
            for (final Form form : values()) {
                if (form.type.equals(title)) {
                    return form;
                }
            }
            throw new RuntimeException("The form type " + title + " was not recognised. Add an entry to " + Form.class.getCanonicalName());
        }

}