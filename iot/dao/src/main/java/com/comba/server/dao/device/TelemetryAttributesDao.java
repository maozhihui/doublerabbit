package com.comba.server.dao.device;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.comba.server.dao.model.device.TelemetryAttributesEntity;





/**
 * 参数模板数据层
 * @author wengzhonghui
 *
 */
public interface TelemetryAttributesDao extends JpaRepository<TelemetryAttributesEntity, String>{
	
	/**
	 * 根据产品分类、开始、结束时间统计设备数量
	 * 
	 * @author wengzhonghui 2017年7月3日
	 * @return
	 */
	@Query("select count(a.id) from  TelemetryAttributesEntity a,DeviceEntity b "
			+ " where a.devId=b.id and b.productId=:productId and a.updateTime >=:startTime and a.updateTime <=:endTime")
	Long countByProductId(@Param("productId") String productId,@Param("startTime") Date startTime
			,@Param("endTime") Date endTime);

	/**
	 * 根据名称统计遥测参数
	 *
	 * @param attributeName 参数名称
	 * @return
	 */
	int countByAttributeNameAndDevId(String attributeName,String devId);

    /**
     * 查找设备所有的遥测属性
     *
     * @param devId 设备ID
     * @return
     */
	List<TelemetryAttributesEntity> findByDevId(String devId);
	
}
