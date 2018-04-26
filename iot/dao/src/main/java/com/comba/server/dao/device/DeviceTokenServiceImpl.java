package com.comba.server.dao.device;

import static com.comba.server.common.data.CacheConstants.DEVICE_CREDENTIALS_CACHE;

import java.util.Optional;

import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.comba.server.common.data.DeviceToken;
import com.comba.server.dao.model.device.DeviceTokenEntity;
import com.comba.server.dao.util.DaoUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DeviceTokenServiceImpl implements DeviceTokenService {

	@Autowired
	private DeviceTokenDao tokenDao;
	
	@Override
	@Cacheable(cacheNames = DEVICE_CREDENTIALS_CACHE, key = "#deviceId", unless="#result == null")
	public DeviceToken findTokenByDevId(String deviceId) {
		if (StringHelper.isNotEmpty(deviceId)) {
			DeviceTokenEntity entity = tokenDao.findOneByDevId(deviceId);
			if (Optional.ofNullable(entity).isPresent()) {
				log.debug("deviceId [{}] find token [{}].",deviceId,entity.getToken());
				return DaoUtil.getData(entity);
			}else {
				log.warn("deviceId [{}] does not find token.",deviceId);
			}
		}
		return null;
	}

	@Override
	public DeviceToken saveAndFlush(DeviceToken deviceToken) {
		return tokenDao.saveAndFlush(new DeviceTokenEntity(deviceToken)).toData();
	}

	@Override
	@CacheEvict(cacheNames = DEVICE_CREDENTIALS_CACHE, key = "#deviceId", beforeInvocation = true)
	public Integer deleteTokenByDevId(String deviceId) {
		return tokenDao.deleteByDevId(deviceId);
	}

}
