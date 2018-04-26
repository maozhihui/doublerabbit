package com.comba.server.dao.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.common.util.StringHelper;

import com.comba.server.common.data.AuditLog;
import com.comba.server.common.data.User;

/**
 * 日志 信息
 * @author wengzhonghui
 *
 */
@Entity
@Table(name="audit_log")
public class AuditLogEntity  implements ToData<AuditLog>{
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")  
	@Column(name="ID")
	private String id;
	private String description;
	@Column(name="type")
	private Integer type;//日志类型，1为系统日志 ，0为用户操作日志 
	public static int TYPE_USER_OPRATION_LOG = 0;//用户操作日志 
	public static int TYPE_SYSTEM_LOG = 1;//系统日志
	
	
	@Column(name="request_ip")
	private String requestIp;
	@Column(name="create_by")
	private String createBy;
	
	@Column(name="create_date")
	private Date createDate;
	
	@Column(name="user_id")
	private String userId;
	
	
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}	
	public Integer getType()
	{
		return type;
	}
	public void setType(Integer type)
	{
		this.type = type;
	}
	public String getRequestIp()
	{
		return requestIp;
	}
	public void setRequestIp(String requestIp)
	{
		this.requestIp = requestIp;
	}
	public String getCreateBy()
	{
		return createBy;
	}
	public void setCreateBy(String createBy)
	{
		this.createBy = createBy;
	}
	public Date getCreateDate()
	{
		return createDate;
	}
	public void setCreateDate(Date createDate)
	{
		this.createDate = createDate;
	}
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	
	
	@Override
	public AuditLog toData() {
		return new AuditLog(id, description, type, requestIp, createBy, createDate, userId);
	}
	public AuditLogEntity() {
		super();
	}
	public AuditLogEntity(AuditLog auditLog) {
		super();
		this.id = auditLog.getAuditLogId();
		this.description = auditLog.getDescription();
		this.type = auditLog.getType();
		this.requestIp = auditLog.getRequestIp();
		this.createBy = auditLog.getCreateBy();
		this.createDate = auditLog.getCreateDate();
		this.userId = auditLog.getUserId();
	}

	
	
}
