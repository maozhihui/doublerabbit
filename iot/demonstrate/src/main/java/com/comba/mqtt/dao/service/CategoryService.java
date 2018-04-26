package com.comba.mqtt.dao.service;

import java.util.List;

import com.comba.mqtt.controller.msg.TreeNode;

/**
 * 
 * @author maozhihui
 * @date 2017年10月31日 下午1:55:05
 */
public interface CategoryService {

	List<TreeNode> findAll(Integer type);
	
}
