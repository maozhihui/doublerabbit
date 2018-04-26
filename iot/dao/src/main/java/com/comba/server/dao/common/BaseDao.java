package com.comba.server.dao.common;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.common.hibernate.HibernateUtils;



/**
 * Dao查询基类，包括分页，sql
 * @author wengzhonghui
 *
 */
@Repository
public class BaseDao  {
	@Resource
	private EntityManager entityManager; 
	
	/**
	 * 根据SQL查询List列表
	 * @param sql
	 * @param map
	 * @return
	 */
	public List queryBySql(String sql,Map<String, Object> map){
		Query query = entityManager.createNativeQuery(sql);
		for (Serializable key : map.keySet()) {
            query.setParameter((String) key, map.get(key));
        }
		return query.getResultList();
	}

    /**
     * 根据SQL查询List列表
     * @param sql
     * @param map
     * @return
     */
    public List queryBySql(String sql,Map<String, Object> map, AliasToEntityMapResultTransformer former){
        Query query = entityManager.createNativeQuery(sql);
        query.unwrap(SQLQuery.class).setResultTransformer(former);
        for (Serializable key : map.keySet()) {
            query.setParameter((String) key, map.get(key));
        }
        return query.getResultList();
    }

    /**
     * 根据SQL查询List列表
     * @param sql
     * @param map
     * @return
     */
    public List queryBySql(String sql,Map<String, Object> map, Object ob){
        Query query = entityManager.createNativeQuery(sql,ob.getClass());
        for (Serializable key : map.keySet()) {
            query.setParameter((String) key, map.get(key));
        }
        return query.getResultList();
    }
	
	/**
	 * 根据hql查询List列表
	 * @param hql
	 * @param map
	 * @return
	 */
	public List queryByHql(String hql,Map<String, Object> map){
		Query query = entityManager.createQuery(hql);
		for (Serializable key : map.keySet()) {  
            query.setParameter((String) key, map.get(key));  
        } 
		return query.getResultList();
	}
	
    /**
     * hql的分页查询
     * @param hql
     * @param pageNo
     * @param pageSize
     * @param map
     * @return
     */
    public Page pagedQuery(String hql, int pageNo, int pageSize,  Map<String, Object> map) {
        Query query=entityManager.createQuery(hql);
        for (Serializable key : map.keySet()) {  
            query.setParameter((String) key, map.get(key));  
        } 
        int start = (pageNo - 1) * pageSize;
        List result = query.setFirstResult(start).setMaxResults(pageSize).getResultList();
        
        // Count查询
        String countQueryString = "select count (*) "
                + HibernateUtils.removeSelect(HibernateUtils.removeOrders(hql));
        Integer totalCount = this.getCount(countQueryString, map);

        if (totalCount < 1) {
            return new Page();
        }


        Page page = new Page(result, totalCount);
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);

        return page;
    }

    /**
     * 获得总记录数.
     * 
     * @param hql
     *            HQL字符串
     * @param map
     *            Map
     * @return 总数
     */
    public Integer getCount(String hql, Map<String, Object> map) {
        
        try {
        	Query query=entityManager.createQuery(hql);
        	if(map!=null){
        		for (Serializable key : map.keySet()) {  
                    query.setParameter((String) key, map.get(key));  
                } 
        	}
        	Object result = query.getSingleResult();
        	return HibernateUtils.getNumber(result);
		} catch (Exception e) {
//			logger.error(e.getMessage(),e);
		}
       return null;
    }
	
    @Transactional
    public int updateByHql(String hql,Map<String, Object> map){
    	Query query = entityManager.createQuery(hql);
		for (Serializable key : map.keySet()) {  
            query.setParameter((String) key, map.get(key));  
        } 
		return query.executeUpdate();
    }

    @Transactional
	public int updateBySql(String sql,Map<String,Object> map){
    	Query query = entityManager.createNativeQuery(sql);
		for (Serializable key : map.keySet()) {
			query.setParameter((String) key, map.get(key));
		}
		return query.executeUpdate();
	}
}
