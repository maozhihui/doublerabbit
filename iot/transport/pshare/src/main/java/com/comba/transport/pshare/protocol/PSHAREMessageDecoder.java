package com.comba.transport.pshare.protocol;


import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;
import com.comba.transport.pshare.common.ByteUtil;
import com.comba.transport.pshare.common.Constant;
import com.comba.transport.pshare.message.PSHAREAbstractMessage;
import com.comba.transport.pshare.message.PSHAREDeviceMacMessage;
import com.comba.transport.pshare.message.PSHAREGetLockStateReqMessage;
import com.comba.transport.pshare.message.PSHAREGetLockStateRespMessage;
import com.comba.transport.pshare.message.PSHARELockDownRespMessage;
import com.comba.transport.pshare.message.PSHARELockUpRespMessage;

@Slf4j
public class PSHAREMessageDecoder implements MessageDecoder {
	
	public MessageDecoderResult decodable(IoSession session, IoBuffer in) {
		// TODO Auto-generated method stub
		log.debug("Decodable received message:", ByteUtil.toHexForLog(in));
		int msgLen = in.remaining();
		if(msgLen == Constant.MAC_LEN){
			return MessageDecoderResult.OK;
		}
		
		byte head = in.get();
		byte addr = in.get();
		byte len = in.get();
		int remain = in.remaining();//remain = len+crc+tail
		
		if(len > remain - 2){
			return MessageDecoderResult.NEED_DATA;
		}
		
		byte cmd = in.get();
			
		if(cmd == Constant.GET_LOCK_STATE
				||cmd == Constant.LOCK_UP
				||cmd == Constant.LOCK_DOWN
				||head == Constant.PACK_HEAD){
			return MessageDecoderResult.OK;
		}
		else {
			log.debug("Funcode not suport!{}" ,ByteUtil.byteToHexString(cmd));
		}
		//正常逻辑应该是MessageDecoderResult.NOT_OK，为了解决适配器返回不正常数据导致地锁无法控制问题，
		//不抛异常，从而不会端口Session连接.2018-02-05 hjl
        // Return NOT_OK if not matches.
        return MessageDecoderResult.OK;
	}

	public MessageDecoderResult decode(IoSession session, IoBuffer buf, ProtocolDecoderOutput out) throws Exception {
		// TODO Auto-generated method stub
		
		log.debug("Decode received message:{},{}" ,session.getRemoteAddress(), ByteUtil.toHexForLog(buf));

		int bufLen = buf.remaining();
		byte[] bufBytes = new byte[bufLen];
		buf.get(bufBytes);
			
		IoBuffer tmpbuf = IoBuffer.allocate(bufLen).setAutoExpand(true);
		tmpbuf.put(bufBytes);
		tmpbuf.flip();

		byte head = tmpbuf.get();
		byte addr = tmpbuf.get();
		byte len = tmpbuf.get();
		byte cmd = tmpbuf.get();
		
		PSHAREAbstractMessage msg = null;
		if(head == Constant.PACK_HEAD){
			msg = new PSHAREGetLockStateReqMessage();
		}
		else if(cmd == Constant.GET_LOCK_STATE) {
			msg = new PSHAREGetLockStateRespMessage();
		}
		else if(cmd == Constant.LOCK_UP) {
			msg = new PSHARELockUpRespMessage();
		}
		else if(cmd == Constant.LOCK_DOWN){
			msg = new PSHARELockDownRespMessage();
		}
		else if(bufLen == Constant.MAC_LEN){	//MAC
			PSHAREDeviceMacMessage message = new PSHAREDeviceMacMessage();
			message.setMac(bufBytes);
			out.write(message);
			return MessageDecoderResult.OK;
		}
		else {
			//正常逻辑应该是MessageDecoderResult.NOT_OK，为了解决适配器返回不正常数据导致地锁无法控制问题，
			//不抛异常，从而不会端口Session连接.2018-02-05 hjl
			
			//MAC地址和返回消息黏包处理
			IoBuffer tmp = IoBuffer.allocate(bufLen).setAutoExpand(true);
			tmp.put(bufBytes);
			tmp.flip();
		
			byte[] mac = new byte[Constant.MAC_LEN];
			tmp.get(mac);
			byte flag = tmp.get(); 
			
			if(flag == Constant.PACK_HEAD||
				flag == Constant.PACK_HEAD_SUCCESS||
				flag == Constant.PACK_HEAD_ERROR){
				
				PSHAREDeviceMacMessage message = new PSHAREDeviceMacMessage();
				message.setMac(mac);
				out.write(message);
			}
			return MessageDecoderResult.OK;
		}
		
		IoBuffer buffer = IoBuffer.allocate(bufLen).setAutoExpand(true);
		buffer.put(bufBytes);
		buffer.flip();
		
		msg.decodeMsg(buffer);
		out.write(msg);
		
        return MessageDecoderResult.OK;
	}

	public void finishDecode(IoSession arg0, ProtocolDecoderOutput arg1) throws Exception {
		// TODO Auto-generated method stub
	}
}
