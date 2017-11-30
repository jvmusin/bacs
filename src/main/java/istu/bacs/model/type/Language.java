package istu.bacs.model.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Language {
	
	C(1, "C"),
	CPP(2, "C++"),
	Delphi(3, "Delphi"),
	FPC(4, "Free Pascal"),
	Python2(5, "Python 2"),
	Python3(6, "Python 3"),
    Mono(8, "Mono");

	private final Integer languageId;
	private final String languageName;
}