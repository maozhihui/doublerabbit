package com.comba.server.common.data.device;

import java.io.IOException;
import java.util.Date;


import com.comba.server.common.data.SearchTextBased;
import com.comba.server.common.data.id.RuleId;
import com.comba.server.common.data.plugin.ComponentLifecycleState;
import com.comba.server.common.data.web.utils.UUIDUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;


/**
 * 规则管理
 * @author wengzhonghui
 *
 */
@Data
public class Rule extends SearchTextBased<RuleId> {

	private static final long serialVersionUID = -8418708379583508367L;

	private String ruleId;
	
	private String tenantId;
	
	private String name;
	
	private String pluginId;
	
	private String filters;//过滤配置
	
	private String processor;//预处理器配置
	
	private String action;//规则与插件适配器
	
	private String additionalinfo;//
	
	private Date createTime;
	
	private Date updateTime;

	private String productId; //产品Id
	private ComponentLifecycleState state;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ruleId == null) ? 0 : ruleId.hashCode());
		return result;
	}
	// 当前固定写死 mzh
	public JsonNode getJsonFilters(){
		ObjectMapper mapper = new ObjectMapper();
		JsonNode obj = null;
		try {
			obj = mapper.readTree("[{\"clazz\": \"com.comba.server.extensions.core.filter.MsgTypeFilter\",\"name\": \"TelemetryFilter\",\"configuration\": {\"messageTypes\": [\"POST_TELEMETRY\",\"POST_ATTRIBUTES\",\"GET_ATTRIBUTES\"]}}]");
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	public JsonNode getJsonFilters(String filters){
		ObjectMapper mapper = new ObjectMapper();
		JsonNode obj = null;
		try {
			obj = mapper.readTree(filters);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	public JsonNode getJsonAction(String action){
		ObjectMapper mapper = new ObjectMapper();
		JsonNode obj = null;
		try {
			obj = mapper.readTree(action);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public String getSearchText() {
		return name;
	}

	public Rule(String ruleId, String tenantId, String name, String pluginId, String filters, String processor,
			String action, String additionalinfo, Date createTime, Date updateTime,String productId) {
		super();
		this.setId(new RuleId(UUIDUtils.toUUID(ruleId)));
		this.ruleId = ruleId;
		this.tenantId = tenantId;
		this.name = name;
		this.pluginId = pluginId;
		this.filters = filters;
		this.processor = processor;
		this.action = action;
		this.additionalinfo = additionalinfo;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.productId = productId;
		state = ComponentLifecycleState.ACTIVE;
	}

	public Rule() {
		super();
		state = ComponentLifecycleState.ACTIVE;
	}
	
	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer("[");
		if (this.getId() != null && this.getId().getId() != null) {
			sb.append("ruleId=").append(this.getId().getId().toString());
		}
		if (this.tenantId != null) {
			sb.append(",tenantId=").append(this.tenantId);
		}
		if (this.name != null) {
			sb.append(",name=").append(this.name);
		}
		if (this.pluginId != null) {
			sb.append(",pluginId=").append(this.pluginId);
		}
		if (this.filters != null) {
			sb.append(",filters=").append(this.filters);
		}
		if (this.processor != null) {
			sb.append(",processor=").append(this.processor);
		}
		if (this.action != null) {
			sb.append(",action=").append(this.action);
		}
		sb.append("]");
		return sb.toString();
	}
}