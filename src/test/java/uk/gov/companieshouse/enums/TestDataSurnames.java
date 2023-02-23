package uk.gov.companieshouse.enums;

import java.util.Random;

/**
 * Enum containing the GDPR approved Surnames stored in DB.
 */
public enum TestDataSurnames {
    DAVIES("Davies"),
    JENKINS("Jenkins"),
    MARTINEZ("Martinez"),
    LEWIS("Lewis"),
    EDWARDS("Edwards"),
    SKIVINGTON("Skivington"),
    BOWEN("Bowen"),
    ANDERSON("Anderson"),
    JONES("Jones"),
    SMITH("Smith"),
    COOK("Cook"),
    POTTER("Potter");

    private String surname;

    TestDataSurnames(String surname) {
        this.surname = surname;
    }

    public static String getRandomTestSurname() {
        Random random = new Random();
        return String.valueOf(values()[random.nextInt(TestDataSurnames.values().length)]);
    }
}
