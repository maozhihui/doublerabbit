package com.comba.server.dao.model.device;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.*;

import com.comba.server.common.data.device.AttributesTemplate;
import com.comba.server.dao.util.Constant;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.common.util.StringHelper;

import com.comba.server.common.data.User;
import com.comba.server.common.data.device.ConfigAttributes;
import com.comba.server.dao.model.ToData;


/**
 * 设备配置属性
 * @author wengzhonghui
 *
 */
@Data
@Entity
@Table(name="config_attributes")
public class ConfigAttributesEntity implements ToData<ConfigAttributes>{

	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")   
	@Column(name="ID")
	private String configAttributeId;
	
	@Column(name="DEV_ID")
	private String devId;
	
	@Column(name="ATTRIBUTE_SCOPE")
	private String attributeScope;//属性类别，client,shared,server
	
	@Column(name="ATTRIBUTE_TEMPLATE_ID")
	private String attributeId;
	
	@Column(name="VALUE_TYPE")
	private String valueType;
	
	@Column(name="READ_ONLY")
	private Integer readOnly;//读与写权限，0-读，1-写，2-读写
	
	@Column(name="IS_OPTIONAL")
	private Integer isOptional;//是否可选
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="value")
	private String value;
	
	@Column(name="CREATE_TIME")
	private Date createTime;
	
	@Column(name="UPDATE_TIME")
	private Date updateTime;

    @JoinColumn(name="ATTRIBUTE_TEMPLATE_ID",insertable=false,nullable=true,updatable=false)
    @OneToOne
    @NotFound(action= NotFoundAction.IGNORE)
    private AttributesTemplateEntity attributesTemplate;

	@Override
	public ConfigAttributes toData() {
	    String attributeName = null;

	    if (attributesTemplate != null){
	        attributeName = attributesTemplate.getName();
        }
		return new ConfigAttributes(configAttributeId, devId, attributeScope, attributeName, valueType, readOnly, isOptional, description, value, createTime, updateTime,attributeId);
	}

	public ConfigAttributesEntity(ConfigAttributes t) {
		super();
		this.configAttributeId = t.getConfigAttributeId();
		this.devId = t.getDevId();
		this.attributeScope = t.getAttributeScope();
		this.attributeId = t.getAttributeId();
		this.valueType = t.getValueType();
		this.readOnly = t.getReadOnly();
		this.isOptional = t.getIsOptional();
		this.description = t.getDescription();
		this.value = t.getValue();
		this.createTime = t.getCreateTime();
		this.updateTime = t.getUpdateTime();
	}

	public ConfigAttributesEntity(AttributesTemplate t,String devId){
		super();
		this.devId = devId;
		this.attributeId = t.getAttributesTemplateId();
		this.valueType = t.getValueType();
		this.readOnly = t.getReadOnly();
		this.isOptional = t.getIsOptional();
		this.description = t.getDescription();
		this.createTime = t.getCreateTime();
		this.updateTime = t.getUpdateTime();
		this.attributeScope = Constant.ATTRIBUTE_SCOPE_TYPE_TEMPLATE;
		this.value = t.getValue();
	}

	public ConfigAttributesEntity() {
		super();
	}

	
	
}