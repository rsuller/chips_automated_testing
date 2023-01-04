package uk.gov.companieshouse.utils;

import org.apache.commons.text.RandomStringGenerator;

public class RandomStringCreator {

    private RandomStringCreator() {
        // Private constructor
    }

    /**
     * Creates a string with random alpha characters.
     *
     * @param length length of the string required
     */
    public static String randomAlphaString(int length) {
        RandomStringGenerator random = new RandomStringGenerator.Builder()
                .withinRange('a', 'z').build();
        return random.generate(length);
    }

    /**
     * Creates a string with random numeric characters.
     *
     * @param length length of the string required
     */
    public static String randomNumeric(int length) {
        RandomStringGenerator random = new RandomStringGenerator.Builder()
                .withinRange('0', '9').build();
        return random.generate(length);
    }

    /**
     * Creates a string with random alphanumeric characters.
     *
     * @param length length of the string required
     */
    public static String randomAlphaNumeric(int length) {
        RandomStringGenerator random = new RandomStringGenerator.Builder()
                .withinRange('0', 'z')
                .filteredBy(Character::isLetterOrDigit)
                .build();
        return random.generate(length);
    }

}
