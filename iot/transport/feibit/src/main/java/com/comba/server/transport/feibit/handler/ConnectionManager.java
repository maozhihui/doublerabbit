package com.comba.server.transport.feibit.handler;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.comba.server.transport.feibit.UdpClient;
import com.comba.server.transport.feibit.bean.ConfigInfo;
import com.comba.server.transport.feibit.bean.ConnectionSession;
import com.comba.server.transport.feibit.common.ByteUtil;
import com.comba.server.transport.feibit.common.Constant;
import com.comba.server.transport.feibit.message.FEIBITDeviceSwitchStateReqMessage;
import com.comba.server.transport.feibit.message.FEIBITGetDevicesMessage;
import com.comba.server.transport.feibit.message.FEIBITGetDevicesRespMessage;
import com.comba.server.transport.feibit.message.FEIBITInfraredDataReqMessage;
import com.comba.server.transport.feibit.message.FEIBITInfraredDataSendMessage;
import com.comba.server.transport.feibit.message.FEIBITSetDeviceSwitchStateMessage;
import com.comba.server.transport.feibit.message.FEIBITSetReportIntervalTimeMessage;
import com.comba.server.transport.feibit.service.FEIBITDeviceApiService;

@Slf4j
public class ConnectionManager extends Thread{
	
	IoConnector ioConnector = null;
	FEIBITClientSessionHandler handler = null;
	FEIBITDeviceApiService deviceApiService = null;
	
	//广播地址，端口，发送信息
	String broadcastAddress = "10.10.107.255";	
	int packetPort = 9090;
	String sendMsg = "GETIP\\r\\n";
	long interval = 60000;
	long onlineTimeout = 0;
	//网关IP，连接端口
//	String gatewayIp = "127.0.0.1";
	int tcpPort = 8001;
	
	public ConnectionManager(ConfigInfo config, FEIBITDeviceApiService service) {
		broadcastAddress = config.getIp();
		packetPort = config.getTcpport();
		interval = config.getInterval();
		onlineTimeout = config.getOnlineTimeout();
		deviceApiService = service;
		//搜索网关，一分钟搜索一次
		UdpClient.send(broadcastAddress,packetPort,sendMsg,interval);
		UdpClient.receive();
	}

