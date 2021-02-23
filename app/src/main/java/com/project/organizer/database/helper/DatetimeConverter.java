package com.project.organizer.database.helper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DatetimeConverter {
    private static String pattern = "yyyy-MM-dd kk:mm:ss";

    public static String getString(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDateTime getDateTime(String value) {
        return LocalDateTime.parse(value, DateTimeFormatter.ofPattern(pattern));
    }
}

