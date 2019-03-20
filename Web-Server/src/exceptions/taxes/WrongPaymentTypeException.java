package exceptions.taxes;

public class WrongPaymentTypeException extends Exception {
	public WrongPaymentTypeException() {
	}

	public WrongPaymentTypeException(String message) {
		super(message);
	}

	public WrongPaymentTypeException(String message, Throwable cause) {
		super(message, cause);
	}

	public WrongPaymentTypeException(Throwable cause) {
		super(cause);
	}

	public WrongPaymentTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
