package com.comba.transport.pm25.protocol;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;

import com.comba.transport.pm25.common.ByteUtil;
import com.comba.transport.pm25.message.PM25AbstractMessage;
import com.comba.transport.pm25.message.PM25ChangeDeviceAddrReqMessage;
import com.comba.transport.pm25.message.PM25QueryDeviceAddrReqMessage;
import com.comba.transport.pm25.message.PM25ReadDataReqMessage;

@Slf4j
public class PM25MessageEncoder implements MessageEncoder {
	private static final Set TYPES;

    static {
        Set types = new HashSet();

        types.add(PM25ReadDataReqMessage.class);
        types.add(PM25QueryDeviceAddrReqMessage.class);
        types.add(PM25ChangeDeviceAddrReqMessage.class);
        TYPES = Collections.unmodifiableSet(types);
    }

	public void encode(IoSession session, Object message,
            ProtocolEncoderOutput out) throws Exception {
		// TODO Auto-generated method stub
		IoBuffer buf=IoBuffer.allocate(2048).setAutoExpand(true);
		PM25AbstractMessage msg = (PM25AbstractMessage)message;
		
		msg.encodeMsg(buf);
		buf.flip();
		out.write(buf);
		log.debug("encode message:{}" ,ByteUtil.toHexForLog(buf));
	}
}
