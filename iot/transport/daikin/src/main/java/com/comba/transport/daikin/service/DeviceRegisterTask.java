package com.comba.transport.daikin.service;

import com.serotonin.modbus4j.ModbusMaster;

import lombok.extern.slf4j.Slf4j;

/**
 * 定期注册设备
 * @author huangjinlong
 * @Description:
 * @create 2018/3/21
 **/
@Slf4j
public class DeviceRegisterTask implements Runnable {
	private ModbusMaster master;
	private String[] hardIdentitys;
	private long onlineTimeout;
    public DeviceRegisterTask(ModbusMaster master,String[] hardIdentitys,long onlineTimeout){
    	this.master = master;
    	this.hardIdentitys = hardIdentitys;
    	this.onlineTimeout = onlineTimeout;
    }

    @Override
    public void run() {
    	if(hardIdentitys != null && master.isConnected()){
			for(int i = 0; i < hardIdentitys.length; i++){	
				ModbusMasterService.onDeviceRegister(hardIdentitys[i],onlineTimeout);
				log.info("硬件标识： [{}] ,modbus model device register.",hardIdentitys[i]);
			}
		}
    }
}
