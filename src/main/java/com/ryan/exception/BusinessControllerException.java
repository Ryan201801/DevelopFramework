package com.ryan.exception;

import com.ryan.model.system.Components;
import com.ryan.util.CommonResponse;
import com.ryan.util.CommonResult;

import javax.ws.rs.core.Response;

public class BusinessControllerException extends RuntimeException{
    private final Response.Status statusCode;
    private final CommonResponse commonResponse;

    public BusinessControllerException(Response.Status status,
                                       Integer code,
                                       String message,
                                       Components component) {
        this.statusCode = status;
        this.commonResponse = new CommonResponse(new CommonResult<>(code, message, component, null));
    }

    public BusinessControllerException(Response.Status status,
                                       CommonResponse commonResponse) {
        this.statusCode = status;
        this.commonResponse = commonResponse;
    }

    public BusinessControllerException(Response.Status status,
                                       Integer code,
                                       String message,
                                       Components component,
                                       Exception e) {
        super(e);
        this.statusCode = status;
        this.commonResponse = new CommonResponse(new CommonResult<>(code, message, component, null));
    }

    public CommonResponse getCommonResponse() {
        return commonResponse;
    }

    public Response.Status getStatusCode() {
        return statusCode;
    }

    public CommonResult getCommonResult() {
        return (CommonResult) commonResponse.getInfo().get(0);
    }
}

