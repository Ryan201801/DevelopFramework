package com.ryan.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Collections;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonResponse<R> {

    private R resource;
    private List<CommonResult<R>> info;

    public CommonResponse() {

    }

    public CommonResponse(CommonResult<R> info) {
        this.info = Collections.singletonList(info);
    }

    public CommonResponse(R resource) {
        this.resource = resource;
    }

    public CommonResponse(R resource, List<CommonResult<R>> info) {
        this.resource = resource;
        this.info = info;
    }

    public R getResource() {
        return resource;
    }

    public void setResource(R resource) {
        this.resource = resource;
    }

    public List<CommonResult<R>> getInfo() {
        return info;
    }

    public void setInfo(List<CommonResult<R>> info) {
        this.info = info;
    }
}