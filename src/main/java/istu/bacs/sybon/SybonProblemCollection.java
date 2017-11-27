package istu.bacs.sybon;

import lombok.Data;

@Data
public class SybonProblemCollection {
    private Integer id;
    private String name;
    private String description;
    private Integer problemCount;
    private SybonProblem[] problems;
}