package com.comba.mqtt.service.rest;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "proxy")
@Data
public class ProxyConfig {

    private boolean enabled;

    private String host;

    private int port;
}
