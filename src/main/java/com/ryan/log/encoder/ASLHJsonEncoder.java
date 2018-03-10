package com.ryan.log.encoder;

import ch.qos.logback.classic.spi.ILoggingEvent;

import java.time.DateTimeException;

public class ASLHJsonEncoder extends JsonEncoder {
    @Override
    protected LogEntry createLogEntry(final ILoggingEvent loggingEvent) throws DateTimeException {
        return new ASLHLogEntry(loggingEvent);
    }
}
