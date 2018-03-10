package com.ryan.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ASLHLog {
    private Logger logger;

    public ASLHLog(Class cls) {
        logger = LoggerFactory.getLogger(cls);
    }

//    public void send(String logType, String msg) {
//        String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS").format(new Date());
//
//        String messageContent = String.format("%s [ImsRest] %s - %s", dateStr, logType, msg);
//        SnmpUtils.sendString(messageContent);
//    }

    public void info(String msg) {
        logger.info(msg);
    }

    public void info(Throwable ex) {
        info(ex.getMessage(), ex);
    }

    public void info(String msg, Throwable ex) {
        logger.info(msg, ex);
    }

    public void error(String msg) {
        logger.error(msg);
    }

    public void error(Throwable ex) {
        info(ex.getMessage(), ex);
    }

    public void error(String msg, Throwable ex) {
        logger.error(msg, ex);
    }

    public void warn(String msg) {
        logger.warn(msg);
    }

    public void warn(Throwable ex) {
        info(ex.getMessage(), ex);
    }

    public void warn(String msg, Throwable ex) {
        logger.warn(msg, ex);
    }

    public void debug(String msg) {
        logger.debug(msg);
    }
}