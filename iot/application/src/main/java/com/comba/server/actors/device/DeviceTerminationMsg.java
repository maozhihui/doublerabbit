package com.comba.server.actors.device;

import com.comba.server.actors.shared.ActorTerminationMsg;
import com.comba.server.common.data.id.SessionId;

/**
 * DeviceActor终止消息
 * @author maozhihui
 *
 */
public class DeviceTerminationMsg extends ActorTerminationMsg<SessionId> {

	public DeviceTerminationMsg(SessionId id) {
		super(id);
	}

}
