package istu.bacs.contest;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Component
@Converter(autoApply = true)
public class ContestConverter implements AttributeConverter<Contest, Integer> {

    private static ContestService contestService;

    @Override
    public Integer convertToDatabaseColumn(Contest contest) {
        return contest.getContestId();
    }

    @Override
    public Contest convertToEntityAttribute(Integer contestId) {
        return contestService.findById(contestId);
    }

    @Bean
    private CommandLineRunner initContestConverter(ContestService contestService) {
        return args -> ContestConverter.contestService = contestService;
    }
}