package com.comba.server.dao.model;

import com.comba.server.common.data.Tenant;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;





/**
 * 租户
 * @author wengzhonghui
 *
 */
@Entity
@Table(name="tenant")
public class TenantEntity implements ToData<Tenant>{

	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid") 
	@Column(name="ID")
	private String tenantId;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="ADDITIONAL_INFO")
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
	public Tenant toData() {
		return new Tenant( tenantId,  name,  additionalInfo);
	}

	public TenantEntity(Tenant t) {
		super();
		if(StringUtils.isNotBlank(t.getTenantId())){
			this.tenantId = t.getTenantId();
		}
		this.name = t.getName();
		this.additionalInfo = t.getAdditionalInfo();
	}

	public TenantEntity() {
		super();
	}


}