package com.comba.transport.pshare;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.comba.server.dao.device.DeviceService;
import com.comba.transport.pshare.service.PSHAREDeviceApiService;

/**
 * 
 *
 */
@Slf4j
@Service
public class PSHAREApp 
{
	@Autowired
	private PSHAREDeviceApiService deviceApiService;
	@Autowired
    private DeviceService deviceService;
	
	@Value("${pshare.enabled}")
	private boolean enabled;
    @Value("${pshare.tcpserver.ip}")
    private String ip;
    @Value("${pshare.tcpserver.port}")
    private int port;
    @Value("${pshare.interval}")
    private int interval; 
	@Value("${pshare.online.timeout}")
	private int onlineTimeout;
	@PostConstruct
    public void init()
    {
        log.info( "PSHAREApp init !" );
    	if (enabled){
    		ConfigInfo config = new ConfigInfo(ip,port,interval,onlineTimeout);
    		new PSHAREServer(config,deviceApiService,deviceService).start();
    	}
    }
}
