package istu.bacs.externalapi.sybonfake;

public final class Measure {

    public static final int BYTE = 1;
    public static final int KILOBYTE = 1024 * BYTE;
    public static final int MEGABYTE = 1024 * KILOBYTE;
    public static final int GIGABYTE = 1024 * MEGABYTE;

    public static final int MILLISECOND = 1;
    public static final int SECOND = 1000 * MILLISECOND;
    public static final int MINUTE = 60 * SECOND;
    public static final int HOUR = 60 * MINUTE;
    public static final int DAY = 24 * HOUR;

    private Measure() {
    }
}