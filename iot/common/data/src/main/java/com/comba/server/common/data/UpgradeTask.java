package com.comba.server.common.data;




import lombok.Data;

import java.util.Date;

/**
 * 业务实体类
 *
 * @作者 sujinxian
 * @创建时间 2018-01-25 16:21:15
 */
@Data
public class UpgradeTask implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
     
    private String id;//
    
     
    private String name;//任务名称
    
     
    private String versionId;//版本id
    
     
    private Integer status;//任务下发状态 1-未下发 2-已下发
    
     
    private Integer deviceNum;//升级设备总数
    
     
    private Integer successNum;//升级成功数
    
     
    private String taskDesc;//描述
    
     
    private Date createTime;//创建时间

    private String deviceIdList; //接收设备ID列表

    private String tenantId;//租户id

    private String version;//升级版本

    public static final Integer TASK_INIT = 0;
    public static final Integer TASK_START = 1;
    public static final Integer TASK_FINISH = 2;


    public UpgradeTask() {
    }

    public UpgradeTask(String id, String name, String versionId, Integer status, Integer deviceNum, Integer successNum, String taskDesc, Date createTime, String tenantId, String version,String deviceIdList) {
        this.id = id;
        this.name = name;
        this.versionId = versionId;
        this.status = status;
        this.deviceNum = deviceNum;
        this.successNum = successNum;
        this.taskDesc = taskDesc;
        this.createTime = createTime;
        this.tenantId = tenantId;
        this.version = version;
        this.deviceIdList = deviceIdList;
    }
}
