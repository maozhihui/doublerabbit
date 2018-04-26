package com.comba.mqtt.controller;

import com.comba.mqtt.controller.msg.AttributeAverageData;
import com.comba.mqtt.controller.msg.StatusInfo;
import com.comba.mqtt.controller.msg.TopStatData;
import com.comba.mqtt.dao.service.DeviceDataService;
import com.comba.mqtt.dao.service.LatestDataService;
import com.comba.mqtt.util.JsonConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 处理场景功能请求
 * @author maozhihui
 *
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/v1")
public class SceneController {

	private final static Integer LANG_ENGLISH = 2;
	
	@Autowired
	private LatestDataService latestDataService;
	
	@Autowired
	private DeviceDataService deviceDataService;
	
	/**
	 * 获取设备所有属性的业务状态信息
	 * @return [{"devId":3,"devName":"温湿度_A1_01","time":12354566,"alarmLevel":"normal","alarmCause":"正常"},{"devId":4,"devName":"温湿度_A1_02","time":12354566,"alarmLevel":"normal","alarmCause":"正常"}]
	 */
	@GetMapping(value = "/statusInfo")
	public String getStatusInfo(Integer type){
		List<StatusInfo> res;
		if (type.equals(LANG_ENGLISH)) {
			res = latestDataService.findAllByEn();
		}else {
			res = latestDataService.findAll();
		}
		log.debug("[{}] find StatusInfo size [{}]",type,res.size());
		if (res == null || res.size() == 0) 
			return "{}";
		return JsonConverter.toJson(res);
	}
	
	/**
	 * 获取温度平均值，按天统计
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return [{"attrName":"temperture","averageStat":{"1505404800000":"66.72173058013766","1503849600000":"78.44827586206897","1505491200000":"65.6443956043956"}}]
	 */
	@GetMapping(value = "/stat/tempertureAverage")
	public String getTempertureAverage(@RequestParam(value = "startTime") long startTime,
										@RequestParam(value = "endTime") long endTime){
		if (startTime > endTime)
			return "{}";
		List<AttributeAverageData> avgStatData = deviceDataService.getAttributeAverageData("temperture", new Date(startTime), new Date(endTime));
		return JsonConverter.toStatJson(avgStatData);
	}
	
	/**
	 * 获取湿度平均值，按天统计
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return [{"attrName":"humidity","averageStat":{"1505404800000":"66.72173058013766","1503849600000":"78.44827586206897","1505491200000":"65.6443956043956"}}]
	 */
	@GetMapping(value = "/stat/humidityAverage")
	public String getHumidityAverage(@RequestParam(value = "startTime") long startTime,
										@RequestParam(value = "endTime") long endTime){
		if (startTime > endTime)
			return "{}";
		List<AttributeAverageData> avgStatData = deviceDataService.getAttributeAverageData("humidity", new Date(startTime), new Date(endTime));
		return JsonConverter.toStatJson(avgStatData);
	}
	
	@GetMapping(value = "/stat/pmAverage")
	public String getPMAverage(@RequestParam(value = "startTime") long startTime,
										@RequestParam(value = "endTime") long endTime){
		if (startTime > endTime)
			return "{}";
		List<AttributeAverageData> avgStatData = deviceDataService.getAttributeAverageData("pm2.5", new Date(startTime), new Date(endTime));
		return JsonConverter.toStatJson(avgStatData);
	}
	
	@GetMapping(value = "/stat/phAverage")
	public String getPHAverage(@RequestParam(value = "startTime") long startTime,
										@RequestParam(value = "endTime") long endTime){
		if (startTime > endTime)
			return "{}";
		List<AttributeAverageData> avgStatData = deviceDataService.getAttributeAverageData("ph", new Date(startTime), new Date(endTime));
		return JsonConverter.toStatJson(avgStatData);
	}
	
	@GetMapping(value = "/stat/tempertureTop")
	public String getTempertureTopData(@RequestParam(value = "startTime") long startTime,
                                       @RequestParam(value = "endTime") long endTime,
                                        @RequestParam Integer type){
		TopStatData res;
		if (type.equals(LANG_ENGLISH)) {
			res = latestDataService.getTopStatByEn("temperture", new Date(startTime), new Date(endTime), 5);
		}else {
			res = latestDataService.getTopStat("temperture", new Date(startTime), new Date(endTime), 5);
		}
		if (res == null) 
			return "{}";
		return JsonConverter.toTopStatJson(res);
	}
	
	@GetMapping(value = "/stat/humidityTop")
	public String getHumidityTopData(@RequestParam(value = "startTime") long startTime,
                                     @RequestParam(value = "endTime") long endTime,
                                     @RequestParam Integer type){
		TopStatData res;
		if (type.equals(LANG_ENGLISH)) {
			res = latestDataService.getTopStatByEn("humidity", new Date(startTime), new Date(endTime), 5);
		}else {
			res = latestDataService.getTopStat("humidity", new Date(startTime), new Date(endTime), 5);
		}
		if (res == null) 
			return "{}";
		return JsonConverter.toTopStatJson(res);
	}
}
