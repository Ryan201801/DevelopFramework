package com.ryan.log.encoder;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import com.ryan.log.encoder.provider.PidProvider;
import com.ryan.log.encoder.provider.HostnameProvider;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.slf4j.MDC;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class LogEntry {
    public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd\'T\'HH:mm:ss.SSS";
    public static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd\'T\'HH:mm:ss.SSS");
    public static final String LOG_ENTRY_OTHER_PREFIX = "logentry.additional.";
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public final String level;
    public final String timestamp;
    public final String message;
    public final String exception;
    public final String hostname;
    public final String processId;
    public final String threadId;
    public final String logger;
    private final Map<String, String> additional;

    public LogEntry(ILoggingEvent var1) throws DateTimeException {
        this.level = var1.getLevel().toString();
        this.timestamp = this.buildTimestamp(var1);
        this.message = var1.getMessage();
        this.exception = this.buildExceptionString(var1.getThrowableProxy());
        this.hostname = (new HostnameProvider()).getPropertyValue();
        this.processId = (new PidProvider()).getPropertyValue();
        this.threadId = var1.getThreadName();
        this.logger = var1.getLoggerName();
        this.additional = this.getAdditionalPropertiesFromMdc();
    }

    @JsonAnyGetter
    public Map<String, String> additional() {
        return this.additional;
    }

    @JsonAnySetter
    public void putAdditional(String var1, String var2) {
        this.additional.put(var1, var2);
    }

    private Map<String, String> getAdditionalPropertiesFromMdc() {
        Map<String, String> var1 = MDC.getCopyOfContextMap();
        if (var1 == null) {
            return null;
        } else {
            return var1.entrySet().stream()
                    .filter(var0 -> var0.getKey().startsWith("logentry.additional."))
                    .collect(Collectors.
                            toMap((Map.Entry<String, String> var0) ->
                                            var0.getKey().substring("logentry.additional.".length())
                                    , Map.Entry::getValue));
        }
    }

    private String buildTimestamp(ILoggingEvent var1) throws DateTimeException {
        return TIMESTAMP_FORMATTER.format(ZonedDateTime.ofInstant(Instant.ofEpochMilli(var1.getTimeStamp()), ZoneOffset.UTC));
    }

    private String buildExceptionString(IThrowableProxy var1) {
        if (var1 == null) {
            return null;
        } else {
            StringBuilder var2 = new StringBuilder();
            var2.append(var1.getClassName()).append(": ").append(var1.getMessage()).append(LINE_SEPARATOR);
            StackTraceElementProxy[] var3 = var1.getStackTraceElementProxyArray();
            if (var3 != null) {
                Stream.of(var3).map(StackTraceElementProxy::toString).collect(() -> {
                    return var2;
                }, StringBuilder::append, StringBuilder::append);
            }

            if (var1.getCause() != null) {
                var2.append(LINE_SEPARATOR).append("Caused by: ").append(this.buildExceptionString(var1.getCause()));
            }

            return var2.toString();
        }
    }
}
