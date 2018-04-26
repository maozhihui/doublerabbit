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
public class ProductId extends UUIDBased implements EntityId {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5208085547184901020L;
	@JsonCreator
	public ProductId(@JsonProperty("id")UUID id){
		super(id);
	}
	
	@JsonIgnore
	@Override
	public EntityType getEntityType() {
		// TODO Auto-generated method stub
		return EntityType.PRODUCT;
	}
	 public static ProductId fromString(String productId) {
	        return new ProductId(UUID.fromString(productId));
	    }
}
