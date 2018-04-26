package com.comba.server.dao.device.template;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.Transient;
import javax.transaction.Transactional;

import com.comba.server.common.data.rest.CategoryTemplateRest;
import com.comba.server.dao.model.device.AttributesTemplateEntity;
import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comba.server.common.data.device.DeviceTemplate;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.common.BaseDao;
import com.comba.server.dao.model.device.DeviceTemplateEntity;
import com.comba.server.dao.util.DaoUtil;




/**
 * 用户服务实现类
 * @author wengzhonghui
 *
 */
@Service("deviceTemplateService")
public class DeviceTemplateServiceImpl implements DeviceTemplateService {
	@Autowired
	private DeviceTemplateDao deviceTemplateDao;
	
	@Resource
	private BaseDao baseDao;
	
	
	public List<DeviceTemplate> findAll() {
		List<DeviceTemplateEntity> list = deviceTemplateDao.findAll();
		return DaoUtil.convertDataList(list);
	}
	
	
	public List<DeviceTemplate> findByIds(Iterable<String> ids){
		List<DeviceTemplateEntity> list = deviceTemplateDao.findAll(ids);
		return DaoUtil.convertDataList(list);
	}
	/**
	 * Save
	 * @param t
	 */
	@Transactional
	@Override
	public DeviceTemplate save(DeviceTemplate t) {
		DeviceTemplateEntity deviceTemplate = new DeviceTemplateEntity(t);
		DeviceTemplate retObj = deviceTemplateDao.save(deviceTemplate).toData();
		t.setDeviceTemplateId(deviceTemplate.getDeviceTemplateId());
		return retObj;
	}

	@Override
	public void delete(String id) {
		deviceTemplateDao.delete(id);
	}

	@Override
	public void delete(DeviceTemplate t) {
		deviceTemplateDao.delete(new DeviceTemplateEntity(t));
	}

	@Override
	public DeviceTemplate getOne(String id) {
		DeviceTemplateEntity t = deviceTemplateDao.findOne(id);
		if(t==null) return null;
		return t.toData();
	}

	@Override
	public boolean exists(String id) {
		return deviceTemplateDao.exists(id);
	}

	@Override
	public void delete(Iterable<DeviceTemplate> entities) {
		for(DeviceTemplate t : entities){
			if(t!=null) deviceTemplateDao.delete(new DeviceTemplateEntity(t));
		}
	}


	@Override
	public void deleteByIds(String[] ids) {
		if(ids!=null && ids.length>0){
			for(String id:ids){
				deviceTemplateDao.delete(id);
			}
		}
	}
	
	
	@Override
	public Page getDataByPage( DeviceTemplate deviceTemplate, int pageNo, int pageSize) {
		
        StringBuffer hql = new StringBuffer(" from DeviceTemplateEntity a ");
		Map<String, Object> params = new HashMap<String, Object>();
		if(deviceTemplate!=null){
			hql.append(getWhereSql(deviceTemplate));
			params = getWhereParam(deviceTemplate);
		}
		String queryHql = hql.toString();
        
		return baseDao.pagedQuery(queryHql, pageNo, pageSize, params);
	}




	@Override
	public Page pagedQuery(int pageNo, int pageSize, DeviceTemplate deviceTemplate,List<String> orderBysList) throws Exception {

		StringBuffer hql = new StringBuffer(" from DeviceTemplateEntity a ");
		Map<String, Object> params = new HashMap<String, Object>();
		String orderBy = "";
		if(orderBysList != null && orderBysList.size()>0){
			for(String i : orderBysList){
				orderBy = i;
			}
		}		
		if(deviceTemplate!=null){
			hql.append(getWhereSql(deviceTemplate));
			params = getWhereParam(deviceTemplate);
		}
		String queryHql = hql.toString();
		
	
		if(orderBy.length()>0){
			queryHql += " order by " + orderBy + " desc ";
		}
		Page resultPage = baseDao.pagedQuery(queryHql, pageNo, pageSize, params);
		List<DeviceTemplateEntity> list = (List<DeviceTemplateEntity>) resultPage.getResult();
		resultPage.setResult(DaoUtil.convertDataList(list));
		return resultPage;
	}

