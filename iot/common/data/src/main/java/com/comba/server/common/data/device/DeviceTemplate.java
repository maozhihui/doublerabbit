package com.comba.server.common.data.device;

import com.comba.server.common.data.SearchTextBased;
import com.comba.server.common.data.id.DeviceTemplateId;
import lombok.Data;

import java.util.Date;





/**
 * 设备模板
 * @author wengzhonghui
 *
 */
@Data
public class DeviceTemplate extends SearchTextBased<DeviceTemplateId> {

	private static final long serialVersionUID = -4538005412680782L;
	
	private String deviceTemplateId;
	
	private String categoryId;
	
	private String name;
	
	private Date createTime;
	
	private Date updateTime;
	
	private String categoryName;

	private String description;

	@Override
	public String getSearchText() {
		return name;
	}

	public DeviceTemplate(String deviceTemplateId, String categoryId, String name, Date createTime,
			Date updateTime,String categoryName,String description) {
		super();
		this.deviceTemplateId = deviceTemplateId;
		this.categoryId = categoryId;
		this.name = name;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.categoryName = categoryName;
		this.description = description;
	}

	public DeviceTemplate() {
		super();
	}
}