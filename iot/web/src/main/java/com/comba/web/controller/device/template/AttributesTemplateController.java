package com.comba.web.controller.device.template;

import com.alibaba.fastjson.JSON;
import com.comba.server.common.data.device.AttributesTemplate;
import com.comba.server.common.data.device.TelemetryAttributes;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.common.data.web.utils.Constants;
import com.comba.server.dao.common.utils.StringUtils;
import com.comba.server.dao.device.ConfigAttributesService;
import com.comba.server.dao.device.DeviceService;
import com.comba.server.dao.device.template.AttributesTemplateService;
import com.comba.web.common.annotation.SystemControllerLog;
import com.comba.web.common.export.ExportUtils;
import com.comba.web.response.ResponseBean;
import com.comba.web.utils.CommonProperties;
import com.google.common.collect.Lists;
import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

import static com.comba.server.common.data.web.utils.Constants.NUMBER_AND_WORD;

/**
 * 参数模板管理
 * @author wengzhonghui
 *
 */
@Controller
@RequestMapping("/attributesTemplate")
public class AttributesTemplateController {
	@Resource
	private AttributesTemplateService attributesTemplateService;

	@Autowired
    private DeviceService deviceService;

	@Autowired
    private ConfigAttributesService configAttributesService;
	

	@RequestMapping("/list")
	 public String list(@ModelAttribute  Page page, Model model,String deviceTemplateId
			 ,String isView,String isTelemetry) throws Exception {
	    //防止xff攻击
	    if (isTelemetry.matches(NUMBER_AND_WORD)
                && deviceTemplateId.matches(NUMBER_AND_WORD)){
            model.addAttribute("deviceTemplateId", deviceTemplateId);
            model.addAttribute("isTelemetry", isTelemetry);
            return "device/template/attributesTemplate/attributesTemplate_list";
        }else{
	        return "login";
        }
	 }
	
	@ResponseBody
	 @RequestMapping("/datasByPage")
	 public Page getAuditDataByPage(AttributesTemplate attributesTemplate,Page page)throws Exception{
        page = page==null?new Page():page;
        return attributesTemplateService.pagedQuery(page.getPageNo(), page.getPageSize(), attributesTemplate,null);
	 }


    @ResponseBody
    @RequestMapping("/queryByDevId")
    public Page queryByDevId(String devId,Integer type,Page page)throws Exception{
        page = page==null?new Page():page;
        return attributesTemplateService.queryByDevId(page.getPageNo(), page.getPageSize(),devId,type);
    }
	
	
	@ResponseBody
	 @RequestMapping("/datasByExport")
	 public ResponseBean  datasByExport(AttributesTemplate user,Page page,Integer organId,Integer isQueryChild
			 ,String exportFlag,HttpSession session,@RequestParam Map<String, Object> parameterMap
			 )throws Exception{
		page.setPageSize(CommonProperties.maxAllowExportNum);
		Page dataPage = getAuditDataByPage(user, page);
		return ExportUtils.exportDatasByExcel(dataPage, exportFlag, session, parameterMap);
	 }
	
	@RequestMapping("/get_attributesTemplateData")
	@ResponseBody
	public AttributesTemplate get_attributesTemplateData(String attributesTemplateId) throws Exception{
		AttributesTemplate attributesTemplate = null;
		if(StringHelper.isNotEmpty(attributesTemplateId)){
			attributesTemplate = attributesTemplateService.getOne(attributesTemplateId);
		}
		return attributesTemplate;
	}
	
	@GetMapping("/to_attributesTemplate_edit")
	public String to_attributesTemplate_edit(String attributesTemplateId,String isTelemetry, Model model) throws Exception{
		AttributesTemplate attributesTemplate = null;
		if(StringHelper.isNotEmpty(attributesTemplateId)){
			attributesTemplate = attributesTemplateService.getOne(attributesTemplateId);
		}
		model.addAttribute("attributesTemplate", attributesTemplate);
		model.addAttribute("isTelemetry", isTelemetry);

		return "device/template/attributesTemplate/attributesTemplate_edit";
	}
	
	@RequestMapping("/attributesTemplate_view")
	public String attributesTemplate_view(String attributesTemplateId, Model model) throws Exception{
		AttributesTemplate attributesTemplate = null;
		if(StringHelper.isNotEmpty(attributesTemplateId)){
			attributesTemplate = attributesTemplateService.getOne(attributesTemplateId);
		}
		model.addAttribute("attributesTemplate", attributesTemplate);
		
		return "device/template/attributesTemplate/attributesTemplate_view";
	}
	
	
	
