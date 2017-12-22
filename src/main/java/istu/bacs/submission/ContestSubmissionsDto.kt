package istu.bacs.submission

class ContestSubmissionsDto(var contestName: String, submissions: List<Submission>) {
    var submissions: List<SubmissionDto> = submissions.map { SubmissionDto(it) }
}