package istu.bacs.web.model;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

@UtilityClass
public class WebModelUtils {

    private static final DateTimeFormatter dateTimeFormatter = ISO_DATE_TIME;

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
}