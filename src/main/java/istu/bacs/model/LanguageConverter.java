package istu.bacs.model;

import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Component
@Converter(autoApply = true)
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