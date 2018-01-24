package istu.bacs.externalapi.fake;

final class Measure {

    static final int BYTE = 1;
    static final int KILOBYTE = 1024 * BYTE;
    static final int MEGABYTE = 1024 * KILOBYTE;
    static final int GIGABYTE = 1024 * MEGABYTE;

    static final int MILLISECOND = 1;
    static final int SECOND = 1000 * MILLISECOND;
    static final int MINUTE = 60 * SECOND;
    static final int HOUR = 60 * MINUTE;
    static final int DAY = 24 * HOUR;

    private Measure() {
    }
}