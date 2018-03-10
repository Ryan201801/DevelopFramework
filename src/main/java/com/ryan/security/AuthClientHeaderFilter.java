package com.ryan.security;

import com.ryan.exception.AuthException;
import com.ryan.model.system.Components;
import com.ryan.model.system.PhoenixErrorCode;
import org.glassfish.jersey.server.ContainerRequest;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;

public class AuthClientHeaderFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        MultivaluedMap<String, String> headers = containerRequestContext.getHeaders();
        String method = ((ContainerRequest) containerRequestContext).getMethod();
        String basePath = ((ContainerRequest) containerRequestContext).getBaseUri().getPath();
        String urlPath = ((ContainerRequest) containerRequestContext).getRequestUri().toString();
        String authorization = headers.getFirst("Authorization");

        if (!method.toLowerCase().equals("options") &&
                (basePath !=null && basePath.equals("/api/allianz/phoenix/")) &&
                (!urlPath.contains("swagger")) &&
                (authorization == null || !authorization.equals("Basic emFhc2RqZndlcmlvdXY6emFhc2RqZndlcmlvdXY="))) {
            throw new AuthException(PhoenixErrorCode.UNAUTHORIZED, Components.PHOENIX);
        }
    }
}
