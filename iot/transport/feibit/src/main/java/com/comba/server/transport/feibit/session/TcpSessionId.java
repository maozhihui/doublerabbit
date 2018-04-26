package com.comba.server.transport.feibit.session;

import java.util.UUID;

import com.comba.server.common.data.id.SessionId;

public class TcpSessionId implements SessionId {
    //final private UUID id;
	final private String id;
	
    public TcpSessionId() {
        this.id = UUID.randomUUID().toString();
    }
    
    public TcpSessionId(UUID id){
    	this.id = id.toString();
    }
    public TcpSessionId(String id){
    	this.id = id;
    }
    
    @Override
    public String toUidStr() {
        return id.toString();
    }
    

}
