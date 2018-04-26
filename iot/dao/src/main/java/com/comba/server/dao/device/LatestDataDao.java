package com.comba.server.dao.device;

import com.comba.server.dao.model.device.LatestDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author maozhihui
 * @Description:
 * @create 2017/12/25 11:34
 **/
public interface LatestDataDao extends JpaRepository<LatestDataEntity,String> {

    LatestDataEntity findByAttributeIdAndDevId(String attributeId,String devId);

    List<LatestDataEntity> findByDevId(String devId);

    LatestDataEntity findByAttributeNameAndDevId(String attributeName,String devId);
}
