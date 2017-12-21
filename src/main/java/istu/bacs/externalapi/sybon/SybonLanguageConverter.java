package istu.bacs.externalapi.sybon;

import istu.bacs.model.Language;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

import static istu.bacs.model.Language.*;

@Component
class SybonLanguageConverter implements Converter<Language, Integer> {

    private static final Map<Language, Integer> supportedLanguages;

    static {
        supportedLanguages = new EnumMap<>(Language.class);
        supportedLanguages.put(C, 1);
        supportedLanguages.put(CPP, 2);
        supportedLanguages.put(Mono, 3);
        supportedLanguages.put(Delphi, 4);
        supportedLanguages.put(FPC, 5);
        supportedLanguages.put(Python2, 6);
        supportedLanguages.put(Python3, 7);
    }

    @Override
    public Integer convert(Language language) {
        return supportedLanguages.get(language);
    }

    public Set<Language> getSupportedLanguages() {
        return supportedLanguages.keySet();
    }
}