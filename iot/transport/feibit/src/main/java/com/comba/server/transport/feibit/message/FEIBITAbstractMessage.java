package com.comba.server.transport.feibit.message;


import java.io.Serializable;

import lombok.Data;
import org.apache.mina.core.buffer.IoBuffer;
import com.comba.server.transport.feibit.common.ByteUtil;
import com.comba.server.transport.feibit.common.Constant;

@Data
public abstract class FEIBITAbstractMessage implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// message length:2 bytes
	public static final int MSG_LEN = 2;
	protected short msgLen = 0;
	// gateway sn length:4 bytes
	public static final int SNID_LEN = 4;
	protected String gatewaySnId = null;
	// 控制标志
	protected byte controlFlag = Constant.CONTROL_FLAG;
	// 消息类型
	protected byte command = 0;

	public void encodeHeader(IoBuffer buf) {
		
		buf.putShort(msgLen);
		byte[] snIdBytes = ByteUtil.hexStringToBytes(gatewaySnId);
		buf.put(snIdBytes[3]);
		buf.put(snIdBytes[2]);
		buf.put(snIdBytes[1]);
		buf.put(snIdBytes[0]);
		buf.put(controlFlag);
		buf.put(command);
	}

	public void decodeHeader(IoBuffer buf) {
		msgLen = buf.getShort();
		byte[] snIdBytes = new byte[SNID_LEN];
		buf.get(snIdBytes);
		gatewaySnId = new String(snIdBytes);
		controlFlag = buf.get();
		command = buf.get();
	}
	
	public abstract void encodeBody(IoBuffer bt);
	public abstract void decodeBody(byte[] body);
}
