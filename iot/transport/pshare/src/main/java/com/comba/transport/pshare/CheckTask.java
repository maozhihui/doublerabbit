package com.comba.transport.pshare;

import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.session.IoSession;


/**
 * 检测任务，负责查询当前地锁状态
 * @author huangjinlong
 * @Description:
 * @create 2018/3/19
 **/
@Slf4j
public class CheckTask implements Runnable {

	private IoSession session;
	private ConnectionSession conn;
	private long interval;
    public CheckTask(IoSession session,ConnectionSession conn,long interval){
    	this.session = session;
    	this.conn = conn;
    	this.interval = interval;
    }

    @Override
    public void run() {
    	try {
	    	for(int i = 0; i < conn.getAddressList().size(); i++){
	    		int addr = conn.getAddressList().get(i);
	    		log.info("硬件标识："+conn.getMac()+"-"+addr);
	    		PSHAREServerSessionHandler.getLockStateReqMessage(session,(byte) addr);
	    		Thread.sleep(interval);
	    	}
    	} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
    }
}
