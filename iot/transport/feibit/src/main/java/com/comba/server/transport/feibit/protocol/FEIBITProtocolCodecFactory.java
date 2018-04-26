package com.comba.server.transport.feibit.protocol;

import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;

import com.comba.server.transport.feibit.message.FEIBITDeviceSwitchStateReqMessage;
import com.comba.server.transport.feibit.message.FEIBITGetDevicesMessage;
import com.comba.server.transport.feibit.message.FEIBITGetGatewayInfoMessage;
import com.comba.server.transport.feibit.message.FEIBITInfraredDataReqMessage;
import com.comba.server.transport.feibit.message.FEIBITInfraredDataSendMessage;
import com.comba.server.transport.feibit.message.FEIBITSetDeviceSwitchStateMessage;

public class FEIBITProtocolCodecFactory extends DemuxingProtocolCodecFactory {
	public FEIBITProtocolCodecFactory() {
		super.addMessageDecoder(FEIBITMessageDecoder.class);

		super.addMessageEncoder(FEIBITGetDevicesMessage.class, FEIBITMessageEncoder.class);
		super.addMessageEncoder(FEIBITGetGatewayInfoMessage.class, FEIBITMessageEncoder.class);
		super.addMessageEncoder(FEIBITSetDeviceSwitchStateMessage.class, FEIBITMessageEncoder.class);
		super.addMessageEncoder(FEIBITDeviceSwitchStateReqMessage.class, FEIBITMessageEncoder.class);
		super.addMessageEncoder(FEIBITInfraredDataReqMessage.class, FEIBITMessageEncoder.class);
		super.addMessageEncoder(FEIBITInfraredDataSendMessage.class, FEIBITMessageEncoder.class);
	}
}
