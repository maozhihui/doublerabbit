package com.comba.server.dao.tenant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.Transient;
import javax.transaction.Transactional;

import com.comba.server.common.data.Organization;
import com.comba.server.dao.device.DeviceService;
import com.comba.server.dao.device.ProductService;
import com.comba.server.dao.plugin.PluginJpaService;
import com.comba.server.dao.rule.RuleJpaService;
import com.comba.server.dao.user.UserService;
import com.comba.server.dao.util.DaoUtil;

import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comba.server.common.data.Tenant;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.common.BaseDao;
import com.comba.server.dao.model.TenantEntity;

import static com.comba.server.dao.util.Constant.ORGANIZATION_CODE;
import static com.comba.server.dao.util.Constant.ORGANIZATION_NAME;


/**
 * 用户服务实现类
 * @author wengzhonghui
 *
 */
@Service("tenantJpaService")
public class TenantServiceImpl implements TenantService {
	@Autowired
	private TenantDao tenantJpaDao;

	@Autowired
	private UserService userJpaService;

	@Autowired
	private DeviceService deviceService;

	@Autowired
	private ProductService productService;

	@Autowired
	private RuleJpaService ruleJpaService;

	@Autowired
	private PluginJpaService pluginJpaService;
	
	@Resource
	private BaseDao baseDao;
	
	
	/**
	 * findAll
	 * @return
	 */
	public List<Tenant> findAll() {
		
		 List<TenantEntity> list = tenantJpaDao.findAll();
		 
		 return DaoUtil.convertDataList(list);
	}
	
	
	public List<Tenant> findByIds(Iterable<String> ids){
		List<TenantEntity> list = tenantJpaDao.findAll(ids);
		 
		 return DaoUtil.convertDataList(list);
	}
	/**
	 * Save
	 * @param t
	 */
	@Transactional
	@Override
	public Tenant save(Tenant t) {
		return tenantJpaDao.save(new TenantEntity(t)).toData();
	}

	@Override
	public void delete(String id) {
		tenantJpaDao.delete(id);
	}

	@Override
	public void delete(Tenant t) {
		tenantJpaDao.delete(new TenantEntity(t));
	}

	@Override
	public Tenant getOne(String id) {
		TenantEntity t = tenantJpaDao.findOne(id);
		if(t==null) return null;
		return t.toData();
		
	}

	@Override
	public boolean exists(String id) {
		return tenantJpaDao.exists(id);
	}

	@Override
	public void delete(Iterable<Tenant> entities) {
		if(entities!=null){
			for(Tenant t:entities){
				tenantJpaDao.delete(new TenantEntity(t));
			}
		}
		
	}


	/**
	 * 删除租户时，需要删除对应的用户，设备，规则，产品,插件
	 *
	 * @param ids 租户ID
	 */
	@Override
	@Transactional
	public void deleteByIds(String[] ids) {
		if(ids!=null && ids.length>0){
			for(String id:ids){
				deleteAllData(id);
				tenantJpaDao.delete(id);
			}
		}
	}

	private void deleteAllData(String tenantId){
		//删除租户下面的所有用户
		userJpaService.deleteByTenantId(tenantId);
		//删除租户下面的所有设备
		deviceService.deleteByTenantId(tenantId);
		//删除租户下面的所有的产品
		productService.deleteByTenantId(tenantId);
		//删除租户下面的规则
		ruleJpaService.deleteByTenantId(tenantId);
		//删除租户下面的插件
		pluginJpaService.deleteByTenantId(tenantId);

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Page getDataByPage( Tenant tenant, int pageNo, int pageSize) {
		
        StringBuffer hql = new StringBuffer(" from TenantEntity a ");
		Map<String, Object> params = new HashMap<String, Object>();
		String orderBy = "";
		if(tenant!=null){
			hql.append(getWhereSql(tenant));
			params = getWhereParam(tenant);
		}
		String queryHql = hql.toString();
        
		Page resultPage = baseDao.pagedQuery(queryHql, pageNo, pageSize, params);
		List<TenantEntity> list = (List<TenantEntity>)resultPage.getResult();
		resultPage.setResult(DaoUtil.convertDataList(list));
		return resultPage;
	}



	@SuppressWarnings("unchecked")
	@Override
	public Page pagedQuery(int pageNo, int pageSize, Tenant tenant,List<String> orderBysList) throws Exception {

		StringBuffer hql = new StringBuffer(" from TenantEntity a ");
		Map<String, Object> params = new HashMap<String, Object>();

		if(tenant!=null){
			hql.append(getWhereSql(tenant));
			params = getWhereParam(tenant);
		}
		String queryHql = hql.toString();
		
		Page resultPage = baseDao.pagedQuery(queryHql, pageNo, pageSize, params);
		List<TenantEntity> list = (List<TenantEntity>)resultPage.getResult();
		resultPage.setResult(DaoUtil.convertDataList(list));
		return resultPage;
	}

	@Transient
	public String getWhereSql(Tenant tenant) {
		StringBuffer sb = new StringBuffer("where 1=1");
		if(tenant.getTenantId()!=null)
			sb.append(" and a.tenantId = :tenantId ");
		if(StringHelper.isNotEmpty(tenant.getName()))
			sb.append(" and a.name like :name ");
		return sb.toString();
	}
	
	
	@Transient
	public Map<String, Object> getWhereParam(Tenant tenant) {
		Map<String, Object> params = new HashMap<String, Object>();
		if(tenant.getTenantId()!=null)
			params.put("tenantId", tenant.getTenantId());
		if(StringHelper.isNotEmpty(tenant.getName()))
			params.put("name", "%" + tenant.getName().trim() + "%");
		return params;
			
	}


	@Override
	public List<Tenant> findTenants() {
		List<TenantEntity> list = tenantJpaDao.findAll();
		return DaoUtil.convertDataList(list);
	}

	/**
	 * 添加租户时，需要添加默认的机构（根节点）
	 *
	 * @param tenantJpa 租户
	 */
	@Transactional
	public void saveTenantAndOrganization(Tenant tenantJpa) throws Exception{
		TenantEntity entity = new TenantEntity(tenantJpa);
		tenantJpaDao.save(entity);

		Organization organization = new Organization();
		organization.setTenantId(entity.getTenantId());
		organization.setName(ORGANIZATION_NAME);
		organization.setCode(ORGANIZATION_CODE);
		organization.setLevelFlag(-1);
	}


	/**
	 * 根据租户名称查找租户
	 *
	 * @param name 租户名称
	 * @return
	 */
	@Override
	public TenantEntity findByName(String name) {
		return tenantJpaDao.findByName(name);
	}

    /**
     * 根据租户名称统计租户
     *
     * @param name 租户名称
     * @return
     */
    @Override
    public int countByName(String name) {
        return tenantJpaDao.countByName(name);
    }
}
