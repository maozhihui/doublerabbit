package com.comba.mqtt.message.comba;

import java.util.List;

import com.comba.mqtt.data.KvEntry;

public interface AbstractDataParser {

	List<KvEntry> convertToKvEntry(int devType,String data);
	
}
