package com.comba.server.dao.device;

import com.comba.server.common.data.device.TelemetryAttributes;
import com.comba.server.common.data.id.EntityId;
import com.comba.server.common.data.kv.KvEntry;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.common.data.web.utils.UUIDUtils;
import com.comba.server.dao.common.BaseDao;
import com.comba.server.dao.model.device.TelemetryAttributesEntity;
import com.comba.server.dao.util.DaoUtil;

import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;




/**
 * 用户服务实现类
 * @author wengzhonghui
 *
 */
@Service("telemetryAttributesService")
public class TelemetryAttributesServiceImpl implements TelemetryAttributesService {
	@Autowired
	private TelemetryAttributesDao telemetryAttributesDao;
	@Autowired
	private HistoryDataDao historyDataDao;
	
	@Resource
	private BaseDao baseDao;
	
	
	/**
	 * findAll
	 * @return
	 */
	public List<TelemetryAttributes> findAll() {
		List<TelemetryAttributesEntity>  list = telemetryAttributesDao.findAll();
		
		return DaoUtil.convertDataList(list);
	}
	
	
	public List<TelemetryAttributes> findByIds(Iterable<String> ids){
		
		List<TelemetryAttributesEntity>  list =  telemetryAttributesDao.findAll(ids);
				
		return DaoUtil.convertDataList(list);
	}
	/**
	 * Save
	 * @param t
	 */
	@Transactional
	@Override
	public TelemetryAttributes save(TelemetryAttributes t) {
		return telemetryAttributesDao.save(new TelemetryAttributesEntity(t)).toData();
	}

