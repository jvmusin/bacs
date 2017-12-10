package istu.bacs.web.dto

import istu.bacs.model.Submission

class ContestSubmissionsDto(var contestName: String, submissions: List<Submission>) {
    var submissions: List<SubmissionDto> = submissions.map { SubmissionDto(it) }
}