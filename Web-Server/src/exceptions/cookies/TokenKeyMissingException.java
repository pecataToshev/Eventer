package exceptions.cookies;

public class TokenKeyMissingException extends Exception {
	public TokenKeyMissingException() {
	}

	public TokenKeyMissingException(String message) {
		super(message);
	}

	public TokenKeyMissingException(String message, Throwable cause) {
		super(message, cause);
	}

	public TokenKeyMissingException(Throwable cause) {
		super(cause);
	}

	public TokenKeyMissingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
