package com.comba.license;

import java.util.Date;
import lombok.Data;

/**
 * license文件信息
 * @author huangjinlong
 *
 */

@Data
public class License {
	//开始时间
	private Date startTime;
	//结束时间
	private Date endTime;
	//序列号
	private String serialNumber;
	//合同号
	private String contractNumber;
	//用户数
	private int UserNumber;
	//网元数
	private int NENumber;
}
