package com.comba.mantun.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Map;

import static com.comba.mantun.message.Constants.*;

/**
 * @author maozhihui
 * @Description:
 * @create 2018/2/25 11:10
 **/
@Slf4j
public class RequestCommon {

    public static String getRequestParam(Map<String, Object> paramMap){
        paramMap.put(CLIENT_ID_VAR, configuration.getClientId());
        paramMap.put(ACCESS_TOKEN_VAR, token);
        paramMap.put(TIMESTAMP_VAR, getCurrentDate());
        paramMap.put(SIGN_VAR, getSign(paramMap));
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        return sb.deleteCharAt(sb.length()-1).toString();
    }

    private static String getSign(Map<String, Object> paramMap){
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            sb.append(entry.getValue());
        }
        sb.append(configuration.getAppSecret());
        return MD5Util.MD5(sb.toString());
    }

    public static HttpEntity<?> getHttpEntity(String requestBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        HttpEntity<String> entity = new HttpEntity<String>(requestBody, headers);
        return entity;
    }
}
