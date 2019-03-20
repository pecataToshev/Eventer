package exceptions.userEntity;

public class IncorrectCredentialsException extends Exception {
	public IncorrectCredentialsException() {
	}

	public IncorrectCredentialsException(String message) {
		super(message);
	}

	public IncorrectCredentialsException(String message, Throwable cause) {
		super(message, cause);
	}

	public IncorrectCredentialsException(Throwable cause) {
		super(cause);
	}

	public IncorrectCredentialsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
