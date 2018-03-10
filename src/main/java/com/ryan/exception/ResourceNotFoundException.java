package com.ryan.exception;

import com.ryan.model.system.Components;
import com.ryan.model.system.PhoenixErrorCode;

import javax.ws.rs.core.Response;

public class ResourceNotFoundException extends CommonExcetpion {
    public ResourceNotFoundException(PhoenixErrorCode errorCode, Components component) {
        this(errorCode.getCode(), errorCode.getMessage(), component);
    }

    public ResourceNotFoundException(Integer code, String message, Components component) {
        super(Response.Status.NOT_FOUND, code, message, component);
    }
}
