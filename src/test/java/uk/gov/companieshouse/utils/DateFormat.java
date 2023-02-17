package uk.gov.companieshouse.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {
    public static String getDateAsString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return simpleDateFormat.format(date);
    }
}
