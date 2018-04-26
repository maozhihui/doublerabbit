package com.comba.server.common.msg.core;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author maozhihui
 * @Description:
 * @create 2017/12/1 20:37
 **/
@Data
@AllArgsConstructor
public class BasicCmdMsg implements Serializable{

    // sms/ms/url/devctrl
    private String type;
    private String dst;
    private Map<String,String> data;
}
