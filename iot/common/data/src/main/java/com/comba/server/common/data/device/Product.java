package com.comba.server.common.data.device;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.common.util.StringHelper;

import com.comba.server.common.data.SearchTextBased;
import com.comba.server.common.data.id.ProductId;
import com.comba.server.common.data.id.UserJpaId;





/**
 * 产品
 * @author wengzhonghui
 *
 */
@Data
public class Product extends SearchTextBased<ProductId> {

	private static final long serialVersionUID = -4538005412680782L;
	
	private String productId;
	
	private String categoryId;
	
	private String tenantId;
	
	private String name;//产品名称
	
	private String brief;//产品简介
	
	private String accessProtocol;//接入协议 
	
	private Date createTime;
	
	private Date updateTime;
	
	private String categoryName;//类别名称

	private Integer type; //产品类型，1：系统生成 2:用户自定义

	private String model;//产品型号

	@Override
	public String getSearchText() {
		return name;
	}

	public Product(String productId, String categoryId, String tenantId, String name, String brief,
			String accessProtocol, Date createTime, Date updateTime,String categoryName,Integer type
			,String model) {
		super();
		this.productId = productId;
		this.categoryId = categoryId;
		this.tenantId = tenantId;
		this.name = name;
		this.brief = brief;
		this.accessProtocol = accessProtocol;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.categoryName = categoryName;
		this.type = type;
		this.model = model;
	}

	public Product() {
		super();
	}
	
}