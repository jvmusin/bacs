package istu.bacs.model;

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
public class Contest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer contestId;
	
	private String contestName;
	
	private LocalDateTime startTime;
	private LocalDateTime finishTime;
	
	@ManyToMany
	@OrderColumn(name = "order")
	@JoinTable(name = "contest_problems",
			joinColumns = @JoinColumn(name = "contest_id"),
			inverseJoinColumns = @JoinColumn(name = "problem_id"))
	private Problem[] problems;
}