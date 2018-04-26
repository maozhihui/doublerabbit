package com.comba.mqtt.message.winext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.comba.mqtt.data.DoubleDataEntry;
import com.comba.mqtt.data.KvEntry;
import com.comba.mqtt.data.StringDataEntry;
import com.comba.mqtt.message.comba.AbstractDataParser;

import lombok.extern.slf4j.Slf4j;

/**
 * 唯传数据解析器
 * @author maozhihui
 * @date 2017年11月10日 下午9:03:04
 */
@Slf4j
public class WinextDataParser implements AbstractDataParser{

	public static final String ALARM_STATUS_KEY = "alarmStatus";
	public static final String VOLTAGE_KEY = "voltage";
	public static final String ISAVAILABLE_KEY = "isAvailable";
	public static final String TEMPERTURE_KEY = "temperture";
	public static final String HUMIDITY_KEY = "humidity";
	// 心跳类型
	private static final int WATCH_TYPE = 3;
	// 状态类型
	private static final int STATUS_TYPE = 2;
	// 状态变更类型
	private static final int STATUS_CHANGE_TYPE = 4;
	// 配置类型
	private static final int CONFIG_TYPE = 1;
	
	@Override
	public List<KvEntry> convertToKvEntry(int deviceType, String data) {
		List<KvEntry> res = new ArrayList<>();
		if (StringUtils.isBlank(data)) {
			log.error("data is empty.");
			return res;
		}
		byte[] bytes = decode(data);
		if (bytes == null) {
			log.error("data decode failure.");
			return res;
		}
		switch (deviceType) 
		{ 
			case 4: 
				parseWenShiDeviceData(res, bytes);
				break; 
			case 12: 
				parseDiCiDeviceData(res, bytes);
				break; 
			case 19: 
				parseYanGanDeviceData(res, bytes);
				break; 
			default: 
				log.error("fPort [{}] not support.",deviceType); 
				break; 
		 }
		 
		return res;
	}
	
	/**
	 * 解析温湿度传感器数据
	 * @param kvEntries
	 * @param data
	 */
	public static void parseWenShiDeviceData(List<KvEntry> kvEntries,byte[] data){
		if (data == null || data.length == 0)
			return;
		int temp = getData(data, 0, 2);
		if (temp != 0) {
			double actul = temp / 100;
			KvEntry kvEntry = new DoubleDataEntry(TEMPERTURE_KEY, actul);
			kvEntries.add(kvEntry);
		}
		int hum = getData(data, 2, 2);
		if (hum != 0) {
			double actul = hum / 100;
			KvEntry kvEntry = new DoubleDataEntry(HUMIDITY_KEY, actul);
			kvEntries.add(kvEntry);
		}
	}
	
	/**
	 * 解析地磁数据
	 * 第一个字节为0x30即48时表示为地磁心跳帧
	 * 第一个字节为0x20即32时表示为地磁状态帧
	 * 第一个字节为0x40即64时表示为地磁状态改变帧
	 * 第一个字节为0x12即18时表示为地磁状态改变帧
	 * @param kvEntries
	 * @param data
	 */
	public static void parseDiCiDeviceData(List<KvEntry> kvEntries,byte[] data){
		if (data == null || data.length == 0)
			return;
		// 右移7位取到最高位数值
		int isUsed = getData(data, 1, 1) >> 7;
		int type = getData(data, 0, 1) >> 4;
		if (type == CONFIG_TYPE) {
			log.warn("upload data type is configuration not support.");
			return;
		}
		KvEntry kvEntry = null;
		if (isUsed == 1) {
			kvEntry = new StringDataEntry(ISAVAILABLE_KEY, "false");
		}else {
			kvEntry = new StringDataEntry(ISAVAILABLE_KEY, "true");
		}
		kvEntries.add(kvEntry);
		if (type == WATCH_TYPE || type == STATUS_TYPE) {
			int temp = getData(data, 2, 2);
			if (temp != 0) {
				double actul = temp / 100;
				kvEntry = new DoubleDataEntry(TEMPERTURE_KEY, actul);
				kvEntries.add(kvEntry);
			}
		}
	}
	
	/**
	 * 解析烟感数据
	 * @param kvEntries
	 * @param data
	 */
	public static void parseYanGanDeviceData(List<KvEntry> kvEntries,byte[] data){
		if (data == null || data.length == 0)
			return;
		int status = getData(data, 0, 1);
		KvEntry kvEntry = null;
		if (status == 0) {
			kvEntry = new StringDataEntry(ALARM_STATUS_KEY, "false");
		}else {
			kvEntry = new StringDataEntry(ALARM_STATUS_KEY, "true");
		}
		kvEntries.add(kvEntry);
		int voltage = getData(data, 1, 1);
		if (voltage != 0) {
			double actul = voltage / 10;
			kvEntry = new DoubleDataEntry(VOLTAGE_KEY, actul);
			kvEntries.add(kvEntry);
		}
	}
	
	/**
	 * 指定索引获取整型数据
	 * @param data
	 * @param index
	 * @param step
	 * @return
	 */
	public static int getData(byte[] data,int index,int step){
		if (data == null || (index+step) > data.length)
			return 0;
		String highStr;
		String lowStr;
		int high = 0;
		int low = 0;
		highStr = Integer.toHexString(data[index] & 0xFF);
		if (highStr.length() == 1) {
			highStr = '0' + highStr;
		}
		high = Integer.parseInt(highStr, 16);
		if (step > 1) {
			lowStr = Integer.toHexString(data[index+step-1] & 0xFF);
			if (lowStr.length() == 1) {
				lowStr = '0' + lowStr;
			}
			low = Integer.parseInt(lowStr,16);
			high = high << 8;
		}
		return (high + low);
	}
	
	public static byte[] decode(String str){
		return Base64.getDecoder().decode(str);
	}
	
	public static long parseTimeData(String timeStr){  
        if(!timeStr.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{6}Z")){  
            return 0L;  
        }  
        timeStr = timeStr.replaceFirst("T", " ").replaceFirst(".\\d{6}Z", "");  
        SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        try {  
        	Date date = sdf.parse(timeStr);  
            Calendar ca=Calendar.getInstance();  
            ca.setTime(date);  
            ca.add(Calendar.HOUR_OF_DAY, 8);  
            return ca.getTime().getTime();
        } catch (ParseException e) {  
        	log.error("parse timeStr [{}] failed,cause [{}].",timeStr,e.getCause()); 
            return 0L;  
        } 
    }
	
}
