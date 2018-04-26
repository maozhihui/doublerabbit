package com.comba.server.common.data.device;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * RuleTest
 *
 * @author maozhihui
 * @create 2017-10-12 14:51
 **/
@RunWith(PowerMockRunner.class)
public class RuleTest {

    @Test
    public void testGetJsonFilters(){
        String filters = "[{\"configuration\":{\"productId\":\"ff8080815ea88f17015ea8a9ab960035\",\"deviceId\":\"ff8080815ea88f17015ea8ab5d13003d\",\"deviceName\":\"aa\"},\"name\":\"MyDeviceFilter\",\"clazz\":\"com.comba.server.extensions.core.filter.MyDeviceFilter\"},{\"configuration\":{\"attributeId\":\"ff8080815ea88f17015ea8ab5d16003f\",\"attributeName\":\"aa\",\"value\":\"g\",\"operator\":\">\"},\"name\":\"MyDeviceFilter\",\"clazz\":\"com.comba.server.extensions.core.filter.MyDeviceTelemetryFilter\"}]";
        Rule rule = new Rule();
        JsonNode jsonNode = rule.getJsonFilters(filters);
        System.out.println(jsonNode);
    }
}
