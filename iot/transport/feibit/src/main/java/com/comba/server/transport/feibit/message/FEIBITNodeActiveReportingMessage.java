package com.comba.server.transport.feibit.message;


import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;

import com.alibaba.fastjson.JSONObject;
import com.comba.server.transport.feibit.bean.FEIBITDeviceAttribute;
import com.comba.server.transport.feibit.common.BitMap;
import com.comba.server.transport.feibit.common.ByteUtil;
import com.comba.server.transport.feibit.common.Constant;
import com.comba.server.transport.feibit.handler.ConnectionManager;
import com.comba.server.transport.feibit.handler.FEIBITClientSessionHandler;


/*
 * 
 * 节点主动上报：该信息由节点主动上报
 */
@Slf4j
@Data
public class FEIBITNodeActiveReportingMessage extends FEIBITAbstractRespMessage{
	//短地址:2 byte
	private final int SHORT_ADDR_LEN = 2;
	private short shortAddr;
	//Endpoint :1 byte ,1-240
	private byte endpoint;
	//Cluster ID:2 byte
	private final int CLUSTER_ID_LEN = 2;
	private short clusterId;
	//报告个数: 1 byte
	private byte reportsCount;
	private ConcurrentHashMap<String,FEIBITDeviceAttribute> deviceAttributesMap = new ConcurrentHashMap<String,FEIBITDeviceAttribute>();
	
	public FEIBITNodeActiveReportingMessage() {
		deviceStateResp = Constant.NODE_ACTIVE_REPORTING;
	}

	public ConcurrentHashMap<String,FEIBITDeviceAttribute> getAttribute(){
		return deviceAttributesMap;
	}
	

	@Override
	public void encodeBody(IoBuffer bt) {
		// TODO Auto-generated method stub
		log.error("not implement yet,can't run here!");
	}
	@Override
	public void decodeBody(byte[] body) {
		
		IoBuffer buf=IoBuffer.allocate(body.length).setAutoExpand(true);
		buf.put(body);
		buf.flip();
		
		shortAddr = ByteUtil.shortConvert_LH(buf.getShort());
		endpoint = buf.get();
		clusterId = ByteUtil.shortConvert_LH(buf.getShort());
		reportsCount = buf.get();
		
		//属性Id和属性Value解析
		for(int i = 0; i < reportsCount; i++){
			
			FEIBITDeviceAttribute deviceAttribute = new FEIBITDeviceAttribute();
			byte[] attributeIdBytes = new byte[2];
			attributeIdBytes[1] = buf.get();
			attributeIdBytes[0] = buf.get();
			byte dataType = buf.get();
			byte dataValueLen = 0;
			
			if(dataType == Constant.UINT_8 || dataType == Constant.INT_8){
				dataValueLen = 1;
			}else if(dataType == Constant.UINT_16 || dataType == Constant.INT_16){
				dataValueLen = 2;
			}else if(dataType == Constant.UINT_24 || dataType == Constant.INT_24){
				dataValueLen = 3;
			}else if(dataType == Constant.UINT_32 || dataType == Constant.INT_32){
				dataValueLen = 4;
			}else{
				break;	//抛弃后面的属性数据
			}
			
			byte[] dataValueBytes = new byte[dataValueLen];
				
			for(int j = dataValueLen-1; j >= 0; j--){
				dataValueBytes[j] = buf.get();
			}

			deviceAttribute.setAttributeId(ByteUtil.bytesToHexString(attributeIdBytes));
			deviceAttribute.setType(dataType);
			deviceAttribute.setValue(ByteUtil.bytesToHexString(dataValueBytes));
			deviceAttributesMap.put(new String(attributeIdBytes), deviceAttribute);
		}
	}
	
