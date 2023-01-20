package uk.gov.companieshouse.enums;

public class Forms {
    public enum Form {

        AD01("AD01", "", false),
        DS01("DS01", "", true);
        private final String type;
        private final String title;
        private final boolean highRiskForm;

        Form(final String type, final String title, final boolean highRiskForm) {
            this.type = type;
            this.title = title;
            this.highRiskForm = highRiskForm;
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