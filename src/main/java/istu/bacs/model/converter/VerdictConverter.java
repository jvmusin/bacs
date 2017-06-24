package istu.bacs.model.converter;

import istu.bacs.model.type.Verdict;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class VerdictConverter implements AttributeConverter<Verdict, String> {
	
	@Override
	public String convertToDatabaseColumn(Verdict verdict) {
		return verdict.toString();
	}
	
	@Override
	public Verdict convertToEntityAttribute(String verdictName) {
		return Verdict.valueOf(verdictName);
	}
}