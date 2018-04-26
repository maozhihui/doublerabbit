package com.comba.server.common.data;

import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.data.web.utils.UUIDUtils;


/**
 * 用户信息
 * @author wengzhonghui
 *
 */
public class Tenant extends SearchTextBased<TenantId> {

	private static final long serialVersionUID = -4538005412680782L;
	
	
	private String tenantId;
	
	private String name;
	
	private String additionalInfo;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	@Override
	public String getSearchText() {
		return tenantId;
	}

	public Tenant(String tenantId, String name, String additionalInfo) {
		super();
		this.setId(new TenantId(UUIDUtils.toUUID(tenantId)));
		this.tenantId = tenantId;
		this.name = name;
		this.additionalInfo = additionalInfo;
	}

	public Tenant() {
		super();
	}
	
	
	
}