package com.comba.web.controller.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import org.hibernate.internal.util.StringHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.comba.server.common.data.Tenant;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.common.data.web.utils.Constants;
import com.comba.server.dao.common.utils.StringUtils;
import com.comba.server.dao.tenant.TenantService;
import com.comba.web.common.annotation.SystemControllerLog;
import com.comba.web.common.export.ExportUtils;
import com.comba.web.response.ResponseBean;
import com.comba.web.utils.CommonProperties;
/**
 * 租户管理
 * @author wengzhonghui
 *
 */
@Controller
@RequestMapping("/tenant")
public class TenantController {
	@Resource
	private TenantService tenantJpaService;
	

	@RequestMapping("/list")
	 public String list(@ModelAttribute  Page page, Model model) throws Exception {
		return "system/tenant/tenant_list";
	 }
	@ResponseBody
	 @RequestMapping("/datasByPage")
	 public Page getAuditDataByPage(Tenant user,Page page)throws Exception{
		 page = page==null?new Page():page;
		 List<String> orderBysList = page.getOrderBys();
		 return tenantJpaService.pagedQuery(page.getPageNo(), page.getPageSize(), user,orderBysList);
	 }
	
	
	@ResponseBody
	 @RequestMapping("/datasByExport")
	 public ResponseBean  datasByExport(Tenant user,Page page,Integer organId,Integer isQueryChild
			 ,String exportFlag,HttpSession session,@RequestParam Map<String, Object> parameterMap
			 )throws Exception{
		page.setPageSize(CommonProperties.maxAllowExportNum);
		Page dataPage = getAuditDataByPage(user, page);
		return ExportUtils.exportDatasByExcel(dataPage, exportFlag, session, parameterMap);
	 }
	
	@RequestMapping("/to_tenant_edit")
	public String toTentEdit(String tenantId, Model model) throws Exception{
		Tenant tenant = null;
		if(StringHelper.isNotEmpty(tenantId)){
			tenant = tenantJpaService.getOne(tenantId);
		}
		model.addAttribute("tenant", tenant);
		
		return "system/tenant/tenant_edit";
	}
	
	@RequestMapping("/tenant_view")
	public String userCheck(String tenantId, Model model) throws Exception{
		Tenant tenant = null;
		if(StringHelper.isNotEmpty(tenantId)){
			tenant = tenantJpaService.getOne(tenantId);
		}
		model.addAttribute("tenant", tenant);
		
		return "system/tenant/tenant_view";
	}
	
	
	
	@RequestMapping(value="addTenant",method=RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "添加租户")
	public ResponseBean addTenant(Tenant tenant,HttpServletRequest request) throws Exception{
		ResponseBean responseBean = new ResponseBean(ResponseBean.FAIL);
		tenantJpaService.saveTenantAndOrganization(tenant);
		request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "添加租户【"+tenant.getName()+"】");
		responseBean.setFlag_success();
		return responseBean;
	}
	
	@RequestMapping(value="updateTenant",method=RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "更新租户")
	public ResponseBean updateTenant(Tenant tenant,HttpServletRequest request) throws Exception{
		ResponseBean responseBean = new ResponseBean(ResponseBean.FAIL);

		tenantJpaService.save(tenant);
		request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "添加租户【"+tenant.getName()+"】");
		if(tenant!=null && tenant.getName()!=null){
			request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "修改租户信息"+tenant.getName()+"】");
		}
		responseBean.setFlag_success();
		return responseBean;
	}
	@RequestMapping(value="deleteTenant",method=RequestMethod.POST)
	@SystemControllerLog(description = "删除租户")
	public @ResponseBody  ResponseBean deleteTenant(@RequestParam(value = "ids[]")  String[] tenantIds
			,HttpServletRequest request) throws Exception{
		ResponseBean responseBean = new ResponseBean();
		if(tenantIds == null || tenantIds.length < 1){
			responseBean.setFlag_fail();
			return responseBean;
		}
		List<String> userIdList= new ArrayList<String>();
		for(String userId : tenantIds){
			userIdList.add(userId);
		}
		StringBuilder logDesc = new StringBuilder();
		List<Tenant> userList = tenantJpaService.findByIds(userIdList);
		if(userList!=null && userList.size()>0){
			for(Tenant tenant : userList){
				if(tenant!=null && tenant.getName()!=null){
					if(logDesc.length()>0) logDesc.append(",");
					logDesc.append(tenant.getName());
				}
			}
			request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "删除租户【"+logDesc.toString()+"】");
		}
		tenantJpaService.deleteByIds(tenantIds);
		responseBean.setFlag_success();
		return responseBean;
	}

	/**
	 * 校验租户名称
	 *
	 * @param name 租户名称
	 * @param tenantId	租户ID
	 * @return
	 */
	@PostMapping("/validateTenantName")
	@ResponseBody
	public ResponseBean validateTenantName(@RequestParam String name,@RequestParam String tenantId){
		ResponseBean response = new ResponseBean(ResponseBean.FAIL);
		if (StringUtils.isNotBlank(tenantId)){
			Tenant tenant = tenantJpaService.getOne(tenantId);
			if (!tenant.getName().equals(name)){
				if (tenantJpaService.findByName(name) != null){
					response.setMessage("该租户已存在");
					return response;
				}
			}
		}else{
			if (tenantJpaService.findByName(name) != null){
				response.setMessage("该租户已存在");
				return response;
			}
		}

		response.setFlag_success();
		return response;
	}
	
}
