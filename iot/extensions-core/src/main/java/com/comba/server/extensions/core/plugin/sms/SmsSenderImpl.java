package com.comba.server.extensions.core.plugin.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.comba.server.extensions.core.rest.RestConfiguration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SmsSenderImpl implements SmsSender {

	private static final String TYPE = "pt";
	private static final String EXT_NO = "";
	private static final int SUCCESS_CODE = 0;
	@Value("${sms.url}")
	private String url;
	@Value("${sms.name}")
	private String name;
	@Value("${sms.pwd}")
	private String pwd;
	@Value("${sms.sign}")
	private String sign;
	
	@Autowired
    RestConfiguration beanConfig;
	
	@Override
	public boolean send(String isdn, String content) {
		RestTemplate restTemplate = beanConfig.restTemplate(beanConfig.httpRequestFactory());
        StringBuffer sb = new StringBuffer();
        sb.append(url).append("?")
            .append("name=").append(name)
            .append("&pwd=").append(pwd)
            .append("&content=").append(content)
            .append("&mobile=").append(isdn)
            .append("&sign=").append(sign)
            .append("&type=pt&extno=");
        try {
        	String response = restTemplate.getForObject(sb.toString(),String.class);
            String[] arrays = response.split(",");
            if (arrays.length == 6){
                ResponseEntity entity = new ResponseEntity(Integer.parseInt(arrays[0]),arrays[1],Integer.parseInt(arrays[2]),Integer.parseInt(arrays[3]),Integer.parseInt(arrays[4]),arrays[5]);
                if (entity.getCode() == SUCCESS_CODE) {
    				return true;
    			}
                log.info("sms response [{}]",entity);
            }else {
				log.error("sms response format is invalid.");
			}
		} catch (RestClientException e) {
			log.error("RestClientException:{}"+e.getMostSpecificCause());
		}
        
		return false;
	}

	@Data
	@AllArgsConstructor
	static public class ResponseEntity{
		// 响应码
		private int code;
		// 发送ID，实际为时间时期戳
	    private String sendId;
	    // 非法数量个数
	    private int invalidCount;
	    // 成功数量个数
	    private int successCount;
	    private int blackCount;
	    // 响应内容
	    private String msg;
	}
}
