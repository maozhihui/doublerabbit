package com.comba.server.dao.device;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;
import javax.persistence.Transient;
import javax.transaction.Transactional;

import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comba.server.common.data.device.Category;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.common.BaseDao;
import com.comba.server.dao.model.device.CategoryEntity;
import com.comba.server.dao.model.device.ProductEntity;
import com.comba.server.dao.util.DaoUtil;




/**
 * 产品服务实现类
 * @author wengzhonghui
 *
 */
@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryDao categoryDao;
	
	@Resource
	private BaseDao baseDao;
	
	
	/**
	 * findAll
	 * @return
	 */
	public List<Category> findAll() {
		List<CategoryEntity> list= categoryDao.findAll();
		return DaoUtil.convertDataList(list);
	}
	
	
	public List<Category> findByIds(Iterable<String> ids){
		
		List<CategoryEntity> list= categoryDao.findAll(ids);
		return DaoUtil.convertDataList(list);
	}
	/**
	 * Save
	 * @param category
	 */
	@Transactional
	@Override
	public Category save(Category category) {
		return categoryDao.save(new CategoryEntity(category)).toData();
	}

	@Override
	public void delete(String id) {
		categoryDao.delete(id);
	}

	@Override
	public void delete(Category category) {
		categoryDao.delete(new CategoryEntity(category));
	}

	@Override
	public Category getOne(String id) {
		CategoryEntity t = categoryDao.findOne(id);
		if(t==null) return null;
		return t.toData();
		
	}

	@Override
	public boolean exists(String id) {
		return categoryDao.exists(id);
	}

	@Override
	public void delete(Iterable<Category> entities) {
		if(entities!=null){
			for(Category t : entities){
				if(t!=null) categoryDao.delete(new CategoryEntity(t));
			}
		}
	}


	@Override
	public void deleteByIds(String[] ids) {
		if(ids!=null && ids.length>0){
			for(String id:ids){
				categoryDao.delete(id);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Page getDataByPage( Category category, int pageNo, int pageSize) {
		
        StringBuffer hql = new StringBuffer(" from CategoryEntity a ");
		Map<String, Object> params = new HashMap<String, Object>();
		if(category!=null){
			hql.append(getWhereSql(category));
			params = getWhereParam(category);
		}
		String queryHql = hql.toString();
        
		Page resultPage = baseDao.pagedQuery(queryHql, pageNo, pageSize, params);
		List<CategoryEntity> list = (List<CategoryEntity>)resultPage.getResult();
		resultPage.setResult(DaoUtil.convertDataList(list));
		return resultPage;
	}




	@Override
	public Page pagedQuery(int pageNo, int pageSize, Category category,List<String> orderBysList) throws Exception {

		StringBuffer hql = new StringBuffer(" from CategoryEntity a ");
		Map<String, Object> params = new HashMap<String, Object>();
		String orderBy = "";
		if(orderBysList != null && orderBysList.size()>0){
			for(String i : orderBysList){
				orderBy = i;
			}
		}		
		if(category!=null){
			hql.append(getWhereSql(category));
			params = getWhereParam(category);
		}
		String queryHql = hql.toString();
		
	
		if(orderBy.length()>0){
			queryHql += " order by " + orderBy + " desc ";
		}
		
		Page resultPage = baseDao.pagedQuery(queryHql, pageNo, pageSize, params);
		List<CategoryEntity> list = (List<CategoryEntity>)resultPage.getResult();
		resultPage.setResult(DaoUtil.convertDataList(list));
		return resultPage;
	}


	/**
	 * 根据编码查找
	 * 
	 * @author wengzhonghui 2017年6月5日
	 * @param code
	 * @return
	 */
	@Override
	public List<Category> findByCode(String code){
		List<CategoryEntity> list= categoryDao.findByCode(code);
		return DaoUtil.convertDataList(list);
	}
	
	
	@Override
	public List<Category> findByCategory(Category category){
		
		StringBuffer hql = new StringBuffer(" from CategoryEntity a ");
		Map<String, Object> params = new HashMap<String, Object>();
		if(category!=null){
			hql.append(getWhereSql(category));
			params = getWhereParam(category);
		}
		List<CategoryEntity> list = baseDao.queryByHql(hql.toString(), params);
		return DaoUtil.convertDataList(list);
	}
	
	@Transient
	public String getWhereSql(Category category) {
		StringBuffer sb = new StringBuffer("where 1=1");
		if (category.getCategoryId() != null)
			sb.append(" and a.id = :categoryId ");
		if (StringHelper.isNotEmpty(category.getName()))
			sb.append(" and a.name like :name ");
		if (StringHelper.isNotEmpty(category.getCode()))
			sb.append(" and a.code like :code ");
		if (category.getLevelFlag() != null)
			sb.append(" and a.levelFlag = :levelFlag ");
		if (category.getParentId() != null )
			sb.append(" and a.parentId = :parentId ");
		return sb.toString();
	}
	
	@Transient
	public Map<String, Object> getWhereParam(Category category) {
		Map<String, Object> params = new HashMap<String, Object>();
		if(category.getCategoryId()!=null)
			params.put("categoryId", category.getCategoryId());
		if(StringHelper.isNotEmpty(category.getName()))
			params.put("name", "%" + category.getName().trim() + "%");
		if(StringHelper.isNotEmpty(category.getCode())) 
			params.put("code", "%" + category.getCode().trim() + "%");
		if (category.getLevelFlag() != null)
			params.put("levelFlag", category.getLevelFlag());
		if (category.getParentId() != null )
			params.put("parentId",category.getParentId());
		return params;
			
	}

	/**
	 * 设备类别根据名称统计
	 *
	 * @param name 名称
	 * @return
	 */
	@Override
	public int countByName(String name) {
		return categoryDao.countByName(name);
	}

	@Override
	public Category findByNameAndLevelFlag(String name,int level){
		if (level < 0 || level > 4)
			return null;
		CategoryEntity entity = categoryDao.findByNameAndLevelFlag(name,level);
		return (entity == null) ? null : entity.toData();
	}

	@Override
	public Optional<String> findLastCodeByParentId(String categoryId){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT MAX(CODE) FROM category WHERE parent_id = :parentId");
		Map<String,Object> params = new HashMap<>();
		params.put("parentId",categoryId);
		List<Object> res = baseDao.queryBySql(sql.toString(),params);
		if (res.isEmpty() || res.get(0) == null)
			return Optional.ofNullable(null);
		return Optional.ofNullable((String)(res.get(0)));
	}
}
