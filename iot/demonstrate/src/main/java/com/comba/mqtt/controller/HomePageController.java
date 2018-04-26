package com.comba.mqtt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.comba.mqtt.data.KvEntry;
import com.comba.mqtt.message.BasicTelemetryUploadRequest;

import lombok.Data;

/**
 * 
 * @author maozhihui
 * {"time":long,"devName":"xxx","devType":4/12/19,"datapoint":{"temperture":25.1,"humidity":32.2}}
 * {"time":long,"devName":"xxx","devType":4/12/19,"alarmLevel":"normal/warn","alarmCause":"温度/湿度超过阀值","datapoint":{"temperture":25.1,"humidity":32.2}}
 */
@Controller
public class HomePageController {

	public static String languageType = "cn";
	
	@Deprecated
	public static Map<String, BasicTelemetryUploadRequest> deviceInfoMap = new ConcurrentHashMap<>(1000);
	@Deprecated
	public static Map<String, BasicTelemetryUploadRequest> applicationCaseMap = new ConcurrentHashMap<>(1000);
	
	@RequestMapping(value="/index")  
    public String homePage() {  
        return "index";  
    }
	
	@Deprecated
	public ResponseBean toResponseBean(BasicTelemetryUploadRequest request){
		ResponseBean responseBean = new ResponseBean();
		responseBean.setDevName(request.getDevName());
		responseBean.setDevType(request.getDeviceType());
		responseBean.setAlarmLevel(request.getAlarmLevel());
		responseBean.setAlarmCause(request.getAlarmCause());
		Map<String, Object> map = new HashMap<String, Object>();
		for (Map.Entry<Long, List<KvEntry>> entry : request.getPayloadData().entrySet()) {
			responseBean.setTime(entry.getKey());
			//responseBean.setDatapoint(entry.getValue());
			for (KvEntry kvEntry : entry.getValue()) {
				map.put(kvEntry.getKey(), kvEntry.getValue());
			}
		}
		responseBean.setDatapoint(map);
		return responseBean;
	}
	
	@Deprecated
	@Data
	public class ResponseBean{
		private String devName;
		private long time;
		private int devType;
		private String alarmLevel;
		private String alarmCause;
		//private List<KvEntry> datapoint;
		private Map<String, Object> datapoint;
		
		public ResponseBean(){}
	}
	
	@RequestMapping(value="/cn/analysis")
    public String analysisCn() {
        return "/cn/analysis";
    }

    @RequestMapping(value="/en/analysis")
    public String analysisEn() {
        return "/en/analysis";
    }

	@RequestMapping(value="/cn/device")
    public String device() {  
        return "/cn/device";
    }

    @RequestMapping(value="/en/device")
    public String deviceEn() {
        return "/en/device";
    }

    @RequestMapping(value="/cn/scene")
    public String sceneCn() {
        return "/cn/scene";
    }

    @RequestMapping(value="/en/scene")
    public String sceneEn() {
        return "/en/scene";
    }

    @RequestMapping(value="/cn/fire")
    public String fireCn() {
        return "/cn/fire";
    }

    @RequestMapping(value="/en/fire")
    public String fireEn() {
        return "/en/fire";
    }
	
	@RequestMapping(value="/homepage")  
    public String homepage() {  
        return "homepage";  
    }


    @Deprecated
	@GetMapping(value = "/device/{devName}/getData")
	@ResponseBody
	public ResponseBean getDeviceData(@PathVariable("devName") String devName){
		if (deviceInfoMap.get(devName) == null) {
			return new ResponseBean();
		}
		BasicTelemetryUploadRequest msg = deviceInfoMap.remove(devName);
		return toResponseBean(msg);
	}
	
	@Deprecated
	@GetMapping(value = "/scene/{devName}/getData")
	@ResponseBody
	public ResponseBean getSceneData(@PathVariable("devName") String devName){
		if (applicationCaseMap.get(devName) == null) {
			return new ResponseBean();
		}
		BasicTelemetryUploadRequest msg = applicationCaseMap.remove(devName);
		return toResponseBean(msg);
	}
}
