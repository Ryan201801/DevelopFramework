package com.ryan.log;

import com.ryan.log.encoder.MDCAttributes;
import org.glassfish.jersey.message.MessageUtils;
import org.slf4j.MDC;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.container.*;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import java.io.*;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;


@PreMatching
public final class LoggingFilter implements ContainerRequestFilter, ClientRequestFilter, ContainerResponseFilter, ClientResponseFilter, WriterInterceptor {
    private static final ASLHLog LOG = new ASLHLog(LoggingFilter.class);

    private static final String ENTITY_LOGGER_PROPERTY = LoggingFilter.class.getName() + ".entityLogger";
    private static final String LOGGING_ID_PROPERTY = LoggingFilter.class.getName() + ".id";
    private static final int DEFAULT_MAX_ENTITY_SIZE = 8192;
    private static final Comparator<Map.Entry<String, List<String>>> COMPARATOR = (o1, o2) -> o1.getKey().compareToIgnoreCase(o2.getKey());
    private final AtomicLong _id;
    private final int maxEntitySize;
    private final boolean printEntity;

    public LoggingFilter() {
        this._id = new AtomicLong(0L);
        this.maxEntitySize = DEFAULT_MAX_ENTITY_SIZE;
        this.printEntity = true;
    }

    private void log(StringBuilder b) {
        LOG.info(b.toString());
    }

    private void printRequestLine(StringBuilder b, String note, String method, URI uri) {
        b.append(note).append(method).append(" ").append(uri.toASCIIString()).append(" ");
    }

    private void printResponseLine(StringBuilder b, String note, int status) {
        b.append(note).append(Integer.toString(status)).append(" ");
    }

    private void printPrefixedHeaders(StringBuilder b, MultivaluedMap<String, String> headers) {
        Iterator<Map.Entry<String, List<String>>> var6 = this.getSortedHeaders(headers.entrySet()).iterator();

        while (true) {
            while (var6.hasNext()) {
                Map.Entry<String, List<String>> headerEntry = var6.next();
                List<String> val = headerEntry.getValue();
                String header = headerEntry.getKey();
                if (val.size() == 1) {
                    b.append(header).append(":").append(val.get(0)).append(" ");
                } else {
                    StringBuilder sb = new StringBuilder();
                    boolean add = false;
                    Iterator<String> var12 = val.iterator();

                    while (var12.hasNext()) {
                        Object s = var12.next();
                        if (add) {
                            sb.append(',');
                        }

                        add = true;
                        sb.append(s);
                    }

                    b.append(header).append(":").append(sb.toString()).append(" ");
                }
            }

            return;
        }
    }

    private Set<Map.Entry<String, List<String>>> getSortedHeaders(Set<Map.Entry<String, List<String>>> headers) {
        TreeSet<Map.Entry<String, List<String>>> sortedHeaders = new TreeSet<>(COMPARATOR);
        sortedHeaders.addAll(headers);
        return sortedHeaders;
    }

    private InputStream logInboundEntity(StringBuilder b, InputStream stream, Charset charset) throws IOException {
        InputStream inputStream = stream;
        if (!stream.markSupported()) {
            inputStream = new BufferedInputStream(stream);
        }

        inputStream.mark(this.maxEntitySize + 1);
        byte[] entity = new byte[this.maxEntitySize + 1];
        int entitySize = inputStream.read(entity);
        String entityStr = new String(entity, 0, Math.min(entitySize, this.maxEntitySize), charset);
        b.append(entityStr);
        if (entitySize > this.maxEntitySize) {
            b.append("...more...");
        }

        b.append(' ');
        inputStream.reset();
        return inputStream;
    }

    public void filter(ClientRequestContext context) throws IOException {
        long id = this._id.incrementAndGet();
        context.setProperty(LOGGING_ID_PROPERTY, Long.valueOf(id));
        StringBuilder b = new StringBuilder();
        this.printRequestLine(b, "REST client sent: ", context.getMethod(), context.getUri());
        if (this.printEntity && context.hasEntity()) {
            LoggingStream stream = new LoggingStream(b, context.getEntityStream());
            context.setEntityStream(stream);
            context.setProperty(ENTITY_LOGGER_PROPERTY, stream);
        }
        this.printPrefixedHeaders(b, context.getStringHeaders());
        this.log(b);
    }

    public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {
        MDC.put(MDCAttributes.MDC_STATUS_CODE, String.valueOf(responseContext.getStatus()));
        StringBuilder b = new StringBuilder();
        this.printResponseLine(b, "REST client received: ", responseContext.getStatus());
        if (this.printEntity && responseContext.hasEntity()) {
            responseContext.setEntityStream(this.logInboundEntity(b, responseContext.getEntityStream(), MessageUtils.getCharset(responseContext.getMediaType())));
        }
        this.printPrefixedHeaders(b, responseContext.getHeaders());
        this.log(b);
    }

    public void filter(ContainerRequestContext context) throws IOException {
        MDC.put(MDCAttributes.MDC_COMMAND, context.getMethod() + " " + context.getUriInfo().getRequestUri().getPath());
        long id = this._id.incrementAndGet();
        context.setProperty(LOGGING_ID_PROPERTY, id);
        StringBuilder b = new StringBuilder();
        this.printRequestLine(b, "Server received : ", context.getMethod(), context.getUriInfo().getRequestUri());
        if (this.printEntity && context.hasEntity()) {
            context.setEntityStream(this.logInboundEntity(b, context.getEntityStream(), MessageUtils.getCharset(context.getMediaType())));
        }
        this.printPrefixedHeaders(b, context.getHeaders());
        this.log(b);
    }

    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        MDC.put(MDCAttributes.MDC_STATUS_CODE, String.valueOf(responseContext.getStatus()));
        StringBuilder b = new StringBuilder();
        this.printResponseLine(b, "Server responded : ", responseContext.getStatus());
        this.printPrefixedHeaders(b, responseContext.getStringHeaders());
        if (this.printEntity && responseContext.hasEntity()) {
            LoggingStream stream = new LoggingStream(b, responseContext.getEntityStream());
            responseContext.setEntityStream(stream);
            requestContext.setProperty(ENTITY_LOGGER_PROPERTY, stream);
        } else {
            this.log(b);
        }
        MDC.remove(MDCAttributes.MDC_COMMAND);
    }

    public void aroundWriteTo(WriterInterceptorContext writerInterceptorContext) throws IOException, WebApplicationException {
        LoggingStream stream = (LoggingStream) writerInterceptorContext.getProperty(ENTITY_LOGGER_PROPERTY);
        writerInterceptorContext.proceed();
        if (stream != null) {
            this.log(stream.getStringBuilder(MessageUtils.getCharset(writerInterceptorContext.getMediaType())));
        }

    }

    private class LoggingStream extends FilterOutputStream {
        private final StringBuilder b;
        private final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        LoggingStream(StringBuilder b, OutputStream inner) {
            super(inner);
            this.b = b;
        }

        StringBuilder getStringBuilder(Charset charset) {
            byte[] entity = this.baos.toByteArray();
            this.b.append(new String(entity, 0, Math.min(entity.length, LoggingFilter.this.maxEntitySize), charset));
            if (entity.length > LoggingFilter.this.maxEntitySize) {
                this.b.append("...more...");
            }
            this.b.append(' ');
            return this.b;
        }

        public void write(int i) throws IOException {
            if (this.baos.size() <= LoggingFilter.this.maxEntitySize) {
                this.baos.write(i);
            }
            this.out.write(i);
        }
    }
}
