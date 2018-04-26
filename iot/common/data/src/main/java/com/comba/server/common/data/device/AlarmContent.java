package com.comba.server.common.data.device;




import lombok.Data;

import java.util.Date;

/**
 * 告警信息业务实体类
 *
 * @作者 sujinxian
 * @创建时间 2018-02-26 16:33:41
 */
@Data
public class AlarmContent implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
     
    private String id;//
    
     
    private String alarmId;//告警id
    
     
    private String alarmName;//告警名称
    
     
    private String alarmContent;//告警内容
    
     
    private Integer alarmLevel;//告警等级
    
     
    private Date createTime;//创建时间
    

    public AlarmContent() {
    }

    public AlarmContent(String id, String alarmId, String alarmName, String alarmContent, Integer alarmLevel, Date createTime) {
        this.id = id;
        this.alarmId = alarmId;
        this.alarmName = alarmName;
        this.alarmContent = alarmContent;
        this.alarmLevel = alarmLevel;
        this.createTime = createTime;
    }

}
