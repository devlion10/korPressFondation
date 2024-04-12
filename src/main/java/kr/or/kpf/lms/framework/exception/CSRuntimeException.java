package kr.or.kpf.lms.framework.exception;

/**
 * RuntimeException
 */
public class CSRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 2776022876280764152L;

	public CSRuntimeException() {
		super();
	}

	public CSRuntimeException(String message) {
		super(message);
	}

	public CSRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public CSRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CSRuntimeException(Throwable cause) {
		super(cause);
	}

}
