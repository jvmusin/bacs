package istu.bacs.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Comparator;

@Data
@Entity
public class Problem implements Comparable<Problem> {

	@Id
	private String problemId;

	@Transient
    private ProblemDetails details;

	@Transient
    private Comparator<Problem> comparator = Comparator.comparing(p -> p.problemId);

    @Override
    public int compareTo(Problem other) {
        return comparator.compare(this, other);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Problem problem = (Problem) o;
        return problemId.equals(problem.problemId);
    }

    @Override
    public int hashCode() {
        return problemId.hashCode();
    }
}
