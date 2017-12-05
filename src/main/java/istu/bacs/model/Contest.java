package istu.bacs.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static java.time.LocalDateTime.now;
import static javax.persistence.FetchType.EAGER;

@Data
@Entity
public class Contest {

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer contestId;
	
	private String contestName;
	
	private LocalDateTime startTime;
	private LocalDateTime finishTime;

	@ManyToMany(fetch = EAGER)
	@OrderColumn(name = "problem_index")
	@JoinTable(name = "contest_problems",
			joinColumns = @JoinColumn(name = "contest_id"),
			inverseJoinColumns = @JoinColumn(name = "problem_id"))
	private List<Problem> problems;

	@OneToMany(mappedBy = "contest")
    private List<Submission> submissions;

	public boolean isRunning() {
        return isStarted() && !isFinished();
    }

    public boolean isStarted() {
	    return startTime != null && now().isBefore(startTime);
    }

    public boolean isFinished() {
	    return finishTime != null && now().isAfter(finishTime);
    }

    public int getProblemIndex(Problem problem) {
	    for (int i = 0; i < problems.size(); i++)
	        if (problems.get(i).getProblemId().equals(problem.getProblemId()))
	            return i;
	    return -1;
    }
}