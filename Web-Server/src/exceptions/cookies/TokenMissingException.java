package exceptions.cookies;

public class TokenMissingException extends Exception {
	public TokenMissingException() {
	}

	public TokenMissingException(String message) {
		super(message);
	}

	public TokenMissingException(String message, Throwable cause) {
		super(message, cause);
	}

	public TokenMissingException(Throwable cause) {
		super(cause);
	}

	public TokenMissingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
