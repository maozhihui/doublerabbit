package com.comba.server.dao.model.device;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.comba.server.common.data.device.AttributesTemplate;
import com.comba.server.dao.model.ToData;
import org.hibernate.internal.util.StringHelper;


/**
 * 参数模板
 * @author wengzhonghui
 *
 */
@Entity
@Table(name="attributes_template")
public class AttributesTemplateEntity implements ToData<AttributesTemplate>{

	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")   
	@Column(name="ID")
	private String attributeTemplateId;
	
	@Column(name="DEVICE_TEMPLATE_ID")
	private String deviceTemplateId;
	
	@Column(name="NAME")
	private String name;

	@Column(name="VALUE")
	private String value;
	
	@Column(name="VALUE_TYPE")
	private String valueType;
	
	@Column(name="IS_TELEMETRY")
	private Integer isTelemetry;//是否为遥测参数
	
	@Column(name="READ_ONLY")
	private Integer readOnly;//读与写权限，0-读，1-写，2-读写
	
	@Column(name="IS_OPTIONAL")
	private Integer isOptional;//是否可选
	
	@Column(name="UNIT")
	private String unit;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="CREATE_TIME")
	private Date createTime;
	
	@Column(name="UPDATE_TIME")
	private Date updateTime;


	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getAttributeTemplateId() {
		return attributeTemplateId;
	}

	public void setAttributeTemplateId(String attributeTemplateId) {
		this.attributeTemplateId = attributeTemplateId;
	}


	public String getDeviceTemplateId() {
		return deviceTemplateId;
	}

	public void setDeviceTemplateId(String deviceTemplateId) {
		this.deviceTemplateId = deviceTemplateId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	public Integer getIsTelemetry() {
		return isTelemetry;
	}

	public void setIsTelemetry(Integer isTelemetry) {
		this.isTelemetry = isTelemetry;
	}

	public Integer getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(Integer readOnly) {
		this.readOnly = readOnly;
	}

	public Integer getIsOptional() {
		return isOptional;
	}

	public void setIsOptional(Integer isOptional) {
		this.isOptional = isOptional;
	}


	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public AttributesTemplate toData() {
		return new AttributesTemplate(attributeTemplateId, deviceTemplateId, name, value,valueType, isTelemetry
				, readOnly, isOptional, unit, description, createTime, updateTime);
	}

	public AttributesTemplateEntity(AttributesTemplate attributesTemplate) {
		super();
		this.attributeTemplateId = attributesTemplate.getAttributesTemplateId();
		this.deviceTemplateId = attributesTemplate.getDeviceTemplateId();
		this.name = attributesTemplate.getName();
		this.value = attributesTemplate.getValue();
		this.valueType = attributesTemplate.getValueType();
		this.isTelemetry = attributesTemplate.getIsTelemetry();
		this.readOnly = attributesTemplate.getReadOnly();
		this.isOptional = attributesTemplate.getIsOptional();
		this.unit = attributesTemplate.getUnit();
		this.description = attributesTemplate.getDescription();
		this.createTime = attributesTemplate.getCreateTime();
		this.updateTime = attributesTemplate.getUpdateTime();
	}

	public AttributesTemplateEntity() {
		super();
	}

	
	
	
	
}