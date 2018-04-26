package com.comba.server.common.data.rest;

import lombok.Data;

/**
 * @author maozhihui
 * @Description:
 * @create 2018/1/2 11:21
 **/
@Data
public class DataForwardRest {

    private static final String ONLINE_EVENT = "online";
    private static final String OFFLINE_EVENT = "offline";
    private static final String DATA_EVENT = "data";
    private static final String MS_TYPE = "ms";
    private static final String URL_TYPE = "url";

    private String id;
    private String event;
    private String name;
    private String type;
    private String dst;
}
