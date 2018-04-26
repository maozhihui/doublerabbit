package com.comba.server.dao.device;

import com.comba.server.common.data.device.AttributesTemplate;
import com.comba.server.common.data.Device;
import com.comba.server.common.data.device.DeviceCamera;
import com.comba.server.common.data.device.DeviceTemplate;
import com.comba.server.common.data.id.DeviceId;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.common.data.web.utils.Constants;
import com.comba.server.common.data.web.utils.UUIDUtils;
import com.comba.server.dao.common.BaseDao;
import com.comba.server.dao.device.template.AttributesTemplateService;
import com.comba.server.dao.device.template.DeviceTemplateService;
import com.comba.server.dao.exception.DataValidationException;
import com.comba.server.dao.model.TenantEntity;
import com.comba.server.dao.model.device.ConfigAttributesEntity;
import com.comba.server.dao.model.device.DeviceEntity;
import com.comba.server.dao.model.device.TelemetryAttributesEntity;
import com.comba.server.dao.service.DataValidator;
import com.comba.server.dao.tenant.TenantDao;
import com.comba.server.dao.util.Constant;
import com.comba.server.dao.util.DaoUtil;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.*;

import static com.comba.server.dao.service.Validator.validateId;
import static com.comba.server.dao.util.DaoUtil.getData;


/**
 * 用户服务实现类
 * @author wengzhonghui
 *
 */
@Slf4j
@Service("deviceJpaService")
public class DeviceServiceImpl implements DeviceService {
	@Autowired
	private DeviceDao deviceDao;

	@Autowired
	private AttributesTemplateService attributesTemplateService;

	@Autowired
	private ConfigAttributesDao configAttributesDao;

	@Autowired
	private TelemetryAttributesDao telemetryAttributesDao;

	@Autowired
    private DeviceTemplateService deviceTemplateService;
	
	@Resource
	private BaseDao baseDao;

	@Resource
    private TenantDao tenantDao;

	private final static Integer IS_CONFIG_ATTRIBUTE = 0;//是否为遥测参数 configAttribute

	/**
	 * findAll
	 * @return
	 */
	public List<Device> findAll() {
		List<DeviceEntity> list = deviceDao.findAll();
		return DaoUtil.convertDataList(list); 
	}
	
	
	public List<Device> findByIds(Iterable<String> ids){
		List<DeviceEntity> list = deviceDao.findAll(ids);
		return DaoUtil.convertDataList(list); 
	}

	/**
	 * 保存设备和参数信息
	 *
	 * @param t
	 */
	@Transactional
	public String  saveDeviceAndAttributes(Device t) {

		DeviceEntity entity = new DeviceEntity(t);
		deviceDao.save(entity);

		List<AttributesTemplate> templates = attributesTemplateService.findByDeviceTemplateId(t.getDeviceTemplateId());
		for (AttributesTemplate template : templates) {
		    //如果是配置属性，需要把属性名称和默认值同步到配置属性表，如果模板更新了，也需要同步更新到配置属性表
			if (IS_CONFIG_ATTRIBUTE.equals(template.getIsTelemetry())) {
				configAttributesDao.save(new ConfigAttributesEntity(template, entity.getDevId()));
			}
		}

		return entity.getDevId();
	}

	/**
	 * Save
	 * @param t
	 */
	@Transactional
	@Override
	public Device save(Device t) {
		return deviceDao.save(new DeviceEntity(t)).toData();
	}

