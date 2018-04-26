package com.comba.server.transport.feibit.message;

import java.util.Map;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.apache.mina.core.buffer.IoBuffer;

import com.comba.server.transport.feibit.common.ByteUtil;
import com.comba.server.transport.feibit.common.Constant;

/*
 * 获取网关信息 响应消息
 * 
 */
@Slf4j
@Data
public class FEIBITGetGatewayInfoRespMessage extends FEIBITAbstractRespMessage {
	
	//Ver(ASC) 网关版本号
	private final int VER_LEN = 5;
	private String version;
	
	//网关SNID
	private final int SNID_LEN = 4;
	private String SNID;
	
	//User Name
	private final int USER_NAME_LEN = 20;
	private String userName;
	
	//Password
	private final int PASSWORD_LEN = 20;
	private String password;
	
	//设备总数:1byte
	private byte devicesTotal;
	
	//组总数:1byte
	private byte groupTotal;
	
	//定时器总数:1byte
	private byte timerTotal;
	
	//场景总数:1byte
	private byte sceneTotal;
	
	//任务总数:1byte
	private byte taskTotal;
	
	//保留：5byte
	private final int RESERVE_LEN = 5;
	private String reserve;
	
	public FEIBITGetGatewayInfoRespMessage(){
		deviceStateResp = Constant.GET_GATEWAY_INFO_RESP;
	}

	@Override
	public void encodeBody(IoBuffer bt) {
		// TODO Auto-generated method stub
		log.error("not implement yet,can't run here!");
	}

	@Override
	public void decodeBody(byte[] body) {
		// TODO Auto-generated method stub
		IoBuffer buf=IoBuffer.allocate(body.length).setAutoExpand(true);
		buf.put(body);
		buf.flip();
		
		byte[] versionBytes = new byte[VER_LEN];
		buf.get(versionBytes);
		version = new String(versionBytes);
		
		byte[] snidBytes = new byte[SNID_LEN];
		for(int i = SNID_LEN-1; i >= 0; i--){
			snidBytes[i] = buf.get();
		}
		SNID = ByteUtil.bytesToHexString(snidBytes);
		
		byte[] userNameBytes = new byte[USER_NAME_LEN];
		buf.get(userNameBytes);
		userName = new String(userNameBytes);
		
		byte[] passwordBytes = new byte[PASSWORD_LEN];
		buf.get(passwordBytes);
		password = new String(passwordBytes);
		
		devicesTotal = buf.get();
		groupTotal = buf.get();
		timerTotal = buf.get();
		sceneTotal = buf.get();
		taskTotal = buf.get();
		
		byte[] reserveBytes = new byte[RESERVE_LEN];
		buf.get(reserveBytes);
		reserve = new String(reserveBytes);
	}
	
	@Override
	public String toString() {
		
		return "FEIBITNodeActiveReportingMessage [version=" + version + ", SNID=" + SNID + ", userName=" + userName
				+ ", password=" + password + ", devicesTotal=" + devicesTotal + ", groupTotal=" + groupTotal 
				+ ", timerTotal="+ timerTotal + ", sceneTotal=" + sceneTotal + ", taskTotal=" + taskTotal 
				+ ", reserve=" + reserve + "]";
	}
}
