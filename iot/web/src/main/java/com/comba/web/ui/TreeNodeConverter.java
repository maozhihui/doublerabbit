package com.comba.web.ui;
public interface TreeNodeConverter<T,V extends TreeNode> {

	public V convert(T t);
}