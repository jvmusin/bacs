package istu.bacs.web.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

public class WebModelUtils {

    private static DateTimeFormatter formatter = ISO_DATE_TIME;

    public static String formatDateTime(LocalDateTime dateTime) {
        return formatter.format(dateTime);
    }

    public static LocalDateTime parseDateTime(String dateTime) {
        return formatter.parse(dateTime, LocalDateTime::from);
    }
}