	public String getWhereSql(DeviceTemplate deviceTemplate) {
		StringBuffer sb = new StringBuffer("where 1=1");
		if(StringHelper.isNotEmpty(deviceTemplate.getDeviceTemplateId()))
			sb.append(" and a.deviceTemplateId = :deviceTemplateId ");
		if(StringHelper.isNotEmpty(deviceTemplate.getName()))
			sb.append(" and a.name like :name ");
        if (StringHelper.isNotEmpty(deviceTemplate.getCategoryId()))
            sb.append(" and a.categoryId = :categoryId ");
		return sb.toString();
	}
	
	public String getWhereSql_2(DeviceTemplate deviceTemplate) {
		StringBuffer sb = new StringBuffer();
		return sb.toString();
	}
	
	public Map<String, Object> getWhereParam(DeviceTemplate deviceTemplate) {
		Map<String, Object> params = new HashMap<String, Object>();
		if(deviceTemplate.getDeviceTemplateId()!=null)
			params.put("deviceTemplateId", deviceTemplate.getDeviceTemplateId());
		if(StringHelper.isNotEmpty(deviceTemplate.getName()))
			params.put("name", "%" + deviceTemplate.getName().trim() + "%");
        if (deviceTemplate.getCategoryId() != null)
            params.put("categoryId", deviceTemplate.getCategoryId());
		return params;
			
	}

	/**
	 * 通过多个ID查找设备模板
	 *
	 * @param categoryId
	 * @return
	 */
	@Override
	public Integer countByCategoryId(String categoryId) {
		return deviceTemplateDao.countByCategoryId(categoryId);
	}

	/**
	 * 根据名称查询设备类别
	 *
	 * @param name 名称
	 * @return
	 */
	@Override
	public int countByName(String name) {
		return deviceTemplateDao.countByName(name);
	}

    /**
     * 根据名称查询设备类别
     *
     * @param name 名称
     * @return
     */
    @Override
    public DeviceTemplate findByName(String name) {
        DeviceTemplateEntity deviceTemplateEntity =  deviceTemplateDao.findByName(name);
        return deviceTemplateEntity == null?null:deviceTemplateEntity.toData();
    }

	@Override
	public List<CategoryTemplateRest> findAllTemplate() {
    	List<DeviceTemplateEntity> entities = deviceTemplateDao.findAll();
    	List<CategoryTemplateRest> res = new ArrayList<>();
    	for (DeviceTemplateEntity entity : entities){
			CategoryTemplateRest rest = new CategoryTemplateRest(entity.getDeviceTemplateId(),
									entity.getCategoryEntity().getCode(),entity.getDescription());
			for (AttributesTemplateEntity attrEntity : entity.getAttributesTemplateEntity()){
				CategoryTemplateRest.TemplateParam param = new CategoryTemplateRest.TemplateParam(attrEntity.getAttributeTemplateId(),
								attrEntity.getName(),attrEntity.getName(),attrEntity.getValueType(),attrEntity.getDescription(),attrEntity.getIsTelemetry());
				rest.getParams().add(param);
			}
			res.add(rest);
		}
		return res;
	}

	@Override
	public DeviceTemplate findByCategoryCode(String code) {
    	StringBuilder sql = new StringBuilder(" from DeviceTemplateEntity d where d.categoryEntity.code=:code");
    	Map<String,Object> param = new HashMap<>();
    	param.put("code",code);
    	List<DeviceTemplateEntity> res = baseDao.queryByHql(sql.toString(),param);
    	if (!res.isEmpty())
    		return res.get(0).toData();
		return null;
	}
}
