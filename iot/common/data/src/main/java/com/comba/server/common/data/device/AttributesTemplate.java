package com.comba.server.common.data.device;

import java.util.Date;

import com.comba.server.common.data.SearchTextBased;
import com.comba.server.common.data.id.AttributesTemplateId;





/**
 * 参数模板
 * @author wengzhonghui
 *
 */
public class AttributesTemplate extends SearchTextBased<AttributesTemplateId> {

	private static final long serialVersionUID = -4538005412680782L;
	
	private String attributesTemplateId;
	
	private String deviceTemplateId;
	
	private String name;

	private String value;
	
	private String valueType;
	
	private Integer isTelemetry;//是否为遥测参数
	
	private Integer readOnly;//读与写权限，0-读，1-写，2-读写
	
	private Integer isOptional;//是否可选
	
	private String unit;
	
	private String description;
	
	private Date createTime;
	
	private Date updateTime;


	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getAttributesTemplateId() {
        return attributesTemplateId;
    }

    public void setAttributesTemplateId(String attributesTemplateId) {
        this.attributesTemplateId = attributesTemplateId;
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
	public String getSearchText() {
		
		return this.name;
	}

	public AttributesTemplate(String attributeTemplateId, String deviceTemplateId, String name,String value, String valueType,
			Integer isTelemetry, Integer readOnly, Integer isOptional, String unit, String description,
			Date createTime, Date updateTime) {
		super();
		this.attributesTemplateId = attributeTemplateId;
		this.deviceTemplateId = deviceTemplateId;
		this.name = name;
		this.value = value;
		this.valueType = valueType;
		this.isTelemetry = isTelemetry;
		this.readOnly = readOnly;
		this.isOptional = isOptional;
		this.unit = unit;
		this.description = description;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	public AttributesTemplate() {
		super();
	}
	
}