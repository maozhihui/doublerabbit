package com.comba.server.common.data.device;




import lombok.Data;

import java.util.Date;

/**
 * 活动告警业务实体类
 *
 * @作者 sujinxian
 * @创建时间 2018-03-01 11:24:16
 */
@Data
public class ActiveAlarm implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
     
    private String id;//
    
     
    private String tenantId;//租户id
    
     
    private String productId;//产品Id 
    
     
    private String devId;//设备id
    
     
    private String alarmId;//告警id
    
     
    private String alarmContent;//告警内容
    
     
    private Date startTime;//告警时间

    private String devName;//设备名称

    private String alarmName;//告警名称
    

    public ActiveAlarm() {
    }


    public ActiveAlarm(String tenantId,String did,AlarmRule rule,String productId){
        this.tenantId = tenantId;
        this.devId = did;
        this.alarmContent = rule.getAlarmContent();
        this.alarmId = rule.getAlarmId();
        this.productId = productId;
        this.startTime = new Date();
    }

    public ActiveAlarm(String did,String productId,String alarmId, String alarmContent,Date startTime){
        this.productId = productId;
        this.devId = did;
        this.alarmId = alarmId;
        this.alarmContent = alarmContent;
        this.startTime = startTime;
    }

    public ActiveAlarm(String id, String tenantId, String productId, String devId, String alarmId, String alarmContent, Date startTime,String devName,String alarmName) {
        this.id = id;
        this.tenantId = tenantId;
        this.productId = productId;
        this.devId = devId;
        this.alarmId = alarmId;
        this.alarmContent = alarmContent;
        this.startTime = startTime;
        this.devName = devName;
        this.alarmName = alarmName;
    }
}
