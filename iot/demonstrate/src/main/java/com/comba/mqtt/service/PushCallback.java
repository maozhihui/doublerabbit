package com.comba.mqtt.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.comba.mqtt.dao.model.DeviceDataEntity;
import com.comba.mqtt.dao.model.DeviceEntity;
import com.comba.mqtt.dao.model.LatestDataEntity;
import com.comba.mqtt.dao.service.DeviceDataService;
import com.comba.mqtt.dao.service.DeviceService;
import com.comba.mqtt.dao.service.LatestDataService;
import com.comba.mqtt.data.KvEntry;
import com.comba.mqtt.data.UploadDataEntry;
import com.comba.mqtt.message.BasicTelemetryUploadRequest;
import com.comba.mqtt.message.MqttMessageConverter;
import com.comba.mqtt.message.TelemetryUploadRequest;
import com.comba.mqtt.message.comba.CombaDataParser;
import com.comba.mqtt.message.comba.DataUpload;
import com.comba.mqtt.message.winext.WinextDataParser;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Component
public class PushCallback implements MqttCallback {

	private static final Gson GSON = new Gson();
	
	//@Value("${temperture.max}")
	private int maxTemperture;
	//@Value("${humidity.max}")
	private int maxHumidity;
	//@Value("${voltage.min}")
	private double minVoltage;
	//@Autowired
	private DeviceService deviceService;
	//@Autowired
	private DeviceDataService deviceDataService;
	//@Autowired
	private LatestDataService latestDataService;
	private MqttServiceContext context;
	private MqttConfig config;
	private TelemetryUploadRequest dataModel;
	private Map<String, TelemetryUploadRequest> parserMap;
	
	public PushCallback(MqttServiceContext context,MqttConfig config){
		this.context = context;
		this.deviceService = context.getDeviceService();
		this.deviceDataService = context.getDeviceDataService();
		this.latestDataService = context.getLatestDataService();
		this.maxTemperture = context.getMaxTemperture();
		this.maxHumidity = context.getMaxHumidity();
		this.minVoltage = context.getMinVoltage();
		this.config = config;
		//initConfiguration();
		//this.parserMap = parserMap;
	}
	
	public void initConfiguration(){
		try {
			Class<TelemetryUploadRequest> clazz = (Class<TelemetryUploadRequest>)Class.forName(config.getModelClass());
			dataModel = clazz.newInstance();
		} catch (Exception e) {
			log.error("dynamic load class [{}] failed.",config.getModelClass());
		}
	}
	
	public void connectionLost(Throwable cause) {
		// TODO Auto-generated method stub
		// 连接丢失后，一般在这里面进行重连  
        System.out.println("连接断开，可以做重连");
	}

	public void messageArrived(String topic, MqttMessage message) throws Exception {
		try {
			String mqttMsg = new String(message.getPayload());
			log.info("recieve mqtt msg:"+mqttMsg);
			/*if (dataModel instanceof com.comba.mqtt.message.comba.DataUpload) {
				dataModel = GSON.fromJson(mqttMsg, com.comba.mqtt.message.comba.DataUpload.class);
			}else if (dataModel instanceof com.comba.mqtt.message.winext.DataUpload) {
				dataModel = GSON.fromJson(mqttMsg, com.comba.mqtt.message.winext.DataUpload.class);
			}else {
				log.error("data model class not support [{}]",config.getModelClass());
				return;
			}*/
			String applicationName;
			JsonElement parser = new JsonParser().parse(mqttMsg);
			if (parser.isJsonObject()) {
				JsonObject object = parser.getAsJsonObject();
				applicationName = object.get("applicationName").getAsString();
				dataModel = GSON.fromJson(mqttMsg, com.comba.mqtt.message.comba.DataUpload.class);
				if (applicationName.equals("Winext")) {
					((DataUpload)dataModel).setDataParser(new WinextDataParser());
				}else if (applicationName.equals("comba")) {
					((DataUpload)dataModel).setDataParser(new CombaDataParser());
				}else {
					log.error("applicationName [{}] is not support.",applicationName);
				}
			}else {
				log.error("parser mqttmsg failed=====");
				return;
			}
			log.info("devEui [{}]",dataModel.getDeviceId());
			Map<Long, List<KvEntry>> payloadData = dataModel.getPayloadData();
			if (payloadData.isEmpty()) {
				log.info("mqtt msg does not have data field.");
				return;
			}
			Optional<DeviceEntity> deviceEntity = deviceService.findByDevEui(dataModel.getDeviceId());
			if (deviceEntity.isPresent()) {
				//log.info("hi==============");
				for (Map.Entry<Long, List<KvEntry>> entry : payloadData.entrySet()) {
					for (KvEntry kvEntry : entry.getValue()) {
						DeviceDataEntity dataEntity = new DeviceDataEntity();
						dataEntity.setDevId(deviceEntity.get().getId());
						dataEntity.setAttrName(kvEntry.getKey());
						dataEntity.setAttrValue(kvEntry.getValueAsString());
						dataEntity.setUpdateTime(new Date(entry.getKey()));
						deviceDataService.save(dataEntity);
						
						// 如果存在则先获取再更新
						Optional<LatestDataEntity> latestEntity = latestDataService.findByDevIdAndAttrName(deviceEntity.get().getId(), kvEntry.getKey());
						LatestDataEntity latestDataEntity = null;
						if (latestEntity.isPresent()) {
							latestDataEntity = latestEntity.get();
						}else {
							latestDataEntity = new LatestDataEntity();
						}
						latestDataEntity.setDevId(deviceEntity.get().getId());
						latestDataEntity.setAttrName(kvEntry.getKey());
						latestDataEntity.setAttrValue(kvEntry.getValueAsString());
						latestDataEntity.setUpdateTime(new Date(entry.getKey()));
						if (applicationName.contains("Winext")) {
							judgeAlarm(latestDataEntity, kvEntry);
						}else {
							latestDataEntity.setAlarmLevel("正常");
							latestDataEntity.setAlarmLevelEn("Normal");
							latestDataEntity.setAlarmCause("状态正常");
							latestDataEntity.setAlarmCauseEn("Normal condition");;
						}
						latestDataService.save(latestDataEntity);
					}
				}
			}
		} catch (Exception e) {
			log.error("recieve msg convert josn failure,{}",e.getMessage());
		}
		
	}

