package istu.bacs.db.problem;

import lombok.*;
import lombok.experimental.Wither;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "problem", schema = "public")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "problemId")
public class Problem {

    @EmbeddedId
    @Wither
    private ProblemId problemId;

    private String name;
    private String statementUrl;

    private int timeLimitMillis;
    private int memoryLimitBytes;
}