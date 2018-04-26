package com.comba.server.exception;

import com.fasterxml.jackson.annotation.JsonValue;

public enum IoTErrorCode {

    SUCCESS(0),
    GENERAL(2),
    AUTHENTICATION(10),
    BAD_REQUEST_PARAMS(201),
    INVALID_ARGUMENTS(202),
    JWT_TOKEN_EXPIRED(301),
    PERMISSION_DENIED(302),
    ITEM_NOT_FOUND(303),
    ITEM_EXISTS(304),

    bad_request_param(2001),
    item_not_found(2002),
    item_exists(2003),
    token_invalid(3001),
    server_error(9999);

    private int errorCode;

    IoTErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @JsonValue
    public int getErrorCode() {
        return errorCode;
    }

}
