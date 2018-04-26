package com.comba.web.controller.device;

import com.alibaba.fastjson.JSON;
import com.comba.server.actors.service.ActorService;
import com.comba.server.actors.session.AbstractCmdMsg;
import com.comba.server.common.data.Device;
import com.comba.server.common.data.device.ConfigAttributes;
import com.comba.server.common.data.id.DeviceId;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.common.data.web.utils.UUIDUtils;
import com.comba.server.common.msg.cmd.*;
import com.comba.server.dao.common.utils.StringUtils;
import com.comba.server.dao.device.ConfigAttributesService;
import com.comba.server.dao.device.DeviceService;
import com.comba.web.response.ResponseBean;
import org.hibernate.internal.util.StringHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 配置参数管理
 * @author wengzhonghui
 *
 */
@Controller
@RequestMapping("/configAttributes")
public class ConfigAttributesController {
	@Resource
	private ConfigAttributesService configAttributesService;
	@Resource
	private DeviceService deviceService;
	@Resource
	private ActorService actorService ;
    
	 @RequestMapping("/list")
	 public String list(@ModelAttribute  Page page, Model model,String devId) throws Exception {
        return "device/configAttributes/configAttributes_list";
	 }
	
	@ResponseBody
	 @RequestMapping("/datasByPage")
	 public Page getAuditDataByPage(ConfigAttributes configAttributes,Page page)throws Exception{
		 page = page==null?new Page():page;
		 Page returnPage = null;
		 List<String> orderBysList = page.getOrderBys();
		 returnPage = configAttributesService.pagedQuery(page.getPageNo(), page.getPageSize(), configAttributes,orderBysList);
		 return returnPage;
	 }

	
	@RequestMapping("/to_configAttributes_edit")
	public String userAdd(@RequestParam Map<String, Object> parameterMap, Model model,Integer selectOrganId) throws Exception{
		
		String configAttributesId= StringUtils.getString(parameterMap.get("configAttributesId"));
		ConfigAttributes configAttributes = null;
		if(StringHelper.isNotEmpty(configAttributesId)){
			configAttributes = configAttributesService.getOne(configAttributesId);
		}
		model.addAttribute("configAttributes", configAttributes);
		
		return "device/configAttributes/configAttributes_edit";
	}
	
	@RequestMapping("/configAttributes_view")
	public String userCheck(@RequestParam Map<String, Object> parameterMap, Model model) throws Exception{
		String configAttributesId= StringUtils.getString(parameterMap.get("configAttributesId"));
		ConfigAttributes configAttributes = null;
		if(StringHelper.isNotEmpty(configAttributesId)){
			configAttributes = configAttributesService.getOne(configAttributesId);
		}
		model.addAttribute("configAttributes", configAttributes);
		
		return "device/configAttributes/configAttributes_view";
	}
	
	@RequestMapping("/get_ConfigAttributesData")
	@ResponseBody
	public ConfigAttributes get_ConfigAttributesData(@RequestParam Map<String, Object> parameterMap, Model model) throws Exception{
		
		String attributesTemplateId= StringUtils.getString(parameterMap.get("configAttributeId"));
		ConfigAttributes attributesTemplate = null;
		if(StringHelper.isNotEmpty(attributesTemplateId)){
			attributesTemplate = configAttributesService.getOne(attributesTemplateId);
		}
		return attributesTemplate;
	}
	
	// 实时查询
	@RequestMapping(value="queryConfigAttributes")
	@ResponseBody
	public ResponseBean queryConfigAttributes(@RequestParam Map<String, Object> parameterMap, Model model,
			HttpSession session) throws Exception{
		ResponseBean responseBean = new ResponseBean();
		String configAttributesId= StringUtils.getString(parameterMap.get("configAttributeId"));
		ConfigAttributes configAttributes = null;
		if(StringHelper.isNotEmpty(configAttributesId)){
			configAttributes = configAttributesService.getOne(configAttributesId);
		}
		// 查询当前设备状态, 属性Id不存在
		if(configAttributes == null) {
			responseBean.setMessage("设备没有"+ configAttributesId +"属性Id");
			responseBean.setFlag_fail();
			return responseBean;
		}
		
		String devId= StringUtils.getString(parameterMap.get("devId"));	
		Device device  = null;
		if(StringHelper.isNotEmpty(devId)) {
			device = deviceService.getOne(devId);
		}
		
		TenantId tenantId = new TenantId(UUIDUtils.toUUID(device.getTenantId()));
		DeviceId gatewayId = null;
		int isGateway = device.getIsGateWay();		// 0-设备，1-网关
		//当设备不是网关，并且有网关
		if(isGateway == 0 && device.getGatewayId() != null) {
			// 有网关的设备，是网关与平台通信的，故需要判断网关的状态
			Device gatewayDevice = deviceService.getOne(device.getGatewayId());
			gatewayId = new DeviceId(UUIDUtils.toUUID(device.getGatewayId()));
			
			if(gatewayDevice.getStatus() == 0){
				responseBean.setMessage("设备连接的网关处于离线状态，操作失败！");
				responseBean.setFlag_fail();
				return responseBean;
			}
		}
		else {
			// 1)设备不是网关，并且没有网关(直连平台设备)
			// 2)设备是网关
			gatewayId = new DeviceId(UUIDUtils.toUUID(device.getDevId()));
			if(device.getStatus() == 0){
				responseBean.setMessage("设备处于离线状态，操作失败！");
				responseBean.setFlag_fail();
				return responseBean;
			}
		}
		
		QueryCmdRequestMsg queryCmdMsg = new QueryCmdRequestMsg(new DeviceId(UUIDUtils.toUUID(devId)));
		queryCmdMsg.addAttribute(configAttributes.getAttributeName());
		queryCmdMsg.setHardIdentity(device.getHardIdentity());
		queryCmdMsg.setGatewayId(device.getGatewayId());
		BasicCommandMsg<QueryCmdRequestMsg> cmdMsg = new BasicCommandMsg<QueryCmdRequestMsg>(tenantId, gatewayId, session.getId(), queryCmdMsg);
		Date createTime = new Date();	
		cmdMsg.setCreateTime(createTime.getTime());
		// 下发给后台
		actorService.onCommandProcess(cmdMsg);

		// 返回操作成功
		responseBean.setFlag_success();
		return responseBean;
	}
	
