package com.comba.server.dao.rule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import com.comba.server.dao.model.ModelConstants;
import com.comba.server.dao.model.device.RuleEntity;
import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comba.server.common.data.device.Rule;
import com.comba.server.common.data.id.RuleId;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.common.data.web.utils.UUIDUtils;
import com.comba.server.dao.common.BaseDao;
import com.comba.server.dao.util.DaoUtil;




/**
 * 插件服务实现类
 * @author wengzhonghui
 *
 */
@Service("ruleService")
public class RuleJpaServiceImpl implements RuleJpaService {
	@Autowired
	private RuleJpaDao ruleDao;
	
	@Resource
	private BaseDao baseDao;
	
	
	/**
	 * findAll
	 * @return
	 */
	public List<Rule> findAll() {
		List<RuleEntity> list = ruleDao.findAll();
		return DaoUtil.convertDataList(list);
	}
	
	
	public List<Rule> findByIds(Iterable<String> ids){
		List<RuleEntity> list = ruleDao.findAll(ids);
		return DaoUtil.convertDataList(list);
	}
	/**
	 * Save
	 * @param t
	 */
	@Transactional
	@Override
	public Rule save(Rule t) {
		return ruleDao.save(new RuleEntity(t)).toData();
	}

	@Override
	public void delete(String id) {
		ruleDao.delete(id);
	}

	@Override
	public void delete(Rule t) {
		ruleDao.delete(new RuleEntity(t));
	}

	@Override
	public Rule getOne(String id) {
		RuleEntity t = ruleDao.findOne(id);
		if(t==null) return null;
		return t.toData();
		
	}

	@Override
	public boolean exists(String id) {
		return ruleDao.exists(id);
	}

	@Override
	public void delete(Iterable<Rule> entities) {
		if(entities!=null){
			for(Rule t : entities){
				ruleDao.delete(new RuleEntity(t));
			}
		}
		
	}


	@Override
	public void deleteByIds(String[] ids) {
		if(ids!=null && ids.length>0){
			for(String id:ids){
				ruleDao.delete(id);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Page getDataByPage( Rule t, int pageNo, int pageSize) {
		
        StringBuffer hql = new StringBuffer(" from RuleEntity a ");
		Map<String, Object> params = new HashMap<String, Object>();
		if(t!=null){
			hql.append(getWhereSql(t));
			params = getWhereParam(t);
		}
		String queryHql = hql.toString();
        
		Page resultPage = baseDao.pagedQuery(queryHql, pageNo, pageSize, params);
		List<RuleEntity> list = (List<RuleEntity>)resultPage.getResult();
		resultPage.setResult(DaoUtil.convertDataList(list));
		return resultPage;
	}



	@SuppressWarnings("unchecked")
	@Override
	public Page pagedQuery(int pageNo, int pageSize, Rule t,List<String> orderBysList) throws Exception {

		StringBuffer hql = new StringBuffer(" from RuleEntity a ");
		Map<String, Object> params = new HashMap<String, Object>();
		//add sort v1.0  后续添加多条件排序 lzh
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
		List<RuleEntity> list = (List<RuleEntity>)resultPage.getResult();
		resultPage.setResult(DaoUtil.convertDataList(list));
		return resultPage;
	}

	public String getWhereSql(Rule t) {
		StringBuffer sb = new StringBuffer("where 1=1");
		if(t.getRuleId()!=null)
			sb.append(" and a.ruleId = :ruleId ");
		if(StringHelper.isNotEmpty(t.getName()))
			sb.append(" and a.name like :name ");
		if(t.getTenantId()!=null)
			sb.append(" and a.tenantId = :tenantId ");
		if (t.getProductId() != null)
			sb.append(" and a.productId = :productId");
		
		return sb.toString();
	}
	
	
	public Map<String, Object> getWhereParam(Rule t) {
		Map<String, Object> params = new HashMap<String, Object>();
		if(t.getRuleId()!=null)
			params.put("ruleId", t.getRuleId());
		if(StringHelper.isNotEmpty(t.getName()))
			params.put("name", "%" + t.getName().trim() + "%");
		if(t.getTenantId()!=null)
			params.put("tenantId", t.getTenantId());
		if (t.getProductId() != null)
			params.put("productId",t.getProductId());
		return params;
			
	}
	
	@Override
	public List<Rule> findByTenantId(String tenantId){
		List<RuleEntity> list = ruleDao.findByTenantId(tenantId);
		return DaoUtil.convertDataList(list);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Rule> findSystemRules() {
		StringBuffer hql = new StringBuffer(" from RuleEntity a ");
		Rule rule = new Rule();
		rule.setTenantId(UUIDUtils.toUUID(ModelConstants.NULL_UUID));
		hql.append(getWhereSql(rule));
		Map<String, Object> params = new HashMap<>(); 
		params = getWhereParam(rule);
		return DaoUtil.convertDataList(baseDao.queryByHql(hql.toString(), params));
	}
	
	@SuppressWarnings("unchecked")
	public List<Rule> findTenantRules(TenantId tenantId) {
		StringBuffer hql = new StringBuffer(" from RuleEntity a ");
		Rule rule = new Rule();
		rule.setTenantId(UUIDUtils.toUUID(tenantId.getId()));
		hql.append(getWhereSql(rule));
		Map<String, Object> params = new HashMap<>(); 
		params = getWhereParam(rule);
		return DaoUtil.convertDataList(baseDao.queryByHql(hql.toString(), params));
	}
	@Override
	public Rule findRuleById(RuleId ruleId) {
		String uuidStr = UUIDUtils.toUUID(ruleId.getId());
		RuleEntity entity = ruleDao.findOne(uuidStr);
		return entity.toData();
	}
	@Override
	public Long countByTenantId(String tenantId){
		return ruleDao.countByTenantId(tenantId);
	}

	/**
	 * 删除租户下面的规则
	 *
	 * @param tenantId 租户ID
	 * @return
	 */
	@Override
	public Integer deleteByTenantId(String tenantId) {
		return ruleDao.deleteByTenantId(tenantId);
	}

	/**
	 * 查询租户下面的产品规则数
	 *
	 * @param tenantId  租户ID
	 * @param productId 产品ID
	 * @return
	 */
	@Override
	public Long countByTenantIdAndProductId(String tenantId, String productId) {
		return ruleDao.countByTenantIdAndProductId(tenantId, productId);
	}


	/**
	 * 根据名称统计规则
	 *
	 * @param name 名称
	 * @return
	 */
	@Override
	public int countByName(String name) {
		return ruleDao.countByName(name);
	}
}

	
