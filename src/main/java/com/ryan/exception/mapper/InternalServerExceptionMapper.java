package com.ryan.exception.mapper;

import com.ryan.exception.InternalServerException;
import com.ryan.log.ASLHLog;
import com.ryan.util.CommonResponse;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Description:
 * User: Ryan服务器内部错误
 * Time: 2018/2/27 15:53
 */
public class InternalServerExceptionMapper implements ExceptionMapper<InternalServerException> {

    private static final ASLHLog LOG = new ASLHLog(AuthExceptionMapper.class);

    @Override
    public Response toResponse(InternalServerException e) {
        LOG.error(e);
        CommonResponse response = new CommonResponse(e.getCommonResult());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(response)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}