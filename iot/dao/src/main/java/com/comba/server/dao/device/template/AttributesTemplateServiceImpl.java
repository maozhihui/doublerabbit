package com.comba.server.dao.device.template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.Transient;
import javax.transaction.Transactional;

import org.hibernate.annotations.common.util.StringHelper;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comba.server.common.data.device.AttributesTemplate;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.common.BaseDao;
import com.comba.server.dao.model.device.AttributesTemplateEntity;
import com.comba.server.dao.util.DaoUtil;




/**
 * 用户服务实现类
 * @author wengzhonghui
 *
 */
@Service("attributesTemplateService")
public class AttributesTemplateServiceImpl implements AttributesTemplateService {
	@Autowired
	private AttributesTemplateDao attributesTemplateDao;
	
	@Resource
	private BaseDao baseDao;
	
	
	/**
	 * findAll
	 * @return
	 */
	public List<AttributesTemplate> findAll() {
		List<AttributesTemplateEntity> list = attributesTemplateDao.findAll();
		
		return DaoUtil.convertDataList(list);
	}
	
	
	public List<AttributesTemplate> findByIds(Iterable<String> ids){
		List<AttributesTemplateEntity> list = attributesTemplateDao.findAll(ids);
		return DaoUtil.convertDataList(list);
	}
	/**
	 * Save
	 * @param attributesTemplate
	 */
	@Transactional
	@Override
	public AttributesTemplate save(AttributesTemplate attributesTemplate) {
		return attributesTemplateDao.save(new AttributesTemplateEntity(attributesTemplate)).toData();
	}

	@Override
	public void delete(String id) {
		attributesTemplateDao.delete(id);
	}

	@Override
	public void delete(AttributesTemplate attributesTemplate) {
		attributesTemplateDao.delete(new AttributesTemplateEntity(attributesTemplate));
	}

	@Override
	public AttributesTemplate getOne(String id) {
		AttributesTemplateEntity entity = attributesTemplateDao.findOne(id);
		if(entity==null) return null;
		return entity.toData();
		
	}

	@Override
	public boolean exists(String id) {
		return attributesTemplateDao.exists(id);
	}

	@Override
	public void delete(Iterable<AttributesTemplate> entities) {
		for(AttributesTemplate t : entities){
			if(t!=null) attributesTemplateDao.delete(new AttributesTemplateEntity(t));
		}
		
	}


	@Override
	public void deleteByIds(String[] ids) {
		if(ids!=null && ids.length>0){
			for(String id:ids){
				attributesTemplateDao.delete(id);
			}
		}
	}
	
	
	@Override
	public Page getDataByPage( AttributesTemplate attributesTemplate, int pageNo, int pageSize) {
		
        StringBuffer hql = new StringBuffer(" from AttributesTemplateEntity a ");
		Map<String, Object> params = new HashMap<String, Object>();
		if(attributesTemplate!=null){
			hql.append(getWhereSql(attributesTemplate));
			params = getWhereParam(attributesTemplate);
		}
		String queryHql = hql.toString();
        
		return baseDao.pagedQuery(queryHql, pageNo, pageSize, params);
	}




	@Override
	public Page pagedQuery(int pageNo, int pageSize, AttributesTemplate attributesTemplate,List<String> orderBysList) throws Exception {

		StringBuffer hql = new StringBuffer(" from AttributesTemplateEntity a ");
		Map<String, Object> params = new HashMap<String, Object>();
		//add sort v1.0  后续添加多条件排序 lzh
		String orderBy = "";
		if(orderBysList != null && orderBysList.size()>0){
			for(String i : orderBysList){
				orderBy = i;
			}
		}		
		if(attributesTemplate!=null){
			hql.append(getWhereSql(attributesTemplate));
			params = getWhereParam(attributesTemplate);
		}
		String queryHql = hql.toString();
		
	
		if(orderBy.length()>0){
			queryHql += " order by " + orderBy + " desc ";
		}
		Page resultPage = baseDao.pagedQuery(queryHql, pageNo, pageSize, params);
		return resultPage;
	}


