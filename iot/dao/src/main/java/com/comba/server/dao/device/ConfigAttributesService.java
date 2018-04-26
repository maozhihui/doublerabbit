package com.comba.server.dao.device;


import com.comba.server.common.data.device.AttributesTemplate;
import com.comba.server.common.data.device.ConfigAttributes;
import com.comba.server.common.data.id.EntityId;
import com.comba.server.common.data.kv.AttributeKvEntry;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.common.CommonService;

import java.util.List;
import java.util.concurrent.ConcurrentMap;




/**
 * 设备配置参数服务类
 * @author wengzhonghui
 *
 */
public interface ConfigAttributesService extends CommonService<ConfigAttributes>{

	
	
	 /**
	 * @param pageNo
	 * @param pageSize
	 * @param attributesTemplate
	 * @return
	 * @throws Exception
	 */
	public Page pagedQuery(int pageNo, int pageSize, ConfigAttributes attributesTemplate,List<String> orderBysList)throws Exception;
	
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
	public List<ConfigAttributes> findByIds(Iterable<String> ids);
	
	public List<AttributeKvEntry> save(EntityId entityId, List<AttributeKvEntry> attributes);
	public boolean save(EntityId entityId, ConcurrentMap<String, String> attributesMap);
	
	/**
	 * 根据设备Id获取设备所有的属性
	 * @param deviceId
	 * @return
	 */
	public List<ConfigAttributes> findAllByDeviceId(EntityId deviceId);

    /**
     * 新建配置属性
     *
     * @param template 模板属性
     * @param devIds 设备id
     */
    void saveConfigAttribute(AttributesTemplate template , List<String> devIds);

    /**
     * 根据属性id删除配置参数
     *
     * @param attributeIds 属性id
     * @return
     */
    int deleteByAttributeId(List<String> attributeIds);
	
}
