package istu.bacs.model;

import istu.bacs.model.type.Language;
import istu.bacs.model.type.Verdict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Submission {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer submissionId;
	
	@ManyToOne
	@JoinColumn(name = "author_id")
	private User author;
	@ManyToOne @JoinColumn(name = "contest_id")
	private Contest contest;
	@ManyToOne @JoinColumn(name = "problem_id")
	private Problem problem;
	
	private LocalDateTime creationTime;
	private Language language;
	private String solution;
	
	private Verdict verdict;
	private Integer firstFailedTest;
	private Double timeConsumedMillis;
	private Double memoryConsumedBytes;
	
}