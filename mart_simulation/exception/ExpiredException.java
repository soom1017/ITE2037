package assignment3.exception;

public class ExpiredException extends Exception {
	public ExpiredException() {
		super("**INVALID ACCESS: please select again.**");
	}
	public ExpiredException(String message) {
		super(message);
	}
}
