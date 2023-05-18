package uk.gov.companieshouse.enums;

import java.util.Random;

/**
 * Enum containing the GDPR approved Forenames stored in DB.
 */
public enum TestDataForenames {
    DAN("Dan"),
    DAVE("Dave"),
    JANE("Jane"),
    TOM("Tom"),
    ADRIAN("Adrian"),
    ALAN("Alan"),
    DAVID("David"),
    JAMES("James"),
    FREDERICK("Frederick"),
    ROBERT("Robert"),
    GARETH("Gareth"),
    DANIEL("Daniel"),
    JASON("Jason"),
    PAUL("Paul");

    TestDataForenames(String forename) {
    }

    public static String getRandomTestForename() {
        Random random = new Random();
        return String.valueOf(values()[random.nextInt(TestDataForenames.values().length)]);
    }
}
