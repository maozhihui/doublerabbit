package com.comba.web.controller.rules;

import akka.event.slf4j.Logger;

import com.alibaba.druid.support.logging.Log;
import com.comba.server.common.data.User;
import com.comba.server.common.data.device.Rule;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.common.data.web.utils.Constants;
import com.comba.server.common.data.web.utils.UUIDUtils;
import com.comba.server.dao.common.utils.StringUtils;
import com.comba.server.dao.rule.RuleJpaService;
import com.comba.server.dao.user.UserService;
import com.comba.web.common.annotation.SystemControllerLog;
import com.comba.web.common.export.ExportUtils;
import com.comba.web.response.ResponseBean;
import com.comba.web.security.SpringSecurityUtils;
import com.comba.web.utils.CommonProperties;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.comba.server.actors.service.ActorService;
import com.comba.server.common.data.plugin.ComponentLifecycleEvent;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.data.id.RuleId;

import org.hibernate.internal.util.StringHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * 规则管理
 * @author wengzhonghui
 *
 */
@Controller
@RequestMapping("/rule")
public class RuleController {
	@Autowired
    protected ActorService actorService;
	@Resource
	private RuleJpaService ruleService;
	@Resource
	private UserService userJpaService;
	

	@RequestMapping("/list")
	 public String list(@ModelAttribute  Page page, Model model) throws Exception {
		model.addAttribute("pluginTypes", CommonProperties.PLUGIN_TYPES);
		return "rules/rule/rule_list";
	 }
	@ResponseBody
	 @RequestMapping("/datasByPage")
	 public Page getAuditDataByPage(Rule rule,Page page)throws Exception{
		 page = page==null?new Page():page;
		 List<String> orderBysList = page.getOrderBys();
		 return ruleService.pagedQuery(page.getPageNo(), page.getPageSize(), rule,orderBysList);
	 }
	
	
	@ResponseBody
	 @RequestMapping("/datasByExport")
	 public ResponseBean  datasByExport(Rule rule,Page page,Integer organId,Integer isQueryChild
			 ,String exportFlag,HttpSession session,@RequestParam Map<String, Object> parameterMap
			 )throws Exception{
		page.setPageSize(CommonProperties.maxAllowExportNum);
		Page dataPage = getAuditDataByPage(rule, page);
		return ExportUtils.exportDatasByExcel(dataPage, exportFlag, session, parameterMap);
	 }
	
	@RequestMapping("/to_rule_edit")
	public String ruleAdd(@RequestParam Map<String, Object> parameterMap, Model model,HttpSession session) throws Exception{
		
		String ruleId= StringUtils.getString(parameterMap.get("ruleId"));
		Rule rule = null;
		if(StringHelper.isNotEmpty(ruleId)){
			rule = ruleService.getOne(ruleId);
		}
		model.addAttribute("rule", rule);
		model.addAttribute("pluginTypes", CommonProperties.PLUGIN_TYPES);
		model.addAttribute(Constants.CUR_PRODUCT_ID, session.getAttribute(Constants.CUR_PRODUCT_ID));
		return "rules/rule/rule_edit";
	}
	
	@RequestMapping("/rule_view")
	public String ruleCheck(@RequestParam Map<String, Object> parameterMap, Model model) throws Exception{
		String ruleId= StringUtils.getString(parameterMap.get("ruleId"));
		Rule rule = null;
		if(StringHelper.isNotEmpty(ruleId)){
			rule = ruleService.getOne(ruleId);
		}
		model.addAttribute("rule", rule);
		model.addAttribute("pluginTypes", CommonProperties.PLUGIN_TYPES);
		return "rules/rule/rule_view";
	}
	
	
	
