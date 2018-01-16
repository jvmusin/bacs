package istu.bacs.web.submission;

import istu.bacs.web.submission.dto.FullSubmissionDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("submissions")
public class SubmissionController {

    private final SubmissionService submissionService;

    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @RequestMapping("{submissionId}")
    public FullSubmissionDto getSubmission(@PathVariable int submissionId) {
        return new FullSubmissionDto(submissionService.findById(submissionId));
    }
}