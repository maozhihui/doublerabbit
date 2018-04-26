package com.comba.server.transport.feibit.message;

import java.io.Serializable;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.apache.mina.core.buffer.IoBuffer;

import com.comba.server.transport.feibit.common.ByteUtil;
import com.comba.server.transport.feibit.common.Constant;

/*
 * 获取当前连接网关的设备 响应消息
 * 
 */
@Slf4j
@Data
public class FEIBITGetDevicesRespMessage extends FEIBITAbstractRespMessage{
	//短地址:2 byte
	public static final int SHORT_ADDR_LEN = 2;
	private short shortAddr;
	
	//Endpoint :1 byte ,1-240
	private byte endpoint;
	
	//Profile ID:2 byte
	private short profileId;
	
	//Device ID:2 byte
	private short deviceId;
	
	//开关状态 0-255
	private byte switchState;
	
	//设备名称长度 1-100
	private byte deviceNameLen;
	
	//设备名称
	private String deviceName;

	//在线状态,0 为不在线，其它值为在线
	private byte onlineState;
	
	//IEEE:8 byte
	public static final int IEEE_LEN = 8;
	private byte[] IEEE;
	
	//SN长度
	private byte snLen;
	
	//SN
	private String sn;
	
	//ZoneType:2 byte,ZoneType 和最近上报的值只有在 DeviceID 为 0x0402 的时候才有意义
	public static final int ZONE_TYPE_LEN = 2;
	private short zoneType;
	
	//保留:2 byte
	private short reserve;
	
	//最近上报的值:4 byte
	private int recentlyReportedValue;
	
	public FEIBITGetDevicesRespMessage() {
		deviceStateResp = Constant.GET_DEVICES_RESP;
	}
	
	@Override
	public void encodeBody(IoBuffer bt) {
		// TODO Auto-generated method stub
		log.error("not implement yet,can't run here!");
	}
	public void decodeBody(byte[] body) {
		
		IoBuffer buf=IoBuffer.allocate(body.length).setAutoExpand(true);
		buf.put(body);
		buf.flip();
		
		/*byte[] shortAddrBytes = new byte[2];
		shortAddrBytes[1] = buf.get();
		shortAddrBytes[0] = buf.get();
		shortAddr = ByteUtil.bytesToHexString(shortAddrBytes);*/
		shortAddr = ByteUtil.shortConvert_LH(buf.getShort());
		endpoint = buf.get();
		
/*		byte[] profileIdBytes = new byte[2];
		profileIdBytes[1] = buf.get();
		profileIdBytes[0] = buf.get();
		profileId = ByteUtil.bytesToHexString(profileIdBytes);*/
		profileId = ByteUtil.shortConvert_LH(buf.getShort());
		
/*		byte[] deviceIdBytes = new byte[2];
		deviceIdBytes[1] = buf.get();
		deviceIdBytes[0] = buf.get();
		deviceId = ByteUtil.bytesToHexString(deviceIdBytes);*/
		deviceId = ByteUtil.shortConvert_LH(buf.getShort());
		
		switchState = buf.get();
		deviceNameLen = buf.get();
		
		byte[] deviceNameBytes = new byte[deviceNameLen];
		buf.get(deviceNameBytes);
		deviceName = new String(deviceNameBytes);
		
		onlineState = buf.get();
		
		/*byte[] IEEEBytes = new byte[IEEE_LEN];
		buf.get(IEEEBytes);
		IEEE = ByteUtil.bytesToHexString(IEEEBytes);*/
		IEEE = new byte[IEEE_LEN];
		buf.get(IEEE);
		
		snLen = buf.get();
		byte[] snBytes = new byte[snLen];
		buf.get(snBytes);
		sn = new String(snBytes);
		
/*		byte[] zoneTypeBytes = new byte[ZONE_TYPE_LEN];
		zoneTypeBytes[1] = buf.get();
		zoneTypeBytes[0] = buf.get();
		zoneType = ByteUtil.bytesToHexString(zoneTypeBytes);*/
		zoneType = ByteUtil.shortConvert_LH(buf.getShort());
		
		//zoneType = buf.getShort();
		//reserve = buf.getShort();
		//recentlyReportedValue = buf.getInt();
	}
	
