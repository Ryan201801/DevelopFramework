package com.ryan.util;

import com.ryan.model.system.Components;
import com.ryan.model.system.PhoenixErrorCode;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonResult<T> implements Serializable {
    private Integer code;
    private String message;
    private Components component;
    private T resource;

    public CommonResult() {

    }

    public CommonResult(T resource) {
        this.resource = resource;
    }

    public CommonResult(PhoenixErrorCode errorCode, String message, T  resource) {
        this.code = errorCode == null ? null: errorCode.getCode();
        this.message = errorCode == null ? null: errorCode.getMessage() + message;
        this.resource = resource;
        this.component = Components.PHOENIX;
    }

    public CommonResult(Integer code, String message, Components component, T  resource) {
        this.code = code;
        this.message = message;
        this.component = component;
        this.resource = resource;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResource() {
        return resource;
    }

    public void setResource(T resource) {
        this.resource = resource;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Components getComponent() {
        return component;
    }

    public void setComponent(Components component) {
        this.component = component;
    }


}




















