	@Transient
	public String getWhereSql(AttributesTemplate attributesTemplate) {
		StringBuffer sb = new StringBuffer("where 1=1");
		if(attributesTemplate.getAttributesTemplateId()!=null)
			sb.append(" and a.attributeTemplateId = :attributeTemplateId ");
		if(attributesTemplate.getDeviceTemplateId()!=null)
			sb.append(" and a.deviceTemplateId = :deviceTemplateId ");
		if(attributesTemplate.getIsTelemetry()!=null)
			sb.append(" and a.isTelemetry = :isTelemetry ");
		if(StringHelper.isNotEmpty(attributesTemplate.getName()))
			sb.append(" and a.name like :name ");
		return sb.toString();
	}
	
	@Transient
	public String getWhereSql_2(AttributesTemplate attributesTemplate) {
		StringBuffer sb = new StringBuffer();
		return sb.toString();
	}
	
	@Transient
	public Map<String, Object> getWhereParam(AttributesTemplate attributesTemplate) {
		Map<String, Object> params = new HashMap<String, Object>();
		if(attributesTemplate.getAttributesTemplateId()!=null)
			params.put("attributeTemplateId", attributesTemplate.getAttributesTemplateId());
		if(attributesTemplate.getDeviceTemplateId()!=null)
			params.put("deviceTemplateId", attributesTemplate.getDeviceTemplateId());
		if(attributesTemplate.getIsTelemetry()!=null)
			params.put("isTelemetry", attributesTemplate.getIsTelemetry());
		if(StringHelper.isNotEmpty(attributesTemplate.getName()))
			params.put("name", "%" + attributesTemplate.getName().trim() + "%");
		return params;
			
	}

	/**
	 * 根据设备模板ID查询参数模板
	 *
	 * @param deviceTemplateId 设备模板ID
	 * @return
	 */
	@Override
	public List<AttributesTemplate> findByDeviceTemplateId(String deviceTemplateId) {
		List<AttributesTemplateEntity> list = attributesTemplateDao.findByDeviceTemplateId(deviceTemplateId);
		return DaoUtil.convertDataList(list);
	}

	/**
	 * 统计设备模板下面的参数模板
	 *
	 * @param name             名称
	 * @param deviceTemplateId 模板ID
	 * @return
	 */
	@Override
	public int countByNameAndDeviceTemplateIdAndIsTelemetry(String name, String deviceTemplateId,Integer isTelemetry) {
		return attributesTemplateDao.countByNameAndDeviceTemplateIdAndIsTelemetry(name, deviceTemplateId,isTelemetry);
	}

    /**
     * 查询该产品下面的设备遥测属性列表
     *
     * @param productId 产品id
     * @return
     */
    @Override
    public List<Map<String,Object>> queryAttributesByProductId(String productId) {
        StringBuffer sql = new StringBuffer("SELECT at.id as id ,at.NAME as name " +
                " FROM attributes_template at " +
                " WHERE at.DEVICE_TEMPLATE_ID in  " +
                " ( select DISTINCT  DEVICE_TEMPLATE_ID from device where PRODUCT_ID = :productId)"+
                " and at.IS_TELEMETRY = 1");
        Map<String,Object> params = new HashMap<>();
        params.put("productId",productId);

        List<Map<String,Object>> list =  baseDao.queryBySql(sql.toString(),params, Transformers.ALIAS_TO_ENTITY_MAP);
        return list;
    }

    /**
     * @param pageNo  页数
     * @param pageSize  每页大小
     * @param devId 属性模板
     * @return
     */
    @Override
    public Page queryByDevId(int pageNo, int pageSize,String devId,Integer type) {
        StringBuffer sql = new StringBuffer("select a from AttributesTemplateEntity a ," +
                "DeviceEntity d where d.deviceTemplateId = a.deviceTemplateId and d.id = :devId and a.isTelemetry = :type");
        Map<String,Object> params = new HashMap<>();
        params.put("devId",devId);
        params.put("type",type);


        Page ret = baseDao.pagedQuery(sql.toString(),pageNo,pageSize,params);
        return ret;
    }
}