	/**
	 * 保存设备列表
	 *
	 * @param list 列表
	 */
	@Transactional
	public List<Device> saveDeviceList(List<HSSFRow> list, String productId,String tenantId) throws Exception {
		List<Device> deviceList = new ArrayList<>();
	    for (HSSFRow row: list){
            Device device;
		    try {
                device = new Device(row,productId,tenantId);
            }catch (Exception e){
		        throw new Exception("导入失败，导入文件格式不对，请按照模板文件设置");
            }

            //如果是空行，则跳过
            if (StringUtils.isEmpty(device.getName()) && StringUtils.isEmpty(device.getHardIdentity())){
                continue;
            }

            if (StringUtils.isBlank(device.getName()) && StringUtils.isBlank(device.getHardIdentity())
                    && StringUtils.isBlank(device.getDeviceTemplateId())
                    && StringUtils.isBlank(device.getIsGateWayStr())){
                throw new Exception("导入失败，设备信息不全 "+device.getName());
            }

            if (Constant.IS_GATEWAY.equals(device.getIsGateWayStr())){
                device.setIsGateWay(1);
            }else if (Constant.IS_NOT_GATEWAY.equals(device.getIsGateWayStr())){
                device.setIsGateWay(0);
            }else{
                throw new Exception("导入失败，设备的网关字段出错 "+device.getIsGateWayStr());
            }

            if (Constants.IS_LORA.equals(device.getIsLoraStr())){
                device.setIsLora(1);
            }else if (Constants.IS_NOT_LORA.equals(device.getIsLoraStr())){
                device.setIsLora(0);
            }else{
                throw new Exception("导入失败，设备的isLora字段出错 "+device.getIsLoraStr());
            }

            if(deviceDao.countByTenantIdAndName(tenantId,device.getName()) > 0){
                throw new Exception("导入失败，设备名称已存在 "+device.getName());
            }

            if(deviceDao.countByHardIdentity(device.getHardIdentity()) > 0){
                throw new Exception("导入失败，设备标识已存在 "+device.getHardIdentity());
            }
            //模板名称解析到DeviceTemplateId字段，所以这里的DeviceTemplateId = DeviceTemplateName
            DeviceTemplate deviceTemplate = deviceTemplateService.findByName(device.getDeviceTemplateId());
            if(deviceTemplate == null){
                throw new Exception("导入失败，模板名称不存在 "+device.getDeviceTemplateId());
            }
            device.setDeviceTemplateId(deviceTemplate.getDeviceTemplateId());

            if (device.getSn() == null){
                device.setSn("");
            }

            deviceList.add(device);
		}
		return deviceList;
	}

	@Override
	public void delete(String id) {
		deviceDao.delete(id);
	}

	@Override
	public void delete(Device t) {
		deviceDao.delete(new DeviceEntity(t));
	}

	@Override
	public Device getOne(String id) {
		DeviceEntity t = deviceDao.findOne(id);
		if(t==null) return null;
		return t.toData();
		
	}

	@Override
	public boolean exists(String id) {
		return deviceDao.exists(id);
	}

	@Override
	public void delete(Iterable<Device> entities) {
		if(entities!=null){
			for(Device t : entities){
				deviceDao.delete(new DeviceEntity(t));
			}
		}
		
	}

