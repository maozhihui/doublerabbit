package com.comba.server.common.data;

import java.util.Date;

import com.comba.server.common.data.id.AuditLogId;

/**
 * 日志 信息
 * @author wengzhonghui
 *
 */
public class AuditLog  extends SearchTextBased<AuditLogId> {
	private static final long serialVersionUID = -4121919078993049946L;
	private String auditLogId;
	private String description;
	private Integer type;//日志类型，1为系统日志 ，0为用户操作日志 
	public static int TYPE_USER_OPRATION_LOG = 0;//用户操作日志 
	public static int TYPE_SYSTEM_LOG = 1;//系统日志
	public static int TYPE_LOGIN_LOG = 2;//登陆日志
	
	
	private String requestIp;
	private String createBy;
	
	private Date createDate;
	
	private String userId;
	
	
	public AuditLog() {
		super();
	}
	public AuditLog(String auditLogId, String description, Integer type, String requestIp, String createBy,
			Date createDate, String userId) {
		super();
		this.auditLogId = auditLogId;
		this.description = description;
		this.type = type;
		this.requestIp = requestIp;
		this.createBy = createBy;
		this.createDate = createDate;
		this.userId = userId;
	}
	public String getAuditLogId() {
		return auditLogId;
	}
	public void setAuditLogId(String auditLogId) {
		this.auditLogId = auditLogId;
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
	public String getSearchText() {
		return this.auditLogId;
	}
	
}
