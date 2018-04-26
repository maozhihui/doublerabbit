package com.comba.alarm.mq;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 告警mq信息实体
 *
 */
@Data
public class MqMessage {

    private String did;

    private String event;

    private List<Alarm> data;

    public MqMessage(String did,String name,Integer notifyType,String cause,Integer level,Date time,String event){
        this.did = did;
        this.event = event;
        this.data = Lists.newArrayList(new Alarm(name, notifyType, cause, level, time));
    }


    @Data
    @AllArgsConstructor
    private class Alarm{
        private String name;
        private Integer notifyType;
        private String cause;
        private Integer level;
        private Date time;
    }

}
