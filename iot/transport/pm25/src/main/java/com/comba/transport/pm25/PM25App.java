package com.comba.transport.pm25;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.comba.server.dao.device.DeviceService;
import com.comba.transport.pm25.service.PM25DeviceApiService;

/**
 * Hello world!
 *
 */
@Slf4j
@Service
public class PM25App 
{
	@Autowired
	private PM25DeviceApiService deviceApiService;
	@Autowired
    private DeviceService deviceService;
	@Value("${pm25.enabled}")
	private boolean enabled;
    @Value("${pm25.tcpserver.ip}")
    private String ip;
    @Value("${pm25.tcpserver.port}")
    private int port;
    @Value("${pm25.interval}")
    private int interval; 
	@Value("${pm25.online.timeout}")
	private int onlineTimeout;
	@PostConstruct
    public void init()
    {
        log.info( "PM25App init !" );
    	if (enabled){
    		ConfigInfo config = new ConfigInfo(ip,port,interval,onlineTimeout);
    		new PM25Server(config,deviceApiService,deviceService).start();
    	}	
    }
}
