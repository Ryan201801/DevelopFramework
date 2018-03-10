package com.ryan.log;

import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.util.Collections;

@Priority(Priorities.HEADER_DECORATOR)
public class TraceIdClientFilter implements ClientRequestFilter {
    private static final ASLHLog LOG = new ASLHLog(TraceIdClientFilter.class);

    @Override
    public void filter(ClientRequestContext clientRequestContext) throws IOException {
        try {
            MultivaluedMap<String, Object> headers = clientRequestContext.getHeaders();
            headers.put("x-trace-id",
                    Collections.singletonList(RequestContextHolder.currentRequestAttributes()
                            .getAttribute("traceId", 0)));
        } catch (IllegalStateException e) {
            LOG.error(e);
        }
    }
}
