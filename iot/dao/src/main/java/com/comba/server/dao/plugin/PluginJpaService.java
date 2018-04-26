package com.comba.server.dao.plugin;


import java.util.List;

import com.comba.server.common.data.device.PluginJpa;
import com.comba.server.common.data.device.Rule;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.common.data.id.PluginJpaId;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.data.page.TextPageData;
import com.comba.server.common.data.page.TextPageLink;
import com.comba.server.common.data.plugin.PluginMetaData;
import com.comba.server.dao.common.CommonService;



/**
 * 插件管理服务类
 * @author wengzhonghui
 *
 */
public interface PluginJpaService extends CommonService<PluginJpa>{

	
	
	 /**
	 * @param pageNo
	 * @param pageSize
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public Page pagedQuery(int pageNo, int pageSize, PluginJpa device,List<String> orderBysList)throws Exception;
	
	/**
	 * 批量删除
	 * @param ids
	 */
	void deleteByIds(String[] ids);
	
	/**
	 * 通过多个ID查找租户
	 * @param ids
	 * @return
	 */
	public List<PluginJpa> findByIds(Iterable<String> ids);
	
	/**
	 * 通过租户ID查找
	 * 
	 * @author wengzhonghui 2017年6月27日
	 * @param ruleId
	 * @return
	 */
	public List<PluginJpa> findByTenantId(String tenantId);
	
	/**
	 * 查找系统插件
	 * @return
	 */
	public List<PluginJpa> findSystemPlugins();
	
	/**
	 * 查找租户插件
	 * @param tenantId
	 * @return
	 */
	public List<PluginJpa> findTenantPlugins(TenantId tenantId);
	
	/**
	 * 通过ID查找插件
	 * @param pluginId
	 * @return
	 */
	public PluginJpa findPluginById(PluginJpaId pluginId);

	/**
	 * 删除租户下面的插件
	 *
	 * @param tenantId 租户ID
	 * @return
	 */
	Integer deleteByTenantId(String tenantId);
}
