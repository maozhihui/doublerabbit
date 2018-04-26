package com.comba.server.dao.model;

import com.comba.server.common.data.User;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;





/**
 * 用户信息
 * @author wengzhonghui
 *
 */
@Entity
@Table(name="user")
public class UserEntity implements ToData<User>{

	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")   
	@Column(name="ID")
	private String userId;
	
	@Column(name="AREA_ID")
	private String areaId;
	
	@Column(name="TENANT_ID")
	private String tenantId;//租户ID
	
	@Column(name="USER_NAME")
	private String userName;//用户名称
	
	@Column(name="PWD")
	private String pwd;//密码
	
	@Column(name="EMAIL")
	private String email;//邮箱
	
	@Column(name="MSISDN")
	private String msisdn;//联系电话
	
	@Column(name="DETAIL_ADDRESS")
	private String detailAddress;//详细地址
	
	@Column(name="STATE_FLAG")
	private Integer stateFlag;
	public static int STATE_FLAG_ACTIVE = 1;//活动状态
	public static int STATE_FLAG_del = 0;//删除状态
	
	
	@Column(name="AUTHORITY")
	private String authority;//用户鉴权类型，sys_admin,tenant_admin,customer_user
	public static String AUTHORITY_SYS_ADMIN = "sys_admin";//系统管理员
	public static String AUTHORITY_TENANT_ADMIN = "tenant_admin";//租户管理员
	public static String AUTHORITY_CUSTOMER_USER = "customer_user";//普通用户

	
	@Column(name="CREATE_TIME")
	private Date createTime;
	
	@Column(name="UPDATE_TIME")
	private Date updateTime;
	
	@Column(name="ACCOUNT")
	private String account;//账号

	@Column(name="TYPE")
	private Integer type; //用户类型
	
	
	@JoinColumn(name="TENANT_ID",insertable=false,nullable=true,updatable=false)
	@OneToOne
    @NotFound(action= NotFoundAction.IGNORE)
	private TenantEntity tenantJpaEntity;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}

	public Integer getStateFlag() {
		return stateFlag;
	}

	public void setStateFlag(Integer stateFlag) {
		this.stateFlag = stateFlag;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
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

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public UserEntity() {
		super();
	}

	@Override
	public User toData() {
		String tenantName = "";
		if(this.tenantJpaEntity!=null && this.tenantJpaEntity.getName()!=null){
			tenantName = this.tenantJpaEntity.getName();
		}
		return new User(userId, areaId, tenantId, userName, pwd, email, msisdn, detailAddress
				, stateFlag, authority, createTime, updateTime, account, type, tenantName);
	}

	public UserEntity(User userJpa) {
		super();
		this.userId = userJpa.getUserId();
		this.areaId = userJpa.getAreaId();
		this.tenantId = userJpa.getTenantId();
		this.userName = userJpa.getUserName();
		this.pwd = userJpa.getPwd();
		this.email = userJpa.getEmail();
		this.msisdn = userJpa.getMsisdn();
		this.detailAddress = userJpa.getDetailAddress();
		this.stateFlag = userJpa.getStateFlag();
		this.authority = userJpa.getAuthority();
		this.createTime = userJpa.getCreateTime();
		this.updateTime = userJpa.getUpdateTime();
		this.account = userJpa.getAccount();
		this.type = userJpa.getType();
	}

	public TenantEntity getTenantJpaEntity() {
		return tenantJpaEntity;
	}

	public void setTenantJpaEntity(TenantEntity tenantJpaEntity) {
		this.tenantJpaEntity = tenantJpaEntity;
	}


	
	
}