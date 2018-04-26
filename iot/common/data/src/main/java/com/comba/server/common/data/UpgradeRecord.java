package com.comba.server.common.data;


import lombok.Data;

import java.util.Date;

/**
 * 业务实体类
 *
 * @作者 sujinxian
 * @创建时间 2018-01-25 16:21:07
 */
@Data
public class UpgradeRecord implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
     
    private String id;//
    
     
    private String versionId;//升级版本id

    private String version;//版本
     
    private String taskId;//任务id

    private String taskName;//任务名称

    private String deviceId;//设备id

    private String deviceName;//设备名称
     
    private Integer status;//升级状态

    private String tenantId;//租户id
     
    private Date createTime;//创建时间

    //任务初始化
    public static final Integer INIT = 0;
    //已通知设备
    public static final Integer SEND = 1;
    //下发消息超时
    public static final Integer SEND_FAIL = 2;
    //收到设备升级成功
    public static final Integer NOTIFY_SUCCESS = 3;
    //收到设备升级失败
    public static final Integer NOTIFY_FAIL = 4;

    public UpgradeRecord() {
    }

    public UpgradeRecord(String versionId, String taskId, String deviceId, String tenantId, Integer status, Date createTime) {
        this.versionId = versionId;
        this.taskId = taskId;
        this.deviceId = deviceId;
        this.tenantId = tenantId;
        this.status = status;
        this.createTime = createTime;
        this.tenantId = tenantId;
    }

    public UpgradeRecord(String id, String versionId, String taskId, String deviceId, String tenantId, Integer status, Date createTime) {
        this.id = id;
        this.versionId = versionId;
        this.taskId = taskId;
        this.deviceId = deviceId;
        this.tenantId = tenantId;
        this.status = status;
        this.createTime = createTime;
        this.tenantId = tenantId;
    }

}
