package istu.bacs.web.model.submission;

import istu.bacs.db.submission.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmitSolution {

    private static final int MAX_SOLUTION_LENGTH = 64 * 1024 - 1;   //64 kilobytes

    private int contestId;

    @NotEmpty
    private String problemIndex;

    @NotNull
    private Language language;

    @NotNull
    @Length(min = 1, max = MAX_SOLUTION_LENGTH)
    private String solution;
}