package istu.bacs.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Submission {
	
	@Id @GeneratedValue
	private Integer submissionId;
	
	@ManyToOne @JoinColumn(name = "author_id")
	private User author;
	@ManyToOne @JoinColumn(name = "contest_id")
	private Contest contest;
	@ManyToOne @JoinColumn(name = "problem_id")
	private Problem problem;
	
	private LocalDateTime creationTime;
	private Language language;
	private String solution;

	private String externalSubmissionId;

	@Transient
    private SubmissionResult result;
}