package ru.javawebinar.topjava.util;

import org.springframework.format.Formatter;

import java.lang.annotation.Annotation;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;


public class LocalDateFormatterISO implements Formatter<LocalDate>, Annotation {

    private DateTimeFormatter formatter = new DateTimeFormatterBuilder().append(DateTimeFormatter.ISO_DATE).toFormatter();

    public LocalDateFormatterISO() {
    }

    @Override
    public String print(LocalDate date, Locale locale) {
        if (date == null) {
            return "";
        }
        return formatter.format(date);
    }

    @Override
    public LocalDate parse(String formatted, Locale locale) {
        if (formatted.length() == 0) {
            return null;
        }

        return LocalDate.parse(formatted.substring(0, formatted.indexOf("T")), formatter);
    }


    @Override
    public Class<? extends Annotation> annotationType() {
        return this.getClass();
    }
}
