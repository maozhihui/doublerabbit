package com.comba.web.ui;

import com.comba.server.common.data.device.Category;
import com.comba.server.dao.model.device.CategoryEntity;

/**
 * 产品类型树列表
 * @author wengzhonghui
 */
public class CategoryTreeNode extends TreeNode implements TreeNodeConverter<Category, CategoryTreeNode> {

    private String name;
    private String code;
    private int position;
    private String sourcesName;
    private String remark;
    /**
   	 * 1、有效标识，正常，其他不正常
   	 */
   	private Integer enabled;
   	
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSourcesName() {
        return sourcesName;
    }

    public void setSourcesName(String sourcesName) {
        this.sourcesName = sourcesName;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   

    public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    

	@Override
    public CategoryTreeNode convert(Category t) {
		CategoryTreeNode node = new CategoryTreeNode();
        // tree node
        node.setId(t.getCategoryId());
        node.setText(t.getName());
        node.setParentId(t.getParentId());
        node.setIconCls("icon-role");
        node.setDepth(t.getLevelFlag());
        // property
        node.setName(t.getName());
        node.setCode(t.getCode());
        node.setPosition(1);
        return node;
    }
}
