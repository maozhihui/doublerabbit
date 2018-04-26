package com.comba.server.common.data.rest;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author maozhihui
 * @Description:DatapointRest 实体类
 * @create 2017/11/27 16:24
 **/
@Data
public class DatapointRest {
    private String did;
    private List<Datapoint> datapoints;
    public DatapointRest(String did){
        this.did = did;
        datapoints = new ArrayList<>();
    }

    @Data
    @AllArgsConstructor
    public static class Datapoint{
        // 属性名称
        private String name;
        // 属性值
        private String value;
        // 上报时间
        private String ts;
    }
}
