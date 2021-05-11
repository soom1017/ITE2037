package assignment2.exception;

public class InvalidAccessException extends Exception {
	public InvalidAccessException() {
		super("**INVALID ACCESS: please select again.**");
	}
	public InvalidAccessException(String message) {
		super("**INVALID ACCESS: " + message + "**");
	}
}
