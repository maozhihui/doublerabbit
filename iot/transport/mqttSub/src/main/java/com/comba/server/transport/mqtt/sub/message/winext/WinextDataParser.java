package com.comba.server.transport.mqtt.sub.message.winext;

import static com.comba.server.transport.mqtt.sub.message.winext.Constants.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.comba.server.common.data.kv.KvEntry;
import com.comba.server.common.data.kv.StringDataEntry;
import com.comba.server.transport.mqtt.sub.message.DataParser;
import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 唯传数据解析器
 * @author maozhihui
 * @date 2017年11月10日 下午9:03:04
 */
@Slf4j
public class WinextDataParser implements DataParser{

	public static final String ALARM_STATUS_KEY = "alarmStatus";
	public static final String VOLTAGE_KEY = "voltage";
	public static final String ISAVAILABLE_KEY = "isAvailable";
	public static final String TEMPERTURE_KEY = "temperature";
	public static final String HUMIDITY_KEY = "humidity";
	public static final String OPENSTATE_KEY = "openState";
	private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.0");
	
	public List<KvEntry> parserData(int deviceType, String data) {
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
			case TEMPERATURE_HUMIDITY_TYPE:
				parseWenShiDeviceData(res, bytes);
				break; 
			case GEOMAGNETIC_TYPE:
				parseDiCiDeviceData(res, bytes);
				break;
			case SMOKE01_TYPE:
			case SMOKE02_TYPE:
				parseYanGanDeviceData(res, bytes);
				break;
			case MANHOLE_COVER_TYPE:
				parseManholeCoverData(res,bytes);
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
	private void parseWenShiDeviceData(List<KvEntry> kvEntries,byte[] data){
		if (data == null || data.length == 0)
			return;
		int temp = getData(data, 0, 2);
		if (temp != 0) {
			double actul = (double) temp / 100;
			KvEntry kvEntry = new StringDataEntry(TEMPERTURE_KEY, DECIMAL_FORMAT.format(actul));
			kvEntries.add(kvEntry);
		}
		int hum = getData(data, 2, 2);
		if (hum != 0) {
			double actul = (double) hum / 100;
			KvEntry kvEntry = new StringDataEntry(HUMIDITY_KEY, DECIMAL_FORMAT.format(actul));
			kvEntries.add(kvEntry);
		}
	}
	
	/**
	 * 解析地磁数据
	 * @param kvEntries
	 * @param data
	 */
	private void parseDiCiDeviceData(List<KvEntry> kvEntries,byte[] data){
		if (data == null || data.length == 0)
			return;
		int packVersion = getData(data,0,1) >> 4;
		switch (packVersion){
			case PACK_DICI_HEARTBEAT:
			case PACK_DICI_STATUS:
				kvEntries.add(convertIsAvailableEntry(ISAVAILABLE_KEY,getData(data,1,1)));
				kvEntries.add(convertVoltageEntry(VOLTAGE_KEY,getData(data,1,1)&(0x7F)));
				kvEntries.add(convertTemperatureEntry(TEMPERTURE_KEY,getData(data,2,2)));
				break;
			case PACK_DICI_CONFIG:
				break;
			case PACK_DICI_STATUS_CHANGED:
				kvEntries.add(convertIsAvailableEntry(ISAVAILABLE_KEY,getData(data,1,1)));
				kvEntries.add(convertVoltageEntry(VOLTAGE_KEY,getData(data,1,1)&(0x7F)));
				break;
			default:
				log.warn("package version [{}] not support.",packVersion);
		}

	}
	
	/**
	 * 解析烟感数据
	 * @param kvEntries
	 * @param data
	 */
	private void parseYanGanDeviceData(List<KvEntry> kvEntries,byte[] data){
		if (data == null || data.length == 0)
			return;
		int status = getData(data, 0, 1);
		KvEntry kvEntry;
		if (status == 0) {
			kvEntry = new StringDataEntry(ALARM_STATUS_KEY, "false");
		}else {
			kvEntry = new StringDataEntry(ALARM_STATUS_KEY, "true");
		}
		kvEntries.add(kvEntry);
		int voltage;
		voltage = getData(data,1,1);
		if (voltage != 0) {
			double actul = (double) voltage / 10;
			kvEntry = new StringDataEntry(VOLTAGE_KEY, DECIMAL_FORMAT.format(actul));
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
	private int getData(byte[] data,int index,int step){
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
	
	private byte[] decode(String str){
		return Base64.getDecoder().decode(str);
	}

	private KvEntry convertIsAvailableEntry(String key,int val){
		// 当最高位为1时表示有车状态
		String isAvailable = "true";
		if ((val >> 7) == 1){
			isAvailable = "false";
		}
		return new StringDataEntry(key,isAvailable);
	}

	private KvEntry convertVoltageEntry(String key,int val){
		double actul = (double) val / 10;
		return new StringDataEntry(key,DECIMAL_FORMAT.format(actul));
	}

	private KvEntry convertTemperatureEntry(String key,int val){
		double actul = (double) val / 100;
		return new StringDataEntry(key,DECIMAL_FORMAT.format(actul));
	}

	/**
	 * 解析井盖上报数据
	 * @param kvEntries
	 * @param data
	 */
	private void parseManholeCoverData(List<KvEntry> kvEntries,byte[] data){
		if (data == null || data.length == 0)
			return;
		int state = getData(data,0,1);
		KvEntry kvEntry = new StringDataEntry(OPENSTATE_KEY,String.valueOf(state));
		kvEntries.add(kvEntry);
		int voltage;
		voltage = getData(data,1,1) & (0x7F);
		if (voltage != 0) {
			double actul = (double) voltage / 10;
			kvEntry = new StringDataEntry(VOLTAGE_KEY, DECIMAL_FORMAT.format(actul));
			kvEntries.add(kvEntry);
		}
	}
}
