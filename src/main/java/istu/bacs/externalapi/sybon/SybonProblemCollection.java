package istu.bacs.externalapi.sybon;

import lombok.Data;

import java.util.List;

@Data
class SybonProblemCollection {
    private Integer id;
    private String name;
    private String description;
    private Integer problemCount;
    private List<SybonProblem> problems;
}