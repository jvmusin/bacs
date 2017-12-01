package istu.bacs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public enum Language {

    C       (0, "C"),
    CPP     (1, "C++"),
    Delphi  (2, "Delphi"),
    FPC     (3, "Free Pascal"),
    Python2 (4, "Python 2"),
    Python3 (5, "Python 3"),
    Mono    (6, "Mono C#");

    private final Integer languageId;
    private final String languageName;

    @Override
    public String toString() {
        return languageName;
    }

    public static Language findById(int id) {
        for (Language lang : values())
            if (lang.languageId == id)
                return lang;
        return null;
    }
}