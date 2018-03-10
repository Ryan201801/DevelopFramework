package com.ryan.exception;

import com.ryan.model.system.Components;
import com.ryan.model.system.PhoenixErrorCode;

import javax.ws.rs.core.Response;

public class InternalServerException extends CommonExcetpion {
    public InternalServerException(Integer code, String message, Components component) {
        super(Response.Status.INTERNAL_SERVER_ERROR, code, message, component);
    }

    public InternalServerException(PhoenixErrorCode errorCode, Components component) {
        this(errorCode.getCode(), errorCode.getMessage(), component);
    }
}
