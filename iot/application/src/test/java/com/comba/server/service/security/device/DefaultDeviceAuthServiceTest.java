package com.comba.server.service.security.device;

import com.comba.server.common.data.Device;
import com.comba.server.common.data.DeviceToken;
import com.comba.server.common.data.id.DeviceId;
import com.comba.server.common.data.session.RegisterSession;
import com.comba.server.dao.device.DeviceService;
import com.comba.server.dao.device.DeviceTokenService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Date;
import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.junit.Assert.*;
/**
 * DefaultDeviceAuthService Test
 */
@RunWith(PowerMockRunner.class)
public class DefaultDeviceAuthServiceTest {

    @Mock
    private DeviceService deviceService;

    @Mock
    private DeviceTokenService tokenService;

    @InjectMocks
    private DefaultDeviceAuthService authService;

    @Before
    public void setUp() throws Exception{
        initMocks(this);
    }

    private RegisterSession getRegisterSession(){
        RegisterSession session = new RegisterSession();
        UUID uuid = UUID.randomUUID();
        session.setDeviceId(uuid.toString());
        session.setToken(uuid.toString());
        session.setRegisterTime(System.currentTimeMillis());
        session.setFreshTime(System.currentTimeMillis());
        session.setStatus(RegisterSession.Status.DEREGISERED);
        return session;
    }

    /**
     * 获取注册会话，查找device为null
     */
    @Test
    public void testGetDeviceRegisterSession01(){
        UUID uuid = UUID.randomUUID();
        DeviceId deviceId = new DeviceId(uuid);
        Device device = null;
        when(deviceService.findDeviceById(deviceId)).thenReturn(device);
        RegisterSession session = authService.getDeviceRegisterSession(uuid.toString());
        assertNull(session);
    }

    /**
     * 获取注册会话，查找deviceToken为null
     */
    @Test
    public void testGetDeviceRegisterSession02(){
        UUID uuid = UUID.randomUUID();
        DeviceId deviceId = new DeviceId(uuid);
        Device device = new Device();
        when(deviceService.findDeviceById(deviceId)).thenReturn(device);
        DeviceToken token = null;
        when(tokenService.findTokenByDevId(uuid.toString())).thenReturn(token);
        RegisterSession session = authService.getDeviceRegisterSession(uuid.toString());
        assertNull(session);
    }

    /**
     * 获取注册会话，正常返回
     */
    @Test
    public void testGetDeviceRegisterSession03(){
        UUID uuid = UUID.randomUUID();
        DeviceId deviceId = new DeviceId(uuid);
        Device device = new Device();
        when(deviceService.findDeviceById(deviceId)).thenReturn(device);
        DeviceToken token = new DeviceToken();
        token.setToken(uuid.toString());
        token.setDeviceId(uuid.toString());
        token.setSessionUpdateTime(new Date(System.currentTimeMillis()));
        token.setTokenCreateTime(new Date(System.currentTimeMillis()));
        when(tokenService.findTokenByDevId(anyString())).thenReturn(token);
        RegisterSession session = authService.getDeviceRegisterSession(uuid.toString());
        assertEquals(uuid.toString(),session.getToken());
    }

    /**
     * 保存注册会话，当数据库中不存在会话时
     * 发现问题：saveRegisterSession方法返回结果只有false
     */
    @Test
    public void testSaveRegisterSession01(){
        RegisterSession session = getRegisterSession();
        when(tokenService.findTokenByDevId(anyString())).thenReturn(null);
        when(tokenService.saveAndFlush(any())).thenReturn(null);
        assertFalse(authService.saveRegisterSession(session));
    }

    /**
     * 保存注册会话，当数据库中存在会话时
     * 发现问题：saveRegisterSession方法返回结果只有false
     */
    @Test
    public void testSaveRegisterSession02(){
        RegisterSession session = getRegisterSession();
        DeviceToken deviceToken = new DeviceToken();
        deviceToken.setToken(UUID.randomUUID().toString());
        deviceToken.setTokenCreateTime(new Date(System.currentTimeMillis()));
        deviceToken.setSessionUpdateTime(new Date(System.currentTimeMillis()));
        deviceToken.setDeviceId(session.getDeviceId());
        when(tokenService.findTokenByDevId(anyString())).thenReturn(deviceToken);
        when(tokenService.saveAndFlush(any())).thenReturn(null);
        assertFalse(authService.saveRegisterSession(session));
    }
}
