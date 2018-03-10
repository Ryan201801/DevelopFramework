package com.ryan.exception;

import com.ryan.model.system.Components;
import com.ryan.model.system.PhoenixErrorCode;

import javax.ws.rs.core.Response;

public class AuthException extends CommonExcetpion {
    public AuthException(PhoenixErrorCode errorCode, Components component) {
        this(errorCode.getCode(), errorCode.getMessage(), component);
    }

    public AuthException(Integer code, String message, Components component) {
        super(Response.Status.UNAUTHORIZED, code, message, component);
    }

    public AuthException(Response.Status status, Integer code, String message, Components component) {
        super(status, code, message, component);
    }

    public AuthException(PhoenixErrorCode errorCode, Components component, Exception e) {
        super(Response.Status.UNAUTHORIZED, errorCode.getCode(), errorCode.getMessage(), component, e);
    }
}