	public short getDeviceType(){
		
		short deviceType = Constant.UNKNOWN;
		if(profileId == Constant.HA_DEVICE){
			if(deviceId == Constant.SENSOR ){
				deviceType = Constant.SENSOR;
				if(zoneType == Constant.DOOR_LOCK_SENSOR){
					deviceType = Constant.DOOR_LOCK_SENSOR;
				}
				else if(zoneType == Constant.HUMAN_INFRARED_SENSOR){
					//deviceType.append("人体红外：");
					deviceType = Constant.HUMAN_INFRARED_SENSOR;
				}
				else if(zoneType == Constant.SMOKE_SENSOR){
					//deviceType.append("烟雾：");
					deviceType = Constant.SMOKE_SENSOR;
				}
				else if(zoneType == Constant.GAS_SENSOR){
					//deviceType.append("气体：");
					deviceType = Constant.GAS_SENSOR;
				}	
				else if(zoneType == Constant.CARBON_MONOXIDE_SENSOR){
					//deviceType.append("一氧化碳：");
					deviceType = Constant.CARBON_MONOXIDE_SENSOR;
				}
				else if(zoneType == Constant.SHOCK_SENSOR){
					//deviceType.append("震动：");
					deviceType = Constant.SHOCK_SENSOR;
				}
				else if(zoneType == Constant.WATER_IMMERSION_SENSOR){
					//deviceType.append("水浸：");
					deviceType = Constant.WATER_IMMERSION_SENSOR;
				}	
				else if(zoneType == Constant.SECURITY_REMOTE_CONTROL_SENSOR){
					//deviceType.append("安防遥控器：");
					deviceType = Constant.SECURITY_REMOTE_CONTROL_SENSOR;
				}	
				else if(zoneType == Constant.SOS_BUTTON_SENSOR){
					//deviceType.append("紧急按钮：");
					deviceType = Constant.SOS_BUTTON_SENSOR;
				}	
				else if(zoneType == Constant.ALARM_SENSOR){
					//deviceType.append("报警器：");
					deviceType = Constant.ALARM_SENSOR;
				}
			}
			else if(deviceId == Constant.THERMO_HYGROMETER){
				//deviceType.append("温湿度：");
				deviceType = Constant.THERMO_HYGROMETER;
			}
			else if(deviceId == Constant.CURTAIN){
				//deviceType.append("窗帘：");
				deviceType = Constant.CURTAIN;
			}
			else if(deviceId == Constant.IR_CONTROL){
				//deviceType.append("红外控制面板：");
				deviceType = Constant.IR_CONTROL;
			}
			else if(deviceId == Constant.ILLUMINATION){
				//deviceType.append("光照：");
				deviceType = Constant.ILLUMINATION;
			}
			else if(deviceId == Constant.POWER_SWITCH){
				//deviceType.append("电源开关：");
				deviceType = Constant.POWER_SWITCH;
			}
			else if(deviceId == Constant.SCENE_SWITCH){
				//deviceType.append("情景开关：");
				deviceType = Constant.SCENE_SWITCH;
			}
			else{
				//deviceType.append("未知：");
				deviceType = Constant.UNKNOWN;
			}
		}
		return deviceType;
	}
	public String getDeviceTypeName(){
		StringBuffer deviceTypeName = new StringBuffer("");
		short deviceType = getDeviceType();
		
		if(deviceType == Constant.SENSOR){
			deviceTypeName.append("传感器：");
		}
		else if(deviceType == Constant.DOOR_LOCK_SENSOR){
			deviceTypeName.append("门禁：");
		}
		else if(deviceType == Constant.HUMAN_INFRARED_SENSOR){
			deviceTypeName.append("人体红外：");
		}
		else if(deviceType == Constant.SMOKE_SENSOR){
			deviceTypeName.append("烟雾：");
		}
		else if(deviceType == Constant.GAS_SENSOR){
			deviceTypeName.append("气体：");
		}	
		else if(deviceType == Constant.CARBON_MONOXIDE_SENSOR){
			deviceTypeName.append("一氧化碳：");
		}
		else if(deviceType == Constant.SHOCK_SENSOR){
			deviceTypeName.append("震动：");
		}
		else if(deviceType == Constant.WATER_IMMERSION_SENSOR){
			deviceTypeName.append("水浸：");
		}	
		else if(deviceType == Constant.SECURITY_REMOTE_CONTROL_SENSOR){
			deviceTypeName.append("安防遥控器：");
		}	
		else if(deviceType == Constant.SOS_BUTTON_SENSOR){
			deviceTypeName.append("紧急按钮：");
		}	
		else if(deviceType == Constant.ALARM_SENSOR){
			deviceTypeName.append("报警器：");
		}
		else if(deviceType == Constant.THERMO_HYGROMETER){
			deviceTypeName.append("温湿度：");
		}
		else if(deviceType == Constant.CURTAIN){
			deviceTypeName.append("窗帘：");
		}
		else if(deviceType == Constant.IR_CONTROL){
			deviceTypeName.append("红外控制面板：");
		}
		else if(deviceType == Constant.ILLUMINATION){
			deviceTypeName.append("光照：");
		}
		else if(deviceType == Constant.POWER_SWITCH){
			deviceTypeName.append("电源开关：");
		}
		else if(deviceType == Constant.SCENE_SWITCH){
			deviceTypeName.append("情景开关：");
		}
		else{
			deviceTypeName.append("未知：");
		}
		
		return deviceTypeName.toString();
	}
	
	
	@Override
	public String toString() {
	
		return "FEIBITGetDevicesRespMessage " + getDeviceTypeName() 
				+ "[shortAddr=" + ByteUtil.shortToHexString(shortAddr) 
				+ ", endpoint=" + endpoint 
				+ ", profileId=" + ByteUtil.shortToHexString(profileId)
				+ ", deviceId="	+ ByteUtil.shortToHexString(deviceId)
				+ ", switchState=" + switchState 
				+ ", deviceNameLen=" + deviceNameLen 
				+ ", deviceName=" + deviceName
				+ ", onlineState=" + onlineState 
				+ ", IEEE=" + IEEE 
				+ ", snLen=" + snLen 
				+ ", sn=" + sn 
				+ ", zoneType=" + ByteUtil.shortToHexString(zoneType)
				+ ", reserve=" + reserve 
				+ "，recentlyReportedValue=" + recentlyReportedValue + "]";
	}
}
