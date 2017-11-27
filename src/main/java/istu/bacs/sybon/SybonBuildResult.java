package istu.bacs.sybon;

import lombok.Data;

@Data
public class SybonBuildResult {
    private Status status;
    private String output;

    public enum Status {
        OK,
        FAILED
    }
}