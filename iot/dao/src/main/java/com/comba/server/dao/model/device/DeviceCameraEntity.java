package com.comba.server.dao.model.device;

import com.comba.server.common.data.device.DeviceCamera;
import com.comba.server.dao.model.ToData;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 摄像头拓展字段实体Bean
 *
 * @作者 sujinxian
 * @创建时间 2018-02-06 09:56:36
 */
@Data
@Entity
@Table(name="device_camera")
public class DeviceCameraEntity implements ToData<DeviceCamera> {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name="ID")
    private String id;//
    
    @Column(name="DEV_ID")
    private String devId;//设备id
    
    @Column(name="USER_NAME")
    private String userName;//用户名
    
    @Column(name="PWD")
    private String pwd;//密码
    
    @Column(name="SERVER_IP")
    private String serverIp;//服务器ip
    
    @Column(name="PORT")
    private Integer port;//端口
    
    @Column(name="PROXY_IP")
    private String proxyIp;//代理ip
    

    public DeviceCameraEntity() {
    }

    public DeviceCameraEntity (String id, String devId, String userName, String pwd, String serverIp, Integer port, String proxyIp) {
        this.id = id;
        this.devId = devId;
        this.userName = userName;
        this.pwd = pwd;
        this.serverIp = serverIp;
        this.port = port;
        this.proxyIp = proxyIp;
    }


   @Override
   public DeviceCamera toData() {
	   return new DeviceCamera( id, devId, userName, pwd, serverIp, port, proxyIp);
   }

    public DeviceCameraEntity (DeviceCamera t) {
		super();
		if(t!=null){
			this.id = t.getId();
			this.devId = t.getDevId();
			this.userName = t.getUserName();
			this.pwd = t.getPwd();
			this.serverIp = t.getServerIp();
			this.port = t.getPort();
			this.proxyIp = t.getProxyIp();
		}
    }
}
