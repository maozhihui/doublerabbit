package com.comba.transport.pshare.protocol;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;

import com.comba.transport.pshare.common.ByteUtil;
import com.comba.transport.pshare.message.PSHAREAbstractMessage;
import com.comba.transport.pshare.message.PSHAREGetLockStateReqMessage;
import com.comba.transport.pshare.message.PSHARELockDownReqMessage;
import com.comba.transport.pshare.message.PSHARELockUpReqMessage;
@Slf4j
public class PSHAREMessageEncoder implements MessageEncoder {
	private static final Set TYPES;

    static {
        Set types = new HashSet();

        types.add(PSHAREGetLockStateReqMessage.class);
        types.add(PSHARELockUpReqMessage.class);
        types.add(PSHARELockDownReqMessage.class);
        TYPES = Collections.unmodifiableSet(types);
    }

	public void encode(IoSession session, Object message,
            ProtocolEncoderOutput out) throws Exception {
		// TODO Auto-generated method stub
		IoBuffer buf=IoBuffer.allocate(2048).setAutoExpand(true);
		PSHAREAbstractMessage msg = (PSHAREAbstractMessage)message;
		
		msg.encodeMsg(buf);
		buf.flip();
		out.write(buf);
		log.debug("encode message:", ByteUtil.toHexForLog(buf));
	}
}
