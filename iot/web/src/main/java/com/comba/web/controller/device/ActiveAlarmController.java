package com.comba.web.controller.device;

import com.comba.server.common.data.device.ActiveAlarm;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.common.data.web.utils.Constants;
import com.comba.server.dao.device.alarm.ActiveAlarmService;
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
import java.util.List;
import java.util.Map;

/**
 * 活动告警 界面控制层实现
 *
 * @作者 sujinxian
 * @创建时间 2018-03-01 11:24:16
 */
@Controller
@RequestMapping("/activeAlarm") 
public class ActiveAlarmController{
	@Resource
	private ActiveAlarmService activeAlarmService;
	
	/**
	 * 进入活动告警列表界面
	 */
	@RequestMapping("/activeAlarm_list")
	 public String list(Model model,HttpSession session) throws Exception {
        model.addAttribute(Constants.CUR_PRODUCT_ID, session.getAttribute(Constants.CUR_PRODUCT_ID));
        return "device/activeAlarm/activeAlarm_list";
	 }
	
	 /**
      *
	 * 获取分页数据
	 */
	 @ResponseBody
	 @RequestMapping("/datasByPage")
	 public Page getDataByPage(ActiveAlarm activeAlarm, Page page)throws Exception{
		 page = page==null?new Page():page;
         return activeAlarmService.queryAlarmByPage(page.getPageNo(), page.getPageSize(), activeAlarm);
	 }
	
	 /**
	 * 导出列表数据
	 */
	@ResponseBody
	 @RequestMapping("/datasByExport")
	 public ResponseBean ExportData(ActiveAlarm activeAlarm, Page page, HttpSession session
			 , @RequestParam Map<String, Object> parameterMap, String exportFlag
			 )throws Exception{
		 page = page==null?new Page():page;
		 Page returnPage = null;
		 List<String> orderBysList = page.getOrderBys();
		 page.setPageSize(CommonProperties.maxAllowExportNum);
		 returnPage = activeAlarmService.getDataByPage(page.getPageNo(), page.getPageSize(), activeAlarm,orderBysList);
		return ExportUtils.exportDatasByExcel(returnPage, exportFlag, session, parameterMap);
	 }
	
	
	/**
	 * 进入编辑界面
	 */
	@RequestMapping("/to_activeAlarm_edit")
	public String activeAlarmEdit(String id, Model model) throws Exception{
		
		ActiveAlarm activeAlarm = null;
		if(StringHelper.isNotEmpty(id))
			activeAlarm = activeAlarmService.getOne(id);
		model.addAttribute("activeAlarm", activeAlarm);
		
		return "device/activeAlarm/activeAlarm_edit";
	}
	
	/**
	 * 进入查看界面
	 * 
	 */
	@RequestMapping("/to_activeAlarm_view")
	public String activeAlarmView(String id, Model model) throws Exception{
		ActiveAlarm activeAlarm = activeAlarmService.getOne(id);
			
		model.addAttribute("activeAlarm", activeAlarm);
		
		return "device/activeAlarm/activeAlarm_view";
	}
	
	
	
	/**
	 * 添加活动告警
	 * 
	 */
	@RequestMapping(value="addActiveAlarm",method=RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "添加活动告警")
	public ResponseBean addActiveAlarm(ActiveAlarm activeAlarm, Model model
			,HttpServletRequest request) throws Exception{
		ResponseBean responseBean = new ResponseBean();
		activeAlarmService.save(activeAlarm);
		request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "添加活动告警【TODO】");
		responseBean.setFlag_success();;
		return responseBean;
		
	}
	
	/**
	 * 更新活动告警
	 */
	@RequestMapping(value="updateActiveAlarm",method=RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "更新活动告警")
	public ResponseBean updateActiveAlarm(ActiveAlarm activeAlarm,HttpServletRequest request) throws Exception{
		ResponseBean responseBean = new ResponseBean();
		activeAlarmService.save(activeAlarm);
		request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "更新活动告警【TODO】");
		responseBean.setFlag_success();;
		return responseBean;
	}
	
	
	/**
	 * 删除活动告警
	 */
	@RequestMapping(value="deleteActiveAlarm",method=RequestMethod.POST)
	@SystemControllerLog(description = "删除活动告警")
	public @ResponseBody  ResponseBean deleteactiveAlarm(@RequestParam(value = "ids[]")  String[] ids
			,HttpServletRequest request) throws Exception{
		ResponseBean responseBean = new ResponseBean();
		if(ids == null || ids.length < 1){
			responseBean.setFlag_fail();
			return responseBean;
		}
		List<String> activeAlarmIdList= Lists.newArrayList(ids);

		StringBuilder logDesc = new StringBuilder();
		List<ActiveAlarm> activeAlarmList = activeAlarmService.findByIds(activeAlarmIdList);
		if(activeAlarmList!=null && activeAlarmList.size()>0){
			
			request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "删除活动告警【TODO】");
		}
		activeAlarmService.deleteByIds(activeAlarmIdList);
		responseBean.setFlag_success();
		return responseBean;
	}


}
