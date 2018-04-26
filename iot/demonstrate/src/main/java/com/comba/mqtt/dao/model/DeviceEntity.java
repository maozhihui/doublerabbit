package com.comba.mqtt.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "device")
public class DeviceEntity implements Serializable {

	private static final long serialVersionUID = 657956879900022873L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@Column(name = "dev_eui")
	private String devEui;
	
	@Column(name = "dev_name")
	private String devName;
	
	@Column(name = "dev_type")
	private int devType;
	
	@Column(name = "location")
	private String location;
	
	@Column(name = "dev_name_en")
	private String devNameEn;
	
	@Column(name = "location_en")
	private String locationEn;
	
	@Column(name = "category_id")
	private int categoryId;
	
	public DeviceEntity(){}
}
