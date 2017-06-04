package istu.bacs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Contest {
	
	private Integer contestId;
	
	private String contestName;
	
	private LocalDateTime startTime;
	private LocalDateTime finishTime;
	
	private List<Problem> problems;
	
}