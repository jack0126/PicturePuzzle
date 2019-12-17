package com.demo.example1.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {

    public static long parseTimeDescription(String time) {
        return parseDescription(time, "HH:mm:ss");
    }

    public static long parseDescription(String description, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.parse(description).getTime();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toTimeDescription(long time) {
        return toDescription(time, "HH:mm:ss");
    }

    public static String toDescription(long time, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(new Date(time));
    }
}
