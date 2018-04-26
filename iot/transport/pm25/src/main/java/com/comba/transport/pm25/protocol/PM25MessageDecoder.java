package com.comba.transport.pm25.protocol;


import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;

import com.comba.transport.pm25.common.ByteUtil;
import com.comba.transport.pm25.common.Constant;
import com.comba.transport.pm25.message.PM25AbstractMessage;
import com.comba.transport.pm25.message.PM25ChangeDeviceAddrRespMessage;
import com.comba.transport.pm25.message.PM25DeviceMacMessage;
import com.comba.transport.pm25.message.PM25QueryDeviceAddrRespMessage;
import com.comba.transport.pm25.message.PM25ReadDataRespMessage;

@Slf4j
public class PM25MessageDecoder implements MessageDecoder {
	
	public MessageDecoderResult decodable(IoSession session, IoBuffer in) {
		// TODO Auto-generated method stub
		log.debug("decodable Received message:" + ByteUtil.toHexForLog(in));
		
		int msgLen = in.remaining();
		byte deviceAddr = in.get();
		byte funCode = in.get();
		
		if(funCode == Constant.READ_DATA_RESP
				||funCode == Constant.CHANGE_DEVICE_ADDR_RESP
				||funCode == Constant.QUERY_DEVICE_ADDR_RESP
				||msgLen == Constant.MAC_LEN){
			return MessageDecoderResult.OK;
		}
		else {
			log.warn("funCode not suport!" + funCode);
		}
        // Return NOT_OK if not matches.
        return MessageDecoderResult.NOT_OK;
	}

	public MessageDecoderResult decode(IoSession session, IoBuffer buf, ProtocolDecoderOutput out) throws Exception {
		// TODO Auto-generated method stub
		
		log.debug("decode Received message:" + ByteUtil.toHexForLog(buf));

		int bufLen = buf.remaining();
		byte[] bufBytes = new byte[bufLen];
		buf.get(bufBytes);
			
		IoBuffer buffer=IoBuffer.allocate(bufLen).setAutoExpand(true);
		buffer.put(bufBytes);
		buffer.flip();

		byte deviceAddr = buffer.get();
		byte funCode = buffer.get();
		byte dataLen = bufBytes[2];
		
		PM25AbstractMessage msg = null;
		
		if(funCode == Constant.READ_DATA_RESP && dataLen ==0x14) {
			msg = new PM25ReadDataRespMessage();
		}else if(funCode == Constant.CHANGE_DEVICE_ADDR_RESP) {
			msg = new PM25ChangeDeviceAddrRespMessage();
		}else if(funCode == Constant.QUERY_DEVICE_ADDR_RESP) {
			msg = new PM25QueryDeviceAddrRespMessage();
		}
		else if(bufLen == Constant.MAC_LEN){	//MAC
			PM25DeviceMacMessage message = new PM25DeviceMacMessage();
			message.setMac(bufBytes);
			out.write(message);
			return MessageDecoderResult.OK;
		}
		else {
			log.warn("funCode not suport!" + funCode);
			return MessageDecoderResult.NOT_OK;
		}
		msg.setDeviceAddr(deviceAddr);
		msg.decodeMsg(buffer);
		out.write(msg);//写错为session.write(msg),则变成发送消息.
		
        return MessageDecoderResult.OK;
	}

	public void finishDecode(IoSession arg0, ProtocolDecoderOutput arg1) throws Exception {
		// TODO Auto-generated method stub
	}
}
