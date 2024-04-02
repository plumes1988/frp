
package com.figure.core.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EncodeException extends RuntimeException {

	private static final long serialVersionUID = 7437400920861098334L;
	protected Throwable cause = null;

    public EncodeException(String message) {
        super(message);
        log.error("An error occurred EncodeException", "{ message:" + message + "\"" + "}");
    }

    public EncodeException(String message, Throwable cause) {
        super(message);
        this.cause = cause;
        log.error("An error occurred EncodeException", "{ message:" + message + "\"" + "}");
    }

    public EncodeException(Throwable cause) {
        super(cause.getMessage());
        this.cause = cause;
    }

    public Throwable getCause() {
        return (this.cause);
    }
}