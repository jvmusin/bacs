package istu.bacs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Problem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer problemId;

	private String problemName;
	private String statementUrl;

    private Integer pretestCount;
    private Integer testCount;

	private Integer timeLimitMillis;
	private Integer memoryLimitBytes;
}