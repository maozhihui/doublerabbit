package com.comba.server.dao.rule;


import java.util.List;

import com.comba.server.common.data.device.Rule;
import com.comba.server.common.data.id.RuleId;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.data.page.TextPageData;
import com.comba.server.common.data.page.TextPageLink;
import com.comba.server.common.data.rule.RuleMetaData;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.common.CommonService;
import com.comba.server.dao.model.device.RuleEntity;




/**
 * 插件管理服务类
 * @author wengzhonghui
 *
 */
public interface RuleJpaService extends CommonService<Rule>{

	
	
	 /**
	 * @param pageNo
	 * @param pageSize
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public Page pagedQuery(int pageNo, int pageSize, Rule device,List<String> orderBysList)throws Exception;
	
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
	public List<Rule> findByIds(Iterable<String> ids);
	
	/**
	 * 查找所有系统规则
	 * @return
	 */
	public List<Rule> findSystemRules();
	
	
	/**
	 * 查找所有租户规则
	 * @param tenantId
	 * @return
	 */
	List<Rule> findTenantRules(TenantId tenantId);
	
	/**
	 * 通过ruleId查找记录
	 * @param ruleId
	 * @return
	 */
	Rule findRuleById(RuleId ruleId);
	/**
	 * 通过租户ID查找
	 * 
	 * @author wengzhonghui 2017年6月27日
	 * @param ruleId
	 * @return
	 */
	public List<Rule> findByTenantId(String tenantId);
	
	/**
	 * 根据租户ID统计规则数量
	 * 
	 * @author wengzhonghui 2017年7月3日
	 * @param tenantId 租户ID
	 * @return
	 */
	Long countByTenantId(String tenantId);

	/**
	 * 删除租户下面的规则
	 *
	 * @param tenantId 租户ID
	 * @return
	 */
	Integer deleteByTenantId(String tenantId);

	/**
	 * 查询租户下面的产品规则数
	 *
	 * @param tenantId 租户ID
	 * @param productId 产品ID
	 * @return
	 */
	Long countByTenantIdAndProductId(String tenantId,String productId);

	/**
	 * 根据名称统计规则
	 *
	 * @param name 名称
	 * @return
	 */
	int countByName(String name);
	
}
