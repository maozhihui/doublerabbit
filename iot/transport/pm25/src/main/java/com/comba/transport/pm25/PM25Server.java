package com.comba.transport.pm25;

import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

import org.apache.mina.filter.codec.ProtocolCodecFilter;

import com.comba.server.dao.device.DeviceService;
import com.comba.transport.pm25.protocol.PM25ProtocolCodecFactory;
import com.comba.transport.pm25.service.PM25DeviceApiService;

/**
 * PM25 TCP Server
 * 
 * @author huangjinlong
 * 
 * @data 2017-12-18
 * 
 */
@Slf4j
public class PM25Server extends Thread{
	
	private static SocketAcceptor acceptor = null;
	private PM25DeviceApiService deviceApiService = null;
	private DeviceService deviceService = null;
	private ExecutorService fixedThreadPool;
	String IP = "10.10.107.187";
	int PORT = 5002;
	long interval = 60000;
	long onlineTimeout = 0;
	
	public PM25Server(ConfigInfo config, PM25DeviceApiService apiService,DeviceService service) {
		deviceApiService = apiService;
		deviceService = service;
		IP = config.getIp();
		PORT = config.getPort();
		interval = config.getInterval();
		onlineTimeout = config.getOnlineTimeout();
		
		try {
			InetSocketAddress address = null;
			if (IP != null && !IP.isEmpty()) {
				address = new InetSocketAddress(IP, PORT);
			} else {
				address = new InetSocketAddress(PORT);
			}
			
			SocketAcceptor acceptor = new NioSocketAcceptor();// tcp/ip 接收者
			PM25ServerSessionHandler handler = new PM25ServerSessionHandler(deviceApiService,deviceService,onlineTimeout);
			DefaultIoFilterChainBuilder chain=acceptor.getFilterChain();// 过滤管道
			chain.addLast("ProtocolCodecFilter", new ProtocolCodecFilter(new PM25ProtocolCodecFactory()));
			acceptor.setReuseAddress(true);
			acceptor.getSessionConfig().setReuseAddress(true);
			acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, (int) interval);// 读写通道10s内无操作进入空闲状态
			acceptor.setHandler(handler);// 设置handler
			acceptor.bind(address);// 设置端口
			log.info(String.format("Server Listing on %s:%s", IP, PORT));
	   
			// 断开所有Session，清空缓存
			for (Map.Entry<IoSession, ConnectionSession> entry : PM25ServerSessionHandler.connSessionMap.entrySet()) {
				entry.getKey().close();
			}
			PM25ServerSessionHandler.connSessionMap.clear();
		}catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	@Override
	public synchronized void run() {
		//和地锁情况是一样的。
		//不用newScheduledThreadPool，是因为newScheduledThreadPool周期执行固定不变的任务，而session变化的。
		fixedThreadPool = Executors.newFixedThreadPool(1);
		
		while (true) {
			try {
				log.debug("port:{},有{}个session连接! ", PORT, PM25ServerSessionHandler.connSessionMap.size());
				for (Map.Entry<IoSession, ConnectionSession> entry : PM25ServerSessionHandler.connSessionMap.entrySet()) {
					IoSession session = entry.getKey();
					ConnectionSession conn = entry.getValue();
					log.debug("mac:"+conn.getMac());
					long intervalTime = interval/20;
					fixedThreadPool.execute(new CheckTask(session,conn,intervalTime));
				}
				//限制线程执行时间interval 为60s,若一个适配器接10个设备，则每个设备查询时间间隔是6s。
				//若1个适配器接设备数量大于30，则查询设备时间间隔小于2s时，可能会导致获取的状态不准。
				Thread.sleep(interval);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
	}
	
	public static boolean sendMessage(IoSession session,Object obj) {
		try {
//			if (!isOpened()) {
//				log.warn("Server is not listen!");
//				return false;
//			}
			if ( PM25ServerSessionHandler.connSessionMap.isEmpty()) {
				log.warn("No client login!");
				return false;
			}
			if (obj == null || obj.toString().length() == 0) {
				log.warn("No data to send!");
				return false;
			}
			
			WriteFuture future = session.write(obj);
			future.await(20, TimeUnit.SECONDS);
			
			if (future.isWritten()) {
				log.info("send message success!Len:{}",obj.toString().length());
			} else {
				log.info("send message fail!");
			}
		
			return true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return false;
	}
	
	public static boolean isOpened() {
		try {
			if (acceptor == null) {
				return false;
			}

			if (!acceptor.isActive()) {
				return false;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return true;
	}
	
	public static void main(String[] args) throws Exception {
		String ip = "10.10.107.187";
		int port = 5002;
		long interval = 60000;
		long onlineTimeout = 0;
		ConfigInfo config = new ConfigInfo(ip,port,interval,onlineTimeout);
		new PM25Server(config,null,null).start();
	}
}
