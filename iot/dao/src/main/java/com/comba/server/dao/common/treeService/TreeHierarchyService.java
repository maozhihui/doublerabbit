package com.comba.server.dao.common.treeService;

import java.util.List;



/**
 * 树形菜单实现
 * @author wengzhonghui
 *
 * @param <E>
 */
public interface TreeHierarchyService<E> {


    /**
     * 根据id获取子数据
     *
     * @param String
     * @return
     * @throws ServiceException
     */
	List<E> getChildrenById( String id) throws Exception;

    /**
     * 根据id获取所有子孙数据
     *
     * @param id
     * @return
     * @throws ServiceException
     */
    List<E> getAllDescendantById( String id) throws Exception;
   

}
