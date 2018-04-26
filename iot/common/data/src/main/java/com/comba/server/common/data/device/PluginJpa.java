package com.comba.server.common.data.device;

import java.io.IOException;
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

import com.comba.server.common.data.SearchTextBased;
import com.comba.server.common.data.id.PluginJpaId;
import com.comba.server.common.data.id.UserJpaId;
import com.comba.server.common.data.web.utils.UUIDUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * 插件管理
 * @author wengzhonghui
 *
 */
public class PluginJpa extends SearchTextBased<PluginJpaId> {

	private static final long serialVersionUID = -8418708379583508367L;
	private String pluginId;
	
	private String tenantId;
	
	private String name;
	
	private String clazz;//插件处理类名
	
	private String configuration;//插件配置信息
	
	private String publicaccess;//
	
	private String additionalinfo;//
	
	private Date createTime;
	
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

	public JsonNode getJsonConfiguration(){
		ObjectMapper mapper = new ObjectMapper();
		JsonNode obj = null;
		try {
			obj = mapper.readTree(configuration);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return obj;
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
	public String getSearchText() {
		return name;
	}

	public PluginJpa(String pluginId, String tenantId, String name, String clazz, String configuration,
			String publicaccess, String additionalinfo, Date createTime, Date updateTime) {
		super();
		this.setId(new PluginJpaId(UUIDUtils.toUUID(pluginId)));
		this.pluginId = pluginId;
		this.tenantId = tenantId;
		this.name = name;
		this.clazz = clazz;
		this.configuration = configuration;
		this.publicaccess = publicaccess;
		this.additionalinfo = additionalinfo;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	public PluginJpa() {
		super();
	}


}