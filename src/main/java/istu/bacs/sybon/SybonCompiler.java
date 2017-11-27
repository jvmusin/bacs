package istu.bacs.sybon;

import lombok.Data;

@Data
public class SybonCompiler {
    private Integer id;
    private String type;
    private Integer timeLimitMillis;
    private Integer memoryLimitBytes;
    private Integer numberOfProcesses;
    private Integer outputLimitBytes;
    private Integer realTimeLimitMillis;
    private String args;
}