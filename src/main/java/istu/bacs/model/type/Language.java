package istu.bacs.model.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Language {
	
	CPP(1, "C++"),
	Java(2, "Java"),
	Kotlin(3, "Kotlin"),
	Python(4, "Python");
	
	private final Integer languageId;
	private final String languageName;
	
}