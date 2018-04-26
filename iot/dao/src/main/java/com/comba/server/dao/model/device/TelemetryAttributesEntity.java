package com.comba.server.dao.model.device;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.comba.server.common.data.device.AttributesTemplate;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.common.util.StringHelper;

import com.comba.server.common.data.User;
import com.comba.server.common.data.device.TelemetryAttributes;
import com.comba.server.dao.model.ToData;


/**
 * 设备遥测属性
 * @author wengzhonghui
 *
 */
@Entity
@Table(name="telemetry_attributes")
@Data
public class TelemetryAttributesEntity implements ToData<TelemetryAttributes>{

	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")  
	@Column(name="ID")
	private String telemetryAttributeId;
	
	@Column(name="DEV_ID")
	private String devId;
	
	@Column(name="ATTRIBUTE_NAME")
	private String attributeName;
	
	@Column(name="VALUE_TYPE")
	private String valueType;
	
	@Column(name="UNIT")
	private String unit;

	@Column(name="UPDATE_TIME")
	private Date updateTime;

	@Override
	public TelemetryAttributes toData() {
		return new TelemetryAttributes(telemetryAttributeId, devId, attributeName
				, valueType, unit , updateTime);
	}

	public TelemetryAttributesEntity(TelemetryAttributes t) {
		super();
		this.telemetryAttributeId = t.getTelemetryAttributeId();
		this.devId = t.getDevId();
		this.attributeName = t.getAttributeName();
		this.valueType = t.getValueType();
		this.unit = t.getUnit();
		this.updateTime = t.getUpdateTime();
	}

	public TelemetryAttributesEntity(AttributesTemplate t,String devId) {
		super();
		this.devId = devId;
		this.attributeName = t.getName();
		this.valueType = t.getValueType();
		this.unit = t.getUnit();
		this.updateTime = t.getUpdateTime();
	}

	public TelemetryAttributesEntity() {
		super();
	}
	
	
}