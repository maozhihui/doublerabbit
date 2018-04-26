package com.comba.server.actors.device;

import akka.event.LoggingAdapter;
import com.comba.server.actors.ActorSystemContext;
import com.comba.server.common.data.id.DeviceId;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.msg.core.*;
import com.comba.server.common.msg.session.ToDeviceMsg;
import com.comba.server.common.transport.auth.DeviceAuthService;
import com.comba.server.dao.device.DeviceService;
import com.comba.server.dao.device.TelemetryAttributesService;
import com.comba.server.dao.model.device.TelemetryAttributesEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.junit.Assert.*;

/**
 * DeviceActorMessageProcessor Test
 * author:maozhihui
 */
@RunWith(PowerMockRunner.class)
public class DeviceActorMessageProcessorTest {

    @Mock
    private ActorSystemContext systemContext;

    @Mock
    private LoggingAdapter adapter;

    private DeviceId deviceId = new DeviceId(UUID.randomUUID());

    private TenantId tenantId = new TenantId(UUID.randomUUID());

    //@InjectMocks
    private DeviceActorMessageProcessor processor;

    @Before
    public void init(){
        initMocks(this);
        List<TelemetryAttributesEntity> res = new ArrayList<>();
        TelemetryAttributesService attributesService = mock(TelemetryAttributesService.class);
        when(attributesService.getListByDevId(any())).thenReturn(res);
        when(systemContext.getTelemetryAttributesService()).thenReturn(attributesService);
        DeviceAuthService authService = mock(DeviceAuthService.class);
        when(authService.getDeviceRegisterSession(any())).thenReturn(null);
        when(authService.saveRegisterSession(any())).thenReturn(true);
        when(systemContext.getDeviceAuthService()).thenReturn(authService);
        DeviceService deviceService = mock(DeviceService.class);
        when(deviceService.updateStatus(any())).thenReturn(true);
        when(systemContext.getDeviceService()).thenReturn(deviceService);
        processor = new DeviceActorMessageProcessor(systemContext,adapter,deviceId);
    }

    @Test
    public void testProcessRegisterRequest(){
        BasicRegisterRequest request = new BasicRegisterRequest(anyInt(),anyString());
        try {
            Method method = PowerMockito.method(processor.getClass(),"processRegisterRequest",BasicRegisterRequest.class);
            ToDeviceMsg toDeviceMsg = (ToDeviceMsg)method.invoke(processor,request);
            assertTrue(toDeviceMsg.isSuccess());
        }catch (Exception e){
            System.out.print("failed:"+e.getMessage());
        }
    }

    @Test
    public void testProcessDeregisterRequest(){
        BasicDeregisterRequest request = new BasicDeregisterRequest(anyString(),anyString());
        try {
            Method method = PowerMockito.method(processor.getClass(),"processDeregisterRequest",BasicDeregisterRequest.class);
            ToDeviceMsg toDeviceMsg = (ToDeviceMsg)method.invoke(processor,request);
            assertTrue(toDeviceMsg.isSuccess());
        }catch (Exception e){
            System.out.print("failed:"+e.getMessage());
        }
    }

    @Test
    public void testProcessPingRequest(){
        BasicPingRequest request = new BasicPingRequest();
        try {
            Method method = PowerMockito.method(processor.getClass(),"processPingRequest",BasicPingRequest.class);
            ToDeviceMsg toDeviceMsg = (ToDeviceMsg)method.invoke(processor,request);
            assertFalse(toDeviceMsg.isSuccess());
        }catch (Exception e){
            System.out.print("failed:"+e.getMessage());
        }
    }
}
