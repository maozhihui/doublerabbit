package com.comba.server.dao.common;

import java.util.List;

import com.comba.server.common.data.web.page.Page;





/**
 * service公用类
 * @author wengzhonghui
 *
 * @param <T>
 */
public interface CommonService<T> {

	/**
	 * 查询所有对象
	 * @return
	 */
	List<T> findAll();
	
	/**
	 * 保存对象
	 * @param obj
	 */
	T save(T obj);
	
	/**
	 * 根据ID删除对象
	 * @param id
	 */
	void delete(String id);
	
	/**
	 * 删除对象
	 * @param obj
	 */
	void delete(T obj);
	
	/**
	 * 根据ID获取一个对象
	 * @param id
	 * @return
	 */
	T getOne(String id);
	
	/**
	 * 根据ID判断对象是否存在
	 * @param id
	 * @return
	 */
	boolean exists(String id);
	
	/**
	 * 删除对象集合
	 * @param entities
	 */
	void delete(Iterable<T> entities);
	
	/**
	 * 分页查询（含排序功能）
	 * @param pageable
	 * @return
	 */
	Page getDataByPage(T obj,int pageNo, int pageSize); 
}
