package com.comba.server.dao.device;

import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Test;

import com.comba.server.common.data.DeviceToken;
import com.comba.server.common.data.web.utils.UUIDUtils;
import com.comba.server.dao.AbstractDaoTest;

public class DeviceTokenServiceTest extends AbstractDaoTest {

	private static final String DEVICE_ID = "ff8080815def3316015df309b317010e";
	
	private DeviceToken generateDeviceToken(){
		DeviceToken token = new DeviceToken();
		token.setId(DEVICE_ID);
		token.setDeviceId(DEVICE_ID);
		token.setToken(UUIDUtils.create().toString());
		Date date = new Date(System.currentTimeMillis());
		token.setSessionUpdateTime(date);
		token.setTokenCreateTime(date);
		return token;
	}
	
	@Test
	public void testsaveAndFlush(){
		 DeviceToken deviceToken = tokenService.saveAndFlush(generateDeviceToken());
		 assertNotNull(deviceToken);
	}
	
	@Test
	public void testFindTokenByDevId(){
		DeviceToken deviceToken = tokenService.saveAndFlush(generateDeviceToken());
		DeviceToken res = tokenService.findTokenByDevId(deviceToken.getDeviceId());
		assertNotNull(res);
	}
	
	@Test
	public void testDeleteTokenByDevId(){
		DeviceToken deviceToken = tokenService.saveAndFlush(generateDeviceToken());
		Integer res = tokenService.deleteTokenByDevId(deviceToken.getDeviceId());
		assertThat(res, is(1));
	}
}
