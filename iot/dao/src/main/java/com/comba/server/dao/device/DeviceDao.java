package com.comba.server.dao.device;


import com.comba.server.common.data.Device;
import com.comba.server.dao.model.device.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;


/**
 * 设备管理数据层
 * @author wengzhonghui
 *
 */
public interface DeviceDao extends JpaRepository<DeviceEntity, String>{
	
	
	/**
	 * 通过产品统计设备的数量
	 * 
	 * @author wengzhonghui 2017年7月3日
	 * @param productId
	 * @return
	 */
	Long countByProductId(String productId);

	/**
	 * 通过租户ID删除设备
	 *
	 * @param tenantId 租户ID
	 * @return
	 */
	Integer deleteByTenantId(String tenantId);

	/**
	 * 通过租户和名称查询设备数量
	 *
	 * @param tenantId
	 * @param name
	 * @return
	 */
	int countByTenantIdAndName(String tenantId,String name);

	/**
	 * 根据设备标识统计设备
	 *
	 * @param hardIdentity 设备标识
	 * @return
	 */
	int countByHardIdentity(String hardIdentity);

    /**
     * 查找产品下面的设备
     *
     * @param productId 产品ID
     * @return
     */
	List<DeviceEntity> findByProductId(String productId);

    /**
     * 根据设备模板ID查询设备
     *
     * @param deviceTemplateId 模板ID
     * @return
     */
    @Query("select count(d.devId) from DeviceEntity d where d.deviceTemplateId in ( :deviceTemplateId)")
	int countByDeviceTemplateId(@Param(value = "deviceTemplateId") List<String> deviceTemplateId);

    /**
     * Find devices by tenantId and device name.
     *
     * @param tenantId the tenantId
     * @param name the device name
     * @return the optional device object
     */
    List<DeviceEntity> findByTenantIdAndName(String tenantId, String name);

    @Transactional
    Integer deleteByDevIdAndProductId(String devId,String productId);

	List<DeviceEntity> findByTenantId(String tenantId);

    /**
     * 根据设备id列表查询设备列表
     *
     * @param ids 设备id列表
     * @return
     */
    @Query("select d from DeviceEntity d where d.devId in ( :ids)")
    List<DeviceEntity> findByDevIdList(@Param(value = "ids") List<String> ids);
}
