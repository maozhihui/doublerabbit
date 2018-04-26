package com.comba.server.transport.kerui;

import java.net.InetSocketAddress;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import com.comba.server.transport.kerui.protocol.KERUIProtocolCodecFactory;
import com.comba.server.transport.kerui.service.KERUIDeviceApiService;

/**
 * 科瑞 TCP Server端
 * 
 * @author huangjinlong
 * 
 * @data 2017-12-07
 * 
 */
@Slf4j
public class KERUIServer extends Thread{
	
	private static SocketAcceptor acceptor = null;
	private KERUIDeviceApiService deviceApiService = null;
	private ExecutorService fixedThreadPool;
	String IP = "10.10.107.187";
	int PORT = 8234;
	int IDLE_TIME = 30;
	long interval = 60000;
	long onlineTimeout = 0;
	public KERUIServer(ConfigInfo config, KERUIDeviceApiService service) {
		deviceApiService = service;
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
			KERUIServerSessionHandler handler = new KERUIServerSessionHandler(deviceApiService,onlineTimeout);
			DefaultIoFilterChainBuilder chain=acceptor.getFilterChain();// 过滤管道
			chain.addLast("ProtocolCodecFilter", new ProtocolCodecFilter(new KERUIProtocolCodecFactory()));
			acceptor.setReuseAddress(true);
			acceptor.getSessionConfig().setReuseAddress(true);
			acceptor.getSessionConfig().setIdleTime(IdleStatus.WRITER_IDLE, IDLE_TIME);// 读写通道10s内无操作进入空闲状态
			acceptor.setHandler(handler);// 设置handler
			acceptor.bind(address);// 设置端口
			log.info("Server Listing on {}:{}", IP, PORT);
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
				IoSession session = KERUIServerSessionHandler.ioSession;
				long intervalTime = interval/20;
				fixedThreadPool.execute(new CheckTask(session,intervalTime));
				Thread.sleep(interval);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
	}
	
	public static boolean sendMessage(IoSession session,Object obj) {
		try {
			if ( KERUIServerSessionHandler.ioSession == null) {
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
				log.info("send message success!Len:" + obj.toString().length());
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
		String ip = "10.10.107.184";
		int port = 8234;
		long interval = 60000;
		long onlineTimeout = 0;
		ConfigInfo config = new ConfigInfo(ip,port,interval,onlineTimeout);
		new KERUIServer(config,null).start();
	}
}
