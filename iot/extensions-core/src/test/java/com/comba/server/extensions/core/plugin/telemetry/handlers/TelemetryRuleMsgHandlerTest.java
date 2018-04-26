package com.comba.server.extensions.core.plugin.telemetry.handlers;

import com.comba.server.common.data.device.ConfigAttributes;
import com.comba.server.common.data.device.TelemetryAttributes;
import com.comba.server.common.data.id.DeviceId;
import com.comba.server.common.data.id.RuleId;
import com.comba.server.common.data.id.TenantId;
import com.comba.server.common.data.kv.StringDataEntry;
import com.comba.server.common.msg.core.BasicGetAttributesRequest;
import com.comba.server.common.msg.core.BasicTelemetryUploadRequest;
import com.comba.server.common.msg.core.GetAttributesRequest;
import com.comba.server.common.msg.core.TelemetryUploadRequest;
import com.comba.server.extensions.api.device.DeviceTelemetryAttributes;
import com.comba.server.extensions.api.plugins.PluginContext;
import com.comba.server.extensions.api.plugins.msg.GetAttributesRequestRuleToPluginMsg;
import com.comba.server.extensions.api.plugins.msg.TelemetryUploadRequestRuleToPluginMsg;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * TelemetryRuleMsgHandler Test
 *
 * @author maozhihui
 * @create 2017-09-29 13:30
 **/
@RunWith(PowerMockRunner.class)
public class TelemetryRuleMsgHandlerTest {

    @Mock
    private PluginContext pluginContext;

    private TenantId tenantId;
    private RuleId ruleId;

    private TelemetryRuleMsgHandler handler;

    @Before
    public void before(){
        initMocks(this);
        handler = new TelemetryRuleMsgHandler();
        tenantId = new TenantId(UUID.randomUUID());
        ruleId = new RuleId(UUID.randomUUID());
    }

    @Test
    public void testHandleGetAttributesRequest(){
        GetAttributesRequest request = new BasicGetAttributesRequest(10);
        GetAttributesRequestRuleToPluginMsg msg = mock(GetAttributesRequestRuleToPluginMsg.class);
        when(msg.getPayload()).thenReturn(request);
        DeviceId deviceId = new DeviceId(UUID.randomUUID());
        when(msg.getDeviceId()).thenReturn(deviceId);
        List<ConfigAttributes> res = new ArrayList<>();
        when(pluginContext.loadAttributes(any())).thenReturn(res);
        handler.handleGetAttributesRequest(pluginContext,tenantId,ruleId,msg);
    }

    @Test
    public void testHandleTelemetryUploadRequest(){
        BasicTelemetryUploadRequest request = new BasicTelemetryUploadRequest("123456");
        request.add(System.currentTimeMillis(),new StringDataEntry("vendor","comba"));
        TelemetryUploadRequestRuleToPluginMsg msg = mock(TelemetryUploadRequestRuleToPluginMsg.class);
        when(msg.getPayload()).thenReturn(request);
        List<TelemetryAttributes> deviceTelemetryAttributes = new ArrayList<>();
        DeviceTelemetryAttributes attributes = new DeviceTelemetryAttributes(deviceTelemetryAttributes);
        when(msg.getTelemetryAttributes()).thenReturn(attributes);
        handler.handleTelemetryUploadRequest(pluginContext,tenantId,ruleId,msg);
    }
}
