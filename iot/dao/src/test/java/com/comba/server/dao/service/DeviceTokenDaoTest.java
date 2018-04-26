package com.comba.server.dao.service;

import java.util.UUID;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.comba.server.dao.AbstractDaoTest;
import com.comba.server.dao.model.device.DeviceTokenEntity;
import org.junit.Before;
import org.junit.After;

public class DeviceTokenDaoTest extends AbstractDaoTest{
	
	private DeviceTokenEntity entity;
	
	@Before
    public void before() {
		entity = getTestEntity();
		DeviceTokenEntity e2 = tokenDao.save(entity);
		Assert.assertEquals(entity.getDevId(),e2.getDevId());
		entity = e2;
	}
	@After
    public void after() {
		tokenDao.delete(entity);
	}
	/*@Test
	public void testDeleteRegisterSessionByDevId(){
		DeviceTokenEntity e1 = getTestEntity();
		DeviceTokenEntity e2 = tokenDao.save(entity);
		tokenDao.deleteByDevId(e2.getDevId());
		DeviceTokenEntity e3 = tokenDao.findOneByDevId(e2.getDevId());
		Assert.assertNull(e3);
		
		List<DeviceTokenEntity> list = tokenDao.findByDevId(e2.getDevId());
		Assert.assertEquals(list.size(), 0);
		tokenDao.deleteByDevId(e2.getDevId());
	}*/
	/*@Test
	public void testFindByDevId(){
		List<DeviceTokenEntity> list = tokenDao.findByDevId(entity.getDevId());
		Assert.assertEquals(1, list.size());
	}*/
	private DeviceTokenEntity getTestEntity(){
		DeviceTokenEntity entity = new DeviceTokenEntity();
		entity.setCreateTime(new Date());
		entity.setUpdateTime(new Date());
		entity.setDevId(UUID.randomUUID().toString());
		entity.setId(entity.getDevId());
		return entity;
	}
	
	@Test
	public void testAddRegisterSession(){
		DeviceTokenEntity entity = getTestEntity();
		DeviceTokenEntity e2 = tokenDao.save(entity);
		Assert.assertEquals(entity.getDevId(),e2.getDevId());
		tokenDao.deleteByDevId(e2.getDevId());
	}
	
}
