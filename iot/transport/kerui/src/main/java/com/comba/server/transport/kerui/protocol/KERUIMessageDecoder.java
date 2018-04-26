package com.comba.server.transport.kerui.protocol;


import lombok.extern.slf4j.Slf4j;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;

import com.comba.server.transport.kerui.common.ByteUtil;
import com.comba.server.transport.kerui.common.Constant;
import com.comba.server.transport.kerui.message.KERUIAbstractMessage;
import com.comba.server.transport.kerui.message.KERUIBindingScreenAndDetectorReqMessage;
import com.comba.server.transport.kerui.message.KERUIBindingScreenAndDetectorRespMessage;
import com.comba.server.transport.kerui.message.KERUICpuIdReqMessage;
import com.comba.server.transport.kerui.message.KERUICpuIdRespMessage;
import com.comba.server.transport.kerui.message.KERUIDestMasterDetectorsStateReqMessage;
import com.comba.server.transport.kerui.message.KERUIDestMasterDetectorsStateRespMessage;
import com.comba.server.transport.kerui.message.KERUIDestMasterDevicesReqMessage;
import com.comba.server.transport.kerui.message.KERUIDestMasterDevicesRespMessage;
import com.comba.server.transport.kerui.message.KERUIDestMasterDevicesTypeReqMessage;
import com.comba.server.transport.kerui.message.KERUIDestMasterDevicesTypeRespMessage;
import com.comba.server.transport.kerui.message.KERUIDetectorStateReqMessage;
import com.comba.server.transport.kerui.message.KERUIDetectorStateRespMessage;
import com.comba.server.transport.kerui.message.KERUIMasterNumberReqMessage;
import com.comba.server.transport.kerui.message.KERUIMasterNumberRespMessage;
import com.comba.server.transport.kerui.message.KERUISetDetectorBoardParameterReqMessage;
import com.comba.server.transport.kerui.message.KERUISetDetectorBoardParameterRespMessage;
import com.comba.server.transport.kerui.message.KERUISetDetectorDisplayModelReqMessage;
import com.comba.server.transport.kerui.message.KERUISetDetectorDisplayModelRespMessage;
import com.comba.server.transport.kerui.message.KERUISetDetectorParameterReqMessage;
import com.comba.server.transport.kerui.message.KERUISetDetectorParameterRespMessage;
import com.comba.server.transport.kerui.message.KERUISetTimeReqMessage;
import com.comba.server.transport.kerui.message.KERUISetTimeRespMessage;

@Slf4j
public class KERUIMessageDecoder implements MessageDecoder {
	
	public MessageDecoderResult decodable(IoSession session, IoBuffer in) {
		// TODO Auto-generated method stub
		log.debug("decodable Received message:{}", ByteUtil.toHexForLog(in));
		
		byte packetHeader = in.get();	
		
		if(packetHeader != Constant.PACKET_HEADER){
			  log.error("can't decode packet header:[{}]", packetHeader);
			  return MessageDecoderResult.NOT_OK;
			}
		
		byte version = in.get();	
		byte destAddr = in.get();
		byte srcAddr = in.get();
		byte cmdCode = in.get();
		
		short dataLen = in.getShort();
		
		if (in.remaining() < dataLen + 2) {
            return MessageDecoderResult.NEED_DATA;
        }
		
		if(cmdCode == Constant.MASTER_NUMBER_RESP
			||cmdCode == Constant.MASTER_NUMBER_REQ
			||cmdCode == Constant.DEST_MASTER_DEVICES_RESP
			||cmdCode == Constant.DEST_MASTER_DEVICES_REQ
			||cmdCode == Constant.DEST_MASTER_DEVICES_TYPE_REQ
			||cmdCode == Constant.DEST_MASTER_DEVICES_TYPE_RESP
			||cmdCode == Constant.DEST_MASTER_DETECTORS_STATE_REQ
			||cmdCode == Constant.DEST_MASTER_DETECTORS_STATE_RESP
			||cmdCode == Constant.DETECTOR_STATE_REQ
			||cmdCode == Constant.DETECTOR_STATE_RESP
			||cmdCode == Constant.SET_DETECTOR_DISPLAY_MODEL_REQ
			||cmdCode == Constant.SET_DETECTOR_DISPLAY_MODEL_RESP
			||cmdCode == Constant.SET_TIME_REQ
			||cmdCode == Constant.SET_TIME_RESP
			||cmdCode == Constant.SET_DETECTOR_BOARD_PARAMETER_REQ
			||cmdCode == Constant.SET_DETECTOR_BOARD_PARAMETER_RESP
			||cmdCode == Constant.BINDING_SCREEN_AND_DETECTOR_REQ
			||cmdCode == Constant.BINDING_SCREEN_AND_DETECTOR_RESP
			||cmdCode == Constant.SET_DETECTOR_PARAMETER_REQ
			||cmdCode == Constant.SET_DETECTOR_PARAMETER_RESP
			||cmdCode == Constant.CPU_ID_REQ
			||cmdCode == Constant.CPU_ID_RESP){
			return MessageDecoderResult.OK;
		}
		else {
			log.error("CommandId [{}] not suport!", cmdCode);
		}
        // Return NOT_OK if not matches.
        return MessageDecoderResult.NOT_OK;
	}

