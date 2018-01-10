package istu.bacs.submission;

import lombok.Getter;

@Getter
public enum Language {
    C       (0, "C"),
    CPP     (1, "C++"),
    Delphi  (2, "Delphi"),
    FPC     (3, "Free Pascal"),
    Python2 (4, "Python 2"),
    Python3 (5, "Python 3"),
    Mono    (6, "Mono C#");

    private final int languageId;
    private final String languageName;

    Language(int languageId, String languageName) {
        this.languageId = languageId;
        this.languageName = languageName;
    }

    public static Language findById(int id) {
        for (Language lang : values())
            if (lang.languageId == id)
                return lang;
        return null;
    }
}