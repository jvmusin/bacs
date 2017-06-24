package istu.bacs.model.converter;

import istu.bacs.model.type.Language;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LanguageConverter implements AttributeConverter<Language, String> {
	
	@Override
	public String convertToDatabaseColumn(Language language) {
		return language.toString();
	}
	
	@Override
	public Language convertToEntityAttribute(String languageName) {
		return Language.valueOf(languageName);
	}
}