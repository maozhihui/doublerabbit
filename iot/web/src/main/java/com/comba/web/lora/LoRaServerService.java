package com.comba.web.lora;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class LoRaServerService {

    @Resource(name = "loraRestTemplate")
    private RestTemplate restTemplate;

    @Value("${lora.createGateway}")
    private String createGateway;

    @Value("${lora.createDevice}")
    private String createDevice;

    @Value("${lora.login}")
    private String login;

    @Value("${lora.userName}")
    private String userName;

    @Value("${lora.password}")
    private String password;

    @Value("${lora.networkServerID}")
    private String networkServerID;

    @Value("${lora.organizationID}")
    private String organizationID;

    @Value("${lora.channelConfigurationID}")
    private String channelConfigurationID;

    @Value("${lora.applicationID}")
    private String applicationID;

    @Value("${lora.deviceProfileID}")
    private String deviceProfileID;

    private static final String header = "Bearer ";

    private static ConcurrentHashMap<String ,ConcurrentHashMap<Date,String>> localToken = new ConcurrentHashMap<>();




    public Object createGateway(String name,String mac,String desc){
        if (getToken() == null){
            log.error("requestUrl = createGateway, token is null ");
            return null;
        }

        LoraReq req = new LoraReq();
        req.setName(Integer.toHexString(name.hashCode()));
        req.setMac(mac);
        req.setNetworkServerID(networkServerID);
        req.setOrganizationID(organizationID);
        req.setChannelConfigurationID(channelConfigurationID);
        req.setPing(Boolean.TRUE);
        req.setDescription(desc);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Grpc-Metadata-Authorization",getToken());
        HttpEntity entity = new HttpEntity(req,headers);

        log.info("createGateway {},{}", createGateway,JSON.toJSONString(entity));

        try{
            ResponseEntity ret = restTemplate.postForEntity(createGateway,entity,Object.class);
            return ret;
        }catch (HttpClientErrorException e){
            if (HttpStatus.UNAUTHORIZED.equals(e.getStatusCode())){
                localToken.clear();
            }
            log.error("createGateway fail {}",e.getMessage());
            return null;
        }
    }

    public Object createDevice(String name,String devEUI,String description){
        if (getToken() == null){
            log.error("requestUrl = createGateway, token is null ");
            return null;
        }

        Map<String,Object> params = new HashMap<>();
        params.put("applicationID",applicationID);
        params.put("devEUI",devEUI);
        params.put("deviceProfileID",deviceProfileID);
        params.put("name",name);
        params.put("description",description);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Grpc-Metadata-Authorization",getToken());
        HttpEntity entity = new HttpEntity(params,headers);
        log.info("createDevice {},{}", createDevice,JSON.toJSONString(entity));

        try{
            ResponseEntity ret = restTemplate.postForEntity(createDevice,entity,Object.class);
            return ret;
        }catch (HttpClientErrorException e){
            if (HttpStatus.UNAUTHORIZED.equals(e.getStatusCode())){
                localToken.clear();
            }
            log.error("createDevice fail {}",e.getMessage());
            return null;
        }
    }

    public Object createAppkey(String devEUI,String appKey){
        if (getToken() == null){
            log.error("requestUrl = createAppkey, token is null ");
            return null;
        }

        Map<String,Object> params = new HashMap<>();
        Map<String,Object> appKeyMap = new HashMap<>();
        appKeyMap.put("appKey",appKey);
        params.put("deviceKeys", appKeyMap);
        params.put("devEUI",devEUI);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Grpc-Metadata-Authorization",getToken());
        HttpEntity entity = new HttpEntity(params,headers);

        String url = createDevice + "/"+devEUI + "/keys";
        log.info("createAppKey {},{}", url,JSON.toJSONString(entity));

        try{
            ResponseEntity ret = restTemplate.postForEntity(url,entity,Object.class);
            return ret;
        }catch (HttpClientErrorException e){
            if (HttpStatus.UNAUTHORIZED.equals(e.getStatusCode())){
                localToken.clear();
            }
            log.error("createAppKey fail {}",e.getMessage());
            return null;
        }
    }

    public Object deleteGateway(String mac){
        String url = createGateway+"/"+mac;
        return exchange(url);
    }

    public Object deleteDevice(String id){
        String url = createDevice+"/"+id;
        return exchange(url);
    }

    private Object exchange(String requestUrl){
        if (getToken() == null){
            log.error("requestUrl = createAppkey, token is null ");
            return null;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Grpc-Metadata-Authorization",getToken());
        HttpEntity entity = new HttpEntity(headers);

        try{
            log.info("exchange iotstp,request {},{}",requestUrl, JSON.toJSONString(entity));
            ResponseEntity<Object> respResponseEntity = restTemplate.exchange(requestUrl, HttpMethod.DELETE,entity,Object.class);
            log.info("exchange iotstp,response {}", JSON.toJSONString(respResponseEntity));
            return respResponseEntity.getBody();

        }catch (HttpClientErrorException e){
            if (HttpStatus.UNAUTHORIZED.equals(e.getStatusCode())){
                localToken.clear();
            }
            log.error("exchange iotstp error "+e);
            return null;
        }
    }


    public String login(String userName,String password){

        Map<String ,Object> params = new HashMap<>();
        params.put("username",userName);
        params.put("password",password);

        Map<String,Object> ret = new HashMap<>();
        HttpEntity entity = new HttpEntity(params);
        try{
            ret = restTemplate.postForObject(login,entity,Map.class);
            if (ret.get("jwt") != null){
                String token = ret.get("jwt").toString();
                String bearerTk = header+token;
                Date date = getTokenExpirationDate(token);
                //token和失效时间一起放到缓存
                ConcurrentHashMap map = new ConcurrentHashMap();
                map.put(date,bearerTk);
                localToken.put("token",map);
                return bearerTk;
            }
        }catch (RestClientException e){
            log.error("lora login fail {}",e.getMessage());
        }
        return null;
    }

    private String getToken(){
        //判断token是否到了有效期
        if (localToken.get("token") != null){
            Date now = new Date();
            ConcurrentHashMap map = localToken.get("token");
            Enumeration<Date> keys = map.keys();
            Date expirationDate = keys.nextElement();
            if (now.before(expirationDate)){
                return map.get(expirationDate).toString();
            }
        }

        return login(userName,password);
    }

    private Date getTokenExpirationDate(String token){
        String[] List = token.split("\\.");
        byte[] b = Base64.getDecoder().decode(List[1]);
        Map<String,Object> userInfo = JSON.parseObject(b,Map.class);
        //获取用户token的过期时间
        if (userInfo.get("exp") != null){
            //获取时间差
            long dateDiff = Long.valueOf( userInfo.get("exp").toString());
            //在原来的时间减去8天，为了减少token的有效时间
            Date calDate = DateUtils.addDays(new Date(),-8);
            //计算出token的失效时间
            Date expireDate = new Date(calDate.getTime()+dateDiff);
            return expireDate;
        }
        return null;
    }

}
