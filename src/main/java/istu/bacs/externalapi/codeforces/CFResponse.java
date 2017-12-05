package istu.bacs.externalapi.codeforces;

import lombok.Data;

@Data
public class CFResponse<T> {
    private CFResponseStatus status;
    private String comment;
    private T result;
}