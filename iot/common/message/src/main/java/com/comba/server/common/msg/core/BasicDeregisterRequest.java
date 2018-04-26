package com.comba.server.common.msg.core;

import java.util.ArrayList;
import java.util.List;

import com.comba.server.common.data.kv.KvEntry;
import com.comba.server.common.msg.session.MsgType;

public class BasicDeregisterRequest extends BasicRequest implements DeregisterRequest {

	private static final long serialVersionUID = 3658321547621881989L;
	
	private final String registerToken;
	private final String deviceId;
	private String gateWayId;
	private final List<KvEntry> data;
	private List<String> idLists;
	public BasicDeregisterRequest(String token,String deviceId) {
        this(DEFAULT_REQUEST_ID,token,deviceId);
    }

    public BasicDeregisterRequest(Integer requestId,String token,String deviceId) {
        super(requestId);
        this.registerToken = token;
        this.deviceId = deviceId;
        data = new ArrayList<KvEntry>();
        idLists = new ArrayList<>();
    }
	@Override
	public MsgType getMsgType() {
		// TODO Auto-generated method stub
		return MsgType.DEREGISTER;
	}

	@Override
	public String getRegisterToken() {
		// TODO Auto-generated method stub
		return registerToken;
	}
	@Override
    public String toString() {
		return String.format("BasicRegisterRequest [registerToken={} deviceId={} jsondata={}", registerToken,deviceId,data);
    }

	@Override
	public String getDeviceId() {
		// TODO 这是系统自动生成描述，请在此补完后续代码
		return deviceId;
	}

	@Override
	public List<KvEntry> getData() {
		// TODO 这是系统自动生成描述，请在此补完后续代码
		return data;
	}
	public void addEntry(KvEntry entry) {
		data.add(entry);
	}

	public String getGateWayId() {
		return gateWayId;
	}

	public void setGateWayId(String gateWayId) {
		this.gateWayId = gateWayId;
	}

	public void addDeviceId(String deviceId){
		if (!deviceId.isEmpty())
			idLists.add(deviceId);
	}
	
}
