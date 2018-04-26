package com.comba.server.common.data;




import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 业务实体类
 *
 * @作者 sujinxian
 * @创建时间 2018-01-24 16:49:36
 */
@Data
@NoArgsConstructor
public class AppVersion implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
     
    private String id;//
    
     
    private String version;//版本号
    
     
    private String fileName;//文件名
    
     
    private String fileSize;//文件大小
    
     
    private String fileDesc;//文件描述
    
     
    private Date createTime;//创建时间
    
     
    private String path;//上传路径

    private String tenantId;//租户id


    public AppVersion(String id, String version, String fileName, String fileSize, String fileDesc, Date createTime, String path,String tenantId) {
        this.id = id;
        this.version = version;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileDesc = fileDesc;
        this.createTime = createTime;
        this.path = path;
        this.tenantId = tenantId;
    }
}
