package com.comba.server.extensions.core.rest;

import org.apache.http.HttpHost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.*;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

@Configuration
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
    public HttpComponentsClientHttpRequestFactory httpRequestFactory2()
    {
        HttpClientBuilder clientBuilder = HttpClients.custom();
        try {
            SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(createIgnoreVerifySSL(),new NullHostNameVerifier());
            clientBuilder.setSSLSocketFactory(csf);

        } catch (Exception e) {
            System.out.println("exception cause:"+e.getMessage());
        }
        if (proxyConfig.isEnabled()){
            HttpHost proxy = new HttpHost(proxyConfig.getHost(), proxyConfig.getPort());
            clientBuilder.setRoutePlanner(new DefaultProxyRoutePlanner(proxy));
        }

        CloseableHttpClient httpClient = clientBuilder.build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setReadTimeout(readTimeout);
        requestFactory.setConnectTimeout(connectionTimeout);

        requestFactory.setHttpClient(httpClient);
        return requestFactory;
    }

    /**
     * 绕过验证
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("TLS");

        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sc.init(null, new TrustManager[] { trustManager }, null);
        return sc;
    }

    public static class NullHostNameVerifier implements HostnameVerifier {

        @Override
        public boolean verify(String arg0, SSLSession arg1) {
            return true;
        }

    }

    @Bean(name = "restTemplate")
    public RestTemplate restTemplate(SimpleClientHttpRequestFactory httpRequestFactory){
        RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
        return restTemplate;
    }

    @Bean(name = "loraRestTemplate")
    public RestTemplate loraRestTemplate(HttpComponentsClientHttpRequestFactory httpRequestFactory){
        RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
        return restTemplate;
    }
}