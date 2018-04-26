package com.comba.server.dao.model.device;

import com.comba.server.common.data.device.DeviceTemplate;
import com.comba.server.dao.model.ToData;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * 设备模板
 * @author wengzhonghui
 *
 */
@Data
@Entity
@Table(name="device_template")
public class DeviceTemplateEntity implements ToData<DeviceTemplate>{

	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")   
	@Column(name="ID")
	private String deviceTemplateId;
	
	@Column(name="CATEGORY_ID")
	private String categoryId;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="CREATE_TIME")
	private Date createTime;
	
	@Column(name="UPDATE_TIME")
	private Date updateTime;

	@Column(name="DESCRIPTION")
	private String description;

	@JoinColumn(name="CATEGORY_ID",insertable=false,nullable=true,updatable=false)
	@OneToOne
    @NotFound(action= NotFoundAction.IGNORE)
	private CategoryEntity categoryEntity;

	@JoinColumn(name = "DEVICE_TEMPLATE_ID",insertable=false,nullable=true,updatable=false)
	@OneToMany(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @NotFound(action=NotFoundAction.IGNORE)
	private List<AttributesTemplateEntity> attributesTemplateEntity;

	@Override
	public DeviceTemplate toData() {
		String categoryName = "";
		if(categoryEntity!=null){
			categoryName = categoryEntity.getName();
		}
		return new DeviceTemplate(deviceTemplateId, categoryId, name, createTime, updateTime,categoryName,description);
	}

	public DeviceTemplateEntity(DeviceTemplate t) {
		super();
		if(StringUtils.isNotBlank(t.getDeviceTemplateId())){
			this.deviceTemplateId = t.getDeviceTemplateId();
		}
		this.categoryId = t.getCategoryId();
		this.name = t.getName();
		this.createTime = t.getCreateTime();
		this.updateTime = t.getUpdateTime();
		this.description = t.getDescription();
	}

	public DeviceTemplateEntity() {
		super();
	}
	
	
	
}