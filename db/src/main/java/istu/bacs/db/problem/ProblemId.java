package istu.bacs.db.problem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ProblemId implements Serializable {
    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    private ResourceName resourceName;
    private String resourceProblemId;
}