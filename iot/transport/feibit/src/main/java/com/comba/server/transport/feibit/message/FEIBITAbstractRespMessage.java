package com.comba.server.transport.feibit.message;

import java.io.Serializable;

import org.apache.mina.core.buffer.IoBuffer;

public abstract class FEIBITAbstractRespMessage implements Serializable {
	
	protected byte deviceStateResp = 0;

	public abstract void encodeBody(IoBuffer bt);
	public abstract void decodeBody(byte[] body);
}
