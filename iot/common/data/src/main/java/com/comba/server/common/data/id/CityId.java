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
 * 【城市Id】
 *
 * @version 
 * @author xianhongdong  2017年6月13日 下午3:33:07
 * 
 */
public class CityId extends UUIDBased implements EntityId {

	/**
	 * Comments for <code>serialVersionUID</code>
	 * 【请在此输入描述文字】
	 */
	private static final long serialVersionUID = 6861562793508908312L;

	@JsonCreator
	public CityId(@JsonProperty("id") UUID id) {
		super(id);
	}
	public static CityId fromString(String cityId) {
        return new CityId(UUID.fromString(cityId));
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
		return EntityType.CITY;
	}

}
