package istu.bacs.web.externalapi.sybon;

import lombok.Data;

@Data
public class SybonBuildResult {
    private Status status;
    private String output;

    enum Status {
        OK,
        FAILED,
        PENDING,
        SERVER_ERROR
    }
}