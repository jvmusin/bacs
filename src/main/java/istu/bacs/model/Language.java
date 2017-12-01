package istu.bacs.model;

import lombok.Getter;

@Getter
public class Language {
	
	public static final Language C = new Language(0, "C");
	public static final Language CPP = new Language(1, "C++");
	public static final Language Delphi = new Language(2, "Delphi");
	public static final Language FPC = new Language(3, "Free Pascal");
	public static final Language Python2 = new Language(4, "Python 2");
	public static final Language Python3 = new Language(5, "Python 3");
	public static final Language Mono = new Language(6, "Mono C#");

	public static Language[] values() {
	    return new Language[]{
	            C,
                CPP,
                Delphi,
                FPC,
                Python2,
                Python3,
                Mono
        };
    }

    private final int languageId;
	private final String languageName;

    private Language(int languageId, String languageName) {
        this.languageId = languageId;
        this.languageName = languageName;
    }

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