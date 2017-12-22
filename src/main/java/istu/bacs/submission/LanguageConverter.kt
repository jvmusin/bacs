package istu.bacs.submission

import org.springframework.stereotype.Component
import javax.persistence.AttributeConverter

@Component
class LanguageConverter : AttributeConverter<Language, Int> {
    override fun convertToDatabaseColumn(language: Language?) = language!!.languageId
    override fun convertToEntityAttribute(id: Int?) = Language.findById(id!!)
}