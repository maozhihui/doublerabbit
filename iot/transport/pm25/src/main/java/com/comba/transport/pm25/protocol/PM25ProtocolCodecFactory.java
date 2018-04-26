package com.comba.transport.pm25.protocol;

import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;
import com.comba.transport.pm25.message.PM25ChangeDeviceAddrReqMessage;
import com.comba.transport.pm25.message.PM25QueryDeviceAddrReqMessage;
import com.comba.transport.pm25.message.PM25ReadDataReqMessage;

public class PM25ProtocolCodecFactory extends DemuxingProtocolCodecFactory {
	  public PM25ProtocolCodecFactory() {
		  	super.addMessageDecoder(PM25MessageDecoder.class);
	        super.addMessageEncoder(PM25ReadDataReqMessage.class,PM25MessageEncoder.class);
	        super.addMessageEncoder(PM25QueryDeviceAddrReqMessage.class,PM25MessageEncoder.class);
	        super.addMessageEncoder(PM25ChangeDeviceAddrReqMessage.class,PM25MessageEncoder.class);
	}
}
