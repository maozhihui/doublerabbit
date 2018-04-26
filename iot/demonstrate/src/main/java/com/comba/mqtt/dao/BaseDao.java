package com.comba.mqtt.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

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
	
    @Transactional
    public int updateByHql(String hql,Map<String, Object> map){
    	Query query = entityManager.createQuery(hql);
		for (Serializable key : map.keySet()) {  
            query.setParameter((String) key, map.get(key));  
        } 
		return query.executeUpdate();
    }
    
}
