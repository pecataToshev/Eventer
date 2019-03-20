package exceptions.taxes;

public class AllTaxPaidException extends Exception {
	public AllTaxPaidException() {
	}

	public AllTaxPaidException(String message) {
		super(message);
	}

	public AllTaxPaidException(String message, Throwable cause) {
		super(message, cause);
	}

	public AllTaxPaidException(Throwable cause) {
		super(cause);
	}

	public AllTaxPaidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
