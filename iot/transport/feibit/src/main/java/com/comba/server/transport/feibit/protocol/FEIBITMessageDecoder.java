package com.comba.server.transport.feibit.protocol;

import lombok.extern.slf4j.Slf4j;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;

import com.comba.server.transport.feibit.common.ByteUtil;
import com.comba.server.transport.feibit.common.Constant;
import com.comba.server.transport.feibit.message.FEIBITAbstractRespMessage;
import com.comba.server.transport.feibit.message.FEIBITDeviceSwitchStateRespMessage;
import com.comba.server.transport.feibit.message.FEIBITGetDevicesRespMessage;
import com.comba.server.transport.feibit.message.FEIBITGetGatewayInfoRespMessage;
import com.comba.server.transport.feibit.message.FEIBITInfraredDataRespMessage;
import com.comba.server.transport.feibit.message.FEIBITNodeActiveReportingMessage;
import com.comba.server.transport.feibit.message.FEIBITSettingDeviceRespMessage;

@Slf4j
public class FEIBITMessageDecoder implements MessageDecoder {

	public MessageDecoderResult decodable(IoSession session, IoBuffer in) {
		// TODO Auto-generated method stub
		byte msgType = in.get();	//响应类型
		byte dataLen = in.get();	//后续包长度
		
		// recv a complete message
		if (in.remaining() < dataLen) {
			return MessageDecoderResult.NEED_DATA;
		}

		if (msgType == Constant.GET_DEVICES_RESP ||
			msgType == Constant.NODE_ACTIVE_REPORTING ||
			msgType == Constant.GET_GATEWAY_INFO_RESP ||
			msgType == Constant.DEVICE_STATE_RESP||
			msgType == Constant.DEVICE_SWITCH_STATE_RESP) {
			return MessageDecoderResult.OK;
		}

		// Return NOT_OK if not matches.
		return MessageDecoderResult.NOT_OK;
	}

	public MessageDecoderResult decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		// TODO Auto-generated method stub
		//log.debug("Receive message buffer:\r\n{}", ByteUtil.toHexForLog(in));
		
		FEIBITAbstractRespMessage msg = null;
		// Try to decode body
		byte response = in.get();	//响应类型
		byte dataLen = in.get();	//后续包长度
		byte[] body = null;
		body = new byte[dataLen];
		in.get(body);
		
		if (response == Constant.GET_DEVICES_RESP) {
			msg = new FEIBITGetDevicesRespMessage();
		} else if (response == Constant.NODE_ACTIVE_REPORTING) {
			msg = new FEIBITNodeActiveReportingMessage();
		} else if (response == Constant.GET_GATEWAY_INFO_RESP) {
			msg = new FEIBITGetGatewayInfoRespMessage();			
		} else if (response == Constant.DEVICE_STATE_RESP) {
			//还要细分消息类型
			byte flag = body[0];
			if(flag == Constant.COMMAND_FLAG){
				msg = new FEIBITSettingDeviceRespMessage();		
			}
			else if(flag == Constant.INFRARED_RETRANSMISSION_AND_LEARN){
				msg = new FEIBITInfraredDataRespMessage();
			}
			
		} else if (response == Constant.DEVICE_SWITCH_STATE_RESP) {
			msg = new FEIBITDeviceSwitchStateRespMessage();	
		} else {
			log.error("Can't decode the message!");
		}
		
		if(msg != null)
		{	
			msg.decodeBody(body);
			out.write(msg);
		}
		return MessageDecoderResult.OK;
	}

	public void finishDecode(IoSession arg0, ProtocolDecoderOutput arg1) throws Exception {
		// TODO Auto-generated method stub
	}
}