	public MessageDecoderResult decode(IoSession session, IoBuffer buf, ProtocolDecoderOutput out) throws Exception {
		// TODO Auto-generated method stub
		log.debug("decode Received message buffer:{}", ByteUtil.toHexForLog(buf));
		
		KERUIAbstractMessage absMsg = new KERUIAbstractMessage();
		boolean result = absMsg.decodeMessage(buf);
		if(!result)
			return MessageDecoderResult.NOT_OK;
		
		byte cmdCode = absMsg.getCmdCode();
		byte[] data = absMsg.getData();
		
		KERUIAbstractMessage msg = null;
		if(cmdCode == Constant.MASTER_NUMBER_RESP) {
			msg = new KERUIMasterNumberRespMessage();
		}
		else if(cmdCode == Constant.MASTER_NUMBER_REQ){//
			msg = new KERUIMasterNumberReqMessage();
		}
		else if(cmdCode == Constant.DEST_MASTER_DEVICES_RESP){//
			msg = new KERUIDestMasterDevicesRespMessage();
		}
		else if(cmdCode == Constant.DEST_MASTER_DEVICES_REQ){//
			msg = new KERUIDestMasterDevicesReqMessage();
		}
		else if(cmdCode == Constant.DEST_MASTER_DEVICES_TYPE_REQ){//
			msg = new KERUIDestMasterDevicesTypeReqMessage();
		}
		else if(cmdCode == Constant.DEST_MASTER_DEVICES_TYPE_RESP){//
			msg = new KERUIDestMasterDevicesTypeRespMessage();
		}
		else if(cmdCode == Constant.DEST_MASTER_DETECTORS_STATE_REQ){//
			msg = new KERUIDestMasterDetectorsStateReqMessage();
		}
		else if(cmdCode == Constant.DEST_MASTER_DETECTORS_STATE_RESP){//
			msg = new KERUIDestMasterDetectorsStateRespMessage();
		}
		else if(cmdCode == Constant.DETECTOR_STATE_REQ){//
			msg = new KERUIDetectorStateReqMessage();
		}
		else if(cmdCode == Constant.DETECTOR_STATE_RESP){//
			msg = new KERUIDetectorStateRespMessage();
		}
		else if(cmdCode == Constant.SET_DETECTOR_DISPLAY_MODEL_REQ){//
			msg = new KERUISetDetectorDisplayModelReqMessage();
		}
		else if(cmdCode == Constant.SET_DETECTOR_DISPLAY_MODEL_RESP){//
			msg = new KERUISetDetectorDisplayModelRespMessage();
		}
		else if(cmdCode == Constant.SET_TIME_REQ){//
			msg = new KERUISetTimeReqMessage();
		}
		else if(cmdCode == Constant.SET_TIME_RESP){//
			msg = new KERUISetTimeRespMessage();
		}
		else if(cmdCode == Constant.SET_DETECTOR_BOARD_PARAMETER_REQ){//
			msg = new KERUISetDetectorBoardParameterReqMessage();
		}
		else if(cmdCode == Constant.SET_DETECTOR_BOARD_PARAMETER_RESP){//
			msg = new KERUISetDetectorBoardParameterRespMessage();
		}
		else if(cmdCode == Constant.BINDING_SCREEN_AND_DETECTOR_REQ){//
			msg = new KERUIBindingScreenAndDetectorReqMessage();
		}
		else if(cmdCode == Constant.BINDING_SCREEN_AND_DETECTOR_RESP){//
			msg = new KERUIBindingScreenAndDetectorRespMessage();
		}
		else if(cmdCode == Constant.SET_DETECTOR_PARAMETER_REQ){//
			msg = new KERUISetDetectorParameterReqMessage();
		}
		else if(cmdCode == Constant.SET_DETECTOR_PARAMETER_RESP){//
			msg = new KERUISetDetectorParameterRespMessage();
		}
		else if(cmdCode == Constant.CPU_ID_REQ){//
			msg = new KERUICpuIdReqMessage();
		}
		else if(cmdCode == Constant.CPU_ID_RESP){//
			msg = new KERUICpuIdRespMessage();
		}
		else {
			log.error("can't decode cmdCode:{}"+cmdCode);
			return MessageDecoderResult.NOT_OK;
		}
		msg.initMessage(absMsg);//absMsg赋值->msg
		msg.decodeData(null);//data可不要
			
		log.info(msg.toString());
		out.write(msg);
        return MessageDecoderResult.OK;
	}

	public void finishDecode(IoSession arg0, ProtocolDecoderOutput arg1) throws Exception {
		// TODO Auto-generated method stub
	}
}
