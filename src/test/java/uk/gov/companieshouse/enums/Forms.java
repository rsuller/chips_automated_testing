package uk.gov.companieshouse.enums;

public class Forms {
    public enum Form {

        AD01("AD01", "AD01: Change to registered office address", false, "REGISTERED OFFICE CHANGED"),
        AP01("AP01", "AP01", false, "DIRECTOR APPOINTED"),
        CH01("CH01", "Change Officers", false, "DIRECTOR'S CHANGE OF PARTICULA..."),
        DS01("DS01", "Process Voluntary Dissolution Form DS01/LLDS01", true, "APPLICATION FOR STRIKING-OFF");
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

        public String gettransactionHistoryPartialDescription() {
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
            throw new RuntimeException("The form type " + title + " was not recognised. Add an entry to " + Forms.class.getCanonicalName());
        }

    }

}