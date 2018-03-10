package com.ryan.exception;


import com.ryan.model.system.Components;
import com.ryan.model.system.PhoenixErrorCode;
import javax.ws.rs.core.Response;

public class CommonExcetpion extends BusinessControllerException {

    public CommonExcetpion(Response.Status statusCode,
                           Integer code, String message,
                           Components component) {
        super(statusCode, code, message, component);
    }

    public CommonExcetpion(Response.Status statusCode,
                           PhoenixErrorCode errorCode,
                           Components component) {
        super(statusCode, errorCode.getCode(), errorCode.getMessage(), component);
    }

    public CommonExcetpion(Response.Status statusCode, Integer code,
                           String message,
                           Components component,
                           Exception e) {
        super(statusCode, code, message, component,e);
    }
}
