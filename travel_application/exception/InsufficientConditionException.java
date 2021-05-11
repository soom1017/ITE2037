package assignment2.exception;

public class InsufficientConditionException extends Exception {
	public InsufficientConditionException() {
		super("**INSUFFICIENT CONDITION: please select again.**");
	}
	public InsufficientConditionException(String message) {
		super(message);
	}
}
