package istu.bacs.web.model.submission;

import istu.bacs.db.submission.Language;
import lombok.Value;

@Value
public class SubmitSolution {
    int contestId;
    String problemIndex;
    Language language;
    String solution;
}