package com.ryan.security;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;

public class CORSResponseFilter implements ContainerResponseFilter{
    @Override
    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) throws IOException {

        MultivaluedMap<String, Object> headers = responseContext.getHeaders();

        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Methods", "GET,PATCH,POST, PUT, DELETE, HEAD, OPTIONS");
        headers.add("Access-Control-Allow-Headers", "X-Requested-With,Origin,Content-Type, Accept, Authorization");
    }
}
