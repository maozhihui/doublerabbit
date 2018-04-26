package com.comba.server.dao.device.template;


import com.comba.server.common.data.device.DeviceTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import com.comba.server.dao.model.device.DeviceTemplateEntity;
import org.springframework.data.jpa.repository.Query;


/**
 * 设备模板数据层
 * @author wengzhonghui
 *
 */
public interface DeviceTemplateDao extends JpaRepository<DeviceTemplateEntity, String>{

    /**
     * 根据设备类别统计
     *
     * @param categoryId 设备类别
     * @return
     */
    Integer countByCategoryId(String categoryId);

    /**
     * 根据名称统计设备类别
     *
     * @param name 名称
     * @return
     */
    int countByName(String name);

    /**
     * 根据名称查询设备类别
     *
     * @param name 名称
     * @return
     */
    DeviceTemplateEntity findByName(String name);

}