	public String getTelemetryData(FEIBITGetDevicesRespMessage device){
		String result = "";
		
		switch(device.getDeviceType()){
			case Constant.DOOR_LOCK_SENSOR:
				result = getDoorLockData();
				break;
			case Constant.HUMAN_INFRARED_SENSOR:
				result = getHumanInfraredData();
				break;
			case Constant.THERMO_HYGROMETER:
				result = getThermoHygrometerData();
				break;	
			case Constant.ILLUMINATION:
				result = getIlluminationData();
				break;
			case Constant.WATER_IMMERSION_SENSOR:
				result = getWaterImmersionData();
				break;
			case Constant.SMOKE_SENSOR:
				result = getSmokeData();
				break;
			default:
				// 开关面板，情景面板，红外遥控器，窗帘，数据不上报
				//result = getUnknowDeviceTypeData();
				break;
		}
		return result;
	}
	
	//门磁
	public String getDoorLockData(){
		JSONObject jsonObj = new JSONObject(true);
		for (Map.Entry<String,FEIBITDeviceAttribute> entry : deviceAttributesMap.entrySet()) {
			//bit0 1-开门，0-关门。bit3 1-电池电压低，0-电池电压正常
			int value = entry.getValue().getValueToInt();
			String attributeId = entry.getValue().getAttributeId();
			if(attributeId.equals("0080")){
				BitMap bits = new BitMap(ByteUtil.intToByte(value)); 
				int bit0 = bits.getBit(0);
				int bit3 = bits.getBit(3);
				jsonObj.put("switch", bit0);
				jsonObj.put("voltageState", bit3);
			}			
			else {
				jsonObj.putAll(getPowerConfiguration(attributeId,value));
			}
		}
		return jsonObj.toString();
	}
	//人体红外
	public String getHumanInfraredData(){
		JSONObject jsonObj = new JSONObject(true);
		for (Map.Entry<String,FEIBITDeviceAttribute> entry : deviceAttributesMap.entrySet()) {
			//bit0 1-有人，0-无人。bit3 1-电池电压低，0-电池电压正常
			int value = entry.getValue().getValueToInt();
			String attributeId = entry.getValue().getAttributeId();
			if(attributeId.equals("0080")){
				BitMap bits = new BitMap(ByteUtil.intToByte(value));  
				int bit0 = bits.getBit(0);
				int bit3 = bits.getBit(3);
				jsonObj.put("infrared", bit0);
				jsonObj.put("voltageState", bit3);
			}
			else {
				jsonObj.putAll(getPowerConfiguration(attributeId,value));
			}
		}
		return jsonObj.toString();
	}
	//温湿度
	public String getThermoHygrometerData(){
		double hundred = 100.0;
		double value = 0;
		JSONObject jsonObj = new JSONObject(true);
		for (Map.Entry<String,FEIBITDeviceAttribute> entry : deviceAttributesMap.entrySet()) {
			value = entry.getValue().getValueToInt()/hundred;
			String attributeId = entry.getValue().getAttributeId();
			if(clusterId==0x0402){//单独上报温度
				jsonObj.put("temperature", value);
			}
			else if(clusterId==0x0405){//单独上报湿度
				jsonObj.put("humidity", value);
			}
			else if(clusterId==0x0104){//同时上报温度，湿度
				if(attributeId.equals("0000")){
					jsonObj.put("temperature", value);
				}
				else if(attributeId.equals("0007")){
					jsonObj.put("humidity", value);
				}
			}
			else {
				value = entry.getValue().getValueToInt();
				jsonObj.putAll(getPowerConfiguration(attributeId,value));
			}
		}
		return jsonObj.toString();
	}
	
