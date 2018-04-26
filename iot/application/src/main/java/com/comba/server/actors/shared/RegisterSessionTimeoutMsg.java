package com.comba.server.actors.shared;

import java.io.Serializable;

import com.comba.server.common.data.id.SessionId;

import lombok.Data;

/**
 * @author xianhongdong
 *
 */
@Data
public class RegisterSessionTimeoutMsg implements Serializable{
	 private static final long serialVersionUID = 1L;

	    private final SessionId sessionId;
}
