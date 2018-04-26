package com.comba.server.exception;

public class IoTException extends Exception {

    private static final long serialVersionUID = 1L;

    private IoTErrorCode errorCode;

    public IoTException() {
        super();
    }

    public IoTException(IoTErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public IoTException(String message, IoTErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public IoTException(String message, Throwable cause, IoTErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public IoTException(Throwable cause, IoTErrorCode errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public IoTErrorCode getErrorCode() {
        return errorCode;
    }

}
