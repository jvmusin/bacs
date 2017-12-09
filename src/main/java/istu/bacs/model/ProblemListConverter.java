package istu.bacs.model;

import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

@Component
@Converter
public class ProblemListConverter implements AttributeConverter<List<Problem>, String> {

    private static final ProblemConverter problemConverter = new ProblemConverter();

    @Override
    public String convertToDatabaseColumn(List<Problem> problems) {
        return problems.stream().map(Problem::getProblemId).collect(joining(","));
    }

    @Override
    public List<Problem> convertToEntityAttribute(String dbData) {
        if (dbData.isEmpty()) return new ArrayList<>();
        return Arrays.stream(dbData.split(","))
                .map(problemConverter::convertToEntityAttribute)
                .collect(toList());
    }
}