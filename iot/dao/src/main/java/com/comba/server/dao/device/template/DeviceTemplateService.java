package com.comba.server.dao.device.template;


import java.util.List;

import com.comba.server.common.data.device.DeviceTemplate;
import com.comba.server.common.data.rest.CategoryTemplateRest;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.common.CommonService;

import javax.persistence.criteria.CriteriaBuilder;


/**
 * 设备模板服务类
 * @author wengzhonghui
 *
 */
public interface DeviceTemplateService extends CommonService<DeviceTemplate>{

	
	
	 /**
	 * @param pageNo
	 * @param pageSize
	 * @param deviceTemplate
	 * @return
	 * @throws Exception
	 */
	public Page pagedQuery(int pageNo, int pageSize, DeviceTemplate deviceTemplate,List<String> orderBysList)throws Exception;
	
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
	public List<DeviceTemplate> findByIds(Iterable<String> ids);

	/**
	 * 通过多个ID查找设备模板
	 *
	 * @param categoryId
	 * @return
	 */
	Integer countByCategoryId(String categoryId);

	/**
	 * 根据名称查询设备类别
	 *
	 * @param name 名称
	 * @return
	 */
	int countByName(String name);

    /**
     * 根据名称查询设备类别
     *
     * @param name 名称
     * @return
     */
    DeviceTemplate findByName(String name);

	List<CategoryTemplateRest> findAllTemplate();

	DeviceTemplate findByCategoryCode(String code);
}
