package com.cthye.lil.loggingspringbootautoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("cthye.logging")
public class CthyeLoggingProperties {
    private String loggerName = "CthyeAuditLogger";

    public String getLoggerName() {
        return loggerName;
    }

    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }
}
