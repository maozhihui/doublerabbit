package com.comba.server.service.security.device;

import com.comba.server.common.data.Device;
import com.comba.server.common.data.DeviceToken;
import com.comba.server.common.data.id.DeviceId;
import com.comba.server.common.data.security.DeviceCredentialsFilter;
import com.comba.server.common.data.session.RegisterSession;
import com.comba.server.common.data.web.utils.UUIDUtils;
import com.comba.server.common.transport.auth.DeviceAuthResult;
import com.comba.server.common.transport.auth.DeviceAuthService;
import com.comba.server.dao.device.DeviceService;
import com.comba.server.dao.device.DeviceTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class DefaultDeviceAuthService implements DeviceAuthService {

    @Autowired
    DeviceService deviceService;

    @Autowired
    private DeviceTokenService tokenService;
    
    //@Value("${device.setting.AuthEnable}")
    //@Getter @Setter private boolean authEnable;

    @Override
    public DeviceAuthResult process(DeviceCredentialsFilter credentialsFilter) {
    	return DeviceAuthResult.of("Credentials is empty!");
    }

    @Override
    public Optional<Device> findDeviceById(DeviceId deviceId) {
        return Optional.ofNullable(deviceService.findDeviceById(deviceId));
    }
    
    @Override
    public Optional<Device> findDeviceByHardIdentity(String hardIdentity){
    	return deviceService.findDeviceByHardIdentity(hardIdentity);
    }

	@Override
	public RegisterSession getDeviceRegisterSession(String devId) {
		// TODO Auto-generated method stub
		String innerIdFormat = UUIDUtils.toUUID(UUID.fromString(devId));
		DeviceId id = DeviceId.fromString(devId);
		
		Device device = deviceService.findDeviceById(id);
		
		if(device == null) return null;

		DeviceToken deviceToken = tokenService.findTokenByDevId(innerIdFormat);

		RegisterSession session = new RegisterSession();
		session.setDeviceId(devId);
		session.setStatus(device.getStatus() == 0 ? RegisterSession.Status.OFFLINE : RegisterSession.Status.ONLINE );
		if(deviceToken != null){
			session.setFreshTime(deviceToken.getSessionUpdateTime().getTime());
			session.setRegisterTime(deviceToken.getTokenCreateTime().getTime());
			session.setToken(deviceToken.getToken());
		}else {
			session.setToken("");
		}
		return session;		
	}

	@Override
	public boolean saveRegisterSession(RegisterSession session) {
		// TODO Auto-generated method stub
		//QX000100275-18
		String innerIdFormat = UUIDUtils.toUUID(UUID.fromString(session.getDeviceId()));
		//DeviceTokenEntity entity = tokenDao.findOneByDevId(innerIdFormat);
		DeviceToken entity = tokenService.findTokenByDevId(innerIdFormat);
		if(entity == null){
			entity = new DeviceToken();
			entity.setTokenCreateTime(new Date(session.getRegisterTime()));
			entity.setSessionUpdateTime(entity.getTokenCreateTime());
			entity.setDeviceId(session.getDeviceId());
			entity.setToken(session.getToken());
			entity.setId(UUID.randomUUID().toString());
		}else{
			//QX000100302-17
			entity.setToken(session.getToken());
			entity.setTokenCreateTime(new Date(session.getRegisterTime()));
			entity.setSessionUpdateTime(new Date(session.getFreshTime()));
		}
		
		entity.setDeviceId(innerIdFormat);
		tokenService.saveAndFlush(entity);
		return false;
	}

	@Override
	public Integer deleteRegisterSessionByDevId(String devId) {
		// TODO Auto-generated method stub
		String innerIdFormat = UUIDUtils.toUUID(UUID.fromString(devId));
		return tokenService.deleteTokenByDevId(innerIdFormat);
	}

	@Override
	public String getRegisterToken(String devId){
		return "";
	}
}
