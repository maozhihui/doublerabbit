package com.comba.server.dao.model.device;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.comba.server.common.data.device.AlarmContent;
import com.comba.server.dao.model.ToData;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;


import java.util.Date;

/**
 * 告警信息实体Bean
 *
 * @作者 sujinxian
 * @创建时间 2018-02-26 16:33:41
 */
@Data
@Entity
@Table(name="alarm_content")
public class AlarmContentEntity implements ToData<AlarmContent> {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name="ID")
    private String id;//
    
    @Column(name="ALARM_ID")
    private String alarmId;//告警id
    
    @Column(name="ALARM_NAME")
    private String alarmName;//告警名称
    
    @Column(name="ALARM_CONTENT")
    private String alarmContent;//告警内容
    
    @Column(name="ALARM_LEVEL")
    private Integer alarmLevel;//告警等级
    
    @Column(name="CREATE_TIME")
    private Date createTime;//告警时间
    

    public AlarmContentEntity() {
    }

    public AlarmContentEntity (String id, String alarmId, String alarmName, String alarmContent, Integer alarmLevel, Date createTime) {
        this.id = id;
        this.alarmId = alarmId;
        this.alarmName = alarmName;
        this.alarmContent = alarmContent;
        this.alarmLevel = alarmLevel;
        this.createTime = createTime;
    }
   
   @Override
   public AlarmContent toData() {
	   return new AlarmContent( id, alarmId, alarmName, alarmContent, alarmLevel, createTime);
   }

    public AlarmContentEntity (AlarmContent t) {
		super();
		if(t!=null){
			this.id = t.getId();
			this.alarmId = t.getAlarmId();
			this.alarmName = t.getAlarmName();
			this.alarmContent = t.getAlarmContent();
			this.alarmLevel = t.getAlarmLevel();
			this.createTime = t.getCreateTime();
		}
    }
}
