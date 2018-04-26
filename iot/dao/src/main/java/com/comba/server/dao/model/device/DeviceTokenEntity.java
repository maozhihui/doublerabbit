/**
 * 
 */
package com.comba.server.dao.model.device;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.comba.server.common.data.DeviceToken;
import com.comba.server.dao.model.ToData;

/**
 * @author xianhongdong
 *
 */
@Entity
@Table(name="device_token")
public class DeviceTokenEntity implements ToData<DeviceToken>{
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")  
	@Column(name="ID")
	private String id;
	@Column(name="DEV_ID")
	private String devId;
	@Column(name="TOKEN")
	private String token;
	@Column(name="TOKEN_CREATETIME")
	private Date createTime;	
	@Column(name="SESSION_UPDATETIME")
	private Date updateTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDevId() {
		return devId;
	}
	public void setDevId(String devId) {
		this.devId = devId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public DeviceTokenEntity(){}
	
	public DeviceTokenEntity(DeviceToken deviceToken) {
		this.id = deviceToken.getId();
		this.devId = deviceToken.getDeviceId();
		this.token = deviceToken.getToken();
		this.createTime = deviceToken.getTokenCreateTime();
		this.updateTime = deviceToken.getSessionUpdateTime();
	}
	
	@Override
	public DeviceToken toData() {
		return new DeviceToken(id, devId, token, createTime, updateTime);
	}
	
}
