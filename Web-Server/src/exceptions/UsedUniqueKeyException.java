package exceptions;

public class UsedUniqueKeyException extends Exception {
	public UsedUniqueKeyException() {
	}

	public UsedUniqueKeyException(String message) {
		super(message);
	}

	public UsedUniqueKeyException(String message, Throwable cause) {
		super(message, cause);
	}

	public UsedUniqueKeyException(Throwable cause) {
		super(cause);
	}

	public UsedUniqueKeyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
