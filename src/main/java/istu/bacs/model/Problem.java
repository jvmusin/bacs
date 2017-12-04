package istu.bacs.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
public class Problem {

	@Id
	private String problemId;

	@Transient
    private ProblemDetails details;
}
