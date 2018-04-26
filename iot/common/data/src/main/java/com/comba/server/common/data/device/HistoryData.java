package com.comba.server.common.data.device;

import com.comba.server.common.data.SearchTextBased;
import com.comba.server.common.data.id.TelemetryAttributesId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;


/**
 * 设备遥测 历史数据
 * @author huangjinlong
 *
 */
@Data
public class HistoryData extends SearchTextBased<TelemetryAttributesId> {
	
	private String telemetryAttributeId;
	
	private String attributeId;
	
	private int intValue;
	
	private String stringValue;
	
	private float floatValue;
	
	private String value;

	private String updateTime;

	private String devId;

	private String telemetryAttributeName;

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime startDate;

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime endDate;

	@Override
	public String getSearchText() {
		return attributeId;
	}

	public HistoryData(String telemetryAttributeId,String attributeId,
			int intValue,String stringValue,float floatValue, String value, String updateTime ,
					   String devId , String telemetryAttributeName) {
		super();
		this.telemetryAttributeId = telemetryAttributeId;
		this.attributeId = attributeId;
		this.intValue = intValue;
		this.stringValue = stringValue;
		this.floatValue = floatValue;
		this.value = value;
		this.updateTime = updateTime;
		this.devId = devId;
		this.telemetryAttributeName = telemetryAttributeName;
	}

	public HistoryData() {
		super();
	}
	
	
}