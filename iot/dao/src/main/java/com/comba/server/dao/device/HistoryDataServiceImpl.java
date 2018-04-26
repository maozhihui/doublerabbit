package com.comba.server.dao.device;

import com.comba.server.common.data.api.DeviceAttributes;
import com.comba.server.common.data.api.DeviceMeta;
import com.comba.server.common.data.device.AttributeData;
import com.comba.server.common.data.device.HistoryData;
import com.comba.server.common.data.device.TelemetryAttributes;
import com.comba.server.common.data.id.EntityId;
import com.comba.server.common.data.kv.KvEntry;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.common.data.web.utils.UUIDUtils;
import com.comba.server.dao.common.BaseDao;
import com.comba.server.dao.model.device.HistoryDataEntity;
import com.comba.server.dao.model.device.LatestDataEntity;
import com.comba.server.dao.util.DaoUtil;
import com.comba.server.dao.util.DateConvertUtil;
import com.google.common.collect.Lists;
import org.hibernate.annotations.common.util.StringHelper;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 用户服务实现类
 * @author wengzhonghui
 *
 */
@Service("historyDataService")
public class HistoryDataServiceImpl implements HistoryDataService {
	@Autowired
	private HistoryDataDao historyDataDao;

	@Autowired
	private LatestDataService latestDataService;

	@Autowired
	private TelemetryAttributesDao telemetryAttributesDao;
	@Resource
	private BaseDao baseDao;

	/**
	 * findAll
	 * @return
	 */
	public List<HistoryData> findAll() {
		List<HistoryDataEntity>  list = historyDataDao.findAll();
		
		return DaoUtil.convertDataList(list);
	}
	
	
	public List<HistoryData> findByIds(Iterable<String> ids){
		
		List<HistoryDataEntity>  list =  historyDataDao.findAll(ids);
				
		return DaoUtil.convertDataList(list);
	}
	/**
	 * Save
	 * @param t
	 */
	@Transactional
	@Override
	public HistoryData save(HistoryData t) {
		return historyDataDao.save(new HistoryDataEntity(t)).toData();
	}

	public List<KvEntry> save(EntityId entityId, List<KvEntry> attributes , List<TelemetryAttributes> telemetryAttributes) {
		List<KvEntry> result = new ArrayList<>();
		String devId = UUIDUtils.toUUID(entityId.getId());
		
		for (KvEntry attributeKvEntry : attributes) {
			String valueType = "string";
			String value = "";
			switch (attributeKvEntry.getDataType()) {
				case BOOLEAN:
					valueType = "boolean";
					value = attributeKvEntry.getBooleanValue().get().toString();
					break;
				case STRING:
					valueType = "string";
					value = attributeKvEntry.getStrValue().get();
					break;
				case LONG:
					valueType = "int";
					value = attributeKvEntry.getLongValue().get().toString();
					break;
				case DOUBLE:
					valueType = "float";
					value = attributeKvEntry.getDoubleValue().get().toString();
				default:
					break;
			}
			String attributeName = attributeKvEntry.getKey();
			String attributeId = null;
			for(TelemetryAttributes t : telemetryAttributes) {
				if(t.getAttributeName().equals(attributeName)) {
					attributeId = t.getTelemetryAttributeId();
					break;
				}
			}
			
			// 遥测属性没有，则插入新属性
			if(attributeId == null) {
				attributeId = UUIDUtils.toUUID(UUID.randomUUID());
			}
			// TODO 写法优化
			HistoryData data = new HistoryData();
			data.setTelemetryAttributeId(devId);
			data.setAttributeId(attributeId);
			data.setValue(value);
			data.setDevId(devId);
			data.setTelemetryAttributeName(attributeName);
			LocalDateTime localtime = LocalDateTime.now();
			data.setUpdateTime( DateConvertUtil.localDateTimeConvertToStr(localtime));

			HistoryDataEntity historyDataEntity = new HistoryDataEntity(data);
			// 插入最新数据表
			LatestDataEntity latestDataEntity = new LatestDataEntity(historyDataEntity);
			latestDataService.save(latestDataEntity);
			HistoryDataEntity res = historyDataDao.save(historyDataEntity);
			if (res != null)
				result.add(attributeKvEntry);
		}
		return result;
	}
	
