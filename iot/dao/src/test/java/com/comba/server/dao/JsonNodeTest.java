package com.comba.server.dao;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonNodeTest {

	@Test
	public void testJSONByString() throws Exception{
		/*String str = new String("\[\{
      \"clazz\"\: \"com.comba.server.extensions.core.filter.MsgTypeFilter\",
      \"name\": \"TelemetryFilter\",
      \"configuration\": \{
        \"messageTypes\": \[
          \"POST_TELEMETRY\",
          \"POST_ATTRIBUTES\",
          \"GET_ATTRIBUTES\"
        \]
      \}
    \}
  \]");
*/  	ObjectMapper mapper = new ObjectMapper();
		JsonNode obj = mapper.readTree("[{\"clazz\": \"org.thingsboard.server.extensions.core.filter.MsgTypeFilter\",\"name\": \"TelemetryFilter\",\"configuration\": {\"messageTypes\": [\"POST_TELEMETRY\",\"POST_ATTRIBUTES\",\"GET_ATTRIBUTES\"]}}]");
		if (obj.isArray()) {
			System.out.println("yes");
		}
	}
}
