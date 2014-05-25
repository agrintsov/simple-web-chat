package com.sagr.common;

import java.lang.String;import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {

    public static final SimpleDateFormat DATE_WITH_TIME_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    public static String getDateWithTimeFormat(Date date){
        return DATE_WITH_TIME_FORMAT.format(date);
    }
}
