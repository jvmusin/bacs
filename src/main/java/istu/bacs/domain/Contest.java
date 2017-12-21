package istu.bacs.domain;

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

    public Duration getTimeSinceContestStart(LocalDateTime dateTime) {
	    return Duration.between(startTime, dateTime);
    }

    @Override
    public boolean equals(Object other) {
	    if (other == null) return false;
        Contest contest = (Contest) other;
        return contestId.equals(contest.contestId);
    }

    @Override
    public int hashCode() {
        return contestId;
    }
}