package com.comba.server.common.data;

import com.comba.server.common.data.device.DeviceCamera;
import com.comba.server.common.data.id.DeviceId;
import com.comba.server.common.data.web.utils.Constants;
import lombok.Data;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.CellType;

import java.util.Date;


/**
 * 设备管理
 * @author wengzhonghui
 *
 */
@Data
public class Device extends SearchTextBased<DeviceId>{

	private static final long serialVersionUID = -8418708379583508367L;
	
	private String devId;
	
	private String tenantId;
	
	private String productId;
	
	private String name;
	
	private String sn;//设备编号
	
	private String hardIdentity;//设备标识
	
	private String userName;//鉴权用户名
	
	private String secretKey;//鉴权密钥
	
	private String categoryId;//设备分类
	
	private String description;//设备描述
	
	private Integer isGateWay;//是否为网关
	
	private String gatewayId;//
	
	private Date createTime;
	
	private Date updateTime;
	
	private String categoryName;//设备类型名称

	private String deviceTemplateId;

	private String deviceTemplateName;
	
	private Integer status;

	private String statusStr;

	private String isGateWayStr;//是否为网关

    private DeviceCamera deviceCamera;//摄像头的拓展字段

    private String position;//设备位置信息

    private Integer isLora;//是否同步lora

    private String appKey; //设备的appKey

    private String isLoraStr;//是否同步lora
	@Override
	public String getSearchText() {
		return name;
	}


	public Device(String devId, String tenantId, String productId, String name, String sn, String hardIdentity,
                  String userName, String secretKey, String categoryId, String description, Integer isGateWay,
                  String gatewayId, Date createTime, Date updateTime, String categoryName,
                  String deviceTemplateId, String deviceTemplateName, int status,DeviceCamera deviceCamera,String position,
                  Integer isLora,String appKey) {
		super();
		this.devId = devId;
		this.tenantId = tenantId;
		this.productId = productId;
		this.name = name;
		this.sn = sn;
		this.hardIdentity = hardIdentity;
		this.userName = userName;
		this.secretKey = secretKey;
		this.categoryId = categoryId;
		this.description = description;
		this.isGateWay = isGateWay;
		this.gatewayId = gatewayId;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.categoryName = categoryName;
		this.deviceTemplateId = deviceTemplateId;
		this.deviceTemplateName = deviceTemplateName;
		this.status = status;
		if (isGateWay == 1){
		    this.isGateWayStr = Constants.IS_GATEWAY;
        }else{
            this.isGateWayStr = Constants.IS_NOT_GATEWAY;
        }
        this.deviceCamera = deviceCamera;
		this.position = position;
		if (status == 1 && deviceCamera == null){
		    this.statusStr = Constants.ONLINE;
        }else if (status == 0 && deviceCamera == null){
		    this.statusStr = Constants.OFFLINE;
        }
        if (isLora != null && isLora == 1){
            this.isLoraStr = Constants.IS_LORA;
        }else {
            this.isLoraStr = Constants.IS_NOT_LORA;
        }
        this.isLora = isLora;
        this.appKey = appKey;
	}

	public Device() {
		super();
		this.userName = "testUser";
		this.secretKey = "123456";
	}

    public Device(DeviceId id) {
        super(id);
    }

    public Device(HSSFRow row, String productId, String tenantId) {

        if (row.getCell(0) != null) {
            row.getCell(0).setCellType(CellType.STRING);
            this.name = row.getCell(0).getStringCellValue();
        }

        if (row.getCell(1) != null) {
            row.getCell(1).setCellType(CellType.STRING);
            this.hardIdentity = row.getCell(1).getStringCellValue();
        }

        if (row.getCell(2) != null) {
            row.getCell(2).setCellType(CellType.STRING);
            this.deviceTemplateId = row.getCell(2).getStringCellValue();
        }

        if (row.getCell(3) != null) {
            row.getCell(3).setCellType(CellType.STRING);
            this.sn = row.getCell(3).getStringCellValue();
        }

        if (row.getCell(4) != null) {
            row.getCell(4).setCellType(CellType.STRING);
            this.isGateWayStr = row.getCell(4).getStringCellValue();
        }

        if (row.getCell(5) != null) {
            row.getCell(5).setCellType(CellType.STRING);
            this.position = row.getCell(5).getStringCellValue();
        }

        if (row.getCell(6) != null) {
            row.getCell(6).setCellType(CellType.STRING);
            this.isLoraStr = row.getCell(6).getStringCellValue();
        }

        if (row.getCell(7) != null) {
            row.getCell(7).setCellType(CellType.STRING);
            this.appKey = row.getCell(7).getStringCellValue();
        }

        if (row.getCell(8) != null) {
            row.getCell(8).setCellType(CellType.STRING);
            this.statusStr = row.getCell(8).getStringCellValue();
        }

        if (row.getCell(9) != null) {
            row.getCell(9).setCellType(CellType.STRING);
            this.description = row.getCell(9).getStringCellValue();
        }

        this.productId = productId;
        this.createTime = new Date();
        this.tenantId = tenantId;
        this.status = 0;
    }

}