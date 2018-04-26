package com.comba.web.controller.device.template;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import com.comba.server.dao.device.DeviceService;
import com.google.common.collect.Lists;
import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.comba.server.common.data.device.DeviceTemplate;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.common.data.web.utils.Constants;
import com.comba.server.dao.common.utils.StringUtils;
import com.comba.server.dao.device.template.DeviceTemplateService;
import com.comba.web.common.annotation.SystemControllerLog;
import com.comba.web.common.export.ExportUtils;
import com.comba.web.response.ResponseBean;
import com.comba.web.utils.CommonProperties;
/**
 * 设备模板管理
 * @author wengzhonghui
 *
 */
@Controller
@RequestMapping("/deviceTemplate")
public class DeviceTemplateController {
	@Resource
	private DeviceTemplateService deviceTemplateService;

	@Autowired
    private DeviceService deviceService;
	

	@RequestMapping("/list")
	 public String list(@ModelAttribute  Page page, Model model) throws Exception {
		return "device/template/deviceTemplate/deviceTemplate_list";
	 }
	@ResponseBody
	 @RequestMapping("/datasByPage")
	 public Page getAuditDataByPage(DeviceTemplate deviceTemplate,Page page)throws Exception{
		 page = page==null?new Page():page;
		 Page returnPage = null;
		 List<String> orderBysList = page.getOrderBys();
		 returnPage = deviceTemplateService.pagedQuery(page.getPageNo(), page.getPageSize(), deviceTemplate,orderBysList);
		 return returnPage;
	 }
	
	
	@ResponseBody
	 @RequestMapping("/datasByExport")
	 public ResponseBean  datasByExport(DeviceTemplate user,Page page,Integer organId,Integer isQueryChild
			 ,String exportFlag,HttpSession session,@RequestParam Map<String, Object> parameterMap
			 )throws Exception{
		page.setPageSize(CommonProperties.maxAllowExportNum);
		Page dataPage = getAuditDataByPage(user, page);
		return ExportUtils.exportDatasByExcel(dataPage, exportFlag, session, parameterMap);
	 }
	
	@RequestMapping("/to_deviceTemplate_edit_index")
	public String toDeviceTemplateEditIndex(String deviceTemplateId, Model model) throws Exception{
		DeviceTemplate deviceTemplate = null;
		if(StringHelper.isNotEmpty(deviceTemplateId)){
			deviceTemplate = deviceTemplateService.getOne(deviceTemplateId);
		}
		model.addAttribute("deviceTemplate", deviceTemplate);
		
		return "device/template/deviceTemplate/deviceTemplateEditIndex";
	}
	
	@RequestMapping("/to_deviceTemplate_view_index")
	public String toDeviceTemplateViewIndex(String deviceTemplateId, Model model) throws Exception{
		DeviceTemplate deviceTemplate = null;
		if(StringHelper.isNotEmpty(deviceTemplateId)){
			deviceTemplate = deviceTemplateService.getOne(deviceTemplateId);
		}
		model.addAttribute("deviceTemplate", deviceTemplate);
		
		return "device/template/deviceTemplate/deviceTemplateEditIndex";
	}
	
	@RequestMapping("/to_deviceTemplate_edit")
	public String userAdd(String deviceTemplateId, Model model) throws Exception{
		DeviceTemplate deviceTemplate = null;
		if(StringHelper.isNotEmpty(deviceTemplateId)){
			deviceTemplate = deviceTemplateService.getOne(deviceTemplateId);
		}
		model.addAttribute("deviceTemplate", deviceTemplate);
		
		return "device/template/deviceTemplate/deviceTemplate_edit";
	}
	
	@RequestMapping("/deviceTemplate_view")
	public String userCheck(String deviceTemplateId, Model model) throws Exception{
		DeviceTemplate deviceTemplate = null;
		if(StringHelper.isNotEmpty(deviceTemplateId)){
			deviceTemplate = deviceTemplateService.getOne(deviceTemplateId);
		}
		model.addAttribute("deviceTemplate", deviceTemplate);
		
		return "device/template/deviceTemplate/deviceTemplate_view";
	}
	
	
	
