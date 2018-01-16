package istu.bacs.externalapi;

public class ExternalApiHelper {
    public static String extractResource(String s) {
        return s.split("@", 2)[0];
    }
    public static String removeResource(String s) {
        return s.split("@", 2)[1];
    }
    public static String addResource(Object s, String resource) {
        return resource + "@" + s;
    }
}