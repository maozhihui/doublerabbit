package com.comba.server.dao.plugin;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comba.server.dao.model.device.PluginJpaEntity;





/**
 * 设备管理数据层
 * @author wengzhonghui
 *
 */
public interface PluginJpaDao extends JpaRepository<PluginJpaEntity, String>{
	
	public List<PluginJpaEntity> findByTenantId(String tenantId);

	Integer deleteByTenantId(String tenantId);
}
