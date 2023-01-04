package uk.gov.companieshouse.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class BarcodeGenerator {

    private static final String VALID_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final int NUM_RANDOM_CHARACTERS = 5;

    private BarcodeDayCalculator barcodeDayCalculator;

    public BarcodeGenerator() {
        this.barcodeDayCalculator = new BarcodeDayCalculator();
    }

    private String getPrefix() {
        Random rand = new Random();
        // remove the X from appearing as the first character as if this appears
        // first then the app treats it as an XML submission.
        final String validCharsForFirstChar = VALID_CHARS.replace("X", "");
        int firstRandomNumber = rand.nextInt(validCharsForFirstChar.length());

        int secondRandomNumber = rand.nextInt(VALID_CHARS.length());
        StringBuffer buffer = new StringBuffer();
        return buffer.append(validCharsForFirstChar.charAt(firstRandomNumber))
                .append(VALID_CHARS.charAt(secondRandomNumber)).toString();
    }

    @Deprecated
    public String generateWithPrefix(Date date) {
        return generateWithPrefix(date, getPrefix());
    }

    @Deprecated
    private String generateWithPrefix(Date date, String prefix) {
        if (date == null) {
            throw new NullPointerException("date is null");
        }
        if (prefix == null) {
            throw new NullPointerException("prefix is null");
        }
        if (prefix.length() > NUM_RANDOM_CHARACTERS) {
            throw new IllegalArgumentException(
                    "Prefix is too long (maximum length is "
                            + NUM_RANDOM_CHARACTERS + ", actual is " + prefix.length() + ')');
        }

        BarcodeWriter barcodeWriter = new BarcodeWriter();
        int prefixLength = prefix.length();
        for (int i = 0; i < prefixLength; ++i) {
            barcodeWriter.appendFixedCharacter(prefix.charAt(i));
        }
        for (int i = prefixLength; i < NUM_RANDOM_CHARACTERS; ++i) {
            barcodeWriter.appendRandomCharacter();
        }

        return generateInternal(barcodeWriter, date);
    }

    private String generateInternal(BarcodeWriter barcodeWriter, Date date) {
        int days = barcodeDayCalculator.calculateDaysFromBaseDate(date);
        barcodeWriter.appendDayCharacters(days);
        barcodeWriter.appendChecksum();

        return barcodeWriter.getBarcode();
    }

    /**
     * Generates a new style barcode.
     *
     * @param date the date must not be before 6 November 2004
     * @return a new style barcode for the date
     */
    public String generateNewStyleBarcode(Date date) {

        final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        // if before 6th nov 2004 throw error
        try {
            Date beforeDate = sdf.parse("2004-11-06 00:00");
            if (date.before(beforeDate)) {
                throw new RuntimeException("Date cannot be before 6th November 2004");
            }
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        String dayOfYearBin = "000000000" + Integer.toBinaryString(dayOfYear);
        dayOfYearBin = dayOfYearBin.substring(dayOfYearBin.length() - 9);
        int year = calendar.get(Calendar.YEAR) - 2011;
        String yearBin = "00000" + Integer.toBinaryString(year);
        yearBin = yearBin.substring(yearBin.length() - 5);
        boolean validBarcode = false;
        char[] barcode = new char[]{'0', '0', '0', '0', '0', '0', '0', '0'};

        do {
            int randomNumber = new Double(Math.floor(new Random().nextFloat() * 524287)).intValue();
            String randomNumberBin = "0000000000000000000" + Integer.toBinaryString(randomNumber);
            randomNumberBin = randomNumberBin.substring(randomNumberBin.length() - 19);
            int count = 0;
            int leadingCharIndex;

            do {
                leadingCharIndex = (int) Math.floor(new Random().nextFloat() * 36);
            } while (VALID_CHARS.charAt(leadingCharIndex) == 'X');

            do {
                String barStr = yearBin + dayOfYearBin + randomNumberBin
                        + String.valueOf(count) + "00";
                String barHi = barStr.substring(0, 4);
                String barLo = barStr.substring(4);
                long barHiBin = Long.parseLong(barHi, 2);
                long barLoBin = Long.parseLong(barLo, 2);
                String parity = "00" + Long.toBinaryString(getParity(barLoBin, barHiBin));

                barStr = barStr.substring(0, 33) + String.valueOf(count)
                        + parity.substring(parity.length() - 2);
                long bar = Long.parseLong(barStr, 2);
                int pos = Math.toIntExact(bar % 36);
                barcode[7] = VALID_CHARS.charAt(pos);
                bar = bar / 36;
                int oldParity = 0;
                for (int i = 6; i > 0; i--) {
                    pos = Math.toIntExact(bar % 36);
                    barcode[i] = VALID_CHARS.charAt(pos);
                    bar = bar / 36;
                    oldParity += pos;
                }
                pos = VALID_CHARS.charAt(oldParity % 36);
                count++;
                if (pos != barcode[7]) {
                    validBarcode = true;
                }

                barcode[0] = VALID_CHARS.charAt(leadingCharIndex);

            } while (!validBarcode && count <= 1);
        } while (!validBarcode);

        return new String(barcode);
    }

    private long getParity(long lo, long hi) {
        long parity = 0;
        parity = parity ^ (lo & 252);
        parity = parity ^ ((lo >> 8)  & 255);
        parity = parity ^ ((lo >> 16) & 255);
        parity = parity ^ ((lo >> 24) & 255);
        parity = parity ^ (hi & 255);
        parity = parity % 4;
        return parity;
    }
}

