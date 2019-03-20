package exceptions.cookies;

public class MissingCookieException extends Exception {
	public MissingCookieException() {
	}

	public MissingCookieException(String message) {
		super(message);
	}

	public MissingCookieException(String message, Throwable cause) {
		super(message, cause);
	}

	public MissingCookieException(Throwable cause) {
		super(cause);
	}

	public MissingCookieException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
