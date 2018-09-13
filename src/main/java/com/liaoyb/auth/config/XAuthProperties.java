package com.liaoyb.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.web.cors.CorsConfiguration;

@ConfigurationProperties(prefix = "xauth", ignoreUnknownFields = false)
@Data
public class XAuthProperties {
    private final CorsConfiguration cors = new CorsConfiguration();
}
