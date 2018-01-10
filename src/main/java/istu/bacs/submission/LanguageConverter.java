package istu.bacs.submission;

import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;

@Component
public class LanguageConverter implements AttributeConverter<Language, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Language language) {
        return language.getLanguageId();
    }

    @Override
    public Language convertToEntityAttribute(Integer languageId) {
        return Language.findById(languageId);
    }
}