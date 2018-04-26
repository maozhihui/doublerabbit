package com.comba.server.dao.user;


import java.util.List;
import java.util.Map;

import com.comba.server.common.data.User;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.common.CommonService;
import com.comba.server.dao.model.UserEntity;

import org.apache.poi.hssf.usermodel.HSSFRow;


/**
 * 用户服务类
 * @author wengzhonghui
 *
 */
public interface UserService extends CommonService<User>{

	
	/**
	 * 根据用户账号查找用户
	 * @param userCode
	 * @return
	 */
	List<User> findByAccount(String account); 
	
	public List<User> findAll();
	
	/**
	 * 通过多个ID查找用户
	 * @param ids
	 * @return
	 */
	public List<User> findByIds(Iterable<String> ids);
	
	/**
	 * 批量删除
	 * @param ids
	 */
	void deleteByIds(String[] ids);
	
	/**
	 * 随机生成数字、字母 定长字符串 lizhonghang
	 * @param s
	 * @throws Exception
	 */
	public String getStringRandom(String length)throws Exception;
	
	/**
	 * 重置密码
	 * @param s
	 * @throws Exception
	 */
	public void resetPassword(String userId,String defaultPas);
	
	/**
	 * 根据ID查找用户
	 * @param 
	 * @throws Exception
	 */
	public User findByUserId(String userId);
	
	 /**
	 * @param pageNo
	 * @param pageSize
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public Page pagedQuery(int pageNo, int pageSize, User user,List<String> orderBysList)throws Exception;
	
	/**
	 * 查询选中的用户名
	 * @param userIds
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectUserNames(String userIds) throws Exception;
	
	/**
	 * 根据用户状态统计用户数量
	 * 
	 * @author wengzhonghui 2017年7月3日
	 * @param stateFlag
	 * @return 用户数量
	 */
	Long countByStateFlag(Integer stateFlag);

	/**
	 *  个人注册时，保存用户信息和默认的Tenant对象
	 *
	 * @param userJpa
	 */
	void saveTenantAndUser(User userJpa) throws Exception;

	/**
	 * 根据联系电话 统计用户数
	 *
	 * @param msisdn 联系电话
	 * @return
	 */
	Integer countByMsisdn(String msisdn);

	/**
	 * 根据租户ID统计用户
	 *
	 * @param tenantId 租户ID
	 * @param type  用户类型
	 *
	 * @return
	 */
	Integer countByTenantIdAndType(String tenantId,Integer type);

	/**
	 * 解析excel列，保存用户列表
	 *
	 * @param rows excel列
	 */
	int saveUserList(List<HSSFRow> rows) throws Exception;

	/**
	 * 根据租户ID删除用户
	 *
	 * @param tenantId 租户ID
	 * @return
	 */
	Integer deleteByTenantId(String tenantId);

	/**
	 * 保存用户，如果是租户管理员，则添加一个默认的产品
	 *
	 * @param user 用户
	 */
	void saveUserAndProduct(User user);

	/**
	 * 根据手机号查询用户
	 *
	 * @param msisdn 手机号码
	 * @return
	 */
	User findByMsisdn(String msisdn);
	/**
	 * 根据用户TenatId查找
	 * @param TenatId
	 * @return
	 */
	List<User> findByTenantId(String tenantId); 
}
