package istu.bacs.model;

import lombok.Data;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static java.time.LocalDateTime.now;

@Data
@Entity
public class Contest {

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer contestId;
	
	private String contestName;
	
	private LocalDateTime startTime;
	private LocalDateTime finishTime;

	@Convert(converter = ProblemListConverter.class)
	private List<Problem> problems;

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

    public Duration getTimeSinceContestStart(Submission submission) {
	    return Duration.between(startTime, submission.getCreationTime());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Contest contest = (Contest) o;
        return contestId.equals(contest.contestId);
    }

    @Override
    public int hashCode() {
        return contestId;
    }
}