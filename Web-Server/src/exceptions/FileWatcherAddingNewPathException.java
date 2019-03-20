package exceptions;

public class FileWatcherAddingNewPathException extends Exception {
	public FileWatcherAddingNewPathException() {
	}

	public FileWatcherAddingNewPathException(String message) {
		super(message);
	}

	public FileWatcherAddingNewPathException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileWatcherAddingNewPathException(Throwable cause) {
		super(cause);
	}

	public FileWatcherAddingNewPathException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
