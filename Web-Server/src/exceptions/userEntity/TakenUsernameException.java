package exceptions.userEntity;

public class TakenUsernameException extends Exception {
	public TakenUsernameException() {
	}

	public TakenUsernameException(String message) {
		super(message);
	}

	public TakenUsernameException(String message, Throwable cause) {
		super(message, cause);
	}

	public TakenUsernameException(Throwable cause) {
		super(cause);
	}

	public TakenUsernameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
