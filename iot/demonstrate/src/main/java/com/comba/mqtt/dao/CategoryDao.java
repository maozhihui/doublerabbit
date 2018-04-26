package com.comba.mqtt.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comba.mqtt.dao.model.CategoryEntity;

/**
 * 
 * @author maozhihui
 * @date 2017年10月31日 下午1:52:46
 */
public interface CategoryDao extends JpaRepository<CategoryEntity, Integer> {

}
