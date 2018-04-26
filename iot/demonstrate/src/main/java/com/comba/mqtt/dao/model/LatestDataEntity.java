package com.comba.mqtt.dao.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "latest_data")
public class LatestDataEntity implements Serializable {

	private static final long serialVersionUID = -4027241752796121263L;

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
	
	@Column(name = "alarm_level")
	private String alarmLevel = "normal";
	
	@Column(name = "alarm_cause")
	private String alarmCause;
	
	@Column(name = "update_time")
	private Date updateTime;
	
	@Column(name = "alarm_level_en")
	private String alarmLevelEn;
	
	@Column(name = "alarm_cause_en")
	private String alarmCauseEn;
	
	public LatestDataEntity(){}
}