	@Override
	public void delete(String id) {
		historyDataDao.delete(id);
	}

	@Override
	public void delete(HistoryData t) {
		historyDataDao.delete(new HistoryDataEntity(t));
	}

	@Override
	public HistoryData getOne(String id) {
		HistoryDataEntity t = historyDataDao.findOne(id);
		if(t==null) return null;
		return t.toData();
		
	}

	@Override
	public boolean exists(String id) {
		return historyDataDao.exists(id);
	}

	@Override
	public void delete(Iterable<HistoryData> entities) {
		if(entities!=null){
			for(HistoryData t : entities){
				historyDataDao.delete(new HistoryDataEntity(t));
			}
		}
		
	}

	/**
	 * 分页查询（含排序功能）
	 *
	 * @param obj
	 * @param pageNo
	 * @param pageSize @return
	 */
	@Override
	public Page getDataByPage(HistoryData obj, int pageNo, int pageSize) {
		return null;
	}


	@Override
	public void deleteByIds(String[] ids) {
		if(ids!=null && ids.length>0){
			for(String id:ids){
				historyDataDao.delete(id);
			}
		}
	}


	public Page pagedQuery(int pageNo, int pageSize, HistoryData historyData,List<String> orderBysList){

		StringBuffer hql = new StringBuffer(" from HistoryDataEntity a ");
		Map<String, Object> params = new HashMap<String, Object>();
		String orderBy = "";
		if(orderBysList != null && orderBysList.size()>0){
			for(String i : orderBysList){
				orderBy = i;
			}
		}
		if(historyData!=null){
			hql.append(getWhereSql(historyData));
			params = getWhereParam(historyData);
		}
		String queryHql = hql.toString();


		if(orderBy.length()>0){
			queryHql += " order by " + orderBy + " desc ";
		}
		Page resultPage = baseDao.pagedQuery(queryHql, pageNo, pageSize, params);
		List<HistoryDataEntity> list = (List<HistoryDataEntity>)resultPage.getResult();
		resultPage.setResult(DaoUtil.convertDataList(list));
		return resultPage;
	}

	public String getWhereSql(HistoryData historyData) {
		StringBuffer sb = new StringBuffer("where 1=1");
		if(StringHelper.isNotEmpty(historyData.getDevId()))
			sb.append(" and a.devId = :devId ");
		if(StringHelper.isNotEmpty(historyData.getTelemetryAttributeName()))
			sb.append(" and a.telemetryAttributeName = :telemetryAttributeName ");
		if(historyData.getStartDate() !=null && historyData.getEndDate() != null)
			sb.append(" and a.updateTime >= :startDate and a.updateTime <= :endDate");
		return sb.toString();
	}

	
	public Map<String, Object> getWhereParam(HistoryData historyData) {
		Map<String, Object> params = new HashMap<String, Object>();
		if(historyData.getDevId()!=null)
			params.put("devId", historyData.getDevId());
		if(historyData.getTelemetryAttributeName()!=null)
			params.put("telemetryAttributeName", historyData.getTelemetryAttributeName());
		if(historyData.getStartDate()!=null)
			params.put("startDate", historyData.getStartDate());
		if(historyData.getEndDate()!=null)
			params.put("endDate", historyData.getEndDate());
		return params;
			
	}

