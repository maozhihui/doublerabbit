package com.comba.server.dao.device;

import com.comba.server.common.data.device.Product;
import com.comba.server.common.data.product.ProductTypeEnum;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.common.BaseDao;
import com.comba.server.dao.model.device.CategoryEntity;
import com.comba.server.dao.model.device.DeviceEntity;
import com.comba.server.dao.model.device.ProductEntity;
import com.comba.server.dao.util.DaoUtil;

import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 用户服务实现类
 * @author wengzhonghui
 *
 */
@Service("productService")
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductDao productDao;

	@Autowired
	private CategoryDao categoryDao;

	@Autowired
    private DeviceService deviceService;
	
	@Resource
	private BaseDao baseDao;


	
	
	/**
	 * findAll
	 * @return
	 */
	public List<Product> findAll() {
		
		List<ProductEntity>  list = productDao.findAll();
		
		return DaoUtil.convertDataList(list);
	}
	
	
	public List<Product> findByIds(Iterable<String> ids){
		
		List<ProductEntity>  list = productDao.findAll(ids);
		
		return DaoUtil.convertDataList(list);
	}
	/**
	 * Save
	 * @param product
	 */
	@Transactional
	@Override
	public Product save(Product product) {
		return productDao.save(new ProductEntity(product)).toData();
	}

	@Override
	public void delete(String id) {
		StringBuilder sql = new StringBuilder("DELETE FROM product WHERE id=:productId");
		Map<String,Object> params = new HashMap<>();
		params.put("productId",id);
		baseDao.updateBySql(sql.toString(),params);
	}

	@Override
	public void delete(Product t) {
		productDao.delete(new ProductEntity(t));
	}

	@Override
	public Product getOne(String id) {
		ProductEntity t = productDao.findOne(id);
		if(t==null) return null;
		return t.toData();
	}

	@Override
	public boolean exists(String id) {
		return productDao.exists(id);
	}

	@Override
	public void delete(Iterable<Product> entities) {
		if(entities!=null){
			for(Product t : entities){
				productDao.delete(new ProductEntity(t));
			}
		}
	}


	@Override
	public void deleteByIds(List<String> ids) {
        for(String productId:ids){
            List<DeviceEntity> devices = deviceService.findByProductId(productId);
            List<String> devId = devices.stream().map(DeviceEntity::getDevId).collect(Collectors.toList());
            deviceService.deleteByIds(devId);
            productDao.delete(productId);
        }
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Page getDataByPage( Product product, int pageNo, int pageSize) {
		
        StringBuffer hql = new StringBuffer(" from ProductEntity a ");
		Map<String, Object> params = new HashMap<String, Object>();
		if(product!=null){
			hql.append(getWhereSql(product));
			params = getWhereParam(product);
		}
		String queryHql = hql.toString();
		Page resultPage = baseDao.pagedQuery(queryHql, pageNo, pageSize, params);
		List<ProductEntity> list = (List<ProductEntity>)resultPage.getResult();
		resultPage.setResult(DaoUtil.convertDataList(list));
		return resultPage;
	}



	@SuppressWarnings("unchecked")
	@Override
	public Page pagedQuery(int pageNo, int pageSize, Product product,List<String> orderBysList) throws Exception {

		StringBuffer hql = new StringBuffer(" from ProductEntity a ");
		Map<String, Object> params = new HashMap<String, Object>();
		//add sort v1.0  后续添加多条件排序 lzh
		String orderBy = "";
		if(orderBysList != null && orderBysList.size()>0){
			for(String i : orderBysList){
				orderBy = i;
			}
		}		
		if(product!=null){
			hql.append(getWhereSql(product));
			params = getWhereParam(product);
		}
		String queryHql = hql.toString();
		
	
		if(orderBy.length()>0){
			queryHql += " order by " + orderBy + " desc ";
		}
		
		Page resultPage = baseDao.pagedQuery(queryHql, pageNo, pageSize, params);
		List<ProductEntity> list = (List<ProductEntity>)resultPage.getResult();
		resultPage.setResult(DaoUtil.convertDataList(list));
		return resultPage;
	}

	public String getWhereSql(Product product) {
		StringBuffer sb = new StringBuffer("where 1=1");
		if(product.getId()!=null)
			sb.append(" and a.id = :id ");
		if(product.getTenantId()!=null)
			sb.append(" and a.tenantId = :tenantId ");
		if(StringHelper.isNotEmpty(product.getName()))
			sb.append(" and a.name like :name ");
		return sb.toString();
	}
	
	public String getWhereSql_2(Product product) {
		StringBuffer sb = new StringBuffer();
		return sb.toString();
	}
	
	public Map<String, Object> getWhereParam(Product product) {
		Map<String, Object> params = new HashMap<String, Object>();
		if(product.getId()!=null)
			params.put("id", product.getId());
		if(product.getTenantId()!=null)
			params.put("tenantId", product.getTenantId());
		if(StringHelper.isNotEmpty(product.getName()))
			params.put("name", "%" + product.getName().trim() + "%");
		return params;
			
	}

	public List<Product> findByTenantId(String tenantId){
		List<ProductEntity>  list = productDao.findByTenantId(tenantId);
		
		return DaoUtil.convertDataList(list);
	}

	/**
	 * 根据产品类别ID查询是否存在产品
	 *
	 * @param categoryId 产品类别id
	 * @return
	 */
	public Integer countByCategoryId(String categoryId) {
		return productDao.countByCategoryId(categoryId);
	}

	/**
	 * 删除租户下面的产品
	 *
	 * @param tenantId 租户ID
	 * @return
	 */
	@Override
	public Integer deleteByTenantId(String tenantId) {
		return productDao.deleteByTenantId(tenantId);
	}

	/**
	 * 生成一个通用的产品
	 *
	 * @param tenantId 产品
	 */
	@Override
	public void saveCommonProduct(String tenantId) {
		Product product = new Product();
		//把类别为其他的id查询出来，设置到产品的类别ID
		CategoryEntity category = categoryDao.findByNameAndLevelFlag("其它",4);
		if (category != null){
			product.setCategoryId(category.getCategoryId());
		}

		product.setType(ProductTypeEnum.COMMON_PRODUCT.getType());
		product.setName(ProductTypeEnum.COMMON_PRODUCT.getName());
		product.setTenantId(tenantId);
		product.setCreateTime(new Date());
		product.setAccessProtocol("HTTP");

		save(product);
	}

	/**
	 * 查询租户的通用产品
	 *
	 * @param tenantId 租户ID
	 * @param type     产品类型
	 * @return
	 */
	@Override
	public List<Product> findAllByTenantIdAndType(String tenantId, Integer type) {
		List<ProductEntity> list =  productDao.findAllByTenantIdAndType(tenantId, type);
		return list == null? null:DaoUtil.convertDataList(list);
	}


	/**
	 * 根据名称查询租户的产品
	 *
	 * @param name     产品名称
	 * @param tenantId 租户ID
	 * @return
	 */
	@Override
	public Product findByNameAndTenantId(String name, String tenantId) {
		ProductEntity product = productDao.findByNameAndTenantId(name,tenantId);
		return product == null?null:product.toData();
	}

	@Override
	public Product findByTenantIdAndCategoryId(String tenantId, String categoryId) {
		ProductEntity entity = productDao.findByTenantIdAndCategoryId(tenantId,categoryId);
		return (entity == null) ? null : entity.toData();
	}

	@Override
	public List<Object[]> findAllByTenantId(String tenantId) {
		StringBuilder sql = new StringBuilder("SELECT p.ID,p.NAME,c.CODE,p.MODEL,p.ACCESS_PROTOCOL,p.BRIEF,p.CREATE_TIME FROM product p,category c WHERE p.CATEGORY_ID=c.ID AND p.TENANT_ID=:tenantId");
		Map<String,Object> params = new HashMap<>();
		params.put("tenantId",tenantId);
		return (baseDao.queryBySql(sql.toString(),params));
	}

    /**
     * 判断产品是否属于指定类别的产品
     *
     * @param productId    产品id
     * @param categoryName
     * @return
     */
    @Override
    public boolean queryProductByCategoryName(String productId, String categoryName) {
        StringBuilder sql = new StringBuilder("SELECT c.name FROM product p,category c WHERE p.CATEGORY_ID=c.ID AND p.id=:productId");
        Map<String,Object> params = new HashMap<>();
        params.put("productId",productId);
        List<?> list = baseDao.queryBySql(sql.toString(),params);

        if (list != null && !list.isEmpty()){
            return categoryName.equals(list.get(0).toString());
        }

        return false;
    }
}