    /**
     * 删除设备的信息，包括了其属性
     *
     * @param ids
     */
	@Override
    @Transactional(rollbackOn = Exception.class)
	public void deleteByIds(List<String> ids) {
        for(String devId:ids){
            List<ConfigAttributesEntity> configAttributes = configAttributesDao.findByDevId(devId);
            if (configAttributes != null)
                configAttributesDao.delete(configAttributes);
            deviceDao.delete(devId);
        }
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Page getDataByPage(Device device, int pageNo, int pageSize) {
		
        StringBuffer hql = new StringBuffer(" from DeviceEntity a ");
		Map<String, Object> params = new HashMap<String, Object>();
		if(device!=null){
			hql.append(getWhereSql(device));
			params = getWhereParam(device);
		}
		String queryHql = hql.toString();
        
		Page resultPage = baseDao.pagedQuery(queryHql, pageNo, pageSize, params);
		List<DeviceEntity> list = (List<DeviceEntity>)resultPage.getResult();
		resultPage.setResult(DaoUtil.convertDataList(list));
		return resultPage;
	}



	@SuppressWarnings("unchecked")
	@Override
	public Page pagedQuery(int pageNo, int pageSize, Device device, List<String> orderBysList) throws Exception {

		StringBuffer hql = new StringBuffer(" from DeviceEntity a ");
		Map<String, Object> params = new HashMap<String, Object>();
		//add sort v1.0  后续添加多条件排序 lzh
		String orderBy = "";
		if(orderBysList != null && orderBysList.size()>0){
			for(String i : orderBysList){
				orderBy = i;
			}
		}		
		if(device!=null){
			hql.append(getWhereSql(device));
			params = getWhereParam(device);
		}
		String queryHql = hql.toString();
		
	
		if(orderBy.length()>0){
			queryHql += " order by " + orderBy + " desc ";
		}
		
		Page resultPage = baseDao.pagedQuery(queryHql, pageNo, pageSize, params);
		List<DeviceEntity> list = (List<DeviceEntity>)resultPage.getResult();
		resultPage.setResult(DaoUtil.convertDataList(list));
		return resultPage;
	}

	
	public String getWhereSql(Device device) {
		StringBuffer sb = new StringBuffer("where 1=1");
		if(device.getDevId()!=null)
			sb.append(" and a.devId = :devId ");
		if(StringHelper.isNotEmpty(device.getName()))
			sb.append(" and a.name like :name ");
		if(device.getProductId()!=null)
			sb.append(" and a.productId = :productId ");
		if (device.getTenantId() != null)
			sb.append(" and a.tenantId = :tenantId");
        if (device.getIsGateWay() != null)
            sb.append(" and a.isGateWay = :isGateWay");
        if (device.getStatus() != null)
            sb.append(" and a.status = :status");
        if (StringUtils.isNoneBlank(device.getHardIdentity()))
            sb.append(" and a.hardIdentity = :hardIdentity");
		return sb.toString();
	}
	
	public Map<String, Object> getWhereParam(Device device) {
		Map<String, Object> params = new HashMap<String, Object>();
		if(device.getDevId()!=null)
			params.put("devId", device.getDevId());
		if(StringHelper.isNotEmpty(device.getName()))
			params.put("name", "%" + device.getName().trim() + "%");
		if(device.getProductId()!=null)
			params.put("productId", device.getProductId());
		if (device.getTenantId() != null )
			params.put("tenantId",device.getTenantId());
        if (device.getIsGateWay() != null )
            params.put("isGateWay",device.getIsGateWay());
        if (device.getStatus() != null )
            params.put("status",device.getStatus());
        if (StringUtils.isNoneBlank(device.getHardIdentity()))
            params.put("hardIdentity",device.getHardIdentity());
		return params;
			
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> deviceStatisByDay(Date startTime,Date endTime){
		StringBuilder sql = new StringBuilder("");
		sql.append("SELECT COUNT(ID) deviceNum,DATE_FORMAT(CREATE_TIME,'%Y-%m-%d') createTime FROM device  ");
		sql.append("WHERE CREATE_TIME>=:startTime AND create_time<=:endTime ");
		sql.append("GROUP BY DATE_FORMAT(CREATE_TIME,'%Y-%m-%d')");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		return baseDao.queryBySql(sql.toString(), params);
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> deviceStatisByDayOfProduct(Date startTime,Date endTime,String productId){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(ID) deviceNum,DATE_FORMAT(CREATE_TIME,'%Y-%m-%d') createTime FROM device  ");
		sql.append(" WHERE CREATE_TIME>=:startTime AND create_time<=:endTime and PRODUCT_ID=:productId ");
		sql.append(" GROUP BY DATE_FORMAT(CREATE_TIME,'%Y-%m-%d')");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		params.put("productId", productId);
		return baseDao.queryBySql(sql.toString(), params);
	}
	
	
	@SuppressWarnings("unchecked")
	public Map<String,Object> deviceStatisByIsGateWayOfProduct(String productId){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(id) devNum,IS_GATEWAY isGateWay FROM device  ");
		sql.append(" WHERE  PRODUCT_ID=:productId ");
		sql.append(" GROUP BY IS_GATEWAY");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productId", productId);
		List<Object[]> list = baseDao.queryBySql(sql.toString(), params);
		Object devNum_notGateWay = null;
		Object devNum_gateWay = null;
		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				Object[] dt = list.get(i);
				if(dt.length>=2){
					if(dt[0]!=null && dt[1]!=null){
						String gateWayType = dt[1].toString();
						if("0".equals(gateWayType)){
							devNum_notGateWay = dt[0];
						}else{
							devNum_gateWay = dt[0];;
						}
					}
					
				}
			}
		}
		devNum_notGateWay = devNum_notGateWay==null?0:devNum_notGateWay;
		devNum_gateWay = devNum_gateWay==null?0:devNum_gateWay;
		Map<String,Object> statis = new HashMap<String,Object>();
		statis.put("devNum_notGateWay", devNum_notGateWay);
		statis.put("devNum_gateWay", devNum_gateWay);
		return statis;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String,Object> deviceStatisByIsGateWay(){
		StringBuilder sql = new StringBuilder("");
		sql.append("SELECT COUNT(id) devNum,IS_GATEWAY isGateWay FROM device GROUP BY IS_GATEWAY ");
		Map<String, Object> params = new HashMap<String, Object>();
		List<Object[]> list = baseDao.queryBySql(sql.toString(), params);
		Object devNum_notGateWay = null;
		Object devNum_gateWay = null;
		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				Object[] dt = list.get(i);
				if(dt.length>=2){
					if(dt[0]!=null && dt[1]!=null){
						String gateWayType = dt[1].toString();
						if("0".equals(gateWayType)){
							devNum_notGateWay = dt[0];
						}else{
							devNum_gateWay = dt[0];;
						}
					}
					
				}
			}
		}
		devNum_notGateWay = devNum_notGateWay==null?0:devNum_notGateWay;
		devNum_gateWay = devNum_gateWay==null?0:devNum_gateWay;
		Map<String,Object> statis = new HashMap<String,Object>();
		statis.put("devNum_notGateWay", devNum_notGateWay);
		statis.put("devNum_gateWay", devNum_gateWay);
		return statis;
	}
	
