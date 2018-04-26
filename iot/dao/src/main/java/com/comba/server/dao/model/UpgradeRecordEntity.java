package com.comba.server.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.comba.server.common.data.UpgradeRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;


import java.util.Date;

/**
 * 实体Bean
 *
 * @作者 sujinxian
 * @创建时间 2018-01-25 16:21:07
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="upgrade_record")
public class UpgradeRecordEntity implements ToData<UpgradeRecord>{

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name="id")
    private String id;//
    
    @Column(name="version_id")
    private String versionId;//升级版本id
    
    @Column(name="task_id")
    private String taskId;//任务id
    
    @Column(name="device_id")
    private String deviceId;//设备id

    @Column(name="tenant_id")
    private String tenantId;//租户id
    
    @Column(name="status")
    private Integer status;//升级状态
    
    @Column(name="create_time")
    private Date createTime;//创建时间

   
   @Override
   public UpgradeRecord toData() {
	   return new UpgradeRecord( id, versionId, taskId, deviceId, tenantId,status, createTime);
   }

    public UpgradeRecordEntity(UpgradeRecord t) {
		super();
		if(t!=null){
			this.id = t.getId();
			this.versionId = t.getVersionId();
			this.taskId = t.getTaskId();
			this.deviceId = t.getDeviceId();
			this.status = t.getStatus();
			this.createTime = t.getCreateTime();
			this.tenantId = t.getTenantId();
		}
    }
}
