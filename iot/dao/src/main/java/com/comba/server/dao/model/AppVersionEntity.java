package com.comba.server.dao.model;

import com.comba.server.common.data.AppVersion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * 实体Bean
 *
 * @作者 sujinxian
 * @创建时间 2018-01-24 16:49:36
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="app_version")
public class AppVersionEntity implements ToData<AppVersion>{

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name="ID")
    private String id;//
    
    @Column(name="VERSION")
    private String version;//版本号
    
    @Column(name="FILE_NAME")
    private String fileName;//文件名
    
    @Column(name="FILE_SIZE")
    private String fileSize;//文件大小
    
    @Column(name="FILE_DESC")
    private String fileDesc;//文件描述
    
    @Column(name="CREATE_TIME")
    private Date createTime;//创建时间
    
    @Column(name="PATH")
    private String path;//上传路径

    @Column(name = "tenant_id")
    private String tenantId;

   
   @Override
   public AppVersion toData() {
	   return new AppVersion( id, version, fileName, fileSize, fileDesc, createTime, path,tenantId);
   }

    public AppVersionEntity (AppVersion t) {
		super();
		if(t!=null){
			this.id = t.getId();
			this.version = t.getVersion();
			this.fileName = t.getFileName();
			this.fileSize = t.getFileSize();
			this.fileDesc = t.getFileDesc();
			this.createTime = t.getCreateTime();
			this.path = t.getPath();
			this.tenantId = t.getTenantId();
		}
    }
}
