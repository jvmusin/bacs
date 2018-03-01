package istu.bacs.web.problem;

public class ProblemNotFoundException extends RuntimeException {

    public ProblemNotFoundException(String message) {
        super(message);
    }
}