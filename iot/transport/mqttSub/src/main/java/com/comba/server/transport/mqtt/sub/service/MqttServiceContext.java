package com.comba.server.transport.mqtt.sub.service;

import com.comba.server.common.transport.SessionMsgProcessor;
import com.comba.server.common.transport.auth.DeviceAuthService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author maozhihui
 * @Description:
 * @create 2017/11/29 19:15
 **/
@Component
public class MqttServiceContext {

    @Autowired
    @Getter private SessionMsgProcessor processor;

    @Autowired
    @Getter private DeviceAuthService authService;
}
