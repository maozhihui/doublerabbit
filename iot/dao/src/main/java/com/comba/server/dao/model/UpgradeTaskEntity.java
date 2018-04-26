package com.comba.server.dao.model;

import javax.persistence.*;

import com.comba.server.common.data.UpgradeTask;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;


import java.util.Date;

/**
 * 实体Bean
 *
 * @作者 sujinxian
 * @创建时间 2018-01-25 16:21:15
 */
@Data
@Entity
@Table(name="upgrade_task")
public class UpgradeTaskEntity implements ToData<UpgradeTask>{

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name="id")
    private String id;//
    
    @Column(name="name")
    private String name;//任务名称
    
    @Column(name="version_id")
    private String versionId;//版本id
    
    @Column(name="status")
    private Integer status;//任务下发状态 1-未下发 2-已下发
    
    @Column(name="device_num")
    private Integer deviceNum;//升级设备总数
    
    @Column(name="success_num")
    private Integer successNum;//升级成功数
    
    @Column(name="task_desc")
    private String taskDesc;//描述
    
    @Column(name="create_time")
    private Date createTime;//创建时间

    @Column(name="tenant_id")
    private String tenantId;//租户id

    @Column(name="dev_ids")
    private String devIds;//设备id列表

    @OneToOne
    @JoinColumn(name = "version_id", insertable = false, updatable = false)
    @NotFound(action= NotFoundAction.IGNORE)
    private AppVersionEntity appVersionEntity;

    public UpgradeTaskEntity() {
    }

    public UpgradeTaskEntity(String id, String name, String versionId, Integer status, Integer deviceNum, Integer successNum, String taskDesc, Date createTime) {
        this.id = id;
        this.name = name;
        this.versionId = versionId;
        this.status = status;
        this.deviceNum = deviceNum;
        this.successNum = successNum;
        this.taskDesc = taskDesc;
        this.createTime = createTime;
    }
   
   @Override
   public UpgradeTask toData() {
        String version = null;
        if (appVersionEntity != null){
            version = appVersionEntity.getVersion();
        }
	   return new UpgradeTask(id, name, versionId, status, deviceNum, successNum, taskDesc, createTime,tenantId,version,devIds);
   }

    public UpgradeTaskEntity(UpgradeTask t) {
		super();
		if(t!=null){
			this.id = t.getId();
			this.name = t.getName();
			this.versionId = t.getVersionId();
			this.status = t.getStatus();
			this.deviceNum = t.getDeviceNum();
			this.successNum = t.getSuccessNum();
			this.taskDesc = t.getTaskDesc();
			this.createTime = t.getCreateTime();
			this.tenantId = t.getTenantId();
			this.devIds = t.getDeviceIdList();
		}
    }
}
