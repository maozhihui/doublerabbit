package com.comba.server.dao.device;


import com.comba.server.common.data.device.TelemetryAttributes;
import com.comba.server.common.data.id.EntityId;
import com.comba.server.common.data.kv.KvEntry;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.common.CommonService;
import com.comba.server.dao.model.device.TelemetryAttributesEntity;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;




/**
 * 遥测参数服务类
 * @author wengzhonghui
 *
 */
public interface TelemetryAttributesService extends CommonService<TelemetryAttributes>{

	
	
	 /**
	 * @param pageNo
	 * @param pageSize
	 * @param telemetryAttributes
	 * @return
	 * @throws Exception
	 */
	public Page pagedQuery(int pageNo, int pageSize, TelemetryAttributes telemetryAttributes,List<String> orderBysList)throws Exception;
	
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
	public List<TelemetryAttributes> findByIds(Iterable<String> ids);
	
	/**
	 * 根据产品分类、开始、结束时间统计设备数量
	 * 
	 * @author wengzhonghui 2017年7月3日
	 * @param productId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Long countByProductId(String productId, Date startTime, Date endTime);
	
	/**
	 * 
	 * 遥测数据按天实现图表统计
	 * @author wengzhonghui 2017年7月14日
	 * @param startTime
	 * @param endTime
	 * @param productId
	 * @return
	 */
	public List<Object[]> attributeStatisByDayOfProduct(Date startTime,Date endTime,String productId);
	
	/**
	 * 按设备和参数名称统计属性值
	 * 
	 * @author wengzhonghui 2017年7月14日
	 * @param startTime
	 * @param endTime
	 * @param devId
	 * @param attributeName
	 * @return
	 */
	public List<?> attributeStaticsByDevIdAndAttributeName(LocalDate startTime, LocalDate endTime, String devId, String attributeName);

	//保存遥测属性数据
	public List<KvEntry> save(EntityId entityId, List<KvEntry> attributes,List<TelemetryAttributes> telemetry);
	// 获取遥测属性数据
	public List<TelemetryAttributesEntity> getListByDevId(String devId);

	/**
	 * 根据名称统计遥测参数
	 *
	 * @param attributeName 参数名称
	 * @param devId 设备ID
	 * @return
	 */
	int countByAttributeNameAndDevId(String attributeName,String devId);
}
