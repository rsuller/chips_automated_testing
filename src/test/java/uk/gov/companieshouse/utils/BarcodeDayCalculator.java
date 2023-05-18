package uk.gov.companieshouse.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BarcodeDayCalculator {

    private static final int BASE_YEAR = 2008;
    private static final int BASE_MONTH = Calendar.MAY;
    private static final int BASE_DAY_OF_MONTH = 25;
    private static final int MIDDAY_HOUR = 12;
    private static final long MILLISECONDS_IN_DAY = 86400000L;

    /**
     * Calculates days from a base date.
     *
     * @param date the date
     * @return days, the number of days calculated.
     */
    int calculateDaysFromBaseDate(Date date) {
        Calendar baseCalendar = Calendar.getInstance();
        baseCalendar.setLenient(false);
        baseCalendar.set(Calendar.YEAR, BASE_YEAR);
        baseCalendar.set(Calendar.MONTH, BASE_MONTH);
        baseCalendar.set(Calendar.DAY_OF_MONTH, BASE_DAY_OF_MONTH);
        baseCalendar.set(Calendar.HOUR_OF_DAY, MIDDAY_HOUR);
        baseCalendar.set(Calendar.MINUTE, 0);
        baseCalendar.set(Calendar.SECOND, 0);
        baseCalendar.set(Calendar.MILLISECOND, 0);

        Calendar receiptCalendar = Calendar.getInstance();
        receiptCalendar.setLenient(false);
        receiptCalendar.setTime(date);
        receiptCalendar.set(Calendar.HOUR_OF_DAY, MIDDAY_HOUR);
        receiptCalendar.set(Calendar.MINUTE, 0);
        receiptCalendar.set(Calendar.SECOND, 0);
        receiptCalendar.set(Calendar.MILLISECOND, 0);

        Date receiptDate = receiptCalendar.getTime();
        Date baseDate = baseCalendar.getTime();
        int days = (int) ((receiptDate.getTime() - baseDate.getTime()) / MILLISECONDS_IN_DAY);
        if (days < 0) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");
            String formattedDate = dateFormat.format(baseDate);
            throw new IllegalArgumentException("Date cannot be before " + formattedDate);
        }

        return days;
    }

}

