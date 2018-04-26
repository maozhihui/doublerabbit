/**
 * 
 */
package com.comba.server.common.msg.session.ex;

/**
 * @author xianhongdong
 *
 */
public class DeviceInvalidTokenException extends Exception{
	private static final long serialVersionUID = 1L;
	public DeviceInvalidTokenException(String msg){
        super(msg);
    }
    
    public DeviceInvalidTokenException(Exception cause){
        super(cause);
    }

    public DeviceInvalidTokenException(String msg, Exception cause){
        super(msg, cause);
    }
}
