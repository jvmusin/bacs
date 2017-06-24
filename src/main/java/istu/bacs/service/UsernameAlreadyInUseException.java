package istu.bacs.service;

public class UsernameAlreadyInUseException extends RuntimeException {
	public UsernameAlreadyInUseException(String username) {
		super("Username is already in use: " + username);
	}
}