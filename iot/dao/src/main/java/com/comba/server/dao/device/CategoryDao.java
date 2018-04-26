package com.comba.server.dao.device;


import com.comba.server.common.data.device.Category;
import com.comba.server.dao.model.device.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;





/**
 * 产品类别 数据层
 * @author wengzhonghui
 *
 */
public interface CategoryDao extends JpaRepository<CategoryEntity, String>{
	
	
	/**
	 * 根据编码查找
	 * 
	 * @author wengzhonghui 2017年6月5日
	 * @param code
	 * @return
	 */
	List<CategoryEntity> findByCode(String code);

	/**
	 * 根据名称和层级查找累呗
	 *
	 * @param name 名称
	 * @param levelFlag 层级
	 * @return
	 */
	CategoryEntity findByNameAndLevelFlag(String name,Integer levelFlag);

	/**
	 * 设备类别根据名称统计
	 *
	 * @param name 名称
	 * @return
	 */
	int countByName(String name);

}
