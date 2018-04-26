package com.comba.server.dao.model.device;

import com.comba.server.common.data.device.AlarmRule;
import com.comba.server.dao.model.ToData;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

/**
 * 告警规则实体Bean
 *
 * @作者 sujinxian
 * @创建时间 2018-02-28 15:55:17
 */
@Data
@Entity
@Table(name="alarm_rule")
public class AlarmRuleEntity implements ToData<AlarmRule> {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name="ID")
    private String id;//
    
    @Column(name="TENANT_ID")
    private String tenantId;//租户id
    
    @Column(name="PRODUCT_ID")
    private String productId;//产品Id 
    
    @Column(name="NAME")
    private String name;//规则名称
    
    @Column(name="ATTRIBUTE")
    private String attribute;//属性
    
    @Column(name="RULE_CONDITION")
    private String ruleCondition;//条件
    
    @Column(name="VALUE")
    private String value;//对比值
    
    @Column(name="TYPE")
    private Integer type;//类型，1，告警通知，2，告警恢复


    @Column(name="ALARM_ID")
    private String alarmId;//告警id
    
    @Column(name="ALARM_CONTENT")
    private String alarmContent;//告警内容
    
    @Column(name="CREATE_TIME")
    private Date createTime;//创建时间
    
    @Column(name="UPDATE_TIME")
    private Date updateTime;//更新时间

    @JoinColumn(name="ALARM_ID",insertable=false,nullable=true,updatable=false)
    @OneToOne
    @NotFound(action= NotFoundAction.IGNORE)
    private AlarmContentEntity  alarmContentEntity;

    public AlarmRuleEntity() {
    }

    public AlarmRuleEntity (String id, String tenantId, String productId, String name, String attribute, String ruleCondition, String value, Integer type, String alarmId, String alarmContent, Date createTime, Date updateTime) {
        this.id = id;
        this.tenantId = tenantId;
        this.productId = productId;
        this.name = name;
        this.attribute = attribute;
        this.ruleCondition = ruleCondition;
        this.value = value;
        this.type = type;
        this.alarmId = alarmId;
        this.alarmContent = alarmContent;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
   
   @Override
   public AlarmRule toData() {
        String alarmName = "";
        Integer level = 0;
        if (alarmContentEntity != null){
            alarmName = alarmContentEntity.getAlarmName();
            level = alarmContentEntity.getAlarmLevel();
        }
	   return new AlarmRule( id, tenantId, productId, name, attribute, ruleCondition, value, type, alarmId, alarmContent, createTime, updateTime,alarmName,level);
   }

    public AlarmRuleEntity (AlarmRule t) {
		super();
		if(t!=null){
			this.id = t.getId();
			this.tenantId = t.getTenantId();
			this.productId = t.getProductId();
			this.name = t.getName();
			this.attribute = t.getAttribute();
			this.ruleCondition = t.getRuleCondition();
			this.value = t.getValue();
			this.type = t.getType();
			this.alarmId = t.getAlarmId();
			this.alarmContent = t.getAlarmContent();
			this.createTime = t.getCreateTime();
			this.updateTime = t.getUpdateTime();
		}
    }
}
