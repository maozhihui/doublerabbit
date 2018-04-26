package com.comba.mqtt.message.winext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.BitSet;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.comba.mqtt.data.DoubleDataEntry;
import com.comba.mqtt.data.KvEntry;
import com.comba.mqtt.data.StringDataEntry;

import lombok.extern.slf4j.Slf4j;

/**
 * 唯传数据解析器
 * @author maozhihui
 * @date 2017年11月10日 下午9:03:04
 */
@Slf4j
public class DataParser {

	public static final String ALARM_STATUS_KEY = "alarmStatus";
	public static final String VOLTAGE_KEY = "voltage";
	public static final String ISAVAILABLE_KEY = "isAvailable";
	public static final String TEMPERTURE_KEY = "temperture";
	public static final String HUMIDITY_KEY = "humidity";
	
	public static List<KvEntry> convertToKvEntry(int deviceType, String data) {
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
	 * @param kvEntries
	 * @param data
	 */
	public static void parseDiCiDeviceData(List<KvEntry> kvEntries,byte[] data){
		if (data == null || data.length == 0)
			return;
		BitSet bitSet = new BitSet(getData(data, 1, 1));
		KvEntry kvEntry = null;
		if (bitSet.get(0)) {
			kvEntry = new StringDataEntry(ISAVAILABLE_KEY, "false");
		}else {
			kvEntry = new StringDataEntry(ISAVAILABLE_KEY, "true");
		}
		kvEntries.add(kvEntry);
		int temp = getData(data, 2, 2);
		if (temp != 0) {
			double actul = temp / 100;
			kvEntry = new DoubleDataEntry(TEMPERTURE_KEY, actul);
			kvEntries.add(kvEntry);
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
		long res = 0L;
		if (StringUtils.isBlank(timeStr) || (!timeStr.contains("T"))) {
			log.warn("timeStr [{}] is invalid.",timeStr);
			return res;
		}
		timeStr = timeStr.replace("Z", " UTC");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
		try {
			Date date = format.parse(timeStr);
			res = date.getTime();
		} catch (ParseException e) {
			log.error("parse timeStr [{}] failed,cause [{}].",timeStr,e.getCause());
		}
		return res;
	}
}
