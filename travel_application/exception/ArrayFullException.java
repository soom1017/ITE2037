package assignment2.exception;

public class ArrayFullException extends Exception {
	public ArrayFullException() {
		super("**FULL ARRAY**");
	}
	public ArrayFullException(String message) {
		super(message);
	}
}
