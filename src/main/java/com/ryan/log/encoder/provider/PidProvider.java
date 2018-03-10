package com.ryan.log.encoder.provider;

import ch.qos.logback.core.PropertyDefinerBase;

import java.lang.management.ManagementFactory;


public class PidProvider extends PropertyDefinerBase {
    public PidProvider() {
    }

    private String jvmName() {
        return ManagementFactory.getRuntimeMXBean().getName();
    }

    private String extractPid(String var1) {
        String[] var2 = var1.split("@");
        return var2.length == 0 ? "UNKNOWN_PID" : var2[0];
    }

    public String getPropertyValue() {
        return this.extractPid(this.jvmName());
    }
}