	/**
	 * 通过产品统计设备的数量
	 * 
	 * @author wengzhonghui 2017年7月3日
	 * @param productId
	 * @return
	 */
	@Override
	public Long countByProductId(String productId){
		return deviceDao.countByProductId(productId);
	}

	/**
	 * 通过产品id查询设备列表
	 *
	 * @param productId 产品id
	 * @return
	 */
	@Override
	public Page queryDeviceByProductId(String productId,Page page) {
		StringBuffer sql = new StringBuffer();
		sql.append("from DeviceEntity  ");
		sql.append(" where productId =:productId");
		Map<String, Object> params = new HashMap<>();
		params.put("productId", productId);
		Page resultPage = baseDao.pagedQuery(sql.toString(), page.getPageNo(), page.getPageSize(), params);
		List<DeviceEntity> list = (List<DeviceEntity>) resultPage.getResult();
		resultPage.setResult(DaoUtil.convertDataList(list));
		return resultPage;
	}

	/**
	 * 删除租户下面的所有的设备
	 *
	 * @param tenantId 租户ID
	 * @return
	 */
	@Override
	public Integer deleteByTenantId(String tenantId) {
		return deviceDao.deleteByTenantId(tenantId);
	}


	/**
	 * 通过租户和名称查询设备数量
	 *
	 * @param tenantId
	 * @param name
	 * @return
	 */
	@Override
	public int countByTenantIdAndName(String tenantId, String name) {
		return deviceDao.countByTenantIdAndName(tenantId,name);
	}

	/**
	 * 根据设备标识统计设备
	 *
	 * @param hardIdentity 设备标识
	 * @return
	 */
	@Override
	public int countByHardIdentity(String hardIdentity) {
		return deviceDao.countByHardIdentity(hardIdentity);
	}

    /**
     * 查找产品下面的设备
     *
     * @param productId 产品ID
     * @return
     */
    @Override
    public List<DeviceEntity> findByProductId(String productId) {
        return deviceDao.findByProductId(productId);
    }

    /**
     * 根据设备模板ID查询设备
     *
     * @param deviceTemplateId 模板ID
     * @return
     */
    @Override
    public int countByDeviceTemplateId(List<String> deviceTemplateId) {
        return deviceDao.countByDeviceTemplateId(deviceTemplateId);
    }


    @Override
    public Device findDeviceById(DeviceId deviceId) {
        log.trace("Executing findDeviceById [{}]", deviceId);
        validateId(deviceId, "Incorrect deviceId " + deviceId);
        List<String> idList = new ArrayList<String>();
        idList.add(deviceId.getId().toString());
        DeviceEntity result = deviceDao.findOne(UUIDUtils.toUUID(deviceId.getId()));
        return getData(result);
    }

    @Override
    public Optional<Device> findDeviceByHardIdentity(String hardIdentity){
        if (org.hibernate.internal.util.StringHelper.isEmpty(hardIdentity))
            return Optional.empty();
        log.debug("Executing findDeviceById [{}]",hardIdentity);
        StringBuffer hql = new StringBuffer(" from DeviceEntity a where a.hardIdentity= :hardIdentity");
        Map<String, Object> params = new HashMap<>();
        params.put("hardIdentity", hardIdentity);
        List<DeviceEntity> result = baseDao.queryByHql(hql.toString(), params);
        if (result.size() > 0) {
            return Optional.of(getData(result.get(0)));
        }
        return Optional.empty();
    }


