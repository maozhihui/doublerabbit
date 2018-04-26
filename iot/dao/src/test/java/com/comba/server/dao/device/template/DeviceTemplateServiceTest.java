package com.comba.server.dao.device.template;

import com.comba.server.common.data.device.DeviceTemplate;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.AbstractDaoTest;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DeviceTemplateServiceTest extends AbstractDaoTest{

    @Autowired
    private DeviceTemplateService deviceTemplateService;

    private DeviceTemplate deviceTemplate = newDeviceTemplate();

    @Test
    public void findAll()throws Exception {
        List<DeviceTemplate> deviceTemplates = deviceTemplateService.findAll();
        Assert.assertTrue(deviceTemplates.size() >= 1);
    }

    @Test
    public void pagedQuery() throws Exception {
        List<String> orderBy = Lists.newArrayList("updateTime");
        Page page = deviceTemplateService.pagedQuery(1,100,deviceTemplate,orderBy);
        Assert.assertTrue(page.getResultSize() >= 0);
    }

    @Test
    public void deleteByIds() throws Exception {
    }

    @Test
    public void findByIds() throws Exception {
        List<DeviceTemplate> deviceTemplates = deviceTemplateService.findByIds(Lists.newArrayList(deviceTemplate.getDeviceTemplateId()));
        Assert.assertTrue(deviceTemplates.size() >= 1);
    }

    @Test
    public void countByCategoryId() throws Exception {
        int count = deviceTemplateService.countByCategoryId(deviceTemplate.getCategoryId());
        Assert.assertTrue(count >= 0);
    }

    @Test
    public void countByName() throws Exception {
        int count = deviceTemplateService.countByName(deviceTemplate.getName());
        Assert.assertTrue(count >= 1);
    }

    @Test
    public void findByName() throws Exception {
        DeviceTemplate deviceTemplateDB = deviceTemplateService.findByName(deviceTemplate.getName());
        Assert.assertEquals(deviceTemplate.getName(),deviceTemplateDB.getName());
    }

    @Test
    public void getDataByPage() throws Exception {
        Page page = deviceTemplateService.getDataByPage(deviceTemplate,1,100);
        Assert.assertTrue(page.getResultSize() >= 0);
    }

    private DeviceTemplate newDeviceTemplate(){
        DeviceTemplate deviceTemplate = new DeviceTemplate();
        deviceTemplate.setName("网关参数模板");
        deviceTemplate.setDeviceTemplateId("8a8aeb8e5dc5d396015dc5dd5f8c0002");
        deviceTemplate.setCategoryId("ff8080815dc50f47015dc5b1500f0008");
        return deviceTemplate;
    }

}