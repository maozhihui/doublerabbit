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

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.common.util.StringHelper;

import com.comba.server.common.data.User;
import com.comba.server.common.data.device.PluginJpa;
import com.comba.server.dao.model.ToData;


/**
 * 插件管理
 * @author wengzhonghui
 *
 */
@Entity
@Table(name="plugin")
public class PluginJpaEntity implements ToData<PluginJpa>{

	private static final long serialVersionUID = -8418708379583508367L;
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")   
	@Column(name="ID")
	private String pluginId;
	
	@Column(name="TENANT_ID")
	private String tenantId;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="CLAZZ")
	private String clazz;//插件处理类名
	
	@Column(name="CONFIGURATION")
	private String configuration;//插件配置信息
	
	@Column(name="PUBLICACCESS")
	private String publicaccess;//
	
	@Column(name="ADDITIONALINFO")
	private String additionalinfo;//
	
	@Column(name="CREATE_TIME")
	private Date createTime;
	
	@Column(name="UPDATE_TIME")
	private Date updateTime;
	
	


	public String getPluginId() {
		return pluginId;
	}

	public void setPluginId(String pluginId) {
		this.pluginId = pluginId;
	}

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

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getConfiguration() {
		return configuration;
	}

	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}

	public String getPublicaccess() {
		return publicaccess;
	}

	public void setPublicaccess(String publicaccess) {
		this.publicaccess = publicaccess;
	}

	public String getAdditionalinfo() {
		return additionalinfo;
	}

	public void setAdditionalinfo(String additionalinfo) {
		this.additionalinfo = additionalinfo;
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
	public PluginJpa toData() {
		return new PluginJpa(pluginId, tenantId, name, clazz, configuration, publicaccess, additionalinfo
				, createTime, updateTime);
	}

	public PluginJpaEntity(PluginJpa t) {
		super();
		this.pluginId = t.getPluginId();
		this.tenantId = t.getTenantId();
		this.name = t.getName();
		this.clazz = t.getClazz();
		this.configuration = t.getConfiguration();
		this.publicaccess = t.getPublicaccess();
		this.additionalinfo = t.getAdditionalinfo();
		this.createTime = t.getCreateTime();
		this.updateTime = t.getUpdateTime();
	}

	public PluginJpaEntity() {
		super();
	}

	
	
	
	

}