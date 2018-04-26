package com.comba.server.dao.model.device;

import com.comba.server.common.data.device.Category;
import com.comba.server.dao.model.ToData;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


/**
 * 产品类别管理
 * @author wengzhonghui
 *
 */
@Entity
@Table(name="category")
public class CategoryEntity implements ToData<Category>{

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")  
	@Column(name="ID")
	private String categoryId;
	
	@Column(name="PARENT_ID")
	private String parentId;
	
	@Column(name="CODE")
	private String code;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="LEVEL_FLAG")
	private Integer levelFlag;//关联层级
	
	
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	

	
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLevelFlag() {
		return levelFlag;
	}

	public void setLevelFlag(Integer levelFlag) {
		this.levelFlag = levelFlag;
	}


	@Override
	public Category toData() {
		
		return new Category(categoryId, parentId, code, name, levelFlag);
	}

	public CategoryEntity(Category category) {
		super();
		this.categoryId = category.getCategoryId();
		this.parentId = category.getParentId();
		this.code = category.getCode();
		this.name = category.getName();
		this.levelFlag = category.getLevelFlag();
	}

	public CategoryEntity() {
		super();
	}

	
}