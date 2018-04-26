package com.comba.server.extensions.core.action;

import com.comba.server.common.msg.core.BasicCmdMsg;
import com.comba.server.common.msg.device.ToDeviceActorMsg;
import com.comba.server.common.msg.session.ToDeviceMsg;
import com.comba.server.extensions.api.component.Action;
import com.comba.server.extensions.api.plugins.PluginAction;
import com.comba.server.extensions.api.plugins.msg.*;
import com.comba.server.extensions.api.rules.RuleContext;
import com.comba.server.extensions.api.rules.RuleProcessingMetaData;
import com.comba.server.extensions.api.rules.SimpleRuleLifecycleComponent;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author maozhihui
 * @Description:
 * @create 2017/12/1 16:45
 **/
@Slf4j
@Action(name = "DeviceCtrlAction",configuration = DeviceCtrlActionConfiguration.class)
public class DeviceCtrlAction extends SimpleRuleLifecycleComponent implements PluginAction<DeviceCtrlActionConfiguration> {

    private DeviceCtrlActionConfiguration config;

    @Override
    public Optional<RuleToPluginMsg<?>> convert(RuleContext ctx, ToDeviceActorMsg toDeviceActorMsg, RuleProcessingMetaData deviceMsgMd) {
        log.info("DeviceCtrlAction,deviceId [{}]",toDeviceActorMsg.getDeviceId().toString());
        List<DeviceCtrlActionConfiguration.CtrlEntity> entities = config.getActions();
        List<BasicCmdMsg> cmdMsgs = new ArrayList<>();
        for (DeviceCtrlActionConfiguration.CtrlEntity entity : entities){
            BasicCmdMsg cmdMsg = new BasicCmdMsg(entity.getType(),entity.getDst(),entity.getData());
            cmdMsgs.add(cmdMsg);
        }
        return Optional.of(new BasicCmdRuleToPluginMsg(toDeviceActorMsg.getTenantId(),toDeviceActorMsg.getCustomerId(),toDeviceActorMsg.getDeviceId(),
                new CtrlCmdMsg(cmdMsgs)));
    }

    @Override
    public Optional<ToDeviceMsg> convert(PluginToRuleMsg<?> response) {
        if (response instanceof ResponsePluginToRuleMsg) {
            return Optional.of(((ResponsePluginToRuleMsg) response).getPayload());
        }
        return Optional.empty();
    }

    @Override
    public boolean isOneWayAction() {
        return false;
    }

    @Override
    public void init(DeviceCtrlActionConfiguration configuration) {
        this.config = configuration;
    }
}