	//光照
	public String getIlluminationData(){
		JSONObject jsonObj = new JSONObject(true);
		for (Map.Entry<String,FEIBITDeviceAttribute> entry : deviceAttributesMap.entrySet()) {
			int value = entry.getValue().getValueToInt();
			String attributeId = entry.getValue().getAttributeId();
			if(clusterId==0x0001){//数据为power configuration 电源配置相关的簇信息
				jsonObj.putAll(getPowerConfiguration(attributeId,value));
			}
			else if(clusterId==0x0400){//单独上报光照数据
				jsonObj.put("illumination", value);
			}
		}
		return jsonObj.toString();
	}
	//水浸
	public String getWaterImmersionData(){
		JSONObject jsonObj = new JSONObject(true);
		for (Map.Entry<String,FEIBITDeviceAttribute> entry : deviceAttributesMap.entrySet()) {
			//bit0 1-有水，0-无水。bit3 1-电池电压低，0-电池电压正常
			int value = entry.getValue().getValueToInt();
			String attributeId = entry.getValue().getAttributeId();
			if(attributeId.equals("0080")){
				BitMap bits = new BitMap(ByteUtil.intToByte(value)); 
				int bit0 = bits.getBit(0);
				int bit3 = bits.getBit(3);
				jsonObj.put("waterState", bit0);
				jsonObj.put("voltageState", bit3);
			}
			else {
				jsonObj.putAll(getPowerConfiguration(attributeId,value));
			}
		}
		return jsonObj.toString();
	}
	
	//烟雾
	public String getSmokeData(){
		JSONObject jsonObj = new JSONObject(true);
		for (Map.Entry<String,FEIBITDeviceAttribute> entry : deviceAttributesMap.entrySet()) {
			int value = entry.getValue().getValueToInt();
			String attributeId = entry.getValue().getAttributeId();
			if(attributeId.equals("0080")){
				//bit0 1-有烟雾，0-无烟雾。bit3 1-电池电压低，0-电池电压正常
				BitMap bits = new BitMap(ByteUtil.intToByte(value)); 
				int bit0 = bits.getBit(0);
				int bit3 = bits.getBit(3);
				jsonObj.put("alarmState", bit0);
				jsonObj.put("voltageState", bit3);
			}
			else {
				jsonObj.putAll(getPowerConfiguration(attributeId,value));
			}
		}
		return jsonObj.toString();
	}
	
	//传感器类型不明确的设备 上报数据
	public String getUnknowDeviceTypeData(){
		JSONObject jsonObj = new JSONObject(true);
		for (Map.Entry<String,FEIBITDeviceAttribute> entry : deviceAttributesMap.entrySet()) {
			jsonObj.put(entry.getValue().getAttributeId(), entry.getValue().getValueToInt());//初始属性id和属性值
		}
		return jsonObj.toString();
	}
	
	//电源配置相关簇信息
	public JSONObject getPowerConfiguration(String attributeId, double value){
		JSONObject jsonObj = new JSONObject(true);
		if(attributeId.equals("0020")){//电压
			//电压默认单位是100mv，转换成单位V
			jsonObj.put("voltage", value/10);
		}
		else if(attributeId.equals("0021")){//电池电量
			jsonObj.put("electric", value);
		}
		else if(attributeId.equals("003e")){//是否欠压，0-正常，1-欠压
			jsonObj.put("undervoltage", value);
		}
		return jsonObj;
	}
	@Override
	public String toString() {
		
		StringBuffer deviceAttributes = new StringBuffer("");
		for (Map.Entry<String,FEIBITDeviceAttribute> entry : deviceAttributesMap.entrySet()) {
			FEIBITDeviceAttribute deviceAttribute = entry.getValue();
			deviceAttributes.append(", attributeId=")
			.append(deviceAttribute.getAttributeId())
			.append(", dataType=")
			.append(ByteUtil.byteToHexString(deviceAttribute.getType()))
			.append(", dataValue=")
			.append(deviceAttribute.getValue())
			.append(", intValue=")
			.append(deviceAttribute.getValueToInt());
		}
		
		return "FEIBITNodeActiveReportingMessage" 
				+ "[shortAddr=" + ByteUtil.shortToHexString(shortAddr) 
				+ ", endpoint=" + ByteUtil.byteToHexString(endpoint) 
				+ ", clusterId=" + ByteUtil.shortToHexString(clusterId)
				+ ", reportsCount=" + reportsCount + deviceAttributes.toString() + "]";
	}
}
