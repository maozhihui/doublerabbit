package com.comba.server.dao.device;


import com.comba.server.dao.model.device.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * 产品 数据层
 * @author wengzhonghui
 *
 */
public interface ProductDao extends JpaRepository<ProductEntity, String>{
	
	public List<ProductEntity> findByTenantId(String tenantId);

	/**
	 * 根据产品类别查询产品个数
	 *
	 * @param categoryId
	 * @return
	 */
	Integer countByCategoryId(String categoryId);

	/**
	 * 删除租户下面的产品
	 *
	 * @param tenantId 租户ID
	 * @return
	 */
	Integer deleteByTenantId(String tenantId);

    /**
     * 查询租户的通用产品
     *
     * @param tenantId 租户ID
     * @param type 产品类型
     * @return
     */
    List<ProductEntity> findAllByTenantIdAndType(String tenantId, Integer type);

	/**
	 * 根据名称查询租户的产品
	 *
	 * @param name 产品名称
	 * @param tenantId 租户ID
	 * @return
	 */
	ProductEntity findByNameAndTenantId(String name,String tenantId);

	/**
	 * 根据租户ID与类别ID查找产品
	 * @param tenantId
	 * @param categoryId
	 * @return
	 */
	ProductEntity findByTenantIdAndCategoryId(String tenantId,String categoryId);
}
