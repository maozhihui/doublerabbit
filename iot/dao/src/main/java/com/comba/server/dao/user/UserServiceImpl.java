package com.comba.server.dao.user;

import com.comba.server.common.data.User;
import com.comba.server.common.data.device.Product;
import com.comba.server.common.data.product.ProductTypeEnum;
import com.comba.server.common.data.user.UserEnum;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.common.BaseDao;
import com.comba.server.dao.common.utils.StringUtils;
import com.comba.server.dao.device.ProductService;
import com.comba.server.dao.model.TenantEntity;
import com.comba.server.dao.model.UserEntity;
import com.comba.server.dao.tenant.TenantDao;
import com.comba.server.dao.util.DaoUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.Transient;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.comba.server.common.data.user.UserEnum.USER_TYPE_TENANT_ADMIN;


/**
 * 用户服务实现类
 * @author wengzhonghui
 *
 */
@Slf4j
@Service("userJpaService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userJpaDao;

	@Autowired
	private TenantDao tenantJpaDao;

	@Autowired
	private ProductService productService;
	
	@Resource
	private BaseDao baseDao;

	@Value("${userImport.initPwd}")
	private String USER_INIT_PWD;//导入用户的初始密码
	
	
	/**
	 * findAll
	 * @return
	 */
	public List<User> findAll() {
		List<UserEntity> list = userJpaDao.findAll();
		return DaoUtil.convertDataList(list);
	}
	
	
	public List<User> findByIds(Iterable<String> ids){
		List<UserEntity> list =  userJpaDao.findAll(ids);
		return DaoUtil.convertDataList(list);
	}
	/**
	 * Save
	 * @param userJpa
	 */
	@Transactional
	@Override
	public User save(User userJpa) {
		userJpa.setCreateTime(new Date());
		userJpa.setStateFlag(User.STATE_FLAG_ACTIVE);
		return userJpaDao.save(new UserEntity(userJpa)).toData();
	}

	@Override
	public void delete(String id) {
		userJpaDao.delete(id);
	}

	@Override
	public void delete(User t) {
		userJpaDao.delete(new UserEntity(t));
	}

	@Override
	public User getOne(String id) {
		
		UserEntity t = userJpaDao.findOne(id);
		if(t==null) return null;
		return t.toData();
		
	}

	@Override
	public boolean exists(String id) {
		return userJpaDao.exists(id);
	}

	@Override
	public void delete(Iterable<User> entities) {
		if(entities!=null){
			for(User t:entities){
				userJpaDao.delete(new UserEntity(t));
			}
		}
	}


	@Override
	public List<User> findByAccount(String account) {
		List<UserEntity> list = userJpaDao.findByAccount(account);
		return DaoUtil.convertDataList(list);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page getDataByPage( User user, int pageNo, int pageSize) {
		
        StringBuffer hql = new StringBuffer(" from UserEntity a ");
		Map<String, Object> params = new HashMap<String, Object>();
		if(user!=null){
			hql.append(getWhereSql(user));
			params = getWhereParam(user);
		}
		String queryHql = hql.toString();
        
		Page resultPage = baseDao.pagedQuery(queryHql, pageNo, pageSize, params);
		List<UserEntity> list = (List<UserEntity>)resultPage.getResult();
		resultPage.setResult(DaoUtil.convertDataList(list));
		return resultPage;
	}


	@Override
	public void deleteByIds(String[] ids) {
		if(ids!=null && ids.length>0){
			for(String id:ids){
				userJpaDao.delete(id);
			}
		}
	}
	@Override
	public void resetPassword(String userId,String defaultPas)  {
		User user = this.getOne(userId);
		if(user!=null){
			user.setPwd(defaultPas);
			userJpaDao.save(new UserEntity(user));
		}
	}
	
	@Transient
	public String getWhereSql(User user) {
		StringBuffer sb = new StringBuffer("where 1=1");
		if(user.getUserId()!=null)
			sb.append(" and a.userId = :userId ");
		if(StringHelper.isNotEmpty(user.getAccount()))
			sb.append(" and a.account like :account ");
		if(StringHelper.isNotEmpty(user.getUserName()))
			sb.append(" and a.userName like :userName ");
		if(user.getTenantId()!=null)
			sb.append(" and a.tenantId = :tenantId ");
		if(user.getType() != null)
			sb.append(" and a.type = :type");
		return sb.toString();
	}

	
	@Transient
	public Map<String, Object> getWhereParam(User user) {
		Map<String, Object> params = new HashMap<String, Object>();
		if(user.getUserId()!=null)
			params.put("userId", user.getUserId());
		if(StringHelper.isNotEmpty(user.getAccount()))
			params.put("account", "%" + user.getAccount().trim() + "%");
		if(StringHelper.isNotEmpty(user.getUserName())) 
			params.put("userName", "%" + user.getUserName().trim() + "%");
		if(user.getTenantId()!=null)
			params.put("tenantId", user.getTenantId());
		if (user.getType() != null)
			params.put("type",user.getType());
		return params;
			
	}


	@Override
	public User findByUserId(String userId) {
		UserEntity t = userJpaDao.findOne(userId);
		if(t==null) return null;
		return t.toData();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page pagedQuery(int pageNo, int pageSize, User user,List<String> orderBysList) throws Exception {

		StringBuffer hql = new StringBuffer(" from UserEntity a ");
		Map<String, Object> params = new HashMap<String, Object>();
		String orderBy = "";
		if(orderBysList != null && orderBysList.size()>0){
			for(String i : orderBysList){
				orderBy = i;
			}
		}		
		if(user!=null){
			hql.append(getWhereSql(user));
			params = getWhereParam(user);
		}
		String queryHql = hql.toString();
		
	
		if(orderBy.length()>0){
			queryHql += " order by " + orderBy + " desc ";
		}
		
		Page resultPage = baseDao.pagedQuery(queryHql, pageNo, pageSize, params);
		List<UserEntity> list = (List<UserEntity>)resultPage.getResult();
		resultPage.setResult(DaoUtil.convertDataList(list));
		return resultPage;
	}


	@Override
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectUserNames(String userIds) throws Exception {
		String hql="select new map(userId as userId,userName as userName) from UserEntity where userId in ("+userIds+")";
		return  baseDao.queryByHql(hql,new HashMap<String, Object>());
	}


	@Override
	public String getStringRandom(String length) throws Exception {
		// TODO 这是系统自动生成描述，请在此补完后续代码
		return null;
	}
	
	/**
	 * 根据用户状态统计用户数量
	 * 
	 * @author wengzhonghui 2017年7月3日
	 * @param stateFlag
	 * @return
	 */
	@Override
	public Long countByStateFlag(Integer stateFlag){
		return userJpaDao.countByStateFlag(stateFlag);
	}

	/**
	 * 个人注册时，保存用户信息和默认的Tenant对象
	 *
	 * @param userJpa
	 */
	@Override
	@Transactional(rollbackOn = Exception.class)
	public void saveTenantAndUser(User userJpa) throws Exception {
		TenantEntity tenantJpa = new TenantEntity();
        tenantJpa.setName(userJpa.getAccount());
        tenantJpaDao.save(tenantJpa);
		//如果tenantId为空，则抛出异常，回滚数据
		if (StringUtils.isBlank(tenantJpa.getTenantId())){
			throw new Exception("数据库返回tenantId 为空");
		}
		userJpa.setTenantId(tenantJpa.getTenantId());
		saveUserAndProduct(userJpa);
	}

	@Override
	public Integer countByMsisdn(String msisdn) {
		return userJpaDao.countByMsisdn(msisdn);
	}

	@Override
	public Integer countByTenantIdAndType(String tenantId,Integer type) {
		return userJpaDao.countByTenantIdAndType(tenantId,type);
	}


	/**
	 * 解析excel列，保存用户列表
	 * 租户已存在管理员的，暂时不进行保存
	 *
	 * @param rows excel列
	 */
	@Transactional(rollbackOn = Exception.class)
	@Override
	public int saveUserList(List<HSSFRow> rows) throws Exception{
		//成功的个数
		int count = 0;
		for (HSSFRow row : rows){
			User user = new User(row);
			//如果是空行，则跳过
			if (StringHelper.isEmpty(user.getAccount()) && StringHelper.isEmpty(user.getMsisdn())){
				continue;
			}

			if (StringHelper.isEmpty(user.getAccount()) || StringHelper.isEmpty(user.getMsisdn())){
				throw new Exception("导入失败，用户信息不全");
			}

			List<UserEntity> users = userJpaDao.findByAccount(user.getAccount());
			if(users != null && users.size() > 0){
				throw new Exception("导入失败，用户已存在"+user.getAccount());
			}

			if (userJpaDao.countByMsisdn(user.getMsisdn()) > 0){
			    throw new Exception("导入失败，该手机号已存在"+user.getMsisdn());
            }

			user.setType(USER_TYPE_TENANT_ADMIN.getType());
			user.setPwd(USER_INIT_PWD);
			saveTenantAndUser(user);
			count++;
		}
		return count;
	}


	/**
	 * 根据租户ID删除用户
	 *
	 * @param tenantId 租户ID
	 * @return
	 */
	@Override
	public Integer deleteByTenantId(String tenantId) {
		return userJpaDao.deleteByTenantId(tenantId);
	}


	/**
	 * 保存用户，如果是租户管理员，则添加一个默认的产品
	 *
	 * @param user 用户
	 */
	@Override
	public void saveUserAndProduct(User user) {
		if (USER_TYPE_TENANT_ADMIN.getType().equals(user.getType())){
			List<Product> products = productService.findAllByTenantIdAndType(user.getTenantId(), ProductTypeEnum.COMMON_PRODUCT.getType());
			//未存在通用产品的需要生成
			if (products == null || products.isEmpty()){
				productService.saveCommonProduct(user.getTenantId());
			}
		}

		save(user);
	}

	/**
	 * 根据手机号查询用户
	 *
	 * @param msisdn 手机号码
	 * @return
	 */
	@Override
	public User findByMsisdn(String msisdn) {
		UserEntity user = userJpaDao.findByMsisdn(msisdn);
		return user == null ? null : user.toData();
	}


	@Override
	public List<User> findByTenantId(String tenantId) {
		// TODO Auto-generated method stub
		List<UserEntity> list = userJpaDao.findByTenantId(tenantId);
		return DaoUtil.convertDataList(list);
	}
}