	@RequestMapping(value="addAttributesTemplate",method=RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "添加参数模板")
	public ResponseBean addAttributesTemplate(AttributesTemplate attributesTemplate,HttpServletRequest request) throws Exception{
		attributesTemplate.setCreateTime(new Date());
		attributesTemplate.setUpdateTime(new Date());

        AttributesTemplate db = attributesTemplateService.save(attributesTemplate);

        //如果是控制参数，需要同步更新
        if (attributesTemplate.getIsTelemetry() == 0){
            List<String> devIds = deviceService.findByDeviceTemplateId(attributesTemplate.getDeviceTemplateId());
            configAttributesService.saveConfigAttribute(db,devIds);
        }

        request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "添加参数模板【"+attributesTemplate.getName()+"】");
		ResponseBean responseBean = new ResponseBean();
		responseBean.setFlag_success();
		return responseBean;
		
	}
	
	@RequestMapping(value="updateAttributesTemplate",method=RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "更新参数模板")
	public ResponseBean updateAttributesTemplate(AttributesTemplate attributesTemplate,HttpServletRequest request) throws Exception{
		attributesTemplate.setUpdateTime(new Date());
		attributesTemplateService.save(attributesTemplate);
		if(attributesTemplate!=null && attributesTemplate.getName()!=null){
			request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "修改参数模板信息"+attributesTemplate.getName()+"】");
		}
		ResponseBean responseBean = new ResponseBean();
		responseBean.setFlag_success();
		return responseBean;
	}
	@RequestMapping(value="deleteAttributesTemplate",method=RequestMethod.POST)
	@SystemControllerLog(description = "删除参数模板")
	public @ResponseBody  ResponseBean deleteGuser(@RequestParam(value = "ids[]")  String[] attributesTemplateIds
			,HttpServletRequest request) throws Exception{
		ResponseBean responseBean = new ResponseBean();
		if(attributesTemplateIds == null || attributesTemplateIds.length < 1){
			responseBean.setFlag_fail();
			return responseBean;
		}


		List<String> templateIds= Lists.newArrayList(attributesTemplateIds);

		List<AttributesTemplate> attributesTemplates = attributesTemplateService.findByIds(templateIds);
        List<String> names = attributesTemplates.stream().map(AttributesTemplate::getName).collect(Collectors.toList());
        request.setAttribute(Constants.AOP_LOG_DESCRIPTION,"删除模板参数id = "+ JSON.toJSONString(names));

        configAttributesService.deleteByAttributeId(templateIds);
		attributesTemplateService.deleteByIds(attributesTemplateIds);
		responseBean.setFlag_success();
		return responseBean;
	}

	/**
	 * 校验设备模板名称
	 *
	 * @param attributeTemplateId 属性模板ID
	 * @param name                模板名称
	 * @return
	 */
	@PostMapping("/validateAttributeTemplateName")
	@ResponseBody
	public ResponseBean validateAttributeTemplateName(String attributeTemplateId, String deviceTemplateId, String name,Integer isTelemetry) {
		ResponseBean response = new ResponseBean(ResponseBean.FAIL);

		if (StringHelper.isNotEmpty(attributeTemplateId)) {
			AttributesTemplate attributesTemplate = attributesTemplateService.getOne(attributeTemplateId);
			if (!attributesTemplate.getName().equals(name) && attributesTemplateService.countByNameAndDeviceTemplateIdAndIsTelemetry(name, deviceTemplateId,isTelemetry) > 0) {
				response.setMessage("属性参数模板名称已存在");
				return response;
			}

		} else if (attributesTemplateService.countByNameAndDeviceTemplateIdAndIsTelemetry(name, deviceTemplateId,isTelemetry) > 0) {
			response.setMessage("属性参数模板已存在");
			return response;
		}

		response.setFlag_success();
		return response;
	}


    /**
     * 查询产品下面的属性列表
     *
     * @param session
     * @return
     */
    @PostMapping("/get")
    @ResponseBody
    public ResponseBean queryAttributesByProductId(HttpSession session){
        ResponseBean response = new ResponseBean();
        if (session.getAttribute(Constants.CUR_PRODUCT_ID) == null){
            response.setMessage("产品为空，请先选择产品");
        }

        List<Map<String,Object>> list = attributesTemplateService.queryAttributesByProductId(session.getAttribute(Constants.CUR_PRODUCT_ID).toString());
        Map<String,Object> ret = new HashMap<>();
        ret.put("attributes",list);
        response.setRelateData(ret);
        response.setFlag_success();
        return response;
    }
	
}
