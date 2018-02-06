package istu.bacs.web.submission;

import istu.bacs.db.submission.Submission;
import istu.bacs.db.user.User;
import istu.bacs.web.submission.dto.EnhancedSubmitSolutionDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SubmissionService {
    Submission findById(int submissionId);
}