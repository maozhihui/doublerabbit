package com.comba.web.controller.system;

import com.comba.server.common.data.UpgradeRecord;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.common.data.web.utils.Constants;
import com.comba.server.dao.common.utils.StringUtils;
import com.comba.server.dao.upgrade.record.UpgradeRecordService;
import com.comba.web.common.annotation.SystemControllerLog;
import com.comba.web.common.export.ExportUtils;
import com.comba.web.response.ResponseBean;
import com.comba.web.security.SpringSecurityUtils;
import com.comba.web.utils.CommonProperties;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



/**
 *  界面控制层实现
 *
 * @作者 sujinxian
 * @创建时间 2018-01-25 16:21:07
 */
@Controller
@RequestMapping("/upgradeRecord")
public class UpgradeRecordController {
	@Resource
	private UpgradeRecordService updateRecordService;
	
	/**
	 * 进入列表界面
	 */
	@RequestMapping("/upgradeRecord_list")
	 public String list(Model model) throws Exception {
		return "system/upgradeRecord/upgradeRecord_list";
	 }
	
	 /**
	 * 获取分页数据
	 */
	 @ResponseBody
	 @RequestMapping("/datasByPage")
	 public Page getDataByPage(UpgradeRecord updateRecord, Page page)throws Exception{
		 page = page==null?new Page():page;
		 List<String> orderBysList = page.getOrderBys();
         String tenantId = SpringSecurityUtils.getCurrentUserTenantId();
         if (StringUtils.isNotBlank(tenantId)){
             updateRecord.setTenantId(tenantId);
         }
		 return updateRecordService.getDataByPage(updateRecord,page.getPageNo(), page.getPageSize());
	 }
	
	 /**
	 * 导出列表数据
	 */
	@ResponseBody
	 @RequestMapping("/datasByExport")
	 public ResponseBean ExportData(UpgradeRecord updateRecord, Page page, HttpSession session
			 , @RequestParam Map<String, Object> parameterMap, String exportFlag
			 )throws Exception{
		 page = page==null?new Page():page;
		 Page returnPage = null;
		 List<String> orderBysList = page.getOrderBys();
		 page.setPageSize(CommonProperties.maxAllowExportNum);
		 returnPage = updateRecordService.getDataByPage(page.getPageNo(), page.getPageSize(), updateRecord,orderBysList);
		return ExportUtils.exportDatasByExcel(returnPage, exportFlag, session, parameterMap);
	 }


	/**
	 * 删除
	 */
	@RequestMapping(value="deleteUpgradeRecord",method=RequestMethod.POST)
	@SystemControllerLog(description = "删除")
	public @ResponseBody  ResponseBean deleteupdateRecord(@RequestParam(value = "ids[]")  String[] ids
			,HttpServletRequest request) throws Exception{
		ResponseBean responseBean = new ResponseBean();
		if(ids == null || ids.length < 1){
			responseBean.setFlag_fail();
			return responseBean;
		}
		List<String> updateRecordIdList= new ArrayList<String>();
		for(String updateRecordId : ids){
			updateRecordIdList.add(updateRecordId);
		}
		StringBuilder logDesc = new StringBuilder();
		List<UpgradeRecord> updateRecordList = updateRecordService.findByIds(updateRecordIdList);
		if(updateRecordList!=null && updateRecordList.size()>0){
			
			request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "删除【TODO】");
		}
		updateRecordService.deleteByIds(ids);
		responseBean.setFlag_success();
		return responseBean;
	}


}
