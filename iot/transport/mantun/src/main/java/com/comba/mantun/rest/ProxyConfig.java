package com.comba.mantun.rest;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component("mantun")
@ConfigurationProperties(prefix = "proxy")
@Data
public class ProxyConfig {

    private boolean enabled;

    private String host;

    private int port;
}
