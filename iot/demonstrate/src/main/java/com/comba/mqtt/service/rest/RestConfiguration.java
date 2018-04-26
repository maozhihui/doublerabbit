package com.comba.mqtt.service.rest;

import lombok.extern.slf4j.Slf4j;
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
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.*;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
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
    public HttpComponentsClientHttpRequestFactory clientHttpRequestFactory()
    {
    	HttpClientBuilder clientBuilder = HttpClients.custom();
    	try {
    		/*TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
            SSLContext sslContext = SSLContexts.custom()
    				                .loadTrustMaterial(null, acceptingTrustStrategy)
    				                .build();*/
    		//HostnameVerifier verifier = SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
            SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(createIgnoreVerifySSL(),new NullHostNameVerifier());
            clientBuilder.setSSLSocketFactory(csf);

            /*Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()  
                    .register("http", PlainConnectionSocketFactory.INSTANCE)  
                    .register("https", csf)  
                    .build();  
            PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);  
            clientBuilder.setConnectionManager(connManager);*/  
      
		} catch (Exception e) {
			log.error("create ssl socket factory failed,cause [{}]",e.getMessage());
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
    
    @Bean
    public RestTemplate restTemplate(SimpleClientHttpRequestFactory httpRequestFactory) {
        RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
        return restTemplate;
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
    
    /*@Bean
    public RestTemplate restTemplate(HttpComponentsClientHttpRequestFactory httpRequestFactory) {
    	RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
    	//restTemplate.getMessageConverters().add(new TextHtmlMappingJackson2HttpMessageConverter());
        return restTemplate;
    }*/
    
    public static class TextHtmlMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter{
    	public TextHtmlMappingJackson2HttpMessageConverter(){
    		List<MediaType> mediaTypes = new ArrayList<>();
            mediaTypes.add(MediaType.TEXT_HTML);
            setSupportedMediaTypes(mediaTypes);
    	}
    } 
    
    public static class NullHostNameVerifier implements HostnameVerifier{

		@Override
		public boolean verify(String arg0, SSLSession arg1) {
			// TODO Auto-generated method stub
			return true;
		}
    	
    }
}