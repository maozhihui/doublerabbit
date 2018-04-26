package com.comba.server.actors.device;

import lombok.Data;

import java.util.Optional;

import com.comba.server.common.msg.cluster.ServerAddress;
import com.comba.server.common.msg.session.SessionType;

/**
 * @author Andrew Shvayka
 */
@Data
public class SessionInfo {
    private final SessionType type;
    private final Optional<ServerAddress> server;
}
