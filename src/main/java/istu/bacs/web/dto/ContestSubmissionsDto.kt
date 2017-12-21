package istu.bacs.web.dto

import istu.bacs.domain.Submission

class ContestSubmissionsDto(var contestName: String, submissions: List<Submission>) {
    var submissions: List<SubmissionDto> = submissions.map { SubmissionDto(it) }
}