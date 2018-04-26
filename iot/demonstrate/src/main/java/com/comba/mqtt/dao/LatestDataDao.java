package com.comba.mqtt.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comba.mqtt.dao.model.LatestDataEntity;

public interface LatestDataDao extends JpaRepository<LatestDataEntity, Integer> {
	
	LatestDataEntity findByDevIdAndAttrName(int devId,String attrName);
	
	void deleteByDevId(int devId);
	
	List<LatestDataEntity> findByDevId(int devId);
}
