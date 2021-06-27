package com.cthye.lil.loggingspringbootautoconfigure;

import com.frankmoley.landon.aop.servicelogging.LoggableAspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(LoggableAspect.class)
@EnableConfigurationProperties(CthyeLoggingProperties.class)
public class CthyeLoggingAutoconfigure {

    private final CthyeLoggingProperties properties;

    @Autowired
    public CthyeLoggingAutoconfigure(CthyeLoggingProperties properties) {
        this.properties = properties;
    }

    @Bean
    public LoggableAspect loggableAspect(){
        return new LoggableAspect(properties.getLoggerName());
    }
}
