package com.comba.server.dao.model.device;

import com.comba.server.common.data.Device;
import com.comba.server.common.data.device.DeviceCamera;
import com.comba.server.common.data.web.utils.StringHelper;
import com.comba.server.dao.model.ToData;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * 设备管理
 * @author wengzhonghui
 *
 */
@Data
@Entity
@Table(name="device")
public class DeviceEntity implements ToData<Device>{

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")  
	@Column(name="ID")
	private String devId;
	
	@Column(name="TENANT_ID")
	private String tenantId;
	
	@Column(name="PRODUCT_ID")
	private String productId;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="SN")
	private String sn;//设备编号
	
	@Column(name="HARD_IDENTITY")
	private String hardIdentity;//设备标识
	
	@Column(name="USER_NAME")
	private String userName;//鉴权用户名
	
	@Column(name="SECRET_KEY")
	private String secretKey;//鉴权密钥
	
	@Column(name="CATEGORY")
	private String categoryId;//设备分类
	
	@Column(name="DESCRIPTION")
	private String description;//设备描述
	
	@Column(name="IS_GATEWAY")
	private Integer isGateWay;//是否为网关
	
	@Column(name="GATEWAY_ID")
	private String gatewayId;//
	
	@Column(name="CREATE_TIME")
	private Date createTime;
	
	@Column(name="UPDATE_TIME")
	private Date updateTime;

	@Column(name="DEVICE_TEMPLATE_ID")
	private String deviceTemplateId;

	@Column(name = "POSITION")
    private String position;

    @Column(name = "IS_LORA")
	private Integer isLora;

    @Column(name = "APP_KEY")
	private String appKey;

    @Column(name="STATUS")
    private int status;

	@JoinColumn(name="DEVICE_TEMPLATE_ID",insertable=false,nullable=true,updatable=false)
	@OneToOne
    @NotFound(action=NotFoundAction.IGNORE)
	private DeviceTemplateEntity deviceTemplateEntity;
	
	

	@JoinColumn(name="CATEGORY",insertable=false,nullable=true,updatable=false)
	@OneToOne
    @NotFound(action= NotFoundAction.IGNORE)
	private CategoryEntity categoryEntity;

	@OneToOne(targetEntity = DeviceCameraEntity.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "device_camera_id",referencedColumnName = "id")
    @NotFound(action= NotFoundAction.IGNORE)
	private DeviceCameraEntity deviceCameraEntity;

	@Override
	public Device toData() {
		String categoryName = "";
		if(categoryEntity!=null){
			categoryName = categoryEntity.getName();
		}
		String deviceTemplateName = "";
		if (deviceTemplateEntity != null){
			deviceTemplateName = deviceTemplateEntity.getName();
		}
        DeviceCamera camera = null;
		if (this.deviceCameraEntity != null){
		    camera = this.deviceCameraEntity.toData();
        }
		return new Device(devId,tenantId, productId, name, sn, hardIdentity, userName, secretKey, categoryId
				, description, isGateWay, gatewayId, createTime, updateTime,categoryName,
				deviceTemplateId,deviceTemplateName ,status,camera,position,isLora,appKey);
	}

	public DeviceEntity(Device t) {
		super();
		if (StringHelper.isNotEmpty(t.getDevId())){
			this.devId = t.getDevId();
		}
		this.tenantId = t.getTenantId();
		this.productId = t.getProductId();
		this.name = t.getName();
		this.sn = t.getSn();
		this.hardIdentity = t.getHardIdentity().trim();
		this.userName = t.getUserName();
		this.secretKey = t.getSecretKey();
		this.categoryId = t.getCategoryId();
		this.description = t.getDescription();
		this.isGateWay = t.getIsGateWay();
		this.gatewayId = t.getGatewayId();
		this.createTime = t.getCreateTime();
		this.updateTime = t.getUpdateTime();
		this.deviceTemplateId = t.getDeviceTemplateId();
		if (t.getStatus() != null){
            this.status = t.getStatus();
        }
		if (t.getDeviceCamera() != null){
		    this.deviceCameraEntity = new DeviceCameraEntity(t.getDeviceCamera());
        }
        this.position = t.getPosition();
		this.isLora = t.getIsLora();
		this.appKey = t.getAppKey();
	}

	public DeviceEntity() {
		super();
	}
	
}