package com.comba.web.controller.auditLog;


import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.comba.server.common.data.AuditLog;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.auditLog.AuditLogService;
import com.comba.server.dao.model.AuditLogEntity;
import com.comba.web.common.export.ExportUtils;
import com.comba.web.response.ResponseBean;
import com.comba.web.security.SpringSecurityUtils;
import com.comba.web.utils.CommonProperties;



/**
 * 日志审计管理
 * @author wengzhonghui
 *
 */
@Controller
@RequestMapping("/audit")
public class AuditLogController
{
	
	@Resource
	private AuditLogService auditLogService;
	
	/**
	 * 日志列表
	 * @param page
	 * @param parameterMap
	 * @param model
	 * @return
	 */
	 @RequestMapping("audit_list")
	 public String auditList() {
		return "auditLog/audit_list";
	}
	 @RequestMapping("operate_list")
	 public String operateList() {
		return "auditLog/operate_list";
	}

	 @ResponseBody
	 @RequestMapping("/datasByPage")
	 public Page getAuditDataByPage(AuditLog auditLog,Page page,String type){
		 page = page==null?new Page():page;
		 String userId=SpringSecurityUtils.getCurrentUserId();
		 boolean isAll = SpringSecurityUtils.isSupperAdmin();
		 return auditLogService.pagedQuery(page.getPageNo()
				 , page.getPageSize(), auditLog ,type,userId,isAll);
	 }
	 
	 @ResponseBody
	 @RequestMapping("/datasByExport")
	 public ResponseBean getAuditDataByPageExport(AuditLog auditLog,Page page,String type
			 ,String exportFlag,HttpSession session,@RequestParam Map<String, Object> parameterMap){
		page.setPageSize(CommonProperties.maxAllowExportNum);
		Page dataPage = getAuditDataByPage(auditLog, page, type);
		return ExportUtils.exportDatasByExcel(dataPage, exportFlag, session, parameterMap);
	 }
	 
	 /**
	  * 所有日志，不做权限控制
	 * @param auditLog
	 * @param page
	 * @return
	 */
	@ResponseBody
	 @RequestMapping("/datasByPageByAll")
	 public Page getAllAuditDataByPage(AuditLog auditLog,Page page,String type){
		 page = page==null?new Page():page;
		 return auditLogService.pagedQuery(page.getPageNo(), page.getPageSize(), auditLog);
	 }

}
