package istu.bacs.web.model.submission;

import istu.bacs.db.submission.Language;
import lombok.Value;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Value
public class SubmitSolution {

    private static final int MAX_SOLUTION_SIZE_BYTES = 64 * 1024 - 1;   //64 kilobytes

    int contestId;

    @NotEmpty
    String problemIndex;

    @NotNull
    Language language;

    @NotEmpty
    @Max(MAX_SOLUTION_SIZE_BYTES)
    String solution;
}