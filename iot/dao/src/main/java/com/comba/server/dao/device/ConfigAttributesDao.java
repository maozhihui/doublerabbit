package com.comba.server.dao.device;


import com.comba.server.dao.model.device.ConfigAttributesEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


/**
 * 设备参数参数配置数据层
 * @author wengzhonghui
 *
 */
public interface ConfigAttributesDao extends JpaRepository<ConfigAttributesEntity, String>{
	List<ConfigAttributesEntity> findByDevId(String devId);
}
