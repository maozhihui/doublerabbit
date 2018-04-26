package com.comba.server.transport.mqtt.sub.service;

import com.comba.server.common.data.kv.KvEntry;
import com.comba.server.transport.mqtt.sub.message.DataParser;
import com.comba.server.transport.mqtt.sub.message.TelemetryUploadRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author maozhihui
 * @Description:
 * @create 2017/12/27 19:56
 **/
@Slf4j
public class MqttMsgHandler {

    private final Map<String,DataParser> parsers;

    public MqttMsgHandler(Map<String,DataParser> parsers){
        this.parsers = parsers;
    }

    public Map<Long,List<KvEntry>> parseData(TelemetryUploadRequest request){
        Map<Long,List<KvEntry>> map = new HashMap<>();
        if (StringUtils.isBlank(request.getAppName())){
            log.warn("Request does not have applicationName [{}]",request);
            return map;
        }
        DataParser dataParser = parsers.get(request.getAppName());
        if (dataParser == null){
            log.warn("applicationName [{}] does not have parser",request.getAppName());
            return map;
        }
        List<KvEntry> parseDataList = dataParser.parserData(request.getDeviceType(),request.getData());
        if (parseDataList.isEmpty())
            return map;
        map.put(request.getUpdateTime(),dataParser.parserData(request.getDeviceType(),request.getData()));
        return map;
    }
}
