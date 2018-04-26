package com.comba.server.dao.model.device;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.common.util.StringHelper;

import com.comba.server.common.data.device.Rule;
import com.comba.server.dao.model.ToData;


/**
 * 规则管理
 * @author wengzhonghui
 *
 */
@Data
@Entity
@Table(name="rule")
public class RuleEntity implements ToData<Rule>{

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid") 
	@Column(name="ID")
	private String ruleId;
	
	@Column(name="TENANT_ID")
	private String tenantId;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="PLUGIN_ID")
	private String pluginId;
	
	@Column(name="FILTERS")
	private String filters;//过滤配置
	
	@Column(name="PROCESSOR")
	private String processor;//预处理器配置
	
	@Column(name="ACTION")
	private String action;//规则与插件适配器
	
	@Column(name="ADDITIONALINFO")
	private String additionalinfo;//
	
	@Column(name="CREATE_TIME")
	private Date createTime;
	
	@Column(name="UPDATE_TIME")
	private Date updateTime;

	@Column(name="PRODUCT_ID")
	private String productId;

	@Override
	public Rule toData() {
		return new Rule(ruleId, tenantId, name, pluginId, filters, processor, action, additionalinfo
				, createTime, updateTime,productId);
	}

	public RuleEntity(Rule t) {
		super();
		this.ruleId = t.getRuleId();
		this.tenantId = t.getTenantId();
		this.name = t.getName();
		this.pluginId = t.getPluginId();
		this.filters = t.getFilters();
		this.processor = t.getProcessor();
		this.action = t.getAction();
		this.additionalinfo = t.getAdditionalinfo();
		this.createTime = t.getCreateTime();
		this.updateTime = t.getUpdateTime();
		this.productId = t.getProductId();
	}

	public RuleEntity() {
		super();
	}

	
	
}