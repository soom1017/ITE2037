package assignment3.exception;

public class NotEnoughBalanceException extends Exception {
	public NotEnoughBalanceException() {
		super();
	}
	public NotEnoughBalanceException(String message) {
		super(message);
	}
}