	public List<KvEntry> save(EntityId entityId, List<KvEntry> attributes,List<TelemetryAttributes> telemetrys) {
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
			for(TelemetryAttributes t : telemetrys) {
				if(t.getAttributeName().equals(attributeName)) {
					attributeId = t.getTelemetryAttributeId();
					break;
				}
			}
			
			// 遥测属性没有，则插入新属性
			if(attributeId == null) {
				attributeId = UUID.randomUUID().toString();
				TelemetryAttributes telemetry = new TelemetryAttributes();
				telemetry.setTelemetryAttributeId(attributeId);
				telemetry.setDevId(devId);
				telemetry.setAttributeName(attributeName);
				telemetry.setValueType(valueType);
				telemetry.setUpdateTime(new Date());
				TelemetryAttributesEntity res = telemetryAttributesDao.save(new TelemetryAttributesEntity(telemetry));

				if (res != null)
					result.add(attributeKvEntry);
			}
		}
		return result;
	}
	
	@Override
	public void delete(String id) {
		telemetryAttributesDao.delete(id);
	}

	@Override
	public void delete(TelemetryAttributes t) {
		telemetryAttributesDao.delete(new TelemetryAttributesEntity(t));
	}

	@Override
	public TelemetryAttributes getOne(String id) {
		TelemetryAttributesEntity t = telemetryAttributesDao.findOne(id);
		if(t==null) return null;
		return t.toData();
		
	}

	@Override
	public boolean exists(String id) {
		return telemetryAttributesDao.exists(id);
	}

	@Override
	public void delete(Iterable<TelemetryAttributes> entities) {
		if(entities!=null){
			for(TelemetryAttributes t : entities){
				telemetryAttributesDao.delete(new TelemetryAttributesEntity(t));
			}
		}
		
	}


	@Override
	public void deleteByIds(String[] ids) {
		if(ids!=null && ids.length>0){
			for(String id:ids){
				telemetryAttributesDao.delete(id);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Page getDataByPage( TelemetryAttributes telemetryAttributes, int pageNo, int pageSize) {
		
        StringBuffer hql = new StringBuffer(" from TelemetryAttributesEntity a ");
		Map<String, Object> params = new HashMap<String, Object>();
		if(telemetryAttributes!=null){
			hql.append(getWhereSql(telemetryAttributes));
			params = getWhereParam(telemetryAttributes);
		}
		String queryHql = hql.toString();
        
		Page resultPage = baseDao.pagedQuery(queryHql, pageNo, pageSize, params);
		List<TelemetryAttributesEntity> list = (List<TelemetryAttributesEntity>)resultPage.getResult();
		resultPage.setResult(DaoUtil.convertDataList(list));
		return resultPage;
	}



	@SuppressWarnings("unchecked")
	@Override
	public Page pagedQuery(int pageNo, int pageSize, TelemetryAttributes telemetryAttributes,List<String> orderBysList) throws Exception {

		StringBuffer hql = new StringBuffer(" from TelemetryAttributesEntity a ");
		Map<String, Object> params = new HashMap<String, Object>();
		String orderBy = "";
		if(orderBysList != null && orderBysList.size()>0){
			for(String i : orderBysList){
				orderBy = i;
			}
		}		
		if(telemetryAttributes!=null){
			hql.append(getWhereSql(telemetryAttributes));
			params = getWhereParam(telemetryAttributes);
		}
		String queryHql = hql.toString();
		
	
		if(orderBy.length()>0){
			queryHql += " order by " + orderBy + " desc ";
		}
		Page resultPage = baseDao.pagedQuery(queryHql, pageNo, pageSize, params);
		List<TelemetryAttributesEntity> list = (List<TelemetryAttributesEntity>)resultPage.getResult();
		resultPage.setResult(DaoUtil.convertDataList(list));
		return resultPage;
	}

	public String getWhereSql(TelemetryAttributes telemetryAttributes) {
		StringBuffer sb = new StringBuffer("where 1=1");
		if(telemetryAttributes.getTelemetryAttributeId()!=null)
			sb.append(" and a.telemetryAttributeId = :telemetryAttributeId ");
		if(telemetryAttributes.getDevId()!=null)
			sb.append(" and a.devId = :devId ");
		if(StringHelper.isNotEmpty(telemetryAttributes.getAttributeName()))
			sb.append(" and a.attributeName like :attributeName ");
		return sb.toString();
	}
	
	public String getWhereSql_2(TelemetryAttributes telemetryAttributes) {
		StringBuffer sb = new StringBuffer();
		return sb.toString();
	}
	
	public Map<String, Object> getWhereParam(TelemetryAttributes telemetryAttributes) {
		Map<String, Object> params = new HashMap<String, Object>();
		if(telemetryAttributes.getTelemetryAttributeId()!=null)
			params.put("telemetryAttributeId", telemetryAttributes.getTelemetryAttributeId());
		if(telemetryAttributes.getDevId()!=null)
			params.put("devId", telemetryAttributes.getDevId());
		if(StringHelper.isNotEmpty(telemetryAttributes.getAttributeName()))
			params.put("attributeName", "%" + telemetryAttributes.getAttributeName().trim() + "%");
		return params;
			
	}
	
	public Long countByProductId(String productId, Date startTime, Date endTime){
		return telemetryAttributesDao.countByProductId(productId, startTime, endTime);
	}
	
	@SuppressWarnings("unchecked")
	public List<TelemetryAttributesEntity> getListByDevId(String devId){
		StringBuilder sql = new StringBuilder("");
		sql.append("  FROM TelemetryAttributesEntity a ");
		sql.append("WHERE a.devId = '"+ devId + "'");
		Map<String, Object> params = new HashMap<String, Object>();
		
		List<TelemetryAttributesEntity> list = baseDao.queryByHql(sql.toString(), params);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> attributeStatisByDayOfProduct(Date startTime,Date endTime,String productId){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT   COUNT(c.ID) deviceNum,  DATE_FORMAT(c.UPDATE_TIME, '%Y-%m-%d') createTime ");
		sql.append("   FROM device a,history_data c");
		sql.append(" WHERE c.UPDATE_TIME>=:startTime AND c.UPDATE_TIME<=:endTime and a.PRODUCT_ID=:productId   ");
		sql.append(" AND a.ID = c.DEV_ID ");
		sql.append(" GROUP BY createTime");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		params.put("productId", productId);
		return baseDao.queryBySql(sql.toString(), params);
	}
	
	@SuppressWarnings("unchecked")
	public List<?> attributeStaticsByDevIdAndAttributeName(LocalDate startTime, LocalDate endTime, String devId, String attributeName){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT   COUNT(a.ID) deviceNum,  DATE_FORMAT(a.UPDATE_TIME, '%Y-%m-%d') uploadTime ");
		sql.append("   FROM history_data a ");
		sql.append(" WHERE a.UPDATE_TIME>=:startTime AND a.UPDATE_TIME<=:endTime ");
		sql.append(" AND a.ATTRIBUTE_NAME in ( :attributeName )");
		sql.append(" AND a.DEV_ID = :devId ");
		sql.append(" GROUP BY uploadTime");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		params.put("devId", devId);
		params.put("attributeName", attributeName);
		return baseDao.queryBySql(sql.toString(), params);
	}

	/**
	 * 根据名称统计遥测参数
	 *
	 * @param attributeName 参数名称
	 * @return
	 */
	@Override
	public int countByAttributeNameAndDevId(String attributeName,String devId) {
		return telemetryAttributesDao.countByAttributeNameAndDevId(attributeName,devId);
	}
}
