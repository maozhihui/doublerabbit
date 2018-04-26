package com.comba.server.actors.session;

import com.comba.server.actors.shared.ActorTerminationMsg;
import com.comba.server.common.data.id.SessionId;

public class SessionTerminationMsg extends ActorTerminationMsg<SessionId> {

    public SessionTerminationMsg(SessionId id) {
        super(id);
    }
}
