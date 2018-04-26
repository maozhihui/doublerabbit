package com.comba.server.dao.model.device;

import com.comba.server.common.data.api.DeviceAttributes;
import com.comba.server.common.data.device.HistoryData;
import com.comba.server.dao.model.ToData;
import com.comba.server.dao.util.DateConvertUtil;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


/**
 * 设备遥测 历史数据
 * @author huangjinlong
 *
 */
@Data
@Entity
@Table(name="history_data")
public class HistoryDataEntity implements ToData<HistoryData>{
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")  
	@Column(name="ID")
	private String telemetryAttributeId;
	
	@Column(name="ATTRIBUTE_ID")
	private String attributeId;
	
	@Column(name="INT_VALUE")
	private int intValue;
	
	@Column(name="FLOAT_VALUE")
	private float floatValue;
	
	@Column(name="STRING_VALUE")
	private String stringValue;
	
	@Column(name="VALUE")
	private String value;
	
	@Column(name="UPDATE_TIME")
	private LocalDateTime updateTime;

	@Column(name="DEV_ID")
	private String devId;

	@Column(name="ATTRIBUTE_NAME")
	private String telemetryAttributeName;

	public String getDevId() {
		return devId;
	}

	public void setDevId(String devId) {
		this.devId = devId;
	}

	@Override
	public HistoryData toData() {
		return new HistoryData(telemetryAttributeId, attributeId, intValue, stringValue
				, floatValue, value, DateConvertUtil.localDateTimeConvertToStr(updateTime),devId,telemetryAttributeName);
	}

	public DeviceAttributes toDeviceAttributes(){
		Map<String,Object> map = new HashMap<>();
		map.put(telemetryAttributeName,value);
		return new DeviceAttributes(DateConvertUtil.convertToDate(updateTime).getTime(),map);
	}

	public HistoryDataEntity(HistoryData t) {
		super();
		this.telemetryAttributeId = t.getTelemetryAttributeId();
		this.attributeId = t.getAttributeId();
		this.intValue = t.getIntValue();
		this.stringValue = t.getStringValue();
		this.floatValue = t.getFloatValue();
		this.value = t.getValue();
		this.updateTime = DateConvertUtil.strConvertToLocalDateTime(t.getUpdateTime());
		this.devId = t.getDevId();
		this.telemetryAttributeName = t.getTelemetryAttributeName();
	}

	public HistoryDataEntity() {
		super();
	}
	
	
}