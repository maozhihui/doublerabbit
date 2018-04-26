package com.comba.server.common.data.rest;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author maozhihui
 * @Description:设置命令实体
 * @create 2017/12/20 13:41
 **/
@Data
public class SetCmdRest {

    private String name;
    private List<Attribute> content;

    @Data
    public static class Attribute{
        private String did;
        private Map<String,String> data;
    }
}
