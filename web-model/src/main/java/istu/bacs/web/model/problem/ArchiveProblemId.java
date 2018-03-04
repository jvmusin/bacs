package istu.bacs.web.model.problem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArchiveProblemId {
    private String resourceName;
    private String resourceProblemId;
}