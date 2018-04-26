package com.comba.server.actors.shared;

import lombok.Data;

import java.io.Serializable;

import com.comba.server.common.data.id.SessionId;

@Data
public class SessionTimeoutMsg implements Serializable {

    private static final long serialVersionUID = 1L;

    private final SessionId sessionId;
}
