package com.comba.server.common.data.device;

import java.util.Date;

import com.comba.server.common.data.SearchTextBased;
import com.comba.server.common.data.id.ConfigAttributesId;
import com.comba.server.common.data.web.utils.StringHelper;
import lombok.Data;


/**
 * 设备配置属性
 * @author wengzhonghui
 *
 */
@Data
public class ConfigAttributes extends SearchTextBased<ConfigAttributesId> {

	private static final long serialVersionUID = -4538005412680782L;
	
	private String configAttributeId;
	
	private String devId;
	
	private String attributeScope;//属性类别，client,shared,server
	
	private String attributeName;

	private String attributeId;

	private String valueType;
	
	private Integer readOnly;//读与写权限，0-读，1-写，2-读写
	
	private Integer isOptional;//是否可选
	
	private String description;
	
	private String value;
	
	private Date createTime;
	
	private Date updateTime;

	@Override
	public String getSearchText() {
		return attributeName;
	}

	public ConfigAttributes(String configAttributeId, String devId, String attributeScope, String attributeName,
			String valueType, Integer readOnly, Integer isOptional, String description, String value, Date createTime,
			Date updateTime,String attributeId) {
		super();
		this.configAttributeId = configAttributeId;
		this.devId = devId;
		this.attributeScope = attributeScope;
		this.attributeName = attributeName;
		this.valueType = valueType;
		this.readOnly = readOnly;
		this.isOptional = isOptional;
		this.description = description;
		this.value = value;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.attributeId = attributeId;
	}

	public ConfigAttributes() {
		super();
	}

	
	
}