package istu.bacs.sybon;

import lombok.Data;

@Data
public class SybonBuildResult {
    private SybonBuildResultStatus status;
    private String output;
}