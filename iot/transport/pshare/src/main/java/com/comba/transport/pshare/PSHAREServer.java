package com.comba.transport.pshare;

import java.net.InetSocketAddress;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import com.comba.server.dao.device.DeviceService;
import com.comba.transport.pshare.protocol.PSHAREProtocolCodecFactory;
import com.comba.transport.pshare.service.PSHAREDeviceApiService;

/**
 * PSHARE TCP Server
 * 
 * @author huangjinlong
 * 
 * @data 2017-12-18
 * 
 */
@Slf4j
public class PSHAREServer extends Thread{
	private PSHAREDeviceApiService deviceApiService = null;
	private DeviceService deviceService = null;
	private ExecutorService fixedThreadPool;
	String IP = "10.10.107.187";
	int PORT = 5002;
	long interval = 60000;
	long onlineTimeout = 0;
	
	public PSHAREServer(ConfigInfo config, PSHAREDeviceApiService apiService,DeviceService service) {
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
			PSHAREServerSessionHandler handler = new PSHAREServerSessionHandler(deviceApiService,deviceService,onlineTimeout);
			DefaultIoFilterChainBuilder chain=acceptor.getFilterChain();// 过滤管道
			chain.addLast("ProtocolCodecFilter", new ProtocolCodecFilter(new PSHAREProtocolCodecFactory()));
			acceptor.setReuseAddress(true);
			acceptor.getSessionConfig().setReuseAddress(true);
			acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, (int) interval);// 读写通道10s内无操作进入空闲状态
			acceptor.setHandler(handler);// 设置handler
			acceptor.bind(address);// 设置端口
			log.info("Server Listing on {}:{}", IP, PORT);
	   
			// 断开所有Session，清空缓存
			for (Map.Entry<IoSession, ConnectionSession> entry : PSHAREServerSessionHandler.connSessionMap.entrySet()) {
				entry.getKey().close();
			}
			PSHAREServerSessionHandler.connSessionMap.clear();
		}catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	@Override
	public synchronized void run() {
		//不用newScheduledThreadPool，是因为newScheduledThreadPool周期执行固定不变的任务，而session变化的。
		fixedThreadPool = Executors.newFixedThreadPool(5);

		while (true) {
			try {
				log.debug("port:{},有{}个session连接! ", PORT, PSHAREServerSessionHandler.connSessionMap.size());
				for (Map.Entry<IoSession, ConnectionSession> entry : PSHAREServerSessionHandler.connSessionMap.entrySet()) {
					IoSession session = entry.getKey();
					ConnectionSession conn = entry.getValue();
					log.debug("mac:"+conn.getMac());
					long intervalTime = interval/20;
					fixedThreadPool.execute(new CheckTask(session,conn,intervalTime));
				}
				//限制线程执行时间interval 为60s,若一个适配器接10个地锁，则每个地锁查询时间间隔是6s。
				//若1个适配器接地锁数量大于30，则查询地锁时间间隔小于2s时，可能会导致获取的状态不准。
				Thread.sleep(interval);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		String ip = "10.10.107.187";
		int port = 16809;
		long interval = 60000;
		long onlineTimeout = 0;
		ConfigInfo config = new ConfigInfo(ip,port,interval,onlineTimeout);
		new PSHAREServer(config,null,null).start();
	}
}