	@RequestMapping(value="addDeviceTemplate",method=RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "添加设备模板")
	public ResponseBean addDeviceTemplate(DeviceTemplate deviceTemplate,HttpServletRequest request) throws Exception{
		ResponseBean responseBean = new ResponseBean();
		deviceTemplate.setCreateTime(new Date());
		deviceTemplate.setUpdateTime(new Date());

		deviceTemplateService.save(deviceTemplate);
		request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "添加设备模板【"+deviceTemplate.getName()+"】");

		if (StringUtils.isBlank(deviceTemplate.getDeviceTemplateId())){
			responseBean.setMessage("数据库插入模板数据失败");
			responseBean.setFlag_fail();
		}else{
			//需要返回模板id给前端
			Map<String ,Object> deviceIdMap = new HashMap<>();
			deviceIdMap.put("deviceTemplateId",deviceTemplate.getDeviceTemplateId());
			responseBean.setRelateData(deviceIdMap);
		}

		responseBean.setFlag_success();
		return responseBean;
		
	}
	
	@RequestMapping(value="updateDeviceTemplate",method=RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "更新设备模板")
	public ResponseBean updateDeviceTemplate(DeviceTemplate deviceTemplate,HttpServletRequest request) throws Exception{
		deviceTemplate.setUpdateTime(new Date());
		deviceTemplateService.save(deviceTemplate);
		request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "添加设备模板【"+deviceTemplate.getName()+"】");
		if(deviceTemplate!=null && deviceTemplate.getName()!=null){
			request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "修改设备模板信息"+deviceTemplate.getName()+"】");
		}
		ResponseBean responseBean = new ResponseBean();
		responseBean.setFlag_success();
		return responseBean;
	}
	@RequestMapping(value="deleteDeviceTemplate",method=RequestMethod.POST)
	@SystemControllerLog(description = "删除设备模板")
	public @ResponseBody  ResponseBean deleteDeviceTemplate(@RequestParam(value = "ids[]")  String[] deviceTemplateIds
			,HttpServletRequest request) throws Exception{
		ResponseBean responseBean = new ResponseBean(ResponseBean.FAIL);
		if(deviceTemplateIds == null || deviceTemplateIds.length < 1){
		    responseBean.setMessage("设备模板id不存在");
			return responseBean;
		}

        List<String> deviceTemplateIdList= Lists.newArrayList(deviceTemplateIds);
        if (deviceService.countByDeviceTemplateId(deviceTemplateIdList) > 0){
            responseBean.setMessage("设备模板存在绑定的设备，不能删除");
            return responseBean;
        }

		StringBuilder logDesc = new StringBuilder();
		List<DeviceTemplate> templateList = deviceTemplateService.findByIds(deviceTemplateIdList);
		if(templateList!=null && templateList.size()>0){
			for(DeviceTemplate deviceTemplate : templateList){
                if(logDesc.length()>0)
                    logDesc.append(",");
                logDesc.append(deviceTemplate.getName());
			}
			request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "删除设备模板【"+logDesc.toString()+"】");
		}

		deviceTemplateService.deleteByIds(deviceTemplateIds);
		responseBean.setFlag_success();
		return responseBean;
	}
	
	@RequestMapping("/insert")
	 public String insert(@ModelAttribute  Page page, Model model) throws Exception {
		return "device/template/deviceTemplate/deviceTemplate_insert";
	 }


	/**
	 * 校验设备模板名称
	 *
	 * @param deviceTemplateId 模板ID
	 * @param name 模板名称
	 * @return
	 */
	 @PostMapping("/validateDeviceTemplateName")
	 @ResponseBody
	 public ResponseBean validateDeviceTemplateName(String deviceTemplateId,String name){
		ResponseBean response = new ResponseBean(ResponseBean.FAIL);

		if (StringHelper.isNotEmpty(deviceTemplateId)){
			DeviceTemplate deviceTemplate = deviceTemplateService.getOne(deviceTemplateId);
			if (!deviceTemplate.getName().equals(name) && deviceTemplateService.countByName(name) > 0){
				response.setMessage("参数模板名称已存在");
				return response;
			}

		} else if (deviceTemplateService.countByName(name) > 0){
			response.setMessage("参数模板已存在");
			return response;
		}

		response.setFlag_success();
		return response;
	 }
}
