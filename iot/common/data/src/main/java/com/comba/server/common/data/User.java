package com.comba.server.common.data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.hibernate.annotations.common.util.StringHelper;

import com.comba.server.common.data.id.UserJpaId;


/**
 * 用户信息
 * @author wengzhonghui
 *
 */
@Data
public class User extends SearchTextBased<UserJpaId> {

	private static final long serialVersionUID = -4538005412680782L;
	
	private String userId;
	
	private String areaId;
	
	private String tenantId;//租户ID
	
	private String userName;//用户名称
	
	private String pwd;//密码
	
	private String email;//邮箱
	
	private String msisdn;//联系电话
	
	private String detailAddress;//详细地址
	
	private Integer stateFlag;
	public static int STATE_FLAG_ACTIVE = 1;//活动状态
	public static int STATE_FLAG_del = 0;//删除状态
	
	private Integer type; //用户类型： 1 系统管理员 2 租户管理员 3 普通用户

	private String authority;//用户鉴权类型，sys_admin,tenant_admin,customer_user

	private Date createTime;
	
	private Date updateTime;
	
	private String account;//账号
	
	private String tenantName;//租户名称

	private String msCaption; //验证码
	
	public User(String userId, String areaId, String tenantId, String userName, String pwd, String email,
			String msisdn, String detailAddress, Integer stateFlag, String authority, Date createTime, Date updateTime,
			String account ,Integer type,String tenantName) {
		super();
		this.userId = userId;
		this.areaId = areaId;
		this.tenantId = tenantId;
		this.userName = userName;
		this.pwd = pwd;
		this.email = email;
		this.msisdn = msisdn;
		this.detailAddress = detailAddress;
		this.stateFlag = stateFlag;
		this.authority = authority;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.account = account;
		this.type = type;
		this.tenantName = tenantName;
	}

	public User(HSSFRow row) {
		if (row.getCell(0) != null) {
            row.getCell(0).setCellType(CellType.STRING);
			this.account = row.getCell(0).getStringCellValue();
		}


		if (row.getCell(1) != null ) {
            row.getCell(1).setCellType(CellType.STRING);
			this.userName = row.getCell(1).getStringCellValue();
		}

		if (row.getCell(2) != null) {
			row.getCell(2).setCellType(CellType.STRING);
			this.msisdn = row.getCell(2).getStringCellValue();
		}

		if (row.getCell(3) != null) {
            row.getCell(3).setCellType(CellType.STRING);
			this.email = row.getCell(3).getStringCellValue();
		}

	}

	public User() {
		super();
	}


	@Override
	public String getSearchText() {
		return this.userName;
	}

	
	
}