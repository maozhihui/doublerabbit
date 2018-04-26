package com.comba.server.dao.common.jpa;
/** 
 * 函数条件构造器 
 *  @author wengzhonghui
 */
public class Projection{
	//函数作用字段
	private String col;
	//函数类型
	private Criterion.Projection type;
	
	public Projection(String col, Criterion.Projection type){
		this.col = col;
		this.type = type;
	}

	public String getCol() {
		return col;
	}

	public Criterion.Projection getType() {
		return type;
	}
	
}
