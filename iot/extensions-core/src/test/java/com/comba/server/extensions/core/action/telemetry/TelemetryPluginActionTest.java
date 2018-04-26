package com.comba.server.extensions.core.action.telemetry;

import com.comba.server.common.data.device.TelemetryAttributes;
import com.comba.server.common.data.kv.AttributeKvEntry;
import com.comba.server.common.data.kv.BaseAttributeKvEntry;
import com.comba.server.common.data.kv.KvEntry;
import com.comba.server.common.data.kv.StringDataEntry;
import com.comba.server.common.msg.core.*;
import com.comba.server.common.msg.device.ToDeviceActorMsg;
import com.comba.server.extensions.api.device.DeviceTelemetryAttributes;
import com.comba.server.extensions.api.plugins.msg.RuleToPluginMsg;
import com.comba.server.extensions.api.rules.RuleContext;
import com.comba.server.extensions.api.rules.RuleProcessingMetaData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.validation.constraints.AssertTrue;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.mockito.Matchers.anyString;
import static org.junit.Assert.*;

/**
 * TelemetryPluginAction Test
 *
 * @author maozhihui
 * @create 2017-09-29 10:02
 **/
@RunWith(PowerMockRunner.class)
public class TelemetryPluginActionTest {

    @Mock
    private RuleContext ruleContext;

    @Mock
    private ToDeviceActorMsg toDeviceActorMsg;

    @Mock
    private RuleProcessingMetaData metaData;

    //@Mock
    private TelemetryPluginAction telemetryPluginAction;

    @Before
    public void before(){
        initMocks(this);
        List<TelemetryAttributes> telemetryAttributes = new ArrayList<>();
        DeviceTelemetryAttributes deviceAttributes = new DeviceTelemetryAttributes(telemetryAttributes);
        when(ruleContext.getDeviceTelemetryAttributes()).thenReturn(deviceAttributes);
        telemetryPluginAction = new TelemetryPluginAction();
    }

    @Test
    public void testConvertRequest01(){
        BasicTelemetryUploadRequest fromDeviceMsg = new BasicTelemetryUploadRequest("123456");
        KvEntry kvEntry = new StringDataEntry("vendor","comba");
        fromDeviceMsg.add(System.currentTimeMillis(),kvEntry);
        when(toDeviceActorMsg.getPayload()).thenReturn(fromDeviceMsg);
        Optional<RuleToPluginMsg<?>> res = telemetryPluginAction.convert(ruleContext,toDeviceActorMsg,metaData);
        assertTrue(res.isPresent());
    }

    @Test
    public void testConvertRequest02(){
        BasicUpdateAttributesRequest fromDeviceMsg = new BasicUpdateAttributesRequest();
        KvEntry kvEntry = new StringDataEntry("vendor","comba");
        AttributeKvEntry attributeKvEntry = new BaseAttributeKvEntry(kvEntry,System.currentTimeMillis());
        fromDeviceMsg.add(attributeKvEntry);
        when(toDeviceActorMsg.getPayload()).thenReturn(fromDeviceMsg);
        Optional<RuleToPluginMsg<?>> res = telemetryPluginAction.convert(ruleContext,toDeviceActorMsg,metaData);
        assertTrue(res.isPresent());
    }

    @Test
    public void testConvertRequest03(){
        BasicGetAttributesRequest fromDeviceMsg = new BasicGetAttributesRequest(20);
        when(toDeviceActorMsg.getPayload()).thenReturn(fromDeviceMsg);
        Optional<RuleToPluginMsg<?>> res = telemetryPluginAction.convert(ruleContext,toDeviceActorMsg,metaData);
        assertTrue(res.isPresent());
    }

    @Test
    public void testConvertRequest04(){
        RegisterRequest fromDeviceMsg = new BasicRegisterRequest("123456");
        when(toDeviceActorMsg.getPayload()).thenReturn(fromDeviceMsg);
        Optional<RuleToPluginMsg<?>> res = telemetryPluginAction.convert(ruleContext,toDeviceActorMsg,metaData);
        assertFalse(res.isPresent());
    }
}
