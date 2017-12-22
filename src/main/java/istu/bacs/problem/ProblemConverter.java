package istu.bacs.problem;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;

@Component
public class ProblemConverter implements AttributeConverter<Problem, String> {

    private static ProblemService problemService;

    @Override
    public String convertToDatabaseColumn(Problem problem) {
        return problem.getProblemId();
    }

    @Override
    public Problem convertToEntityAttribute(String problemId) {
        return problemService.findById(problemId);
    }

    @Bean
    private CommandLineRunner initProblemConverter(ProblemService problemService) {
        return args -> ProblemConverter.problemService = problemService;
    }
}