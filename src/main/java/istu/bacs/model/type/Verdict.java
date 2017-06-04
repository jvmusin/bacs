package istu.bacs.model.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Verdict {
	
	Accepted(1, "Accepted"),
	WrongAnswer(2, "Wrong answer"),
	CompileError(3, "Compile error"),
	TimeLimitExceeded(4, "Time limit exceeded"),
	MemoryLimitExceeded(5, "Memory limit exceeded");
	
	private final Integer verdictId;
	private final String verdictName;
	
}