	private void judgeAlarm(LatestDataEntity dataEntity,KvEntry kvEntry){
		String status = BasicTelemetryUploadRequest.ALARM_NORMAL;
		String cause = BasicTelemetryUploadRequest.NORMAL_CAUSE;
		String statusEn = BasicTelemetryUploadRequest.ALARM_NORMAL_EN;
		String causeEn = BasicTelemetryUploadRequest.NORMAL_CAUSE_EN;
		if (kvEntry.getKey().equals(MqttMessageConverter.TEMPERTURE_KEY)) {
			if (kvEntry.getDoubleValue().get() >= maxTemperture) {
				status = BasicTelemetryUploadRequest.ALARM_WARN;
				cause = "温度超过阀值:"+maxTemperture;
				statusEn = BasicTelemetryUploadRequest.ALARM_WARN_EN;
				causeEn = "Temperture exceeds threshold " + maxTemperture;
			}else {
				cause = "温度" + cause;
				causeEn = "Normal temperature";
			}
		}
		if (kvEntry.getKey().equals(MqttMessageConverter.HUMIDITY_KEY)) {
			if (kvEntry.getDoubleValue().get() >= maxHumidity) {
				status = BasicTelemetryUploadRequest.ALARM_WARN;
				cause = "湿度超过阀值:"+maxHumidity;
				statusEn = BasicTelemetryUploadRequest.ALARM_WARN_EN;
				causeEn = "Humiture exceeds threshold " + maxHumidity;
			}else {
				cause = "湿度" + cause;
				causeEn = "Normal humidity";
			}
		}
		if (kvEntry.getKey().equals(MqttMessageConverter.ALARM_STATUS_KEY)) {
			if (kvEntry.getStrValue().get().equalsIgnoreCase("true")) {
				status = BasicTelemetryUploadRequest.ALARM_WARN;
				cause = "烟雾告警请处理。";
				statusEn = BasicTelemetryUploadRequest.ALARM_WARN_EN;
				causeEn = "Smoke alarm";
			}else {
				cause = "状态正常";
				causeEn = "Normal condition";
			}
		}
		if (kvEntry.getKey().equals(MqttMessageConverter.ISAVAILABLE_KEY)) {
			if (kvEntry.getStrValue().get().equalsIgnoreCase("false")) {
				status = "不可用";
				cause = "车位己停车";
				statusEn = BasicTelemetryUploadRequest.ALARM_WARN_EN;
				causeEn = "Parking is not available";
			}else {
				cause = "车位可用";
				causeEn = "Parking is available";
			}
		}
		if (kvEntry.getKey().equals(MqttMessageConverter.VOLTAGE_KEY)) {
			if (kvEntry.getDoubleValue().get() < minVoltage) {
				status = BasicTelemetryUploadRequest.ALARM_WARN;
				cause = "电量过低告警";
				statusEn = BasicTelemetryUploadRequest.ALARM_WARN_EN;
				causeEn = "Low voltage alarm";
			}else {
				cause = "电压正常";
				causeEn = "Normal voltage";
			}
		}
		dataEntity.setAlarmLevel(status);
		dataEntity.setAlarmCause(cause);
	}
	
	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub
		// publish后会执行到这里  
        System.out.println("deliveryComplete---------"+ token.isComplete());
	}

}
