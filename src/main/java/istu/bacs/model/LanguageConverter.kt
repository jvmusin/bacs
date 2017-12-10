package istu.bacs.model

import org.springframework.stereotype.Component
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Component
@Converter(autoApply = true)
class LanguageConverter : AttributeConverter<Language, Int> {
    override fun convertToDatabaseColumn(language: Language?) = language!!.languageId
    override fun convertToEntityAttribute(id: Int?) = Language.findById(id!!)
}