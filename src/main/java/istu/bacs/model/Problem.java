package istu.bacs.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Problem {

	@Id
	private String problemId;

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