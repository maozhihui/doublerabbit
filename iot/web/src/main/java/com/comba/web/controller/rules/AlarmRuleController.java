package com.comba.web.controller.rules;

import com.alibaba.fastjson.JSON;
import com.comba.server.common.data.device.AlarmContent;
import com.comba.server.common.data.device.AlarmRule;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.common.data.web.utils.Constants;
import com.comba.server.dao.device.AlarmContentService;
import com.comba.server.dao.device.alarm.AlarmRuleService;
import com.comba.web.common.annotation.SystemControllerLog;
import com.comba.web.common.export.ExportUtils;
import com.comba.web.response.ResponseBean;
import com.comba.web.security.SpringSecurityUtils;
import com.comba.web.utils.CommonProperties;
import com.google.common.collect.Lists;
import org.hibernate.internal.util.StringHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 告警规则 界面控制层实现
 *
 * @作者 sujinxian
 * @创建时间 2018-02-28 15:55:17
 */
@Controller
@RequestMapping("/alarmRule") 
public class AlarmRuleController{
	@Resource
	private AlarmRuleService alarmRuleService;

    @Resource
    private AlarmContentService alarmContentService;
	
	/**
	 * 进入告警规则列表界面
	 */
	@RequestMapping("/alarmRule_list")
	 public String list(Model model,HttpSession session) throws Exception {
        model.addAttribute(Constants.CUR_PRODUCT_ID, session.getAttribute(Constants.CUR_PRODUCT_ID));
        return "device/alarmRule/alarmRule_list";
	 }
	
	 /**
	 * 获取分页数据
	 */
	 @ResponseBody
	 @RequestMapping("/datasByPage")
	 public Page getDataByPage(AlarmRule alarmRule,Page page)throws Exception{
		 page = page==null?new Page():page;
		 List<String> orderBysList = page.getOrderBys();
		 //设置租户id
         alarmRule.setTenantId(SpringSecurityUtils.getCurrentUserTenantId());

         return alarmRuleService.getDataByPage(page.getPageNo(), page.getPageSize(), alarmRule,orderBysList);
	 }
	
	 /**
	 * 导出列表数据
	 */
	@ResponseBody
	 @RequestMapping("/datasByExport")
	 public ResponseBean ExportData(AlarmRule alarmRule, Page page, HttpSession session
			 , @RequestParam Map<String, Object> parameterMap, String exportFlag
			 )throws Exception{
		 page = page==null?new Page():page;
		 Page returnPage = null;
		 List<String> orderBysList = page.getOrderBys();
		 page.setPageSize(CommonProperties.maxAllowExportNum);
		 returnPage = alarmRuleService.getDataByPage(page.getPageNo(), page.getPageSize(), alarmRule,orderBysList);
		return ExportUtils.exportDatasByExcel(returnPage, exportFlag, session, parameterMap);
	 }
	
	
	/**
	 * 进入编辑界面
	 */
	@RequestMapping("/to_alarmRule_edit")
	public String alarmRuleEdit(String id, Model model,HttpSession session) throws Exception{
		
		AlarmRule alarmRule = null;
		if(StringHelper.isNotEmpty(id))
			alarmRule = alarmRuleService.getOne(id);


        List<AlarmContent> alarmContents = alarmContentService.findAll();
        if (alarmContents != null){
            model.addAttribute("alarmContents", JSON.toJSONString(alarmContents));
        }

        model.addAttribute(Constants.CUR_PRODUCT_ID, session.getAttribute(Constants.CUR_PRODUCT_ID));
        model.addAttribute("alarmRule", alarmRule);
		
		return "device/alarmRule/alarmRule_edit";
	}
	
	/**
	 * 进入查看界面
	 * 
	 */
	@RequestMapping("/to_alarmRule_view")
	public String alarmRuleView(String id, Model model) throws Exception{
		AlarmRule alarmRule = alarmRuleService.getOne(id);
			
		model.addAttribute("alarmRule", alarmRule);
		
		return "device/alarmRule/alarmRule_view";
	}
	
	
	
	/**
	 * 添加告警规则
	 * 
	 */
	@RequestMapping(value="addAlarmRule",method=RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "添加告警规则")
	public ResponseBean addAlarmRule(AlarmRule alarmRule, Model model
			,HttpServletRequest request) throws Exception{
		ResponseBean responseBean = new ResponseBean();
        String tenantId = SpringSecurityUtils.getCurrentUserTenantId();
        alarmRule.setTenantId(tenantId);
        alarmRule.setCreateTime(new Date());

		alarmRuleService.save(alarmRule);
		request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "添加告警规则 【 "+alarmRule.getName()+"】");
		responseBean.setFlag_success();;
		return responseBean;
		
	}
	
	/**
	 * 更新告警规则
	 */
	@RequestMapping(value="updateAlarmRule",method=RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "更新告警规则")
	public ResponseBean updateAlarmRule(AlarmRule alarmRule,HttpServletRequest request) throws Exception{
		ResponseBean responseBean = new ResponseBean();

		AlarmRule rule = alarmRuleService.getOne(alarmRule.getId());
		if (rule == null){
		    responseBean.setMessage("item not find");
		    return responseBean;
        }

        alarmRule.setTenantId(rule.getTenantId());
		alarmRule.setCreateTime(rule.getCreateTime());
		alarmRuleService.save(alarmRule);
		request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "更新告警规则 【"+alarmRule.getName()+"】");
		responseBean.setFlag_success();;
		return responseBean;
	}
	
	
	/**
	 * 删除告警规则
	 */
	@RequestMapping(value="deleteAlarmRule",method=RequestMethod.POST)
	@SystemControllerLog(description = "删除告警规则")
	public @ResponseBody  ResponseBean deletealarmRule(@RequestParam(value = "ids[]")  String[] ids
			,HttpServletRequest request) throws Exception{
		ResponseBean responseBean = new ResponseBean();
		if(ids == null || ids.length < 1){
			responseBean.setFlag_fail();
			return responseBean;
		}
		List<String> alarmRuleIdList= Lists.newArrayList(ids);

		StringBuilder logDesc = new StringBuilder();
		List<AlarmRule> alarmRuleList = alarmRuleService.findByIds(alarmRuleIdList);
	    for (AlarmRule rule:alarmRuleList){
            logDesc.append(rule.getName());
            logDesc.append(" ");
        }

        request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "删除告警规则 【"+logDesc.toString()+"】");
		alarmRuleService.deleteByIds(alarmRuleIdList);
		responseBean.setFlag_success();
		return responseBean;
	}


}
