package com.ryan.log.encoder;

import ch.qos.logback.classic.spi.ILoggingEvent;

import java.util.Map;

public class ASLHLogEntry extends LogEntry {
    public final String userId;
    public final String traceId;
    public final String accountId;
    public final String command;
    public final Integer statusCode;
    public final String serviceName;

    public ASLHLogEntry(final ILoggingEvent loggingEvent) {
        super(loggingEvent);
        Map<String, String> mdcContext = loggingEvent.getMDCPropertyMap();
        this.userId = mdcContext.get(MDCAttributes.MDC_USER_ID);
        this.traceId = mdcContext.get(MDCAttributes.MDC_X_TRACE_ID);
        this.accountId = mdcContext.get(MDCAttributes.MDC_ACCOUNT_ID);
        this.command = mdcContext.get(MDCAttributes.MDC_COMMAND);
        this.statusCode = loadStatusCodeFromMdc(mdcContext.get(MDCAttributes.MDC_STATUS_CODE));
        this.serviceName = loggingEvent.getLoggerContextVO().getPropertyMap().get(MDCAttributes.MDC_SERVICE_NAME);
    }

    private Integer loadStatusCodeFromMdc(String statusCode) {
        try {
            return Integer.parseInt(statusCode);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
