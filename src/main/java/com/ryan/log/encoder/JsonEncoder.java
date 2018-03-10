package com.ryan.log.encoder;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.encoder.EncoderBase;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.time.DateTimeException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;


public class JsonEncoder extends EncoderBase<ILoggingEvent> {
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static final byte[] LINE_SEPARATOR_BYTES;
    private final ObjectMapper jsonMapper = new ObjectMapper();

    public JsonEncoder() {
    }

    public void init(OutputStream var1) throws IOException {
        super.init(var1);
    }

    public void start() {
        super.start();
    }

    public void stop() {
        super.stop();
    }

    public void doEncode(ILoggingEvent var1) throws IOException {
        if (!this.isStarted()) {
            throw new IllegalStateException("Encoding attempted before starting.");
        } else {
            this.writeLoggingEventToOutputStream(var1);
            if (LINE_SEPARATOR_BYTES != null) {
                this.outputStream.write(LINE_SEPARATOR_BYTES);
            }

            this.outputStream.flush();
        }
    }

    public void close() throws IOException {
    }

    private String wrapErrorMessage(String var1) {
        return "{\"level\" : \"ERROR\",\"timestamp\" : \"" + LogEntry.TIMESTAMP_FORMATTER.format(ZonedDateTime.now(ZoneOffset.UTC)) + "\",\"message\" : \"Error while logging: " + var1 + "\",\"logger\" : \"" + this.getClass().getName() + "\"}";
    }

    private void writeLoggingEventToOutputStream(ILoggingEvent var1) throws IOException {
        String var3;
        try {
            this.jsonMapper.writeValue(this.outputStream, this.createLogEntry(var1));
        } catch (DateTimeException var4) {
            var3 = this.wrapErrorMessage("Could not create LogEntry: " + var4.getMessage());
            this.outputStream.write(var3.getBytes());
        } catch (JsonMappingException | JsonGenerationException var5) {
            var3 = this.wrapErrorMessage("Could not serialize LogEntry: " + var5.getMessage());
            this.outputStream.write(var3.getBytes());
        }

    }

    protected LogEntry createLogEntry(ILoggingEvent var1) throws DateTimeException {
        return new LogEntry(var1);
    }

    static {
        LINE_SEPARATOR_BYTES = LINE_SEPARATOR == null ? null : LINE_SEPARATOR.getBytes(Charset.forName("UTF-8"));
    }
}
