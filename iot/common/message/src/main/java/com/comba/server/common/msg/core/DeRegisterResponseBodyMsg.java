package com.comba.server.common.msg.core;

public class DeRegisterResponseBodyMsg extends ResponseBodyMsg {

	private static final long serialVersionUID = 1238189950677181519L;

	public DeRegisterResponseBodyMsg(int errno, String error) {
		super(errno, error);
	}

}
