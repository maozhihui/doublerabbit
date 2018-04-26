/**
 * 
 */
package com.comba.server.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import org.junit.Test;

/**
 * @author xianhongdong
 *
 */
public class JsonTest {

	private static Gson GSON = new Gson();
	private static final ObjectMapper mapper = new ObjectMapper();

	public static void main(String[] args) {
		ObjectMapper mapper = new ObjectMapper(); 
		ObjectNode filter = mapper.createObjectNode ();
		filter.put("clazz", "com.comba.server.extensions.core.filter.MsgTypeFilter");
		filter.put("name", "TelemetryFilter");
		
		//ObjectNode msgTypeArray = mapper.createObjectNode ();
		ArrayNode msgTypeArray = mapper.createArrayNode ();
		ObjectNode configNode = mapper.createObjectNode ();
		msgTypeArray.add("POST_TELEMETRY");
		msgTypeArray.add("POST_ATTRIBUTES");
		msgTypeArray.add("GET_ATTRIBUTES");
		
		configNode.put("messageTypes",msgTypeArray);
		filter.put("configuration", configNode);
		
		ArrayNode filtersNode = mapper.createArrayNode ();
		filtersNode.add(filter);
		
		System.out.println(filtersNode.toString());
		
		List<String> strList = new ArrayList<String>();
		strList.add("1");
		strList.add("2");
		strList.add("3");
		int c = 0;
		strList.forEach(item->{System.out.println(item);});

		System.out.println(UUID.randomUUID().toString());
	}

	@Test
	public void testMapperRead() throws Exception{
		String str = "{\"filter\":\"temperature>20 && temperature<38\"}";
		ObjectMapper mapper = new ObjectMapper();
		Class clazz = Class.forName("com.comba.server.extensions.core.filter.JsFilterConfiguration");
		mapper.readValue(str,clazz);
	}
}
