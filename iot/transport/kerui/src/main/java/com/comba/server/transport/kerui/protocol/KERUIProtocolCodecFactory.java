package com.comba.server.transport.kerui.protocol;

import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;

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


public class KERUIProtocolCodecFactory extends DemuxingProtocolCodecFactory {
	  public KERUIProtocolCodecFactory() {
		  	super.addMessageDecoder(KERUIMessageDecoder.class);
		  	
		  	super.addMessageEncoder(KERUIMasterNumberRespMessage.class,KERUIMessageEncoder.class);
			super.addMessageEncoder(KERUIMasterNumberReqMessage.class,KERUIMessageEncoder.class);
			super.addMessageEncoder(KERUIDestMasterDevicesRespMessage.class,KERUIMessageEncoder.class);
			super.addMessageEncoder(KERUIDestMasterDevicesReqMessage.class,KERUIMessageEncoder.class);
			super.addMessageEncoder(KERUIDestMasterDevicesTypeReqMessage.class,KERUIMessageEncoder.class);
			super.addMessageEncoder(KERUIDestMasterDevicesTypeRespMessage.class,KERUIMessageEncoder.class);
			super.addMessageEncoder(KERUIDestMasterDetectorsStateReqMessage.class,KERUIMessageEncoder.class);
			super.addMessageEncoder(KERUIDestMasterDetectorsStateRespMessage.class,KERUIMessageEncoder.class);
			super.addMessageEncoder(KERUIDetectorStateReqMessage.class,KERUIMessageEncoder.class);
			super.addMessageEncoder(KERUIDetectorStateRespMessage.class,KERUIMessageEncoder.class);
			super.addMessageEncoder(KERUISetDetectorDisplayModelReqMessage.class,KERUIMessageEncoder.class);
			super.addMessageEncoder(KERUISetDetectorDisplayModelRespMessage.class,KERUIMessageEncoder.class);
			super.addMessageEncoder(KERUISetTimeReqMessage.class,KERUIMessageEncoder.class);
			super.addMessageEncoder(KERUISetTimeRespMessage.class,KERUIMessageEncoder.class);
			super.addMessageEncoder(KERUISetDetectorBoardParameterReqMessage.class,KERUIMessageEncoder.class);
			super.addMessageEncoder(KERUISetDetectorBoardParameterRespMessage.class,KERUIMessageEncoder.class);
			super.addMessageEncoder(KERUIBindingScreenAndDetectorReqMessage.class,KERUIMessageEncoder.class);
			super.addMessageEncoder(KERUIBindingScreenAndDetectorRespMessage.class,KERUIMessageEncoder.class);
			super.addMessageEncoder(KERUISetDetectorParameterReqMessage.class,KERUIMessageEncoder.class);
			super.addMessageEncoder(KERUISetDetectorParameterRespMessage.class,KERUIMessageEncoder.class);
			super.addMessageEncoder(KERUIMessageEncoder.class,KERUIMessageEncoder.class);
			super.addMessageEncoder(KERUICpuIdReqMessage.class,KERUIMessageEncoder.class);
			super.addMessageEncoder(KERUICpuIdRespMessage.class,KERUIMessageEncoder.class);
	}
}
