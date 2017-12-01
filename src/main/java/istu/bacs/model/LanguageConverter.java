package istu.bacs.model;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Locale;

@Component
@Converter(autoApply = true)
public class LanguageConverter implements Formatter<Language>, AttributeConverter<Language, Integer> {
    //For Thymeleaf
    @Override
    public Language parse(String s, Locale locale) {
        return Language.findById(Integer.parseInt(s));
    }

    @Override
    public String print(Language language, Locale locale) {
        throw new RuntimeException("How to use it?!");
    }

    @Override
    public Integer convertToDatabaseColumn(Language language) {
        return language.getLanguageId();
    }

    @Override
    public Language convertToEntityAttribute(Integer languageId) {
        return Language.findById(languageId);
    }
}