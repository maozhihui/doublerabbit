package com.comba.server.dao.device.template;


import java.util.List;
import java.util.Map;

import com.comba.server.common.data.device.AttributesTemplate;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.common.CommonService;
import com.comba.server.dao.model.device.AttributesTemplateEntity;


/**
 * 设备模板服务类
 * @author wengzhonghui
 *
 */
public interface AttributesTemplateService extends CommonService<AttributesTemplate>{

	
	
	 /**
	 * @param pageNo
	 * @param pageSize
	 * @param attributesTemplate
	 * @return
	 * @throws Exception
	 */
	public Page pagedQuery(int pageNo, int pageSize, AttributesTemplate attributesTemplate,List<String> orderBysList)throws Exception;
	
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
	public List<AttributesTemplate> findByIds(Iterable<String> ids);

	/**
	 * 根据设备模板ID查询参数模板
	 *
	 * @param deviceTemplateId 设备模板ID
	 * @return
	 */
	List<AttributesTemplate> findByDeviceTemplateId(String deviceTemplateId);

	/**
	 * 统计设备模板下面的参数模板
	 *
	 * @param name 名称
	 * @param deviceTemplateId 模板ID
	 * @param isTelemetry 是否遥测参数
	 * @return
	 */
	int countByNameAndDeviceTemplateIdAndIsTelemetry(String name,String deviceTemplateId,Integer isTelemetry);


    /**
     * 查询该产品下面的设备遥测属性列表
     *
     * @param productId 产品id
     * @return
     */
    List<Map<String,Object>> queryAttributesByProductId(String productId);

    /**
     * @param pageNo  页数
     * @param pageSize  每页大小
     * @param devId 设备id
     * @param type 属性类型
     * @return
     */
    public Page queryByDevId(int pageNo, int pageSize,String devId,Integer type);
	
}
