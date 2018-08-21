package ru.javawebinar.topjava.util;

import org.springframework.format.Formatter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

public class LocalTimeFormatterISO implements Formatter<LocalTime> {


    private DateTimeFormatter formatter = new DateTimeFormatterBuilder().append(DateTimeFormatter.ISO_TIME).toFormatter();

    public LocalTimeFormatterISO() {
    }

    @Override
    public String print(LocalTime date, Locale locale) {
        if (date == null) {
            return "";
        }
        return formatter.format(date);
    }

    @Override
    public LocalTime parse(String formatted, Locale locale) {
        if (formatted.length() == 0) {
            return null;
        }
        return LocalTime.parse(formatted.substring(formatted.indexOf("T")+1), formatter);
    }
}
