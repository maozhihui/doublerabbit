package com.comba.server.common.data.device;




import lombok.Data;

import java.util.Date;

/**
 * 告警规则业务实体类
 *
 * @作者 sujinxian
 * @创建时间 2018-02-28 15:55:17
 */
@Data
public class AlarmRule implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
     
    private String id;//
    
     
    private String tenantId;//租户id
    
     
    private String productId;//产品Id 
    
     
    private String name;//规则名称
    
     
    private String attribute;//属性
    
     
    private String ruleCondition;//条件
    
     
    private String value;//对比值
    
     
    private Integer type;//类型，1，告警通知，2，告警恢复
    
     
    private String alarmId;//告警id

    private String alarmName;//告警名称


    private String alarmContent;//告警内容

    private Integer alarmLevel;//告警等级

     
    private Date createTime;//创建时间
    
     
    private Date updateTime;//更新时间
    

    public AlarmRule() {
    }

    public AlarmRule(String id, String tenantId, String productId, String name, String attribute, String ruleCondition, String value, Integer type, String alarmId, String alarmContent, Date createTime, Date updateTime,String alarmName,Integer alarmLevel) {
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
        this.alarmName = alarmName;
        this.alarmLevel = alarmLevel;
    }
}
