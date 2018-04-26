package com.comba.server.dao.device;

import com.comba.server.dao.AbstractDaoTest;
import com.comba.server.dao.model.device.DeviceEntity;
import com.google.common.collect.Lists;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

public class DeviceDaoTest extends AbstractDaoTest{

    @Autowired
    private DeviceDao deviceDao;

    private  DeviceEntity deviceEntity = newDeviceEntity();

    @Before
    public void setUp() throws Exception {
        deviceDao.save(deviceEntity);
    }

    @After
    public void after() throws Exception {
        deviceDao.delete(deviceEntity.getDevId());
    }

    @Test
    public void deleteByTenantId() throws Exception {
        DeviceEntity device = newDeviceEntity();
        device.setTenantId("deleteTenantId");
        deviceDao.save(device);
        int ret = deviceDao.deleteByTenantId(device.getTenantId());
        Assert.assertEquals(1,ret);
    }

    @Test
    public void countByProductId() throws Exception {
        long ret = deviceDao.countByProductId("ff123456789");
        Assert.assertEquals(1,ret);
    }

    @Test
    public void countByTenantIdAndName() throws Exception {
        int ret = deviceDao.countByTenantIdAndName(deviceEntity.getTenantId(),deviceEntity.getName());
        Assert.assertEquals(1,ret);
    }

    @Test
    public void countByHardIdentity() throws Exception {
        int ret = deviceDao.countByHardIdentity(deviceEntity.getHardIdentity());
        Assert.assertEquals(1,ret);
    }

    @Test
    public void findByProductId() throws Exception {
        List<DeviceEntity> devices = deviceDao.findByProductId(deviceEntity.getProductId());
        Assert.assertEquals(deviceEntity.getProductId(),devices.get(0).getProductId());
    }

    @Test
    public void countByDeviceTemplateId() throws Exception {
        int ret = deviceDao.countByDeviceTemplateId(Lists.newArrayList(deviceEntity.getDeviceTemplateId()));
        Assert.assertTrue(ret >= 1);
    }

    @Test
    public void findByTenantIdAndName() throws Exception {
        List<DeviceEntity> deviceEntityDBs = deviceDao.findByTenantIdAndName(deviceEntity.getTenantId(),deviceEntity.getName());
        Assert.assertEquals(deviceEntity.getTenantId(),deviceEntityDBs.get(0).getTenantId());
    }

    private  DeviceEntity newDeviceEntity(){
        DeviceEntity device = new DeviceEntity();
        device.setProductId("ff123456789");
        device.setHardIdentity("ff123456789");
        device.setDeviceTemplateId("8a8aeb8e5dc5d396015dc5dd5f8c0002");
        device.setIsGateWay(1);
        device.setTenantId("ff123456789");
        device.setSn("001");
        device.setName("comba_Test");

        return device;
    }

}