package istu.bacs.web.submission;

import istu.bacs.web.contest.ContestNotFoundException;
import istu.bacs.web.model.submission.Submission;
import istu.bacs.web.model.submission.SubmitSolution;
import istu.bacs.web.problem.ProblemNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/submissions")
@AllArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;

    @GetMapping("/{submissionId}")
    public ResponseEntity<Submission> getSubmission(@PathVariable int submissionId) {
        istu.bacs.db.submission.Submission submission = submissionService.findById(submissionId);
        if (submission == null)
            return notFound().build();
        return ok(Submission.fromDb(submission));
    }

    @GetMapping
    public List<Submission> getSubmissions(
            @RequestParam(name = "contestId", required = false) Integer contestId,
            @RequestParam(name = "problemIndex", required = false) String problemIndex,
            @RequestParam(name = "author", required = false) String authorUsername) {

        return submissionService.findAll(contestId, problemIndex, authorUsername).stream()
                .map(Submission::fromDb)
                .collect(toList());
    }

    @PostMapping
    public ResponseEntity submitSolution(@Valid @RequestBody SubmitSolution sol) {
        try {
            return ok(submissionService.submit(sol));
        } catch (ContestNotFoundException | ProblemNotFoundException e) {
            return badRequest().body(e.getMessage());
        }
    }
}