package cn.chinaunicom.awarding.core.exception;

public class AwardingCoreException extends Exception {

	private static final long serialVersionUID = 1L;

	public AwardingCoreException(String message) {
		super(message);
	}

	public AwardingCoreException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
