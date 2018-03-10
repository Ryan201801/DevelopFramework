package com.ryan.exception.mapper;

import com.ryan.exception.ResourceNotFoundException;
import com.ryan.log.ASLHLog;
import com.ryan.util.CommonResponse;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Description:资源没有找到异常
 * User: Ryan
 * Time: 2018/2/27 10:55
 */
public class ResourceNotFoundExceptionMapper  implements ExceptionMapper<ResourceNotFoundException> {
    private static final ASLHLog LOG = new ASLHLog(AuthExceptionMapper.class);

    @Override
    public Response toResponse(ResourceNotFoundException e) {
        LOG.error(e);
        CommonResponse response = new CommonResponse(e.getCommonResult());
        return Response.status(Response.Status.NOT_FOUND)
                .entity(response)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}