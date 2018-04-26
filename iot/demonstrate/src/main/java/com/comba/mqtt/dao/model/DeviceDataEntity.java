package com.comba.mqtt.dao.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 
 * @author maozhihui
 *
 */
@Data
@Entity
@Table(name = "device_data")
public class DeviceDataEntity implements Serializable {

	private static final long serialVersionUID = 7942059963265562353L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	//@JoinColumn(name = "dev_id")
	//@ManyToOne(targetEntity = DeviceEntity.class)
	@Column(name = "dev_id")
	private int devId;
	
	@Column(name = "attr_name")
	private String attrName;
	
	@Column(name = "attr_value")
	private String attrValue;
	
	@Column(name = "update_time")
	private Date updateTime;
	
	public DeviceDataEntity(){}
}
