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
}
