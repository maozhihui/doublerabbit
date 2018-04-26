package com.comba.server.dao.device;


import com.comba.server.common.data.Device;
import com.comba.server.common.data.device.DeviceCamera;
import com.comba.server.common.data.id.DeviceId;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.dao.common.CommonService;
import com.comba.server.dao.model.device.DeviceEntity;

import org.apache.poi.hssf.usermodel.HSSFRow;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * 设备管理服务类
 * @author wengzhonghui
 *
 */
public interface DeviceService extends CommonService<Device>{

	
	
	 /**
	 * @param pageNo
	 * @param pageSize
	 * @param device
	 * @return
	 * @throws Exception
	 */
	Page pagedQuery(int pageNo, int pageSize, Device device, List<String> orderBysList)throws Exception;
	
	/**
	 * 批量删除
	 * @param ids
	 */
	void deleteByIds(List<String> ids);
	
	/**
	 * 通过多个ID查找租户
	 * @param ids
	 * @return
	 */
	List<Device> findByIds(Iterable<String> ids);
	
	/**
	 * 按天统计设备总数
	 * 
	 * @author wengzhonghui 2017年6月28日
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<Object[]> deviceStatisByDay(Date startTime,Date endTime);
	
	/**
	 * 按天统计产品设备总数
	 * 
	 * @author wengzhonghui 2017年6月28日
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<Object[]> deviceStatisByDayOfProduct(Date startTime,Date endTime,String productId);
	
	/**
	 * 根据是否网关统计某个产品的设备数量
	 * 
	 * @author wengzhonghui 2017年7月3日
	 * @param productId 
	 * @return devNum_notGateWay:非网关设备的数量，devNum_gateWay：网关设备数量
	 */
	Map<String,Object> deviceStatisByIsGateWayOfProduct(String productId);
	
	/**
	 * 根据是否网关统计所有设备数量
	 * 
	 * @author wengzhonghui 2017年7月3日
	 * @return devNum_notGateWay:非网关设备的数量，devNum_gateWay：网关设备数量
	 */
	Map<String,Object> deviceStatisByIsGateWay();
	
	
	/**
	 * 通过产品统计设备的数量
	 * 
	 * @author wengzhonghui 2017年7月3日
	 * @param productId
	 * @return
	 */
	Long countByProductId(String productId);

	/**
	 * 通过产品查询设备列表
	 *
	 * @param productId 产品id
	 * @return
	 */
	Page queryDeviceByProductId(String productId,Page page);

	/**
	 * 保存设备和参数信息
	 *
	 * @param t
	 */
	String saveDeviceAndAttributes(Device t);

	/**
	 * 保存设备信息列表
	 *
	 * @param jpaList 列表
	 */
    List<Device> saveDeviceList(List<HSSFRow> jpaList, String productId,String tenantId) throws Exception;

	/**
	 * 删除租户下面的所有的设备
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
    int countByDeviceTemplateId(List<String> deviceTemplateId);


    Device findDeviceById(DeviceId deviceId);

    /**
     * 根据硬件标识获获设备
     * @param hardIdentity
     * @return
     */
    Optional<Device> findDeviceByHardIdentity(String hardIdentity);

    /**
     * 设备信息保存
     * @param device
     * @return
     */
    Device saveDevice(Device device);

    /**
     * 更新设备状态
     * @param device
     * @return
     */
    boolean updateStatus(Device device);

    void delete(String deviceId,String productId);

	List<Device> findByTenantId(String tenantId);


    /**
     * 根据设备模板id查询设备id列表
     *
     * @param deviceTemplateId 设备模板id
     * @return
     */
	List<String> findByDeviceTemplateId(String deviceTemplateId);
	
	 /**
     * 根据设备mac匹配查询设备硬件标识列表
     *
     * @param mac 设备列表
     * @return
     */

    public List<String> findLikeMacByDeviceHardIdentity(String mac);

    /**
     * 查询设备和属性名是否存在
     *
     * @param devId 设备id
     * @param attributeName 属性名
     * @return
     */
    String findByDevIdAndAttributeName(String devId,String attributeName);

    /**
     * 根据设备id列表查询设备列表
     *
     * @param ids 设备id列表
     * @return
     */
    List<Device> findByDevIdList(List<String> ids);
}
