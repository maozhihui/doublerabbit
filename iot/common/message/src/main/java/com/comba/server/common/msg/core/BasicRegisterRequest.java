package com.comba.server.common.msg.core;

import java.util.ArrayList;
import java.util.List;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import com.comba.server.common.data.kv.KvEntry;
import com.comba.server.common.msg.session.MsgType;

public class BasicRegisterRequest extends BasicRequest implements RegisterRequest{

	private String deviceId;
	private final List<KvEntry> data;
	public BasicRegisterRequest(String deviceId) {
        this(DEFAULT_REQUEST_ID,deviceId);
    }

    public BasicRegisterRequest(Integer requestId,String deviceId) {
        super(requestId);
        this.deviceId = deviceId;
        this.data = new ArrayList<>(); 
    }
	@Override
	public MsgType getMsgType() {
		// TODO Auto-generated method stub
		return MsgType.REGISTER;
	}

	@Override
	public String getDeviceId() {
		// TODO Auto-generated method stub
		return deviceId;
	}
	@Override
    public String toString() {
        return "BasicRegisterRequest [deviceId=" + deviceId + "]";
    }

	@Override
	public List<KvEntry> getData() {		
		return data;
	}
	
	@Override
	public long getOnlineTimeout(){
		long timeout = 0;
		for (KvEntry entry : data) {
			if(entry.getKey().equals("onlineTimeout")){
				timeout = (long) entry.getValue();
				return timeout;
			}
		}
		return timeout;
	}

	public void addEntry(KvEntry entry){
		data.add(entry);
	}
}
