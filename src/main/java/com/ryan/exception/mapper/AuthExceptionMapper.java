package com.ryan.exception.mapper;

import com.ryan.exception.AuthException;
import com.ryan.log.ASLHLog;
import com.ryan.util.CommonResponse;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AuthExceptionMapper implements ExceptionMapper<AuthException> {
    private static final ASLHLog LOG = new ASLHLog(AuthExceptionMapper.class);

    @Override
    public Response toResponse(AuthException e) {
        LOG.error(e);
        CommonResponse response = new CommonResponse(e.getCommonResult());
        if (e.getStatusCode() == Response.Status.FORBIDDEN) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(response)
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(response)
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .header("WWW-Authenticate", String.format("error=\"%s\"", e.getCommonResult().getMessage()))
                    .build();
        }
    }
}
