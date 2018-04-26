package com.comba.web.controller.rules;

import com.alibaba.fastjson.JSON;
import com.comba.server.actors.service.ActorService;
import com.comba.server.common.data.User;
import com.comba.server.common.data.device.Rule;
import com.comba.server.common.data.id.RuleId;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.data.plugin.ComponentLifecycleEvent;
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
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.json.JSONObject;

import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.comba.server.actors.service.ActorService;
import com.comba.server.common.data.plugin.ComponentLifecycleEvent;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.data.id.RuleId;
/**
 *  租户的规则管理
 */
@Controller
@RequestMapping("/ruleTenant")
public class RuleTenantController {

	@Autowired
    protected ActorService actorService;
    @Resource
    private RuleJpaService ruleService;
	@Resource
	private UserService userJpaService;

    @RequestMapping("/list")
    public String list(@ModelAttribute Page page, Model model,HttpSession session) throws Exception {
        model.addAttribute("pluginTypes", CommonProperties.PLUGIN_TYPES);
        model.addAttribute(Constants.CUR_PRODUCT_ID, session.getAttribute(Constants.CUR_PRODUCT_ID));
        return "rules/ruleTenant/rule_list";
    }
    @ResponseBody
    @RequestMapping("/datasByPage")
    public Page getAuditDataByPage(Rule rule, Page page)throws Exception{
        page = page==null?new Page():page;
        List<String> orderBysList = page.getOrderBys();
        return ruleService.pagedQuery(page.getPageNo(), page.getPageSize(), rule,orderBysList);
    }


    @ResponseBody
    @RequestMapping("/datasByExport")
    public ResponseBean datasByExport(Rule rule, Page page, Integer organId, Integer isQueryChild
            , String exportFlag, HttpSession session, @RequestParam Map<String, Object> parameterMap
    )throws Exception{
        page.setPageSize(CommonProperties.maxAllowExportNum);
        Page dataPage = getAuditDataByPage(rule, page);
        return ExportUtils.exportDatasByExcel(dataPage, exportFlag, session, parameterMap);
    }

    @RequestMapping("/to_rule_edit")
    public String ruleAdd(String ruleId, Model model,HttpSession session) throws Exception{
        Rule rule = null;
        if(StringHelper.isNotEmpty(ruleId)){
            rule = ruleService.getOne(ruleId);
        }
        model.addAttribute("rule", rule);
        model.addAttribute("pluginTypes", CommonProperties.PLUGIN_TYPES);
        model.addAttribute(Constants.CUR_PRODUCT_ID, session.getAttribute(Constants.CUR_PRODUCT_ID));
        return "rules/ruleTenant/rule_edit";
    }

    @RequestMapping("/rule_view")
    public String ruleCheck(String ruleId, Model model) throws Exception{
        Rule rule = null;
        if(StringHelper.isNotEmpty(ruleId)){
            rule = ruleService.getOne(ruleId);
        }
        model.addAttribute("rule", rule);
        model.addAttribute("pluginTypes", CommonProperties.PLUGIN_TYPES);
        return "rules/ruleTenant/rule_view";
    }



    @RequestMapping(value="addRule",method= RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "添加规则")
    public ResponseBean addRule(String datas,HttpServletRequest request) throws Exception{
        Rule rule = JSON.parseObject(datas,Rule.class);
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

    @RequestMapping(value="updateRule",method=RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "更新规则")
    public ResponseBean updateRule(String datas,HttpServletRequest request) throws Exception{
        Rule rule = JSON.parseObject(datas,Rule.class);
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
                }
            }
            request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "删除规则【"+logDesc.toString()+"】");
        }
        ruleService.deleteByIds(ruleIds);
        responseBean.setFlag_success();
        if(ruleList!=null && ruleList.size()>0){
            for(Rule rule : ruleList){
            	//通知后台程序更新规则缓存
				RuleId rId = new RuleId(UUIDUtils.toUUID(rule.getRuleId())); 
				TenantId tenantId = new TenantId(UUIDUtils.toUUID(rule.getTenantId()));
				actorService.onRuleStateChange(tenantId,rId, ComponentLifecycleEvent.DELETED);
            }
        }
        return responseBean;
    }
    
    /**
	 * 校验规则名称
	 *
	 * @param ruleId 设备类别ID
	 * @param name 名称
	 * @return
	 */
	@PostMapping("/validateRuleName")
	@ResponseBody
	public ResponseBean validateRuleName(String ruleId,String name){
		ResponseBean response = new ResponseBean(ResponseBean.FAIL);

		if (StringHelper.isNotEmpty(ruleId)){
			Rule ruleDB = ruleService.getOne(ruleId);
			if (!ruleDB.getName().equals(name) && ruleService.countByName(name) > 0){
				response.setMessage("该设备类别已存在");
				return response;
			}
		}else if(ruleService.countByName(name) > 0){
			response.setMessage("该设备类别已存在");
			return response;
		}

		response.setFlag_success();
		return response;
	}
}
