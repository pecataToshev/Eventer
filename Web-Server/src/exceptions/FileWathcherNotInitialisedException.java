package exceptions;

public class FileWathcherNotInitialisedException extends Exception {
	public FileWathcherNotInitialisedException() {
	}

	public FileWathcherNotInitialisedException(String message) {
		super(message);
	}

	public FileWathcherNotInitialisedException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileWathcherNotInitialisedException(Throwable cause) {
		super(cause);
	}

	public FileWathcherNotInitialisedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
