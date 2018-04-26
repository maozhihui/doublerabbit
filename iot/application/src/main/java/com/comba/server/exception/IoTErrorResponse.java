package com.comba.server.exception;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class IoTErrorResponse {
    // HTTP Response Status Code
    private final HttpStatus status;

    // General Error message
    private final String message;

    // Error code
    private final IoTErrorCode errorCode;

    private final Date timestamp;

    protected IoTErrorResponse(final String message, final IoTErrorCode errorCode, HttpStatus status) {
        this.message = message;
        this.errorCode = errorCode;
        this.status = status;
        this.timestamp = new java.util.Date();
    }

    public static IoTErrorResponse of(final String message, final IoTErrorCode errorCode, HttpStatus status) {
        return new IoTErrorResponse(message, errorCode, status);
    }

    public Integer getStatus() {
        return status.value();
    }

    public String getMessage() {
        return message;
    }

    public IoTErrorCode getErrorCode() {
        return errorCode;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
