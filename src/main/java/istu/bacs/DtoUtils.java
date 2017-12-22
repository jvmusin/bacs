package istu.bacs;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DtoUtils {
    public static final String DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm";
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

    public static String format(LocalDateTime time) {
        return formatter.format(time);
    }

    public static LocalDateTime parse(String time) {
        return (LocalDateTime) formatter.parse(time);
    }
}