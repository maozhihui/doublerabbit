package com.comba.server.common.data;


import com.comba.server.common.data.id.CategoryId;


/**
 * 租户机构管理
 * @author wengzhonghui
 *
 */
public class Organization extends SearchTextBased<CategoryId> {

	private static final long serialVersionUID = -8418708379583508367L;
	private String organizationId;
	
	private String parentId;
	
	private String code;
	
	private String name;
	
	private String tenantId;//租户ID
	
	private Integer levelFlag;//关联层级
	

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLevelFlag() {
		return levelFlag;
	}

	public void setLevelFlag(Integer levelFlag) {
		this.levelFlag = levelFlag;
	}


	@Override
	public String getSearchText() {
		return name;
	}

	
	
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Organization(String organizationId, String parentId, String code, String name
			, Integer levelFlag,String tenantId) {
		super();
		this.organizationId = organizationId;
		this.parentId = parentId;
		this.code = code;
		this.name = name;
		this.levelFlag = levelFlag;
		this.tenantId = tenantId;
	}

	public Organization() {
		super();
	}
	
	

}