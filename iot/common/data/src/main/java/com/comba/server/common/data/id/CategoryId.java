/**
 * 
 */
package com.comba.server.common.data.id;

import java.util.UUID;

import com.comba.server.common.data.EntityType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author xianhongdong
 *
 */
public class CategoryId extends UUIDBased implements EntityId{

	@JsonCreator
	public CategoryId(@JsonProperty("id") UUID id){
		super(id);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -797728687191287811L;

	@JsonIgnore
	@Override
	public EntityType getEntityType() {
		// TODO Auto-generated method stub
		return EntityType.CATEGORY;
	}
	 public static CategoryId fromString(String categoryId) {
	        return new CategoryId(UUID.fromString(categoryId));
	    }

}
