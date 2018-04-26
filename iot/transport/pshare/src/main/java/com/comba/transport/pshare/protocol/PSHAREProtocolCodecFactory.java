package com.comba.transport.pshare.protocol;

import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;

import com.comba.transport.pshare.message.PSHAREGetLockStateReqMessage;
import com.comba.transport.pshare.message.PSHARELockDownReqMessage;
import com.comba.transport.pshare.message.PSHARELockUpReqMessage;


public class PSHAREProtocolCodecFactory extends DemuxingProtocolCodecFactory {
	  public PSHAREProtocolCodecFactory() {
		  	super.addMessageDecoder(PSHAREMessageDecoder.class);
		  	
	        super.addMessageEncoder(PSHAREGetLockStateReqMessage.class,PSHAREMessageEncoder.class);
	        super.addMessageEncoder(PSHARELockUpReqMessage.class,PSHAREMessageEncoder.class);
	        super.addMessageEncoder(PSHARELockDownReqMessage.class,PSHAREMessageEncoder.class);
	}
}
