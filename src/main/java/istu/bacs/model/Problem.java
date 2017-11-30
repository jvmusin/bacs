package istu.bacs.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Problem {

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer problemId;

	@Transient
    private ProblemDetails details;

    @Data @AllArgsConstructor
    public static class ProblemDetails {
        private String problemName;
        private String statementUrl;

        private Integer pretestCount;
        private Integer testCount;

        private Integer timeLimitMillis;
        private Integer memoryLimitBytes;
    }
}