package assignment3.exception;

public class ExpiredException extends Exception {
	public ExpiredException() {
		super("**Selected Product is EXPIRED: please select again.**");
	}
	public ExpiredException(String message) {
		super("**Selected Product is EXPIRED: " + message + "**");
	}
}