    @Override
    public Device saveDevice(Device device) {
        log.trace("Executing saveDevice [{}]", device);
        deviceValidator.validate(device);

        DeviceEntity saveDev = deviceDao.save(new DeviceEntity(device));
        return getData(saveDev);
    }


    private DataValidator<Device> deviceValidator =
            new DataValidator<Device>() {
                @Override
                protected void validateDataImpl(Device device) {
                    if (org.springframework.util.StringUtils.isEmpty(device.getName())) {
                        throw new DataValidationException("Device name should be specified!");
                    }
                    if (device.getTenantId() == null) {
                        throw new DataValidationException("Device should be assigned to tenant!");
                    } else {
                        TenantEntity tenant = tenantDao.findOne(device.getTenantId());
                        if (tenant == null) {
                            throw new DataValidationException("Device is referencing to non-existent tenant!");
                        }
                    }
                }
            };

    @Override
    public boolean updateStatus(Device device) {
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer(" update DeviceEntity a ");
        hql.append("set a.status= :status ");
        params.put("id", UUIDUtils.toUUID(device.getId().getId()));
        params.put("status", device.getStatus());
        Optional<String> gatewayId = Optional.ofNullable(device.getGatewayId());
        if (gatewayId.isPresent()) {
            hql.append(",a.gatewayId= :gatewayId ");
            // 注意这里网关ID是不带"-"
            params.put("gatewayId", device.getGatewayId());
        }
        hql.append(" where a.id= :id");
        int result = baseDao.updateByHql(hql.toString(), params);
        return (result==0 ? false : true);
    }

	@Override
	public void delete(String deviceId, String productId) {
		String sql = "DELETE FROM device WHERE id=:devId AND product_id=:productId";
		Map<String,Object> params = new HashMap<>();
		params.put("devId",deviceId);
		params.put("productId",productId);
		baseDao.updateBySql(sql,params);
	}

	@Override
	public List<Device> findByTenantId(String tenantId){
		List<DeviceEntity> list = deviceDao.findByTenantId(tenantId);
		return DaoUtil.convertDataList(list);
	}

    /**
     * 根据设备模板id查询设备id列表
     *
     * @param deviceTemplateId 设备模板id
     * @return
     */
    @Override
    public List<String> findByDeviceTemplateId(String deviceTemplateId) {
        String sql = "select id from Device where device_Template_Id = :deviceTemplateId ";

        Map<String,Object> params = new HashMap<>();
        params.put("deviceTemplateId",deviceTemplateId);

        return baseDao.queryBySql(sql,params);
    }
    
    /**
     * 根据设备mac匹配查询设备硬件标识列表
     *
     * @param mac 设备列表
     * @return
     */
    @Override
    public List<String> findLikeMacByDeviceHardIdentity(String mac) {
        String sql = "select hard_Identity from Device where hard_Identity like :mac";

        Map<String,Object> params = new HashMap<>();
        params.put("mac",mac+"%");

        return baseDao.queryBySql(sql,params);
    }

    /**
     * 查询设备和属性名是否存在
     *
     * @param devId         设备id
     * @param attributeName 属性名
     * @return
     */
    @Override
    public String findByDevIdAndAttributeName(String devId, String attributeName) {
        String sql = "select  d.hardIdentity from DeviceEntity d,ConfigAttributesEntity c ,AttributesTemplateEntity a " +
                " where d.id=c.devId and c.attributeId = a.id " +
                "and d.id = :devId and a.name = :attributeName";
        Map<String,Object> params = new HashMap<>();
        params.put("devId",devId);
        params.put("attributeName",attributeName);

        List<String> list = baseDao.queryByHql(sql,params);
        if (list == null || list.isEmpty()){
            return null;
        }
        return list.get(0);
    }


    /**
     * 根据设备id列表查询设备列表
     *
     * @param ids 设备id列表
     * @return
     */
    @Override
    public List<Device> findByDevIdList(List<String> ids) {
        List<DeviceEntity> list = deviceDao.findByDevIdList(ids);
        return list == null? null:DaoUtil.convertDataList(list);
    }
}
