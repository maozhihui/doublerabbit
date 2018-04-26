package com.comba.mqtt.message.comba;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.comba.mqtt.data.KvEntry;
import com.comba.mqtt.data.StringDataEntry;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.extern.slf4j.Slf4j;

/**
 * comba mqtt数据解析器
 * @author maozhihui
 * @date 2017年11月13日 上午9:16:47
 */
@Slf4j
public class CombaDataParser implements AbstractDataParser{

	private static final Gson GSON = new Gson();
	private static final String TYPE_KEY = "type";
	private static final String DATA_KEY = "data";
	private static final String AMMETER_TYPE = "ammeter";
	private static final String SWITCH_TYPE = "switch";
	private static final String WATERMETER_TYPE = "watermeter";
	private static final String AMMETER_ATTR = "electricity";
	private static final String SWITCH_ATTR = "switch";
	private static final String WATERMETER_ATTR = "volume";
	
	
	@Override
	public List<KvEntry> convertToKvEntry(int devType,String data) {
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
		//System.out.println("data str=" + new String(bytes));
		JsonElement element = new JsonParser().parse(new String(bytes));
		if (element.isJsonObject()) {
			JsonObject object = element.getAsJsonObject();
			JsonElement dataElement = object.get(DATA_KEY);
			if (object.get(TYPE_KEY).getAsString().equals(SWITCH_TYPE)) {
				KvEntry kvEntry = new StringDataEntry(SWITCH_ATTR, dataElement.getAsString());
				log.info("kventity [{}]",kvEntry);
				res.add(kvEntry);
			}else if (object.get(TYPE_KEY).getAsString().equals(AMMETER_TYPE)) {
				KvEntry kvEntry = new StringDataEntry(AMMETER_ATTR, dataElement.getAsString());
				log.info("kventity [{}]",kvEntry);
				res.add(kvEntry);
			}else if (object.get(TYPE_KEY).getAsString().equals(WATERMETER_TYPE)) {
				KvEntry kvEntry = new StringDataEntry(WATERMETER_ATTR, dataElement.getAsString());
				log.info("kventity [{}]",kvEntry);
				res.add(kvEntry);
			}
		}
		 
		return res;
	}
	
	public static byte[] decode(String str){
		return Base64.getDecoder().decode(str);
	}
	
	
}
