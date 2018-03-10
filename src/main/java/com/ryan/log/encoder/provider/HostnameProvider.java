package com.ryan.log.encoder.provider;

import ch.qos.logback.core.PropertyDefinerBase;

import java.lang.management.ManagementFactory;

public class HostnameProvider extends PropertyDefinerBase {
    private final String[] KEYS = new String[]{"computername", "hostname"};

    public HostnameProvider() {
    }

    private String getProperty(String var1) {
        return System.getenv(var1) != null ? System.getenv(var1) : System.getenv(var1.toUpperCase());
    }

    private String loopProperties() {
        String[] var1 = this.KEYS;
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            String var4 = var1[var3];
            String var5 = this.getProperty(var4);
            if (var5 != null) {
                return var5;
            }
        }

        return this.lastChance();
    }

    private String lastChance() {
        String[] var1 = this.jvmName().split("@");
        return var1.length > 1 ? var1[1] : "HOSTNAME_NOT_DETERMINED";
    }

    private String jvmName() {
        return ManagementFactory.getRuntimeMXBean().getName();
    }

    public String getPropertyValue() {
        return this.loopProperties();
    }
}
