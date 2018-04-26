package com.comba.server.common.msg.session;

import com.comba.server.common.msg.aware.SessionAwareMsg;
import com.comba.server.common.msg.device.ToDeviceActorMsg;

/**
 * NotificationMsg
 * 用于包装接入第三方封闭系统的通知消息，目前为MQTT推送方式
 * @author maozhihui
 * @create 2017-10-10 13:22
 **/
public interface NotificationMsg extends SessionAwareMsg {
    ToDeviceActorMsg getToDeviceActorMsg();
}
