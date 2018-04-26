package com.comba.server.common.msg.session;

import com.comba.server.common.data.id.SessionId;
import com.comba.server.common.msg.device.ToDeviceActorMsg;

/**
 * BasicNotificationMsg
 *
 * @author maozhihui
 * @create 2017-10-10 13:40
 **/
public class BasicNotificationMsg implements NotificationMsg{

    private final SessionId sessionId;
    private final ToDeviceActorMsg toDeviceActorMsg;

    public BasicNotificationMsg(SessionId sessionId,ToDeviceActorMsg msg){
        this.sessionId = sessionId;
        this.toDeviceActorMsg = msg;
    }

    @Override
    public ToDeviceActorMsg getToDeviceActorMsg() {
        return toDeviceActorMsg;
    }

    @Override
    public SessionId getSessionId() {
        return sessionId;
    }
}
