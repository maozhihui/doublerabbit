/**
 * 
 */
package com.comba.server.common.msg.session.ex;

/**
 * @author xianhongdong
 *
 */
public class DeviceInvalidPasswordException extends Exception{
	private static final long serialVersionUID = 1L;
	public DeviceInvalidPasswordException(String msg){
        super(msg);
    }
    
    public DeviceInvalidPasswordException(Exception cause){
        super(cause);
    }

    public DeviceInvalidPasswordException(String msg, Exception cause){
        super(msg, cause);
    }
}
