package com.comba.server.common.data.device;



import com.comba.server.common.data.SearchTextBased;
import com.comba.server.common.data.id.CategoryId;
import lombok.Data;


/**
 * 产品类别管理
 * @author wengzhonghui
 *
 */
@Data
public class Category extends SearchTextBased<CategoryId> {

	private static final long serialVersionUID = -8418708379583508367L;
	private String categoryId;
	
	private String parentId;
	
	private String code;
	
	private String name;
	
	private Integer levelFlag;//关联层级

	@Override
	public String getSearchText() {
		return name;
	}

	public Category(String categoryId, String parentId, String code, String name, Integer levelFlag) {
		super();
		this.categoryId = categoryId;
		this.parentId = parentId;
		this.code = code;
		this.name = name;
		this.levelFlag = levelFlag;
	}

	public Category() {
		super();
	}
	
	

}