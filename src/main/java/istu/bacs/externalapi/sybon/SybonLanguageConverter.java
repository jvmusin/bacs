package istu.bacs.externalapi.sybon;

import istu.bacs.model.Language;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
class SybonLanguageConverter implements Converter<Language, Integer> {

    @Override
    public Integer convert(Language language) {
        switch (language) {
            case C:       return 1;
            case CPP:     return 2;
            case Delphi:  return 3;
            case FPC:     return 4;
            case Python2: return 5;
            case Python3: return 6;
            case Mono:    return 8;
            default: throw new RuntimeException("Language is not supported: " + language);
        }
    }
}