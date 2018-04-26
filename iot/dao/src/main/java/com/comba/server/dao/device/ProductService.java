package com.comba.server.dao.device;


import com.comba.server.common.data.device.Product;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.common.CommonService;
import com.comba.server.dao.model.device.ProductEntity;

import java.util.List;


/**
 * 产品服务类
 * @author wengzhonghui
 *
 */
public interface ProductService extends CommonService<Product>{

	
	
	 /**
	 * @param pageNo
	 * @param pageSize
	 * @param product
	 * @return
	 * @throws Exception
	 */
	public Page pagedQuery(int pageNo, int pageSize, Product product,List<String> orderBysList)throws Exception;
	
	/**
	 * 批量删除
	 * @param ids
	 */
	void deleteByIds(List<String> ids);
	
	/**
	 * 通过多个ID查找租户
	 * @param ids
	 * @return
	 */
	public List<Product> findByIds(Iterable<String> ids);
	
	/**
	 * 根据租户ID查找
	 * 
	 * @author wengzhonghui 2017年6月28日
	 * @param tenantId
	 * @return
	 */
	public List<Product> findByTenantId(String tenantId);

	/**
	 * 根据产品类别ID查找产品
	 *
	 * @param categoryId 产品类别id
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
	 * 生成一个通用的产品
	 *
	 * @param tenantId 产品
	 */
	void saveCommonProduct(String tenantId);


	/**
	 * 查询租户的通用产品
	 *
	 * @param tenantId 租户ID
	 * @param type 产品类型
	 * @return
	 */
	List<Product> findAllByTenantIdAndType(String tenantId, Integer type);

	/**
	 * 根据名称查询租户的产品
	 *
	 * @param name 产品名称
	 * @param tenantId 租户ID
	 * @return
	 */
	Product findByNameAndTenantId(String name,String tenantId);

	Product findByTenantIdAndCategoryId(String tenantId,String categoryId);

	/**
	 * 根据租户ID查找该租户下的所有产品列表
	 * 适用于REST请求
	 * @param tenantId
	 * @return
	 */
	List<Object[]> findAllByTenantId(String tenantId);

    /**
     * 判断产品是否属于指定类别的产品
     *
     * @param productId 产品id
     * @return
     */
	boolean queryProductByCategoryName(String productId,String categoryName);
}
