package com.comba.server.dao.device;

import static com.comba.server.dao.util.DaoUtil.convertDataList;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.Resource;
import javax.persistence.Transient;
import javax.transaction.Transactional;

import com.comba.server.common.data.device.AttributesTemplate;
import com.google.common.collect.Lists;
import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comba.server.common.data.device.ConfigAttributes;
import com.comba.server.common.data.id.EntityId;
import com.comba.server.common.data.kv.AttributeKvEntry;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.common.data.web.utils.UUIDUtils;
import com.comba.server.dao.common.BaseDao;
import com.comba.server.dao.model.device.ConfigAttributesEntity;
import com.comba.server.dao.util.DaoUtil;

import lombok.extern.slf4j.Slf4j;




/**
 * 用户服务实现类
 * @author wengzhonghui
 *
 */
@Slf4j
@Service("configAttributesService")
public class ConfigAttributesServiceImpl implements ConfigAttributesService {
	@Autowired
	private ConfigAttributesDao configAttributesDao;
	
	@Resource
	private BaseDao baseDao;
	
	
	/**
	 * findAll
	 * @return
	 */
	public List<ConfigAttributes> findAll() {
		List<ConfigAttributesEntity> list = configAttributesDao.findAll();
		return DaoUtil.convertDataList(list);
	}
	
	
	public List<ConfigAttributes> findByIds(Iterable<String> ids){
		List<ConfigAttributesEntity> list = configAttributesDao.findAll(ids);
		return DaoUtil.convertDataList(list);
	}

	/**
	 *
	 * @param t
	 * @return
	 */
	@Transactional
	@Override
	public ConfigAttributes save(ConfigAttributes t) {
		return configAttributesDao.save(new ConfigAttributesEntity(t)).toData();
	}

	@Override
	public void delete(String id) {
		configAttributesDao.delete(id);
	}

	@Override
	public void delete(ConfigAttributes t) {
		configAttributesDao.delete(new ConfigAttributesEntity(t));
	}

	@Override
	public ConfigAttributes getOne(String id) {
		ConfigAttributesEntity t = configAttributesDao.findOne(id);
		if(t==null) return null;
		return t.toData();
		
	}

	@Override
	public boolean exists(String id) {
		return configAttributesDao.exists(id);
	}

	@Override
	public void delete(Iterable<ConfigAttributes> entities) {
		if(entities!=null){
			for(ConfigAttributes t : entities){
				if(t!=null){
					configAttributesDao.delete(new ConfigAttributesEntity(t));
				}
			}
		}
	}