	@Override
	public void run() {
		
		while (true) {
			try {
				for (Map.Entry<String, String> entry : UdpClient.gatewaysMap.entrySet()) {
					String gatewaySn = entry.getKey();
					String gatewayIp = entry.getValue();
					ConnectionSession connSession = getGatewayConnectionSession(gatewaySn);
					if(connSession == null){//连接网关，一分钟轮询一次
						boolean connected = connectFEIBIT(gatewayIp,tcpPort);
						//若连接失败，则从缓存中删除。
						if(!connected)
							UdpClient.gatewaysMap.remove(gatewaySn);
					}
/*					else {	//已连接，则查询设备列表
						ConnectionManager.getDeviceMessage(connSession.getSession(),gatewaySn);
						ConnectionManager.getInfraredDataReqMessage(connSession.getSession(),gatewaySn);
					}*/
				}
				//UdpClient.gatewaysMap.clear();
				Thread.sleep(interval);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		} 
	}

	private IoConnector initConnector() {
		if(ioConnector == null ){
			ioConnector = new NioSocketConnector();
		}
		if(handler == null){
			handler = new FEIBITClientSessionHandler(deviceApiService,onlineTimeout);
			ioConnector.setHandler(handler);
		}
		long idleTime = interval/1000;
		if(idleTime < 60){
			idleTime = 60;
		}
		ioConnector.setConnectTimeoutMillis(5000);
		ioConnector.getSessionConfig().setIdleTime(IdleStatus.WRITER_IDLE, (int) idleTime);//60s
		
		return ioConnector;
	}

	private IoSession createSession(String ip,int port) {
		IoSession session = null;
		try {
			IoConnector ioConnector = initConnector();
			SocketAddress sockaddr = new InetSocketAddress(ip, port);
			ConnectFuture connectFuture = null;
			connectFuture = ioConnector.connect(sockaddr);
			connectFuture.awaitUninterruptibly();

			if (connectFuture.isConnected()) {
				session = connectFuture.getSession();
			}
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		
		return session;
	}

	private boolean connectFEIBIT(String ip, int port) throws InterruptedException {
		
			IoSession session = null;
			try {
				log.info("Try to login {}:{} ...",ip,port);
				
				session = createSession(ip,port);
				Thread.sleep(1000);

				if (session == null || !session.isConnected()) {
					log.error(" connect remote server {}:{} fail,wait 10S for next trying...",ip,port);
					Thread.sleep(10000);
					return false;
				} 
				else{
					log.info("{}:{} login remote ok.",ip,port);
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				
				if (session != null && session.isConnected()) {//异常 断开连接
					session.close();
				}
				Thread.sleep(10000);
				return false;
			}
		return true;
	}
	
	//网关SNID是否有效
	public static boolean verifyGatewaySn(String sn) {
		boolean result = false;
		if(sn != null && sn.length() == 8){
			result = true;
		}
		return result;
	}
	
	public static ConnectionSession getGatewayConnectionSession(String gatewaySn){
		
		for(Map.Entry<IoSession, ConnectionSession> entry : FEIBITClientSessionHandler.connSessionMap.entrySet()){
			ConnectionSession connSession = entry.getValue();
			if(connSession.getGatewaySn().equals(gatewaySn)){//若网关已连接，则跳过。否则连接网关
				return connSession;
			}
		}
		return null;
	}
	
	public static String findGatewaySnByIp(String ip){
		String sn = null;
		
		for(Map.Entry<String, String> conn : UdpClient.gatewaysMap.entrySet()){
			if(conn.getValue().equals(ip)){
				sn = conn.getKey();
				break;
			}
		}
		return sn;
	}
	
	//批量设置窗帘开关状态
	public static void setCurtainsState(String value){
		if(value == null)return;
		byte state = (byte)Integer.parseInt(value);
		for(Map.Entry<IoSession, ConnectionSession> entry : FEIBITClientSessionHandler.connSessionMap.entrySet()){
			ConnectionSession connSession = entry.getValue();
			//会议室网关
			if(connSession.getGatewaySn().equals("112e9192")){
				for(Map.Entry<String,FEIBITGetDevicesRespMessage> entry2:connSession.getDevicesMap().entrySet()){
					//会议室窗帘
					FEIBITGetDevicesRespMessage device = entry2.getValue();
					if(device.getDeviceType() == Constant.CURTAIN){
						setDeviceSwitchStateMessage(connSession.getSession(),connSession.getGatewaySn(),device,state);
						
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				//查询到会议室网关后，不用继续查询。
				break;
			}
		}
	}

	public static void setDeviceMessage(IoSession session, String gatewaySnId, FEIBITGetDevicesRespMessage deviceMsg, String attribute,String value){
		//根据属性值设置对应的设备，如窗帘开关，属性名称是switchState,则调用setDeviceSwitchStateMessage
		if(attribute.equals("switch")){
			byte state = (byte)Integer.parseInt(value);
			setDeviceSwitchStateMessage(session,gatewaySnId,deviceMsg,state);
		}
		else if(attribute.equals("irId")){//红外转发面板
			byte state = (byte)Integer.parseInt(value);
			sendInfraredDataMessage(session,gatewaySnId,deviceMsg,state);
		}
		else if(attribute.equals("interval")){
			short reportInterval = (short) Integer.parseInt(value);
			setReportIntervalTimeMessage(session,gatewaySnId,deviceMsg,reportInterval);
		}
		else{
			log.error("The {} device not suport setting {}!",deviceMsg.getSn(),attribute);
		}
	}
	
	//设置设备开关状态
	public static void setDeviceSwitchStateMessage(IoSession session, String gatewaySnId, FEIBITGetDevicesRespMessage deviceMsg, byte state) {
		if(deviceMsg.getDeviceType() == Constant.CURTAIN
				||deviceMsg.getDeviceType() == Constant.POWER_SWITCH
				||deviceMsg.getDeviceType() == Constant.SCENE_SWITCH){
			FEIBITSetDeviceSwitchStateMessage msg = new FEIBITSetDeviceSwitchStateMessage();
			msg.setGatewaySnId(gatewaySnId);
			msg.setShortAddr(deviceMsg.getShortAddr());
			msg.setEndpoint(deviceMsg.getEndpoint());
			msg.setSwitchState(state);
			session.write(msg);
			log.info("Send device switch message:{}", msg);
		}
		else {
			log.error("The {} device not suport setting switch state!",deviceMsg.getSn());
		}
	}
	
	//获取设备开关状态
	public static void getDeviceSwitchStateMessage(IoSession session, String gatewaySnId, FEIBITGetDevicesRespMessage deviceMsg) {
		FEIBITDeviceSwitchStateReqMessage msg = new FEIBITDeviceSwitchStateReqMessage();
		msg.setGatewaySnId(gatewaySnId);
		msg.setShortAddr(deviceMsg.getShortAddr());
		msg.setEndpoint(deviceMsg.getEndpoint());
		session.write(msg);
	}
	
	//设置上报时间间隔
	public static void setReportIntervalTimeMessage(IoSession session, String gatewaySnId, FEIBITGetDevicesRespMessage deviceMsg, short interval){

		short clusterId = 0;
		short attributeId = 0;
		//不同设备，对应的clusterId,attribueId是不一样的,目前针对电压进行设置
		//人体红外，门磁，水浸，温湿度，烟感
		switch(deviceMsg.getDeviceType()){
			case Constant.DOOR_LOCK_SENSOR:
				clusterId = 0;
				attributeId = 0;
				break;
			case Constant.HUMAN_INFRARED_SENSOR:
				clusterId = 0;
				attributeId = 0;
				break;
			case Constant.THERMO_HYGROMETER:
				clusterId = 0;
				attributeId = 0;
				break;	
			case Constant.WATER_IMMERSION_SENSOR:
				clusterId = 0;
				attributeId = 0;
				break;
			case Constant.SMOKE_SENSOR:
				clusterId = 0;
				attributeId = 0;
				break;
			default:
				log.error("The {} device not suport report interval time!",deviceMsg.getSn());
				return;
		}
		
		FEIBITSetReportIntervalTimeMessage msg = new FEIBITSetReportIntervalTimeMessage();
		msg.setGatewaySnId(gatewaySnId);
		msg.setShortAddr(deviceMsg.getShortAddr());
		msg.setEndpoint(deviceMsg.getEndpoint());
		msg.setClusterId(clusterId);
		msg.setAttributeId(attributeId);
		msg.setDataType(Constant.UINT_16);
		msg.setReportInterval(interval);
		session.write(msg);
		log.info("Send report interval time message:{}", msg);
	}
	
	//获取当前网关连接设备
	public static void getDeviceMessage(IoSession session, String gatewaySn){
		//清空缓存
		ConnectionSession connSession = FEIBITClientSessionHandler.connSessionMap.get(session);
		if(connSession == null) {
			connSession = new ConnectionSession(session);
		}
		
		connSession.getDevicesMap().clear();
		connSession.getInfraredMap().clear();
		
		FEIBITGetDevicesMessage msg = new FEIBITGetDevicesMessage();
		msg.setGatewaySnId(gatewaySn);
		session.write(msg);
	}

	//查询网关的红外数据
	public static void getInfraredDataReqMessage(IoSession session, String gatewaySnId){
		FEIBITInfraredDataReqMessage msg = new FEIBITInfraredDataReqMessage();
		msg.setGatewaySnId(gatewaySnId);
		session.write(msg);
	}
	
	//发送网关的红外数据
	public static void sendInfraredDataMessage(IoSession session, String gatewaySnId, FEIBITGetDevicesRespMessage deviceMsg, byte irId){
		//若是红外转发面板
		if(deviceMsg.getDeviceType() == Constant.IR_CONTROL){
			FEIBITInfraredDataSendMessage msg = new FEIBITInfraredDataSendMessage();
			msg.setGatewaySnId(gatewaySnId);
			msg.setShortAddr(deviceMsg.getShortAddr());
			msg.setEndpoint(deviceMsg.getEndpoint());
			msg.setIrId(irId);
			session.write(msg);
			log.info("Send infrared message:{}", msg);
		}
	}
}
