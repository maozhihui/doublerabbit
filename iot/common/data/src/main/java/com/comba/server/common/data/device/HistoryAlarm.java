package com.comba.server.common.data.device;




import lombok.Data;

import java.util.Date;

/**
 * 历史告警业务实体类
 *
 * @作者 sujinxian
 * @创建时间 2018-03-05 09:18:44
 */
@Data
public class HistoryAlarm implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
     
    private String id;//
    
     
    private String tenantId;//租户id
    
     
    private String productId;//产品Id 
    
     
    private String devId;//设备id

    private String devName; //设备名称
    
     
    private String alarmId;//告警id
    
     
    private String alarmContent;//告警内容
    
     
    private Date startTime;//告警时间
    
     
    private Date confirmTime;//告警恢复时间

    private String alarmName; //告警名称
    

    public HistoryAlarm() {
    }

    public HistoryAlarm(ActiveAlarm alarm) {
        this.productId = alarm.getProductId();
        this.devId = alarm.getDevId();
        this.alarmId = alarm.getAlarmId();
        this.alarmContent = alarm.getAlarmContent();
        this.startTime = alarm.getStartTime();
        this.confirmTime = new Date();
    }

    public HistoryAlarm(String id, String tenantId, String productId, String devId, String alarmId, String alarmContent, Date startTime, Date confirmTime,String devName,String alarmName) {
        this.id = id;
        this.tenantId = tenantId;
        this.productId = productId;
        this.devId = devId;
        this.alarmId = alarmId;
        this.alarmContent = alarmContent;
        this.startTime = startTime;
        this.confirmTime = confirmTime;
        this.devName = devName;
        this.alarmName = alarmName;
    }

}
