package com.comba.server.common.data.device;


import lombok.Data;

/**
 * 摄像头拓展字段业务实体类
 *
 * @作者 sujinxian
 * @创建时间 2018-02-06 09:56:37
 */
@Data
public class DeviceCamera implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
     
    private String id;//
    
     
    private String devId;//设备id
    
     
    private String userName;//用户名
    
     
    private String pwd;//密码
    
     
    private String serverIp;//服务器ip
    
     
    private Integer port;//端口
    
     
    private String proxyIp;//代理ip
    

    public DeviceCamera() {
    }

    public DeviceCamera(String id, String devId, String userName, String pwd, String serverIp, Integer port, String proxyIp) {
        this.id = id;
        this.devId = devId;
        this.userName = userName;
        this.pwd = pwd;
        this.serverIp = serverIp;
        this.port = port;
        this.proxyIp = proxyIp;
    }
}
