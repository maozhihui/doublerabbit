package com.comba.server.dao.plugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comba.server.common.data.device.PluginJpa;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.common.BaseDao;
import com.comba.server.dao.model.device.PluginJpaEntity;
import com.comba.server.dao.util.DaoUtil;
import com.comba.server.common.data.id.PluginJpaId;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.data.web.utils.UUIDUtils;
import com.comba.server.dao.model.ModelConstants;



/**
 * 插件服务实现类
 * @author wengzhonghui
 *
 */
@Service("pluginJpaService")
public class PluginJpaServiceImpl implements PluginJpaService {
	@Autowired
	private PluginJpaDao pluginJpaDao;
	
	@Resource
	private BaseDao baseDao;
	

	/**
	 * findAll
	 * @return
	 */
	public List<PluginJpa> findAll() {
		List<PluginJpaEntity> list = pluginJpaDao.findAll();
		
		return DaoUtil.convertDataList(list); 
	}
	
	
	public List<PluginJpa> findByIds(Iterable<String> ids){
		List<PluginJpaEntity> list = pluginJpaDao.findAll(ids);
		
		return DaoUtil.convertDataList(list); 
	}
	/**
	 * Save
	 * @param user
	 */
	@Transactional
	@Override
	public PluginJpa save(PluginJpa t) {
		return pluginJpaDao.save(new PluginJpaEntity(t)).toData();
	}

	@Override
	public void delete(String id) {
		pluginJpaDao.delete(id);
	}

	@Override
	public void delete(PluginJpa t) {
		pluginJpaDao.delete(new PluginJpaEntity(t));
	}

	@Override
	public PluginJpa getOne(String id) {
		
		PluginJpaEntity t = pluginJpaDao.findOne(id);
		if(t==null) return null;
		return t.toData();
	}

	@Override
	public boolean exists(String id) {
		return pluginJpaDao.exists(id);
	}

	@Override
	public void delete(Iterable<PluginJpa> entities) {
		if(entities!=null){
			for(PluginJpa t : entities){
				if(t!=null){
					pluginJpaDao.delete(new PluginJpaEntity(t));
				}
			}
		}
		
	}


	@Override
	public void deleteByIds(String[] ids) {
		if(ids!=null && ids.length>0){
			for(String id:ids){
				pluginJpaDao.delete(id);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Page getDataByPage( PluginJpa t, int pageNo, int pageSize) {
		
        StringBuffer hql = new StringBuffer(" from PluginJpaEntity a ");
		Map<String, Object> params = new HashMap<String, Object>();
		if(t!=null){
			hql.append(getWhereSql(t));
			params = getWhereParam(t);
		}
		String queryHql = hql.toString();
        
		Page resultPage = baseDao.pagedQuery(queryHql, pageNo, pageSize, params);
		List<PluginJpaEntity> list = (List<PluginJpaEntity>)resultPage.getResult();
		resultPage.setResult(DaoUtil.convertDataList(list));
		return resultPage;
	}



	@SuppressWarnings("unchecked")
	@Override
	public Page pagedQuery(int pageNo, int pageSize, PluginJpa t,List<String> orderBysList) throws Exception {

		StringBuffer hql = new StringBuffer(" from PluginJpaEntity a ");
		Map<String, Object> params = new HashMap<String, Object>();
		String orderBy = "";
		if(orderBysList != null && orderBysList.size()>0){
			for(String i : orderBysList){
				orderBy = i;
			}
		}		
		if(t!=null){
			hql.append(getWhereSql(t));
			params = getWhereParam(t);
		}
		String queryHql = hql.toString();
		
	
		if(orderBy.length()>0){
			queryHql += " order by " + orderBy + " desc ";
		}
		
		Page resultPage = baseDao.pagedQuery(queryHql, pageNo, pageSize, params);
		List<PluginJpaEntity> list = (List<PluginJpaEntity>)resultPage.getResult();
		resultPage.setResult(DaoUtil.convertDataList(list));
		return resultPage;
	}

	
	public String getWhereSql(PluginJpa t) {
		StringBuffer sb = new StringBuffer("where 1=1");
		if(t.getPluginId()!=null)
			sb.append(" and a.pluginId = :pluginId ");
		if(StringHelper.isNotEmpty(t.getName()))
			sb.append(" and a.name like :name ");
		if(t.getTenantId()!=null)
			sb.append(" and a.tenantId = :tenantId ");
		
		return sb.toString();
	}
	
	public Map<String, Object> getWhereParam(PluginJpa t) {
		Map<String, Object> params = new HashMap<String, Object>();
		if(t.getPluginId()!=null)
			params.put("pluginId", t.getPluginId());
		if(StringHelper.isNotEmpty(t.getName()))
			params.put("name", "%" + t.getName().trim() + "%");
		if(t.getTenantId()!=null)
			params.put("tenantId", t.getTenantId());
		return params;
			
	}
	
	public List<PluginJpa> findByTenantId(String tenantId){
		List<PluginJpaEntity> list = pluginJpaDao.findByTenantId(tenantId);
		return DaoUtil.convertDataList(list); 
	}

	@Override
	public List<PluginJpa> findSystemPlugins() {
		StringBuffer hql = new StringBuffer(" from PluginJpaEntity a ");
		PluginJpa plugin = new PluginJpa();
		plugin.setTenantId(UUIDUtils.toUUID(ModelConstants.NULL_UUID));
		hql.append(getWhereSql(plugin));
		Map<String, Object> params = new HashMap<>(); 
		params = getWhereParam(plugin);
		return DaoUtil.convertDataList(baseDao.queryByHql(hql.toString(), params));
	}


	@Override
	public List<PluginJpa> findTenantPlugins(TenantId tenantId) {
		StringBuffer hql = new StringBuffer(" from PluginJpaEntity a ");
		PluginJpa plugin = new PluginJpa();
		plugin.setTenantId(UUIDUtils.toUUID(tenantId.getId()));
		hql.append(getWhereSql(plugin));
		Map<String, Object> params = new HashMap<>(); 
		params = getWhereParam(plugin);
		return DaoUtil.convertDataList(baseDao.queryByHql(hql.toString(), params));
	}


	@Override
	public PluginJpa findPluginById(PluginJpaId pluginId) {
		String uuidStr = UUIDUtils.toUUID(pluginId.getId());
		return pluginJpaDao.findOne(uuidStr).toData();
	}

	/**
	 * 删除租户下面的插件
	 *
	 * @param tenantId 租户ID
	 * @return
	 */
	@Override
	public Integer deleteByTenantId(String tenantId) {
		return pluginJpaDao.deleteByTenantId(tenantId);
	}
}
