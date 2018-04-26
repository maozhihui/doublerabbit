package com.comba.server.dao.device;

import com.comba.server.common.data.device.ConfigAttributes;
import com.comba.server.common.data.id.DeviceId;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.AbstractDaoTest;
import com.comba.server.dao.model.device.ConfigAttributesEntity;
import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ConfigAttributesServiceTest extends AbstractDaoTest {

    @Autowired
    private ConfigAttributesService configAttributesService;

    @Autowired
    private ConfigAttributesDao configAttributesDao;

    private ConfigAttributes configAttributes = newConfigAttributes();

    @Before
    public void setUp() throws Exception {
        ConfigAttributesEntity configAttributesEntity = new ConfigAttributesEntity(configAttributes);
        configAttributesDao.save(configAttributesEntity);
        configAttributes.setConfigAttributeId(configAttributesEntity.getConfigAttributeId());
    }

    @After
    public void tearDown() throws Exception {
        configAttributesService.delete(configAttributes.getConfigAttributeId());
    }

    @Test
    public void findAll() throws Exception {
        List<ConfigAttributes> configAttributesEntities = configAttributesService.findAll();
        assertTrue(configAttributesEntities.size() >= 0);
    }

    @Test
    public void pagedQuery() throws Exception {
        List<String> orderBy = Lists.newArrayList("updateTime");
        Page page = configAttributesService.pagedQuery(100,1000,configAttributes,orderBy);
        Assert.assertTrue(page.getResultSize() >= 0);

    }

    @Test
    public void getDataByPage() throws Exception {
        Page page = configAttributesService.getDataByPage(configAttributes,100,1000);
        Assert.assertTrue(page.getResultSize() >= 0);
    }

    @Test
    public void deleteByIds() throws Exception {
    }

    @Test
    public void findByIds() throws Exception {
        List<String> ids = Lists.newArrayList(configAttributes.getConfigAttributeId());
        List<ConfigAttributes> configAttributesList = configAttributesService.findByIds(ids);
        assertTrue(configAttributesList.size() >= 1);
    }

    @Test
    public void save() throws Exception {
        configAttributesService.save(configAttributes);
    }

    @Test
    public void findAllByDeviceId() throws Exception {
        List<ConfigAttributes> configAttributesList = configAttributesService.findAllByDeviceId(DeviceId.fromString2(configAttributes.getDevId()));
        assertTrue(configAttributesList.size() >= 1);
    }


    private ConfigAttributes newConfigAttributes(){
        ConfigAttributes configAttributes = new ConfigAttributes();
        configAttributes.setAttributeName("test");
        configAttributes.setDevId("8a8aeb8e5e4f8fa8015e512b60890041");
        configAttributes.setValueType("String");
        configAttributes.setAttributeScope("1");
        configAttributes.setReadOnly(1);
        return configAttributes;
    }

}