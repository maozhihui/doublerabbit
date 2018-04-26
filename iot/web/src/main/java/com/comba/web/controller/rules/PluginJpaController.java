package com.comba.web.controller.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import org.hibernate.internal.util.StringHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.comba.server.common.data.device.Category;
import com.comba.server.common.data.device.PluginJpa;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.common.data.web.utils.Constants;
import com.comba.server.dao.common.utils.StringUtils;
import com.comba.server.dao.plugin.PluginJpaService;
import com.comba.web.common.annotation.SystemControllerLog;
import com.comba.web.common.export.ExportUtils;
import com.comba.web.response.ResponseBean;
import com.comba.web.security.SpringSecurityUtils;
import com.comba.web.ui.TreeNode;
import com.comba.web.utils.CommonProperties;
/**
 * 插件管理
 * @author wengzhonghui
 *
 */
@Controller
@RequestMapping("/plugin")
public class PluginJpaController {
	@Resource
	private PluginJpaService pluginJpaService;
	

	@RequestMapping("/list")
	 public String list(@ModelAttribute  Page page, Model model) throws Exception {
		model.addAttribute("pluginTypes", CommonProperties.PLUGIN_TYPES);
		return "rules/plugin/plugin_list";
	 }
	@ResponseBody
	 @RequestMapping("/datasByPage")
	 public Page getAuditDataByPage(PluginJpa plugin,Page page)throws Exception{
		 page = page==null?new Page():page;
		 Page returnPage = null;
		 List<String> orderBysList = page.getOrderBys();
		 returnPage = pluginJpaService.pagedQuery(page.getPageNo(), page.getPageSize(), plugin,orderBysList);
		 return returnPage;
	 }
	
	
	@ResponseBody
	 @RequestMapping("/datasByExport")
	 public ResponseBean  datasByExport(PluginJpa plugin,Page page,Integer organId,Integer isQueryChild
			 ,String exportFlag,HttpSession session,@RequestParam Map<String, Object> parameterMap
			 )throws Exception{
		page.setPageSize(CommonProperties.maxAllowExportNum);
		Page dataPage = getAuditDataByPage(plugin, page);
		return ExportUtils.exportDatasByExcel(dataPage, exportFlag, session, parameterMap);
	 }
	
	@RequestMapping("/to_plugin_edit")
	public String pluginAdd(@RequestParam Map<String, Object> parameterMap, Model model,Integer selectOrganId) throws Exception{
		
		String pluginId= StringUtils.getString(parameterMap.get("pluginId"));
		PluginJpa plugin = null;
		if(StringHelper.isNotEmpty(pluginId)){
			plugin = pluginJpaService.getOne(pluginId);
		}
		model.addAttribute("plugin", plugin);
		model.addAttribute("pluginTypes", CommonProperties.PLUGIN_TYPES);
		return "rules/plugin/plugin_edit";
	}
	
	@RequestMapping("/plugin_view")
	public String pluginCheck(@RequestParam Map<String, Object> parameterMap, Model model) throws Exception{
		String pluginId= StringUtils.getString(parameterMap.get("pluginId"));
		PluginJpa plugin = null;
		if(StringHelper.isNotEmpty(pluginId)){
			plugin = pluginJpaService.getOne(pluginId);
		}
		model.addAttribute("plugin", plugin);
		model.addAttribute("pluginTypes", CommonProperties.PLUGIN_TYPES);
		return "rules/plugin/plugin_view";
	}
	
	
	
	@RequestMapping(value="addPlugin",method=RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "添加插件")
	public ResponseBean addPlugin(PluginJpa plugin,String[] checkdel, Model model
			,HttpServletRequest request) throws Exception{
		plugin.setTenantId(SpringSecurityUtils.getCurrentUserTenantId());
		pluginJpaService.save(plugin);
		request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "添加插件【"+plugin.getName()+"】");
		ResponseBean responseBean = new ResponseBean();
		responseBean.setFlag_success();
		return responseBean;
		
	}
	
	@RequestMapping(value="updatePlugin",method=RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "更新插件")
	public ResponseBean updatePlugin(PluginJpa plugin,HttpServletRequest request) throws Exception{
		plugin.setTenantId(SpringSecurityUtils.getCurrentUserTenantId());
		pluginJpaService.save(plugin);
		request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "添加插件【"+plugin.getName()+"】");
		if(plugin!=null && plugin.getName()!=null){
			request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "修改插件信息"+plugin.getName()+"】");
		}
		ResponseBean responseBean = new ResponseBean();
		responseBean.setFlag_success();
		return responseBean;
	}
	@RequestMapping(value="deletePlugin",method=RequestMethod.POST)
	@SystemControllerLog(description = "删除插件")
	public @ResponseBody  ResponseBean deleteGplugin(@RequestParam(value = "ids[]")  String[] pluginIds
			,HttpServletRequest request) throws Exception{
		ResponseBean responseBean = new ResponseBean();
		if(pluginIds == null || pluginIds.length < 1){
			responseBean.setFlag_fail();
			return responseBean;
		}
		List<String> pluginIdList= new ArrayList<String>();
		for(String pluginId : pluginIds){
			pluginIdList.add(pluginId);
		}
		StringBuilder logDesc = new StringBuilder();
		List<PluginJpa> pluginList = pluginJpaService.findByIds(pluginIdList);
		if(pluginList!=null && pluginList.size()>0){
			for(PluginJpa plugin : pluginList){
				if(plugin!=null && plugin.getName()!=null){
					if(logDesc.length()>0) logDesc.append(",");
					logDesc.append(plugin.getName());
				}
			}
			request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "删除插件【"+logDesc.toString()+"】");
		}
		pluginJpaService.deleteByIds(pluginIds);
		responseBean.setFlag_success();;
		return responseBean;
	}
	
	@ResponseBody
    @RequestMapping( value = "/getTreeData")
    public List<TreeNode> getTreeData(PluginJpa plugin,String isExpand) throws Exception{
		 
        List<PluginJpa> datas = this.pluginJpaService.findByTenantId(SpringSecurityUtils.getCurrentUserTenantId());;
        if(datas==null) return null;
        List<TreeNode> nodes = new ArrayList<TreeNode>();
        if(datas!=null && datas.size()>0){ 
        	for(PluginJpa t:datas){
        		TreeNode node = new TreeNode();
        		node.setId(t.getPluginId());
        		node.setText(t.getName());
        		node.setParentId("1");
        		nodes.add(node);
        	}
        }
        List<TreeNode> treeDatas = TreeNode.buildTree(nodes);
    	return treeDatas;
    }
	
}