	@Override
	public void deleteByIds(String[] ids) {
		if(ids!=null && ids.length>0){
			for(String id:ids){
				configAttributesDao.delete(id);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Page getDataByPage( ConfigAttributes configAttributes, int pageNo, int pageSize) {
		
        StringBuffer hql = new StringBuffer(" from ConfigAttributesEntity a ");
		Map<String, Object> params = new HashMap<String, Object>();
		if(configAttributes!=null){
			hql.append(getWhereSql(configAttributes));
			params = getWhereParam(configAttributes);
		}
		String queryHql = hql.toString();
        
		Page resultPage = baseDao.pagedQuery(queryHql, pageNo, pageSize, params);
		List<ConfigAttributesEntity> list = (List<ConfigAttributesEntity>)resultPage.getResult();
		resultPage.setResult(DaoUtil.convertDataList(list));
		return resultPage;
	}



	@SuppressWarnings("unchecked")
	@Override
	public Page pagedQuery(int pageNo, int pageSize, ConfigAttributes configAttributes,List<String> orderBysList) throws Exception {

		StringBuffer hql = new StringBuffer(" from ConfigAttributesEntity a ");
		Map<String, Object> params = new HashMap<String, Object>();
		String orderBy = "";
		if(orderBysList != null && orderBysList.size()>0){
			for(String i : orderBysList){
				orderBy = i;
			}
		}		
		if(configAttributes!=null){
			hql.append(getWhereSql(configAttributes));
			params = getWhereParam(configAttributes);
		}
		String queryHql = hql.toString();
		
	
		if(orderBy.length()>0){
			queryHql += " order by " + orderBy + " desc ";
		}
		Page resultPage = baseDao.pagedQuery(queryHql, pageNo, pageSize, params);
		List<ConfigAttributesEntity> list = (List<ConfigAttributesEntity>)resultPage.getResult();
		resultPage.setResult(DaoUtil.convertDataList(list));
		return resultPage;
	}

	
	@Transient
	public String getWhereSql( ConfigAttributes configAttributes) {
		StringBuffer sb = new StringBuffer("where 1=1");
		if(configAttributes.getConfigAttributeId()!=null)
			sb.append(" and a.configAttributeId = :configAttributeId ");
		if(configAttributes.getDevId()!=null)
			sb.append(" and a.devId = :devId ");
		if(configAttributes.getAttributeScope()!=null)
			sb.append(" and a.attributeScope = :attributeScope ");
		if(StringHelper.isNotEmpty(configAttributes.getAttributeName()))
			sb.append(" and a.attributeName like :attributeName ");
		return sb.toString();
	}
	
	@Transient
	public Map<String, Object> getWhereParam( ConfigAttributes configAttributes) {
		Map<String, Object> params = new HashMap<String, Object>();
		if(configAttributes.getConfigAttributeId()!=null)
			params.put("configAttributeId", configAttributes.getConfigAttributeId());
		if(configAttributes.getDevId()!=null)
			params.put("devId", configAttributes.getDevId());
		if(configAttributes.getAttributeScope()!=null)
			params.put("attributeScope", configAttributes.getAttributeScope());
		if(StringHelper.isNotEmpty(configAttributes.getAttributeName()))
			params.put("attributeName", "%" + configAttributes.getAttributeName().trim() + "%");
		return params;
			
	}


	@Override
	public List<AttributeKvEntry> save(EntityId entityId, List<AttributeKvEntry> attributes) {
		long start_time = System.currentTimeMillis();
		List<AttributeKvEntry> result = new ArrayList<>();
		String deviceIdStr = UUIDUtils.toUUID(entityId.getId());
		StringBuffer sb = new StringBuffer("");
		Map<String, Object> params = new HashMap<>();
		log.debug("update param size [{}]",attributes.size());
		for (AttributeKvEntry kvEntry : attributes){
			if (!sb.toString().isEmpty() || !params.isEmpty()) {
				sb.setLength(0);
				params.clear();
			}
			sb.append("UPDATE config_attributes ca,attributes_template atemp SET ca.update_time=:updateTime,ca.value=:val WHERE ca.attribute_template_id=atemp.id AND ca.dev_id=:devId AND atemp.name=:attName");
			params.put("updateTime",new Date(kvEntry.getLastUpdateTs()));
			params.put("val",kvEntry.getValueAsString());
			params.put("devId",deviceIdStr);
			params.put("attName", kvEntry.getKey());
			int res = baseDao.updateBySql(sb.toString(), params);
			if (res > 0){
				result.add(kvEntry);
				log.debug("did [{}] update config attribute success kvEntry [{}]",deviceIdStr,kvEntry);
			}else {
				log.debug("did [{}] update config attribute failed kvEntry [{}]",deviceIdStr,kvEntry);
			}
		}
		/*for (AttributeKvEntry attributeKvEntry : attributes) {
			if (!sb.toString().isEmpty() || !params.isEmpty()) {
				sb.setLength(0);
				params.clear();
			}
			sb.append("update ConfigAttributesEntity a set ");
			if (attributeKvEntry.getLastUpdateTs() > 0) {
				sb.append(" a.updateTime = :updateTime ");
				params.put("updateTime", new Date(attributeKvEntry.getLastUpdateTs()));
			}
			String valueType = "string";
			String valueStr = "";
			switch (attributeKvEntry.getDataType()) {
				case STRING:
					valueType = "string";
					valueStr = attributeKvEntry.getStrValue().get();
					break;
				case LONG:
					valueType = "int";
					valueStr = attributeKvEntry.getLongValue().get().toString();
					break;
				case DOUBLE:
					valueType = "float";
					valueStr = attributeKvEntry.getDoubleValue().get().toString();
					break;
				default:
					break;
			}
			sb.append(",a.valueType = :valueType,a.value = :value ");
			params.put("valueType", valueType);
			params.put("value", valueStr);
			sb.append(" where a.devId = :devId and a.attributeName = :attributeName");
			params.put("devId", deviceIdStr);
			params.put("attributeName", attributeKvEntry.getKey());
			int res = baseDao.updateByHql(sb.toString(), params);
			if (res > 0){
				result.add(attributeKvEntry);
				log.debug("update success kventry [{}]",attributeKvEntry);
			}else {
				log.debug("update failed kventry [{}]",attributeKvEntry);
			}
		}*/
		log.debug("did [{}] update config attributes success size [{}],actual size [{}],cost time [{}]ms",
								deviceIdStr,result.size(),attributes.size(),(System.currentTimeMillis()-start_time));
		return result;
	}
	
	@Override
	public boolean save(EntityId entityId, ConcurrentMap<String, String> attributesMap) {
		boolean result = false;
		String deviceId = UUIDUtils.toUUID(entityId.getId());
		StringBuffer sb = new StringBuffer("UPDATE config_attributes ca,attributes_template ae SET ca.value=:attrValue,ca.update_time=:updateTime WHERE ca.attribute_template_id = ae.id AND ca.dev_id=:devId AND ae.name=:attrName");
		
		Map<String, Object> params = null;
		for (Map.Entry<String, String> entry : attributesMap.entrySet()) {
			params = new HashMap<>();
			params.put("updateTime", new Date());
			params.put("attrValue", entry.getValue());
			params.put("devId", deviceId);
			params.put("attrName", entry.getKey());
			int res = baseDao.updateBySql(sb.toString(), params);
			if (res > 0)
				result = true;
		}
		return result;
	}


	@Override
	public List<ConfigAttributes> findAllByDeviceId(EntityId deviceId) {
		// TODO Auto-generated method stub
		String dbUUIDFormat = UUIDUtils.toUUID(deviceId.getId());
		List<ConfigAttributesEntity> result = configAttributesDao.findByDevId(dbUUIDFormat);
		return convertDataList(result);		
	}


    /**
     * 新建配置属性
     *
     * @param template 模板属性
     * @param devIds 设备id
     */
	public void saveConfigAttribute(AttributesTemplate template ,List<String> devIds){
	    List<ConfigAttributesEntity> list = Lists.newArrayList();
	    for (String devId : devIds){
            template.setCreateTime(new Date());
            ConfigAttributesEntity configAttributes = new ConfigAttributesEntity(template,devId);
            list.add(configAttributes);
        }
        if (!list.isEmpty()){
            configAttributesDao.save(list);
        }
    }

    /**
     * 根据属性id删除配置参数
     *
     * @param attributeIds 属性id
     * @return
     */
    public int deleteByAttributeId(List<String> attributeIds){
	    String sql = "delete from config_attributes where attribute_template_id in :ids";

	    Map<String,Object> params = new HashMap<>();
	    params.put("ids",attributeIds);

	    return baseDao.updateBySql(sql,params);
    }
}
