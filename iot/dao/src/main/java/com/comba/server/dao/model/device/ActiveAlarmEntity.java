package com.comba.server.dao.model.device;

import javax.persistence.*;

import com.comba.server.common.data.device.ActiveAlarm;
import com.comba.server.dao.model.ToData;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.util.Date;

/**
 * 活动告警实体Bean
 *
 * @作者 sujinxian
 * @创建时间 2018-03-01 11:24:16
 */
@Data
@Entity
@Table(name="active_alarm")
public class ActiveAlarmEntity implements ToData<ActiveAlarm> {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name="ID")
    private String id;//
    
    @Column(name="TENANT_ID")
    private String tenantId;//租户id
    
    @Column(name="PRODUCT_ID")
    private String productId;//产品Id 
    
    @Column(name="DEV_ID")
    private String devId;//设备id
    
    @Column(name="ALARM_ID")
    private String alarmId;//告警id
    
    @Column(name="ALARM_CONTENT")
    private String alarmContent;//告警内容
    
    @Column(name="START_TIME")
    private Date startTime;//告警时间

    @JoinColumn(name="DEV_ID",insertable=false,nullable=true,updatable=false)
    @OneToOne
    @NotFound(action= NotFoundAction.IGNORE)
    private DeviceEntity  deviceEntity;


    @JoinColumn(name="ALARM_ID",insertable=false,nullable=true,updatable=false)
    @OneToOne
    @NotFound(action= NotFoundAction.IGNORE)
    private AlarmContentEntity  alarmContentEntity;
    

    public ActiveAlarmEntity() {
    }

    public ActiveAlarmEntity (String id, String tenantId, String productId, String devId, String alarmId, String alarmContent, Date startTime) {
        this.id = id;
        this.tenantId = tenantId;
        this.productId = productId;
        this.devId = devId;
        this.alarmId = alarmId;
        this.alarmContent = alarmContent;
        this.startTime = startTime;
    }

   @Override
   public ActiveAlarm toData() {
        String devName = "";
        String alarmName ="";
        if (deviceEntity != null){
            devName = deviceEntity.getName();
        }

        if (alarmContentEntity != null){
            alarmName = alarmContentEntity.getAlarmName();
        }
	   return new ActiveAlarm( id, tenantId, productId, devId, alarmId, alarmContent, startTime,devName,alarmName);
   }

    public ActiveAlarmEntity (ActiveAlarm t) {
		super();
		if(t!=null){
			this.id = t.getId();
			this.tenantId = t.getTenantId();
			this.productId = t.getProductId();
			this.devId = t.getDevId();
			this.alarmId = t.getAlarmId();
			this.alarmContent = t.getAlarmContent();
			this.startTime = t.getStartTime();
		}
    }
}
