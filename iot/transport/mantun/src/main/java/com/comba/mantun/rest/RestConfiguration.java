package com.comba.mantun.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;

@Configuration("mantunConfig")
@ConditionalOnClass(ProxyConfig.class)
public class RestConfiguration {
    @Value("${rest.ReadTimeout}")
    private int readTimeout;
    @Value("${rest.ConnectTimeout}")
    private int connectionTimeout;
    @Autowired
    private ProxyConfig proxyConfig;

    @Bean
    public SimpleClientHttpRequestFactory httpRequestFactory(){
        SimpleClientHttpRequestFactory httpRequestFactory = new SimpleClientHttpRequestFactory();
        httpRequestFactory.setReadTimeout(readTimeout);
        httpRequestFactory.setConnectTimeout(connectionTimeout);
        if (proxyConfig.isEnabled()){
            SocketAddress address = new InetSocketAddress(proxyConfig.getHost(),proxyConfig.getPort());
            Proxy proxy = new Proxy(Proxy.Type.HTTP,address);
            httpRequestFactory.setProxy(proxy);
        }

        return httpRequestFactory;
    }

    @Bean
    public RestTemplate restTemplate(SimpleClientHttpRequestFactory httpRequestFactory){
        RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
        return restTemplate;
    }
}