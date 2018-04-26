package com.comba.server.dao.user;

import com.comba.server.dao.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;





/**
 * 用户数据层
 * @author wengzhonghui
 *
 */
public interface UserDao extends JpaRepository<UserEntity, String>{

	/**
	 * 根据用户账号查找用户
	 * @param account
	 * @return
	 */
	List<UserEntity> findByAccount(String account); 
	
	
	/**
	 * 根据用户ID查找
	 * @param userId
	 * @return
	 */
	List<UserEntity> findByUserId(String userId); 
	
	/**
	 * 根据用户状态统计用户数量
	 * 
	 * @author wengzhonghui 2017年7月3日
	 * @param stateFlag
	 * @return
	 */
	Long countByStateFlag(Integer stateFlag);

	/**
	 * 根据手机号码统计用户数量
	 *
	 * @return
	 */
	Integer countByMsisdn(String msisdn);

	/**
	 *
	 * 根据租户ID统计用户
	 *
	 * @param tenantId 租户ID
	 * @return
	 */
	Integer countByTenantIdAndType(String tenantId,Integer type);

	/**
	 * 根据tenant ID 删除用户
	 *
	 * @param tenantId 租户ID
	 */
	Integer deleteByTenantId(String tenantId);

	/**
	 * 根据手机查找用户
	 *
	 * @param msisdn 手机号码
	 * @return
	 */
	UserEntity findByMsisdn(String msisdn);
	
	/**
	 * 根据用户TenatId查找
	 * @param TenatId
	 * @return
	 */
	List<UserEntity> findByTenantId(String tenantId); 
}
