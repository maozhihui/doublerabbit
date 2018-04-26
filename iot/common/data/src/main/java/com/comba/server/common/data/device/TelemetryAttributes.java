package com.comba.server.common.data.device;

import java.util.Date;



import com.comba.server.common.data.SearchTextBased;
import com.comba.server.common.data.id.TelemetryAttributesId;
import lombok.Data;


/**
 * 设备遥测属性
 * @author wengzhonghui
 *
 */
@Data
public class TelemetryAttributes extends SearchTextBased<TelemetryAttributesId> {
	
	private static final long serialVersionUID = -4538005412680782L;
	
	private String telemetryAttributeId;
	
	private String devId;
	
	private String attributeName;
	
	private String valueType;
	
	private String unit;

	
	private Date updateTime;


	@Override
	public String getSearchText() {
		return attributeName;
	}

	public TelemetryAttributes(String telemetryAttributeId, String devId, String attributeName,
			String valueType, String unit, Date updateTime) {
		super();
		this.telemetryAttributeId = telemetryAttributeId;
		this.devId = devId;
		this.attributeName = attributeName;
		this.valueType = valueType;
		this.unit = unit;
		this.updateTime = updateTime;
	}

	public TelemetryAttributes() {
		super();
	}
	
	
}