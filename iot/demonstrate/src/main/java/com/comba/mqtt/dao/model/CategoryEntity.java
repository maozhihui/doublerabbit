package com.comba.mqtt.dao.model;

import java.io.Serializable;

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
@Table(name = "category")
public class CategoryEntity implements Serializable{
	 
	private static final long serialVersionUID = -882020055654181731L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@Column(name = "parent_id")
	private int parentId;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "level_flag")
	private int levelFlag;
	
	@Column(name = "name_en")
	private String nameEn;
}
