package com.comba.server.extensions.core.action;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author maozhihui
 * @Description:
 * @create 2017/12/1 16:53
 **/
@Data
public class DeviceCtrlActionConfiguration {

    List<CtrlEntity> actions = new ArrayList<>();

    public void add(CtrlEntity ctrlEntity){
        actions.add(ctrlEntity);
    }

    @Data
    @AllArgsConstructor
    public static class CtrlEntity{
        private String type;
        private String dst;
        private Map<String,String> data;
    }
}
