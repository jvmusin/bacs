package istu.bacs.model;

public enum Verdict {
    OK("OK"),
    WRONG_ANSWER("Wrong Answer"),
    PRESENTATION_ERROR("Presentation Error"),
    QUERIES_LIMIT_EXCEEDED("Queries Limit Exceeded"),
    INCORRECT_REQUEST("Incorrect Request"),
    INSUFFICIENT_DATA("Insufficient Data"),
    EXCESS_DATA("Excess Data"),
    OUTPUT_LIMIT_EXCEEDED("Output Limit Exceeded"),
    TERMINATION_REAL_TIME_LIMIT_EXCEEDED("Termination Real Time Limit Exceeded"),
    ABNORMAL_EXIT("Abnormal Exit"),
    MEMORY_LIMIT_EXCEEDED("Memory Limit Exceeded"),
    TIME_LIMIT_EXCEEDED("Time Limit Exceeded"),
    REAL_TIME_LIMIT_EXCEEDED("Real Time Limit Exceeded"),
    TERMINATED_BY_SYSTEM("Terminated By System"),
    CUSTOM_FAILURE("Custom Failure"),
    FAIL_TEST("Fail Test"),
    FAILED("Failed"),
    SKIPPED("Skipped"),

    COMPILE_ERROR("Compile Error"),
    PENDING("Pending"),

    SERVER_ERROR("Server Error");

    public final String value;

    Verdict(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}