	// 实时设置
	@RequestMapping(value="setConfigAttributes")
	@ResponseBody
	public ResponseBean setConfigAttributes(@RequestParam String configAttributes,String devId,
			HttpSession session) throws Exception{
		ResponseBean responseBean = new ResponseBean();

        SetCmdRequestMsg setCmdMsg = new SetCmdRequestMsg(new DeviceId(UUIDUtils.toUUID(devId)));
        List<ConfigAttributes> attributesList = JSON.parseArray(configAttributes,ConfigAttributes.class);
        for (ConfigAttributes attribute: attributesList){
            setCmdMsg.addAttribute(attribute.getAttributeName(),attribute.getValue());
        }

		Device device  = null;
		if(StringHelper.isNotEmpty(devId)) {
			device = deviceService.getOne(devId);
		}
		
		DeviceId gatewayId = null;

		// 接网关的设备，是网关与平台通信的，故需要判断网关的状态
		if(device.getGatewayId() != null) {
			Device gatewayDevice = deviceService.getOne(device.getGatewayId());
			gatewayId = new DeviceId(UUIDUtils.toUUID(device.getGatewayId()));
			
			if(gatewayDevice.getStatus() == 0){		// 0-离线，1-在线
				responseBean.setMessage("设备连接的网关处于离线状态，操作失败！");
				return responseBean;
			}
		}
		else {
			// 1)普通设备，没有网关(直连平台设备)
			// 2)设备是网关
			gatewayId = new DeviceId(UUIDUtils.toUUID(device.getDevId()));
			if(device.getStatus() == 0){
				responseBean.setMessage("设备处于离线状态，操作失败！");
				return responseBean;
			}
		}

        TenantId tenantId = new TenantId(UUIDUtils.toUUID(device.getTenantId()));
        setCmdMsg.setHardIdentity(device.getHardIdentity());
		setCmdMsg.setGatewayId(device.getGatewayId());
        BasicCommandMsg<SetCmdRequestMsg> cmdMsg = new BasicCommandMsg<SetCmdRequestMsg>(tenantId,gatewayId,session.getId(),setCmdMsg);
		Date createTime = new Date();	
		cmdMsg.setCreateTime(createTime.getTime());
		
		// 下发给后台
		actorService.onCommandProcess(cmdMsg);
		
		// 返回操作成功
		responseBean.setFlag_success();
		return responseBean;
	}
	
	// 命令响应
	@RequestMapping(value="respConfigAttributes")
	@ResponseBody
	public List<Map<String, Object>> respConfigAttributes(@RequestParam Map<String, Object> parameterMap, Model model,
			HttpSession session) throws Exception{
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String configAttributesId = StringUtils.getString(parameterMap.get("configAttributeId"));
		String deviceId= StringUtils.getString(parameterMap.get("devId"));

		BasicCommandMsg<QueryCmdResponseMsg> queryRespMsg = AbstractCmdMsg.queryRespMsgMap.get(deviceId + session.getId());
		BasicCommandMsg<SetCmdResponseMsg> setRespMsg = AbstractCmdMsg.setRespMsgMap.get(deviceId + session.getId());
		if(queryRespMsg != null) {
			// 删除响应信息
			AbstractCmdMsg.queryRespMsgMap.remove(deviceId + session.getId());
			
			Map<String, Object> map = new HashMap<>();
			map.put("command", "query");
			map.put("time", queryRespMsg.getCreateTime());
			if(queryRespMsg.getData().getStatusCode() == 0) {
				map.put("flag", "success");
			}
			else {
				map.put("flag", "fail");
			}
			list.add(map);
		}
		
		if(setRespMsg != null) {
			// 删除响应信息
			AbstractCmdMsg.setRespMsgMap.remove(deviceId + session.getId());
			
			Map<String, Object> map = new HashMap<>();
			map.put("command", "set");
			map.put("time", setRespMsg.getCreateTime());
			if(setRespMsg.getData().getStatusCode() == 0) {
				map.put("flag", "success");
				
				// 消息下发成功，更新设置值到数据库(已改到deviceActor里面)
				//SetCmdResponseMsg setCmdRespMsg = (SetCmdResponseMsg)setRespMsg.getData();
				//configAttributesService.save(new DeviceId(UUIDUtils.toUUID(deviceId)), setCmdRespMsg.getAttributes());
			}
			else {
				map.put("flag", "fail");
			}
			list.add(map);
		}
		
		return list;
	}
}
