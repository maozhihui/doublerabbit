package com.comba.server.common.data;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DeviceToken业务对象
 * @author maozhihui
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceToken implements Serializable {

	private static final long serialVersionUID = -9011004406137883291L;

	private String id;
	private String deviceId;
	private String token;
	private Date tokenCreateTime;
	private Date sessionUpdateTime;
	
	
}
