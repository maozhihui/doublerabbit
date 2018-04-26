/**
 * 
 */
package com.comba.server.common.msg.session.ex;

/**
 * @author xianhongdong
 *
 */
public class DeviceNotExistException extends Exception{
	private static final long serialVersionUID = 1L;
	public DeviceNotExistException(String msg){
        super(msg);
    }
    
    public DeviceNotExistException(Exception cause){
        super(cause);
    }

    public DeviceNotExistException(String msg, Exception cause){
        super(msg, cause);
    }
}
