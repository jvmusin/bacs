package istu.bacs.model;

import istu.bacs.model.type.Language;
import istu.bacs.model.type.Verdict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class Submission {
	
	private Integer submissionId;
	
	private User author;
	private Contest contest;
	private Problem problem;
	
	private LocalDateTime creationTime;
	private Language language;
	private String solution;
	
	private Verdict verdict;
	private Integer firstFailedTest;
	private Double timeConsumedMillis;
	private Double memoryConsumedBytes;
	
}