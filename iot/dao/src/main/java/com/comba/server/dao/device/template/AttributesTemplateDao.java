package com.comba.server.dao.device.template;


import com.comba.server.dao.model.device.AttributesTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.QueryHint;
import java.util.List;


/**
 * 参数模板数据层
 * @author wengzhonghui
 *
 */
public interface AttributesTemplateDao extends JpaRepository<AttributesTemplateEntity, String>{

    /**
     * 根据设备模板ID查找属性模板
     *
     * @param deviceTemplateId
     * @return
     */
    List<AttributesTemplateEntity> findByDeviceTemplateId(String deviceTemplateId);

    /**
     * 统计设备模板下面的参数模板
     *
     * @param name 名称
     * @param deviceTemplateId 模板ID
     * @return
     */
    int countByNameAndDeviceTemplateIdAndIsTelemetry(String name,String deviceTemplateId,Integer isTelemetry);
}