	@PostMapping(value="addRule")
	@ResponseBody
	@SystemControllerLog(description = "添加规则")
	public ResponseBean addRule(Rule rule,HttpServletRequest request) throws Exception{
		Date now = new Date();
		rule.setTenantId(SpringSecurityUtils.getCurrentUserTenantId());
		rule.setCreateTime(now);
		rule.setUpdateTime(now);
        // action字串 json解析
		String action = rule.getAction();
		JSONObject jsonAction = JSONObject.fromObject(action);
		String configuration = jsonAction.get("configuration").toString();
		JSONObject jsonConfiguration = JSONObject.fromObject(configuration);
    	// 查询TenantId租户管理员的电话号码，补填到dstNum
    	List<User> userJpaEntityList = userJpaService.findByTenantId(rule.getTenantId());
    	for(User userJpaEntity:userJpaEntityList) {
    		if(userJpaEntity.getType() ==2) {
    	    	jsonConfiguration.put("dstNum", userJpaEntity.getMsisdn());
    	    	jsonAction.put("configuration", jsonConfiguration);
    	    	break;
    		}
    	}
    	
    	rule.setAction(jsonAction.toString());
		Rule newRule = ruleService.save(rule);
		
		//通知后台程序更新规则缓存
		RuleId rId = new RuleId(UUIDUtils.toUUID(newRule.getRuleId())); 
		TenantId tenantId = new TenantId(UUIDUtils.toUUID(newRule.getTenantId()));
		actorService.onRuleStateChange(tenantId,rId, ComponentLifecycleEvent.ACTIVATED);
		
		request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "添加规则【"+rule.getName()+"】");
		ResponseBean responseBean = new ResponseBean();
		responseBean.setFlag_success();
		return responseBean;
		
	}
	
	@PostMapping(value="updateRule")
	@ResponseBody
	@SystemControllerLog(description = "更新规则")
	public ResponseBean updateRule(Rule rule,HttpServletRequest request) throws Exception{
		rule.setTenantId(SpringSecurityUtils.getCurrentUserTenantId());
		rule.setUpdateTime(new Date());
        // action字串 json解析
		String action = rule.getAction();
		JSONObject jsonAction = JSONObject.fromObject(action);
		String configuration = jsonAction.get("configuration").toString();
		JSONObject jsonConfiguration = JSONObject.fromObject(configuration);
    	// 查询TenantId租户管理员的电话号码，补填到dstNum
    	List<User> userJpaEntityList = userJpaService.findByTenantId(rule.getTenantId());
    	for(User userJpaEntity:userJpaEntityList) {
    		if(userJpaEntity.getType() ==2) {
    	    	jsonConfiguration.put("dstNum", userJpaEntity.getMsisdn());
    	    	jsonAction.put("configuration", jsonConfiguration);
    	    	break;
    		}
    	}
    	
    	rule.setAction(jsonAction.toString());
		ruleService.save(rule);
		
		//通知后台程序更新规则缓存
		RuleId rId = new RuleId(UUIDUtils.toUUID(rule.getRuleId())); 
		TenantId tenantId = new TenantId(UUIDUtils.toUUID(rule.getTenantId()));
		actorService.onRuleStateChange(tenantId,rId, ComponentLifecycleEvent.UPDATED);
		
		request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "添加规则【"+rule.getName()+"】");
		if(rule!=null && rule.getName()!=null){
			request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "修改规则信息"+rule.getName()+"】");
		}
		ResponseBean responseBean = new ResponseBean();
		responseBean.setFlag_success();
		return responseBean;
	}
	@RequestMapping(value="deleteRule",method=RequestMethod.POST)
	@SystemControllerLog(description = "删除规则")
	public @ResponseBody  ResponseBean deleteGrule(@RequestParam(value = "ids[]")  String[] ruleIds
			,HttpServletRequest request) throws Exception{
		ResponseBean responseBean = new ResponseBean();
		if(ruleIds == null || ruleIds.length < 1){
			responseBean.setFlag_fail();
			return responseBean;
		}
		List<String> ruleIdList= new ArrayList<String>();
		
		for(String ruleId : ruleIds){
			ruleIdList.add(ruleId);
		}
		StringBuilder logDesc = new StringBuilder();
		List<Rule> ruleList = ruleService.findByIds(ruleIdList);
		if(ruleList!=null && ruleList.size()>0){
			for(Rule rule : ruleList){
				if(rule!=null && rule.getName()!=null){
					if(logDesc.length()>0) logDesc.append(",");
					logDesc.append(rule.getName());
					
					//通知后台程序更新规则缓存
					RuleId rId = new RuleId(UUIDUtils.toUUID(rule.getRuleId())); 
					TenantId tenantId = new TenantId(UUIDUtils.toUUID(rule.getTenantId()));
					actorService.onRuleStateChange(tenantId,rId, ComponentLifecycleEvent.DELETED);
				}				
			}
			request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "删除规则【"+logDesc.toString()+"】");
		}
		ruleService.deleteByIds(ruleIds);
		responseBean.setFlag_success();;
		return responseBean;
	}
	
	@RequestMapping("/to_filter_edit")
	 public String toFilterEdit(@ModelAttribute  Page page, Model model,String filterId) throws Exception {
		model.addAttribute("filterTypes", CommonProperties.FILTER_TYPES);
		model.addAttribute("filterId",filterId);
		return "rules/rule/filter_edit";
	 }
	
	@RequestMapping("/to_processor_edit")
	 public String to_processor_edit(@ModelAttribute  Page page, Model model,String processId) throws Exception {
		model.addAttribute("processTypes", CommonProperties.PROCCESSOR_TYPES);
		model.addAttribute("processId",processId);
		return "rules/rule/processor_edit";
	 }
}
