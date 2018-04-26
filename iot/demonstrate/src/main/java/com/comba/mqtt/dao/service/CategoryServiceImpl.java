package com.comba.mqtt.dao.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.comba.mqtt.controller.msg.TreeNode;
import com.comba.mqtt.dao.CategoryDao;
import com.comba.mqtt.dao.model.CategoryEntity;

/**
 * 
 * @author maozhihui
 * @date 2017年10月31日 下午1:56:17
 */
@Service
public class CategoryServiceImpl implements CategoryService {

	@Value("${language.type:cn}")
	private String langType;
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Override
	public List<TreeNode> findAll(Integer type) {
		List<CategoryEntity> categoryEntities = categoryDao.findAll();
		List<TreeNode> res = new ArrayList<>();
		for (CategoryEntity entity : categoryEntities) {
			TreeNode node = null;
			if (type == 1) {
				node = new TreeNode(entity.getId(), entity.getParentId(), 
						entity.getCode(), entity.getName(), entity.getLevelFlag());	
			}else if(type == 2) {
				node = new TreeNode(entity.getId(), entity.getParentId(), 
						entity.getCode(), entity.getNameEn(), entity.getLevelFlag());
			}
			if (node != null)
				res.add(node);
		}
		return res;
	}

}
