package istu.bacs.web.externalapi.sybon;

import lombok.Data;

import java.util.List;

@Data
public class SybonProblemCollection {
    private int id;
    private String name;
    private String description;
    private Integer problemsCount;
    private List<SybonProblem> problems;
}