package com.comba.server.dao;

import com.comba.server.dao.device.DeviceTokenDao;
import com.comba.server.dao.device.DeviceTokenService;
import com.comba.server.dao.plugin.PluginJpaService;
import com.comba.server.dao.rule.RuleService;
import com.comba.server.dao.tenant.TenantService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest()
//@TestPropertySource(locations = {"classpath:application-test.yml"})
@ActiveProfiles("test")
@Transactional
@Rollback
public abstract class AbstractDaoTest {

	@Autowired
	protected DeviceTokenDao tokenDao;
	
	@Autowired
	protected DeviceTokenService tokenService;
	
	@Autowired
	protected RuleService ruleService;
	
	@Autowired
	protected PluginJpaService pluginJpaService;
	
	@Autowired
	protected TenantService tenantService;
}
