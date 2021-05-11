package assignment2.exception;

public class InsufficientConditionException extends Exception {
	public InsufficientConditionException() {
		super("insufficient condition");
	}
	public InsufficientConditionException(String message) {
		super(message);
	}
}
