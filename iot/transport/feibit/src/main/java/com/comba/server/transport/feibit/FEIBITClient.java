package com.comba.server.transport.feibit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.comba.server.transport.feibit.bean.ConfigInfo;
import com.comba.server.transport.feibit.handler.ConnectionManager;
import com.comba.server.transport.feibit.service.FEIBITDeviceApiService;

@Slf4j
@Service
public class FEIBITClient {

	@Autowired
	private FEIBITDeviceApiService deviceApiService;

	@Value("${feibit.enabled}")
	private boolean enabled;
	@Value("${feibit.udpbroadcast.ip}")
	private String ip;
	@Value("${feibit.tcpclient.port}")
	private int tcpport;
	@Value("${feibit.interval}")
	private int interval;
	@Value("${feibit.online.timeout}")
	private int onlineTimeout;
	@PostConstruct
    public void init(){
		if (enabled){
			ConfigInfo config = new ConfigInfo(ip,tcpport,interval,onlineTimeout);
			new ConnectionManager(config,deviceApiService).start();
		}
		else
			log.info("feibit module is not load.");
	}
	
	@PreDestroy
	public void disconnect() {
		  
	}
	
	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO Auto-generated method stub	
		String ip = "10.10.107.255";	
		//String ip = "192.168.188.255";	
		int port = 9090;
		int interval = 60000;
		int timeout = 60000;
		ConfigInfo config = new ConfigInfo(ip,port,interval,timeout);
		new ConnectionManager(config,null).start();

	/*	//批量操作
		while(true){
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
            String outMessage = stdin.readLine();
			ConnectionManager.setCurtainsState(outMessage);
			Thread.sleep(5000);
		}*/
	}
}
