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
public class DataParser {

	private static final Gson GSON = new Gson();
	private static final String TYPE_KEY = "type";
	private static final String DATA_KEY = "data";
	
	public static List<KvEntry> convertToKvEntry(String data) {
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
			KvEntry kvEntry = new StringDataEntry(DATA_KEY, dataElement.getAsString());
			//System.out.println("kventity=" + kvEntry);
			log.debug("kventity [{}]",kvEntry);
			res.add(kvEntry);
		}
		 
		return res;
	}
	
	public static byte[] decode(String str){
		return Base64.getDecoder().decode(str);
	}
	
	
}
