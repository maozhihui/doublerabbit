package com.comba.server.transport.kerui.protocol;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;

import com.comba.server.transport.kerui.common.ByteUtil;
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
public class KERUIMessageEncoder implements MessageEncoder {
	private static final Set TYPES;

    static {
        Set types = new HashSet();
		types.add(KERUIMasterNumberRespMessage.class);
		types.add(KERUIMasterNumberReqMessage.class);
		types.add(KERUIDestMasterDevicesRespMessage.class);
		types.add(KERUIDestMasterDevicesReqMessage.class);
		types.add(KERUIDestMasterDevicesTypeReqMessage.class);
		types.add(KERUIDestMasterDevicesTypeRespMessage.class);
		types.add(KERUIDestMasterDetectorsStateReqMessage.class);
		types.add(KERUIDestMasterDetectorsStateRespMessage.class);
		types.add(KERUIDetectorStateReqMessage.class);
		types.add(KERUIDetectorStateRespMessage.class);
		types.add(KERUISetDetectorDisplayModelReqMessage.class);
		types.add(KERUISetDetectorDisplayModelRespMessage.class);
		types.add(KERUISetTimeReqMessage.class);
		types.add(KERUISetTimeRespMessage.class);
		types.add(KERUISetDetectorBoardParameterReqMessage.class);
		types.add(KERUISetDetectorBoardParameterRespMessage.class);
		types.add(KERUIBindingScreenAndDetectorReqMessage.class);
		types.add(KERUIBindingScreenAndDetectorRespMessage.class);
		types.add(KERUISetDetectorParameterReqMessage.class);
		types.add(KERUISetDetectorParameterRespMessage.class);
		types.add(KERUICpuIdReqMessage.class);
		types.add(KERUICpuIdRespMessage.class);

        TYPES = Collections.unmodifiableSet(types);
    }

	public void encode(IoSession session, Object message,
            ProtocolEncoderOutput out) throws Exception {
		// TODO Auto-generated method stub
		
		IoBuffer buf=IoBuffer.allocate(2048).setAutoExpand(true);
		KERUIAbstractMessage msg = (KERUIAbstractMessage)message;
		
		msg.encodeData(null);//不做编码工作，参数没用，函数名称改一下(Data赋值)
		msg.encodeMessage(buf);//整个消息体编码
		
		buf.flip();
		out.write(buf);
		log.debug("encode message buffer:{}", ByteUtil.toHexForLog(buf));
	}
}
