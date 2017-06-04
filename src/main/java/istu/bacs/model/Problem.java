package istu.bacs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Problem {
	
	private Integer problemId;
	
	private String problemName;
	
	private Double timeLimitMillis;
	private Double memoryLimitBytes;
	
}