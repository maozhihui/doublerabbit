package com.comba.server.common.data.rule;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author maozhihui
 * @Description:
 * @create 2018/1/3 14:09
 **/
@Data
public class TelemetryData {

    private final String did;
    private final String event;
    private List<DataPoint> data;

    public TelemetryData(String did){
        this.did = did;
        this.event = "data";
        data = new ArrayList<>();
    }

    @Data
    @AllArgsConstructor
    public static class DataPoint{
        private String name;
        private String value;
        private long ts;
    }
}
