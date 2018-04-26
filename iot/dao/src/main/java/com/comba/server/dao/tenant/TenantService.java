package com.comba.server.dao.tenant;


import java.util.List;

import com.comba.server.common.data.Tenant;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.common.CommonService;
import com.comba.server.dao.model.TenantEntity;


/**
 * 租户服务类
 * @author wengzhonghui
 *
 */
public interface TenantService extends CommonService<Tenant>{

	
	
	 /**
	 * @param pageNo
	 * @param pageSize
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public Page pagedQuery(int pageNo, int pageSize, Tenant tenant,List<String> orderBysList)throws Exception;
	
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
	public List<Tenant> findByIds(Iterable<String> ids);
	
	public List<Tenant> findTenants();

	/**
	 * 添加租户时，需要添加默认的机构（根节点）
	 *
	 * @param tenantJpa 租户
	 */
	public void saveTenantAndOrganization(Tenant tenantJpa) throws Exception;

	/**
	 * 根据租户名称查找租户
	 *
	 * @param name 租户名称
	 * @return
	 */
	TenantEntity findByName(String name);

    /**
     * 根据租户名称统计租户
     *
     * @param name 租户名称
     * @return
     */
    int countByName(String name);
}
