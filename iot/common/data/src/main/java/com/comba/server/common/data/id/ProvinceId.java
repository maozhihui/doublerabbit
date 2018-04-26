/**
 * Copyright (C) 2015 Comba Telecom Systems Holdings Ltd. All rights reserved
 *
 * 本代码版权归京信通信系统控股有限公司所有，且受到相关的法律保护。
 * 没有经过版权所有者的书面同意，
 * 任何其他个人或组织均不得以任何形式将本文件或本文件的部分代码用于其他商业用途。
 */
package com.comba.server.common.data.id;

import java.util.UUID;

import com.comba.server.common.data.EntityType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 【省份ID】
 *
 * @version 
 * @author xianhongdong  2017年6月8日 上午11:29:36
 * 
 */
public class ProvinceId extends UUIDBased implements EntityId {

	private static final long serialVersionUID = 1L;
	@JsonCreator
	public ProvinceId(@JsonProperty("id") UUID id) {
		super(id);
	}
	public static ProvinceId fromString(String provinceId) {
        return new ProvinceId(UUID.fromString(provinceId));
    }
	/**
	 * 【请在此输入描述文字】
	 * 
	 * (non-Javadoc)
	 * @see com.comba.server.common.data.id.EntityId#getEntityType()
	 */
	@JsonIgnore
	@Override
	public EntityType getEntityType() {
		// TODO 这是系统自动生成描述，请在此补完后续代码
		return EntityType.PROVINCE;
	}

}
