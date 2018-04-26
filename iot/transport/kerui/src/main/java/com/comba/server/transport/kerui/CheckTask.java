package com.comba.server.transport.kerui;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.session.IoSession;
import com.comba.server.transport.kerui.bean.ControllerInfo;

/**
 * 检测任务，负责查询当前超声波状态
 * @author huangjinlong
 * @Description:
 * @create 2018/3/19
 **/
@Slf4j
public class CheckTask implements Runnable {
	private IoSession session;
	private long interval;
    public CheckTask(IoSession session,long interval){
    	this.session = session;
    	this.interval = interval;
    }

    @Override
    public void run() {
	    try {
	    	for(Map.Entry<Integer, ControllerInfo> entry:KERUIServerSessionHandler.mastersMap.entrySet()){
				int node = entry.getKey();
				//主控制器是不连接设备的，不需要查询
				if(node != 0x01){	
					//查询哪个地址是有设备的
					KERUIServerSessionHandler.getDestMasterDevicesReqMessage(session,(byte) node);
					Thread.sleep(interval);
					//查询设备状态，有车或无车
					KERUIServerSessionHandler.getDestMasterDetectorsStateReqMessage(session, (byte) node);
					Thread.sleep(interval);
					//KERUIServerSessionHandler.getDestMasterDevicesTypeReqMessage(session, node);
					//Thread.sleep(interval);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
    }
}
