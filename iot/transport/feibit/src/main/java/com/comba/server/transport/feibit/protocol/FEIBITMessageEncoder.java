package com.comba.server.transport.feibit.protocol;


import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;
import com.comba.server.transport.feibit.common.ByteUtil;
import com.comba.server.transport.feibit.message.FEIBITAbstractMessage;
import com.comba.server.transport.feibit.message.FEIBITDeviceSwitchStateReqMessage;
import com.comba.server.transport.feibit.message.FEIBITGetDevicesMessage;
import com.comba.server.transport.feibit.message.FEIBITGetGatewayInfoMessage;
import com.comba.server.transport.feibit.message.FEIBITInfraredDataReqMessage;
import com.comba.server.transport.feibit.message.FEIBITInfraredDataSendMessage;
import com.comba.server.transport.feibit.message.FEIBITSetDeviceSwitchStateMessage;

@Slf4j
@SuppressWarnings("rawtypes")
public class FEIBITMessageEncoder implements MessageEncoder<Object> {
	@SuppressWarnings("unused")
	private static final Set<Class> TYPES;

    static {
        Set<Class> types = new HashSet<Class>();
               
        types.add(FEIBITGetDevicesMessage.class);
        types.add(FEIBITGetGatewayInfoMessage.class);
        types.add(FEIBITSetDeviceSwitchStateMessage.class);
        types.add(FEIBITDeviceSwitchStateReqMessage.class);
        types.add(FEIBITInfraredDataReqMessage.class);
        types.add(FEIBITInfraredDataSendMessage.class);
        
        TYPES = Collections.unmodifiableSet(types);
    }

	public void encode(IoSession session, Object message,
            ProtocolEncoderOutput out) throws Exception {
		// TODO Auto-generated method stub
		
		IoBuffer buf=IoBuffer.allocate(2048).setAutoExpand(true);
		FEIBITAbstractMessage m = (FEIBITAbstractMessage)message;
		m.encodeHeader(buf);
		m.encodeBody(buf);
    
		short msgLen = (short) buf.position();
		buf.putShort(0,ByteUtil.shortConvert_LH(msgLen));
		buf.flip();
		log.debug("Send message buffer:{}", ByteUtil.toHexForLog(buf));

		out.write(buf);
	}
}
