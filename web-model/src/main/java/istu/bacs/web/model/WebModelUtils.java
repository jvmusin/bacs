package istu.bacs.web.model;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

@UtilityClass
public class WebModelUtils {

    private DateTimeFormatter formatter = ISO_DATE_TIME;

    public String formatDateTime(LocalDateTime dateTime) {
        return formatter.format(dateTime);
    }

    public LocalDateTime parseDateTime(String dateTime) {
        return formatter.parse(dateTime, LocalDateTime::from);
    }
}