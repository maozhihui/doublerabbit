package com.comba.server.dao.device;


import java.util.List;
import java.util.Optional;

import com.comba.server.common.data.device.Category;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.common.CommonService;




/**
 * 产品服务类
 * @author wengzhonghui
 *
 */
public interface CategoryService extends CommonService<Category>{

	
	
	 /**
	 * @param pageNo
	 * @param pageSize
	 * @param category
	 * @return
	 * @throws Exception
	 */
	public Page pagedQuery(int pageNo, int pageSize, Category category,List<String> orderBysList)throws Exception;
	
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
	public List<Category> findByIds(Iterable<String> ids);
	
	/**
	 * 根据编码查找
	 * 
	 * @author wengzhonghui 2017年6月5日
	 * @param code
	 * @return
	 */
	List<Category> findByCode(String code);
	
	/**
	 * 根据Category的属性查找 
	 * 
	 * @author wengzhonghui 2017年6月5日
	 * @param category
	 * @return
	 */
	List<Category> findByCategory(Category category);


	/**
	 * 设备类别根据名称统计
	 *
	 * @param name 名称
	 * @return
	 */
	int countByName(String name);

	/**
	 * 根据设备名称类别查找
	 * @param name
	 * @return
	 */
	Category findByNameAndLevelFlag(String name,int level);

	/**
	 * 通过父级ID查找子级的最新CODE编码
	 * @param categoryId
	 * @return
	 */
	Optional<String> findLastCodeByParentId(String categoryId);
}
