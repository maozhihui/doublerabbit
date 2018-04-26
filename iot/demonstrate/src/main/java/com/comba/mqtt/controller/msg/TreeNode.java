package com.comba.mqtt.controller.msg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.comba.mqtt.dao.model.DeviceEntity;

import lombok.Data;

/**
 * 
 * @author maozhihui
 * @date 2017年10月31日 下午2:02:02
 */
@Data
public class TreeNode {

	private int id;
	private int parentId;
	private String code;
	private String name;
	private int levelFlag;
	private List<DeviceMetaData> leafs;
	//private List<TreeNode> childs;
	
	public TreeNode(int id,int parentId,String code,String name,int levelFlag){
		this.id = id;
		this.parentId = parentId;
		this.code = code;
		this.name = name;
		this.levelFlag = levelFlag;
	}
	
	public void addLeaf(DeviceMetaData metaData){
		if (this.leafs == null) {
			leafs = new ArrayList<>();
		}
		leafs.add(metaData);
	}
}
