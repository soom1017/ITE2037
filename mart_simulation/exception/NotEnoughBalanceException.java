package assignment3.exception;

public class NotEnoughBalanceException extends Exception {
	public NotEnoughBalanceException() {
		super("**NOT ENOUGH BALANCE**");
	}
	public NotEnoughBalanceException(String message) {
		super("**NOT ENOUGH BALANCE: " + message + "**");
	}
}
