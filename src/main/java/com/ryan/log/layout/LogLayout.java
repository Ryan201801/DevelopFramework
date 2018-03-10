package com.ryan.log.layout;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.LayoutBase;

import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.TimeZone;


public class LogLayout extends LayoutBase<ILoggingEvent> {
    private static final String TAB = "\t";
    private static final String LINE_SEP = "\n";
    private static final String NONE = "-";

    public String doLayout(ILoggingEvent event) {
        StringBuilder sb;
        sb = new StringBuilder(128);
        StackTraceElement caller = event.getCallerData()[1];
        Map loggerContext = event.getLoggerContextVO().getPropertyMap();
        Map<String, String> mdcContext = event.getMDCPropertyMap();
        sb.append(event.getLevel());
        sb.append(TAB);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss,SSS'Z'");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        sb.append(dateFormat.format(event.getTimeStamp()));
        sb.append(TAB);
        sb.append(loggerContext.get("springAppName"));
        sb.append(TAB);
        sb.append(loggerContext.get("HOSTNAME"));
        sb.append(TAB);
        sb.append(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
        sb.append(TAB);
        sb.append(event.getThreadName());
        sb.append(TAB);
        String userId = mdcContext.get("username");
        sb.append(userId == null ? NONE : userId);
        sb.append(TAB);
        String traceId = mdcContext.get("traceId");
        sb.append(traceId == null ? NONE : traceId);
        sb.append(TAB);
        String accountId = mdcContext.get("accountId");
        sb.append(accountId == null ? NONE : accountId);
        sb.append(TAB);
        sb.append(caller.getMethodName());
        sb.append(TAB);
        sb.append(event.hashCode());
        sb.append(TAB);
        String loggerName = event.getLoggerName();
        sb.append(loggerName.substring(loggerName.lastIndexOf('.') + 1));
        sb.append(TAB);
        String message = event.getFormattedMessage();
        message = message.replace("\r\n", " $n ").replace("\n", " $n ").replace("\t", " $t ");
        sb.append("$Message: ").append(message).append(", ");
        sb.append("$Request: ").append(mdcContext.get("method")).append(":").append(mdcContext.get("command")).append(", ");
        sb.append("$Class: ").append(caller.getClassName()).append(", ");
        sb.append("$Method: ").append(caller.getMethodName()).append(", ");
        sb.append("$LineNo: ").append(caller.getLineNumber());
        sb.append(LINE_SEP);
        return sb.toString();
    }
}
