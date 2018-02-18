package istu.bacs.externalapi.aggregator;

public class ApiNotFoundException extends RuntimeException {

    public ApiNotFoundException(String message) {
        super(message);
    }
}