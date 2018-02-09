package istu.bacs.web.model.submission;

import istu.bacs.db.submission.Language;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Value
public class SubmitSolution {

    private static final int MAX_SOLUTION_LENGTH = 64 * 1024 - 1;   //64 kilobytes

    int contestId;

    @NotEmpty
    String problemIndex;

    @NotNull
    Language language;

    @NotEmpty
    @Length(min = 1, max = MAX_SOLUTION_LENGTH)
    String solution;
}