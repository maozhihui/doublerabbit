package com.comba.server.dao.model.device;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.common.util.StringHelper;

import com.comba.server.common.data.User;
import com.comba.server.common.data.device.Product;
import com.comba.server.dao.model.ToData;

/**
 * 产品
 * @author wengzhonghui
 *
 */
@Entity
@Table(name="product")
@Data
public class ProductEntity implements ToData<Product>{

	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid") 
	@Column(name="ID")
	private String id;
	
	@Column(name="CATEGORY_ID")
	private String categoryId;
	
	@Column(name="TENANT_ID")
	private String tenantId;
	
	@Column(name="NAME")
	private String name;//产品名称
	
	@Column(name="BRIEF")
	private String brief;//产品简介
	
	@Column(name="ACCESS_PROTOCOL")
	private String accessProtocol;//接入协议 
	
	@Column(name="CREATE_TIME")
	private Date createTime;
	
	@Column(name="UPDATE_TIME")
	private Date updateTime;

	@Column(name="TYPE")
	private Integer type;

	@Column(name="MODEL")
	private String model;
	
	@OneToOne
	@JoinColumn(name="CATEGORY_ID",insertable = false,updatable = false,nullable=true)
    @NotFound(action= NotFoundAction.IGNORE)
    private CategoryEntity categoryEntity;

	@Override
	public Product toData() {
		String categoryName = "";
		if(categoryEntity!=null){
			categoryName = categoryEntity.getName();
		}
		return new Product(id, categoryId, tenantId, name, brief, accessProtocol, createTime, updateTime,categoryName,type,model);
	}

	public ProductEntity(Product t) {
		super();
		this.id = t.getProductId();
		this.categoryId = t.getCategoryId();
		this.tenantId = t.getTenantId();
		this.name = t.getName();
		this.brief = t.getBrief();
		this.accessProtocol = t.getAccessProtocol();
		this.createTime = t.getCreateTime();
		this.updateTime = t.getUpdateTime();
		this.type = t.getType();
		this.model = t.getModel();
	}

	public ProductEntity() {
		super();
	}
	
	
	
	
}