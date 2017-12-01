package istu.bacs.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Problem {

	@Id
	private String problemId;

	@Transient
    private ProblemDetails details;
}
