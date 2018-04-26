package com.comba.server.common.transport.session;

import lombok.extern.slf4j.Slf4j;

import com.comba.server.common.data.Device;
import com.comba.server.common.data.security.DeviceCredentials;
import com.comba.server.common.data.security.DeviceCredentialsFilter;
import com.comba.server.common.data.security.DeviceTokenCredentials;
import com.comba.server.common.data.session.RegisterSession;
import com.comba.server.common.data.web.utils.StringHelper;
import com.comba.server.common.data.web.utils.UUIDUtils;
import com.comba.server.common.msg.session.SessionContext;
import com.comba.server.common.msg.session.ex.DeviceInvalidPasswordException;
import com.comba.server.common.msg.session.ex.DeviceInvalidTokenException;
import com.comba.server.common.msg.session.ex.DeviceNotExistException;
import com.comba.server.common.transport.SessionMsgProcessor;
import com.comba.server.common.transport.auth.DeviceAuthService;

import java.util.Optional;

@Slf4j
public abstract class DeviceAwareSessionContext implements SessionContext {

    protected final DeviceAuthService authService;
    protected final SessionMsgProcessor processor;    
    protected volatile Device device;

    public DeviceAwareSessionContext(SessionMsgProcessor processor, DeviceAuthService authService) {
        this.processor = processor;
        this.authService = authService;
    }

    public DeviceAwareSessionContext(SessionMsgProcessor processor, DeviceAuthService authService, Device device) {
        this(processor, authService);
        this.device = device;
    }
    
    /**
     * 接入登录验证
     * 1，进行硬件标识与平台deviceId转换，如果成功则下一步，失败返回
     * 2，判断DeviceCredentialsType类型
     * 2.1 如果为USERNAME_PWD类型，
     * 2.1.1 开启用户名密码验证开关，则进行用户名与密码验证
     * 2.1.2 如果开关关闭，则平台deviceId转换成功则成功，否则失败
     * 2.2 如果为ACCESS_TOKEN类型
     * 2.2.1 判断相等则成功，否则失败
     * @param credentials
     * @return
     * @throws DeviceInvalidPasswordException 
     * @throws DeviceInvalidTokenException 
     * @throws DeviceNotExistException 
     */
    public boolean login(String hardIdentity,DeviceCredentialsFilter credentials) throws DeviceInvalidPasswordException, DeviceInvalidTokenException, DeviceNotExistException { 
    	boolean isValid = false;
    	if (checkDeviceExist(hardIdentity)) {
    		switch (credentials.getCredentialsType()) {
			case ACCESS_DEVICEID:
				isValid = true;
				break;
			case ACCESS_TOKEN:
				DeviceTokenCredentials deviceTokenCredentials = (DeviceTokenCredentials)credentials;
				RegisterSession registerSession = authService.getDeviceRegisterSession(UUIDUtils.toUUID(device.getDevId()).toString());
				if (registerSession != null && StringHelper.isNotEmpty(registerSession.getToken()) &&
						registerSession.getToken().equalsIgnoreCase(deviceTokenCredentials.getCredentialsId())) {
					isValid = true;
				}else {
					log.warn("{} server token is empty or device token {} is invalid.",device.getHardIdentity(),deviceTokenCredentials.getCredentialsId());
					throw new DeviceInvalidTokenException("invalid token");
				}
				break;
			case USERNAME_PWD:
				DeviceCredentials deviceCredentials = (DeviceCredentials)credentials;
				if ((StringHelper.isNotEmpty(device.getUserName()) && device.getUserName().equals(deviceCredentials.getCredentialsId())) &&
					(StringHelper.isNotEmpty(device.getSecretKey()) && device.getSecretKey().equals(deviceCredentials.getCredentialsValue()))) {
					isValid = true;
				}else {
					log.warn("{} device username {} and pwd {} is invalid.",device.getHardIdentity(),deviceCredentials.getCredentialsId(),deviceCredentials.getCredentialsValue());
					throw new DeviceInvalidPasswordException("username or password invalid");
				}
				break;
			default:
				log.warn("{} CredentialsType not supported!",credentials.getCredentialsType());								
			}
		}else{
			throw new DeviceNotExistException("device not exist");
		}
    	
    	
        return isValid;
    }

    public boolean login(DeviceCredentialsFilter credentials){
    	return true;
    }
    
    public boolean checkDeviceExist(String hardIdentity) {		

    	if (StringHelper.isEmpty(hardIdentity)) {
    		log.debug("hardIdentity param is invalid.");
			return false;
		}
		//根据硬件标识查询数据库
		Optional<Device> deviceOpt = authService.findDeviceByHardIdentity(hardIdentity);
		if (deviceOpt.isPresent()) {
			device = deviceOpt.get();
			return true;
		} else {
			log.debug("Can't find device using credentials [{}] due to {}", hardIdentity);
			return false;
		}
	}	
    
	public boolean checkDeviceExist(DeviceCredentialsFilter credentials) {		
		return checkDeviceExist(credentials.getCredentialsId());
	}
	
    public DeviceAuthService getAuthService() {
        return authService;
    }

    public SessionMsgProcessor getProcessor() {
        return processor;
    }

    public Device getDevice() {
        return device;
    }
}
