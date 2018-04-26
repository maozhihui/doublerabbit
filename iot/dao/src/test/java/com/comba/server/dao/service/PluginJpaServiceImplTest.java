package com.comba.server.dao.service;

import java.util.List;

import org.junit.Test;

import com.comba.server.common.data.device.PluginJpa;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.data.web.utils.UUIDUtils;
import com.comba.server.dao.AbstractDaoTest;

public class PluginJpaServiceImplTest extends AbstractDaoTest {

	@Test
	public void testFindSystemPlugins(){
		List<PluginJpa> list = pluginJpaService.findSystemPlugins();
		for (PluginJpa pluginJpa : list) {
			System.out.println("pluginId="+pluginJpa.getId()+",tenantId="+pluginJpa.getTenantId());
			System.out.println("idid="+pluginJpa.getId().getId());
		}
	}
	
	@Test
	public void testFindTenantPlugins(){
		TenantId id = new TenantId(UUIDUtils.toUUID("8a8aeb955ce30528015ce308de8e0002"));
		List<PluginJpa> list = pluginJpaService.findTenantPlugins(id);
		for (PluginJpa pluginJpa : list) {
			System.out.println("pluginId="+pluginJpa.getId()+",tenantId="+pluginJpa.getTenantId());
		}
	}
	
}
