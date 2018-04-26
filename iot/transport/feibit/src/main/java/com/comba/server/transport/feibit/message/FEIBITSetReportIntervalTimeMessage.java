package com.comba.server.transport.feibit.message;

import java.util.Map;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.apache.mina.core.buffer.IoBuffer;

import com.comba.server.transport.feibit.FEIBITClient;
import com.comba.server.transport.feibit.bean.FEIBITDeviceAttribute;
import com.comba.server.transport.feibit.common.ByteUtil;
import com.comba.server.transport.feibit.common.Constant;
import com.comba.server.transport.feibit.handler.FEIBITClientSessionHandler;

/*
 * 设置报告间隔时间
 *
 *
 * S:
 * R:
 */
@Slf4j
@Data
public class FEIBITSetReportIntervalTimeMessage extends FEIBITAbstractMessage {
	//参数长度
	private byte parameterLen;
	//地址模式 0x02
	private byte addrMode;
	//短地址:2 byte
	private final int shortAddrLen = 2;
	private short shortAddr;
	//保留
	private final int reserve1Len = 6;
	private byte[] reserve1;
	
	private byte endpoint;
	//保留
	private final int reserve2Len = 2;
	private byte[] reserve2;
	private short clusterId;
	private short attributeId;
	private byte dataType;
	//上报间隔(ms)
	private short reportInterval;
	
	public FEIBITSetReportIntervalTimeMessage() {
		command = Constant.SET_REPORT_INTERVAL_TIME;
		addrMode = 0x02;
		reserve1 = new byte[reserve1Len];
		reserve2 = new byte[reserve2Len];
	}

	@Override
	public void encodeBody(IoBuffer buf) {
		// TODO Auto-generated method stub
		parameterLen =  1 + shortAddrLen + reserve1Len + 1 + reserve2Len + 1;
		
		buf.put(parameterLen);
		buf.put(addrMode);
		buf.putShort(ByteUtil.shortConvert_LH(shortAddr));
		buf.put(reserve1);
		buf.put(endpoint);
		buf.put(reserve2);
		buf.putShort(ByteUtil.shortConvert_LH(clusterId));
		buf.putShort(ByteUtil.shortConvert_LH(attributeId));
		buf.put(dataType);
		buf.putShort(ByteUtil.shortConvert_LH(reportInterval));
	}

	@Override
	public void decodeBody(byte[] body) {
		// TODO Auto-generated method stub
		log.error("not implement yet,can't run here!");
	}
	
	@Override
	public String toString() {

		return "FEIBITSetReportIntervalTimeMessage " 
				+ "["
				+ "parameterLen=" + parameterLen
				+ ",addrMode=" + addrMode
				+ ",shortAddr=" + ByteUtil.shortToHexString(shortAddr) 
				+ ",reserve1=" + ByteUtil.bytesToHexString(reserve1)
				+ ",endpoint=" + ByteUtil.byteToHexString(endpoint) 
				+ ",reserve2=" + ByteUtil.bytesToHexString(reserve2)
				+ ",clusterId=" + ByteUtil.shortToHexString(clusterId)
				+ ",attributeId=" + ByteUtil.shortToHexString(attributeId)
				+ ",dataType=" + ByteUtil.byteToHexString(dataType) 
				+ ",reportInterval=" + reportInterval+","+ByteUtil.shortToHexString(reportInterval)
			    + "]";
	}
}
