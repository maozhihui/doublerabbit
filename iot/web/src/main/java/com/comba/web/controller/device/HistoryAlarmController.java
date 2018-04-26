package com.comba.web.controller.device;

import com.comba.server.common.data.device.HistoryAlarm;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.common.data.web.utils.Constants;
import com.comba.server.dao.device.alarm.HistoryAlarmService;
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
 * 历史告警 界面控制层实现
 *
 * @作者 sujinxian
 * @创建时间 2018-03-05 09:18:44
 */
@Controller
@RequestMapping("/historyAlarm") 
public class HistoryAlarmController{
	@Resource
	private HistoryAlarmService historyAlarmService;
	
	/**
	 * 进入历史告警列表界面
	 */
	@RequestMapping("/historyAlarm_list")
	 public String list(Model model,HttpSession session) throws Exception {
        model.addAttribute(Constants.CUR_PRODUCT_ID, session.getAttribute(Constants.CUR_PRODUCT_ID));
        return "device/historyAlarm/historyAlarm_list";
	 }
	
	 /**
	 * 获取分页数据
	 */
	 @ResponseBody
	 @RequestMapping("/datasByPage")
	 public Page getDataByPage(HistoryAlarm historyAlarm, Page page)throws Exception{
		 page = page==null?new Page():page;
         return  historyAlarmService.queryAlarmByPage(page.getPageNo(), page.getPageSize(), historyAlarm);
	 }
	
	 /**
	 * 导出列表数据
	 */
	@ResponseBody
	 @RequestMapping("/datasByExport")
	 public ResponseBean ExportData(HistoryAlarm historyAlarm, Page page, HttpSession session
			 , @RequestParam Map<String, Object> parameterMap, String exportFlag
			 )throws Exception{
		 page = page==null?new Page():page;
		 Page returnPage = null;
		 List<String> orderBysList = page.getOrderBys();
		 page.setPageSize(CommonProperties.maxAllowExportNum);
		 returnPage = historyAlarmService.getDataByPage(page.getPageNo(), page.getPageSize(), historyAlarm,orderBysList);
		return ExportUtils.exportDatasByExcel(returnPage, exportFlag, session, parameterMap);
	 }
	
	
	/**
	 * 进入编辑界面
	 */
	@RequestMapping("/to_historyAlarm_edit")
	public String historyAlarmEdit(String id, Model model) throws Exception{
		
		HistoryAlarm historyAlarm = null;
		if(StringHelper.isNotEmpty(id))
			historyAlarm = historyAlarmService.getOne(id);
		model.addAttribute("historyAlarm", historyAlarm);
		
		return "device/historyAlarm/historyAlarm_edit";
	}
	
	/**
	 * 进入查看界面
	 * 
	 */
	@RequestMapping("/to_historyAlarm_view")
	public String historyAlarmView(String id, Model model) throws Exception{
		HistoryAlarm historyAlarm = historyAlarmService.getOne(id);
			
		model.addAttribute("historyAlarm", historyAlarm);
		
		return "device/historyAlarm/historyAlarm_view";
	}
	
	
	
	/**
	 * 添加历史告警
	 * 
	 */
	@RequestMapping(value="addHistoryAlarm",method=RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "添加历史告警")
	public ResponseBean addHistoryAlarm(HistoryAlarm historyAlarm, Model model
			,HttpServletRequest request) throws Exception{
		ResponseBean responseBean = new ResponseBean();
		historyAlarmService.save(historyAlarm);
		request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "添加历史告警【TODO】");
		responseBean.setFlag_success();;
		return responseBean;
		
	}
	
	/**
	 * 更新历史告警
	 */
	@RequestMapping(value="updateHistoryAlarm",method=RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "更新历史告警")
	public ResponseBean updateHistoryAlarm(HistoryAlarm historyAlarm,HttpServletRequest request) throws Exception{
		ResponseBean responseBean = new ResponseBean();
		historyAlarmService.save(historyAlarm);
		request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "更新历史告警【TODO】");
		responseBean.setFlag_success();;
		return responseBean;
	}
	
	
	/**
	 * 删除历史告警
	 */
	@RequestMapping(value="deleteHistoryAlarm",method=RequestMethod.POST)
	@SystemControllerLog(description = "删除历史告警")
	public @ResponseBody  ResponseBean deletehistoryAlarm(@RequestParam(value = "ids[]")  String[] ids
			,HttpServletRequest request) throws Exception{
		ResponseBean responseBean = new ResponseBean();
		if(ids == null || ids.length < 1){
			responseBean.setFlag_fail();
			return responseBean;
		}
		List<String> historyAlarmIdList= Lists.newArrayList(ids);
		StringBuilder logDesc = new StringBuilder();
		List<HistoryAlarm> historyAlarmList = historyAlarmService.findByIds(historyAlarmIdList);
		if(historyAlarmList!=null && historyAlarmList.size()>0){
			
			request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "删除历史告警【TODO】");
		}
		historyAlarmService.deleteByIds(historyAlarmIdList);
		responseBean.setFlag_success();
		return responseBean;
	}


}