	/**
	 * 根据时间查询设备遥测数据
	 *
	 * @param historyData 记录
	 * @param page 页面
	 * @return
	 */
	public Map<String,Object> getDeviceData(HistoryData historyData, Page page){
		Map<String,Object> result = new HashMap<>();
		StringBuffer hql = new StringBuffer(" from HistoryDataEntity a ");
		Map<String, Object> params = new HashMap<>();

		if(historyData!=null){
			hql.append(getWhereSql(historyData));
			params = getWhereParam(historyData);
		}
		String queryHql = hql.toString();

        queryHql += " order by updateTime desc ";
		Page resultPage = baseDao.pagedQuery(queryHql, page.getPageNo(), page.getPageSize(), params);
		List<HistoryDataEntity> list = (List<HistoryDataEntity>)resultPage.getResult();

		//设置页面属性实体
		DeviceMeta deviceMeta = new DeviceMeta();
		deviceMeta.setLimit(page.getPageSize());
		deviceMeta.setTotal(resultPage.getTotalCount());

		List<DeviceAttributes> deviceAttributes = Lists.newArrayList();
		if (list != null) {
			//转换为DeviceAttributes
			deviceAttributes.addAll(list.stream().map(HistoryDataEntity::toDeviceAttributes).collect(Collectors.toList()));

			//如果存在下一页数据，则需要把下一页的Url组装
			if (list.size() < resultPage.getTotalCount()) {
				//取下一页的开始时间
				LocalDateTime nextDate = list.get(list.size() - 1).getUpdateTime();
				//获取下一页的url
				String url = getNextPageUrl(historyData, nextDate, page.getPageSize());
				deviceMeta.setNext(url);
			}

		}

		result.put("meta",deviceMeta);
		result.put("objects",deviceAttributes);
		return result;
	}

	private String getNextPageUrl(HistoryData historyData,LocalDateTime nextDate,int limit){
		StringBuilder url = new StringBuilder("/api/device/");
		url.append(historyData.getDevId());
		url.append("/data?");
		url.append("start_ts=");
		url.append(DateConvertUtil.convertToDate(historyData.getStartDate()).getTime());
		url.append("&end_ts=");
		//减1是为了过滤当前这条
		url.append(DateConvertUtil.convertToDate(nextDate).getTime()-1);
		url.append("&limit=");
		url.append(limit);
		return url.toString();
	}


	/**
	 * 查询产品的今日的遥测数据量
	 *
	 * @param productId  产品
	 * @return
	 */
	@Override
	public Long countByProductId(String productId) {
		LocalDateTime nowTime = LocalDate.now().atStartOfDay();
		return historyDataDao.countByProductId(productId, nowTime);
	}

	@Override
	public List<AttributeData> getLatestDeviceData(String devId) {
		List<AttributeData> res = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT hd.ATTRIBUTE_NAME,hd.VALUE,hd.UPDATE_TIME FROM history_data hd ");
		sql.append("WHERE hd.DEV_ID=:devId1 AND hd.UPDATE_TIME=(SELECT MAX(update_time) FROM history_data ha WHERE ha.DEV_ID=:devId2)");
		Map<String,Object> params = new HashMap<>();
		params.put("devId1",devId);
		params.put("devId2",devId);
		List<Object[]> lists = baseDao.queryBySql(sql.toString(),params);
		for (Object[] row : lists){
			AttributeData data = new AttributeData((String)row[0],(String) row[1],((Date)row[2]).getTime());
			res.add(data);
		}
		return res;
	}

    /**
     * 获取设备的最新上报时间
     *
     * @param devIds 设备id列表
     * @return
     */
	public List<Map<String, Object>> getDeviceLatestUpdateTime(List<String> devIds){
	    StringBuffer sql = new StringBuffer();
	    sql.append("SELECT DEV_ID as did,DATE_FORMAT(max(UPDATE_TIME),'%Y-%m-%d %H:%i:%s') as updateTime,VALUE as currentValue ,ATTRIBUTE_NAME as name " +
                " FROM history_data " +
                " WHERE DEV_ID IN (:devIds) "+
                " GROUP BY DEV_ID");

        Map<String,Object> params = new HashMap<>();
        params.put("devIds",devIds);

        List<Map<String,Object>> map =  baseDao.queryBySql(sql.toString(),params, Transformers.ALIAS_TO_ENTITY_MAP);
        return map;
    }
}
