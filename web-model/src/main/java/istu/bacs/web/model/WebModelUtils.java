package istu.bacs.web.model;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ISO_DATE;
import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

@UtilityClass
public class WebModelUtils {

    private static final DateTimeFormatter dateTimeFormatter = ISO_DATE_TIME;
    private static final DateTimeFormatter dateFormatter = ISO_DATE;

    public static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null)
            return null;
        return dateTimeFormatter.format(dateTime);
    }

    public static LocalDateTime parseDateTime(String dateTime) {
        if (dateTime == null)
            return null;
        return dateTimeFormatter.parse(dateTime, LocalDateTime::from);
    }

    public static String formatDate(LocalDate date) {
        if (date == null)
            return null;
        return dateFormatter.format(date);
    }

    public static LocalDate parseDate(String date) {
        if (date == null)
            return null;
        return dateFormatter.parse(date, LocalDate::from);
    }
}