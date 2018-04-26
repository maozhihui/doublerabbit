package com.comba.server.dao.device;

import java.util.List;
import java.util.Map;

import com.comba.server.common.data.device.AttributeData;

import com.comba.server.common.data.device.HistoryData;
import com.comba.server.common.data.device.TelemetryAttributes;
import com.comba.server.common.data.id.EntityId;
import com.comba.server.common.data.kv.KvEntry;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.common.CommonService;




/**
 * 遥测参数服务类
 * @author huangjinlong
 *
 */
public interface HistoryDataService extends CommonService<HistoryData>{

	
//	
//	 /**
//	 * @param pageNo
//	 * @param pageSize
//	 * @param user
//	 * @return
//	 * @throws Exception
//	 */
//	public Page pagedQuery(int pageNo, int pageSize, TelemetryAttributes telemetryAttributes,List<String> orderBysList)throws Exception;
	
	/**
	 * 批量删除
	 * @param ids
	 */
	void deleteByIds(String[] ids);
	
	/**
	 * 通过多个ID查找
	 * @param ids
	 * @return
	 */
	public List<HistoryData> findByIds(Iterable<String> ids);
	
//	/**
//	 * 根据产品分类、开始、结束时间统计设备数量
//	 * 
//	 * @author wengzhonghui 2017年7月3日
//	 * @param productId
//	 * @param startTime
//	 * @param endTime
//	 * @return
//	 */
//	public Long countByProductId(String productId, Date startTime, Date endTime);
	
	public List<KvEntry> save(EntityId entityId, List<KvEntry> attributes ,List<TelemetryAttributes> telemetrys);

	/**
	 * 分页查询
	 *
	 * @param pageNo
	 * @param pageSize
	 * @param historyData
	 * @param orderBysList
	 * @return
	 * @throws Exception
	 */
	public Page pagedQuery(int pageNo, int pageSize, HistoryData historyData,List<String> orderBysList);

	/**
	 * 根据时间查询设备遥测数据
	 *
	 * @param historyData 记录
	 * @param page 页面
	 * @return
	 */
	public Map<String,Object> getDeviceData(HistoryData historyData, Page page);

	/**
	 * 查询产品的今日的遥测数据量
	 *
	 * @param productId 产品
	 * @return
	 */
	Long countByProductId(String productId);

	/**
	 * 查询设备最新属性数据 2017-10-13 add by mzh
	 * @param devId
	 * @return
	 */
	List<AttributeData> getLatestDeviceData(String devId);

    /**
     * 获取设备的最新上报时间
     *
     * @param devIds 设备id列表
     * @return
     */
    List<Map<String, Object>> getDeviceLatestUpdateTime(List<String> devIds);
}
