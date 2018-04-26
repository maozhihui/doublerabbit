package com.comba.server.dao.rule;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comba.server.dao.model.device.RuleEntity;






/**
 * 规则管理数据层
 * @author wengzhonghui
 *
 */
public interface RuleJpaDao extends JpaRepository<RuleEntity, String>{
	
	
	public List<RuleEntity> findByTenantId(String tenantId);
	
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
