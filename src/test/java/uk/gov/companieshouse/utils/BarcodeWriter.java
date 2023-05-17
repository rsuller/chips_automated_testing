package uk.gov.companieshouse.utils;

import java.util.Random;

public class BarcodeWriter {

    private static final String VALID_CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int NUM_VALID_CHARACTERS = VALID_CHARACTERS.length();

    private final StringBuffer buf = new StringBuffer();
    private final Random random = new Random();
    private int checksum;

    void appendRandomCharacter() {
        int randomIndex = random.nextInt(NUM_VALID_CHARACTERS);
        appendCharacterAndUpdateChecksum(randomIndex);
    }

    void appendFixedCharacter(char ch) {
        int index = VALID_CHARACTERS.indexOf(ch);
        if (index == -1) {
            throw new IllegalArgumentException("Character not in valid set \""
                    + VALID_CHARACTERS + "\": " + ch);
        }
        appendCharacterAndUpdateChecksum(index);
    }

    void appendDayCharacters(int days) {
        int divisionIndex = (days % 1296) / NUM_VALID_CHARACTERS;
        appendCharacterAndUpdateChecksum(divisionIndex);

        int remainderIndex = days % NUM_VALID_CHARACTERS;
        appendCharacterAndUpdateChecksum(remainderIndex);
    }

    void appendChecksum() {
        int index = checksum % NUM_VALID_CHARACTERS;
        appendCharacter(index);
    }

    public String getBarcode() {
        return buf.toString();
    }

    private void appendCharacterAndUpdateChecksum(int index) {
        appendCharacter(index);
        checksum += index;
    }

    private void appendCharacter(int index) {
        char nextChar = VALID_CHARACTERS.charAt(index);
        buf.append(nextChar);
    }

}