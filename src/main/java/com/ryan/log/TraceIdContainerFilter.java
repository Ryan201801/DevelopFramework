package com.ryan.log;

import com.ryan.log.encoder.MDCAttributes;
import org.slf4j.MDC;
import org.springframework.web.context.request.RequestContextHolder;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.Collections;
import java.util.Random;


public class TraceIdContainerFilter implements ContainerRequestFilter {
    private final static Random randomizer = new Random();

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        String traceId = containerRequestContext.getHeaders().getOrDefault("x-trace-id", Collections.singletonList(generateTraceId())).get(0);
        RequestContextHolder.getRequestAttributes().setAttribute("traceId", traceId, 0);
        MDC.put(MDCAttributes.MDC_X_TRACE_ID, traceId);
    }

    public final static String generateTraceId() {
        final byte[] randomBytes = new byte[16];
        randomizer.nextBytes(randomBytes);
        return DatatypeConverter.printHexBinary(randomBytes);
    }
}
