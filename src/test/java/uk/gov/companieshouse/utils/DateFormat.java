package uk.gov.companieshouse.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {

    /**
     * Get date as a string in the format dd/MM/yyyy.
     *
     * @param date the date
     * @return the date as a string
     */
    public static String getDateAsString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return simpleDateFormat.format(date);
    }
}
