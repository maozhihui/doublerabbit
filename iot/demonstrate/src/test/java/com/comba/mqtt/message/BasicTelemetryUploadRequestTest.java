package com.comba.mqtt.message;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.comba.mqtt.data.DoubleDataEntry;
import com.comba.mqtt.data.KvEntry;
import com.comba.mqtt.data.StringDataEntry;
import com.google.gson.Gson;

public class BasicTelemetryUploadRequestTest {

	private static Gson GSON = new Gson(); 
	
	@Test
	public void test() {
		String devId = "ffffff1000000132";
		System.out.println(devId.substring(6, devId.length()));
	}

	/*@Test
	public void testJson(){
		BasicTelemetryUploadRequest request = new BasicTelemetryUploadRequest("ffffff100000013b", 4);
		KvEntry kvEntry = new StringDataEntry("temp", "hello");
		KvEntry kvEntry2 = new DoubleDataEntry("hump", 54.0);
		List<KvEntry> list = new ArrayList<KvEntry>();
		list.add(kvEntry);
		list.add(kvEntry2);
		request.addData(System.currentTimeMillis(), list);
		String jsonStr = GSON.toJson(request);
		System.out.println(jsonStr);
	}*/
}
