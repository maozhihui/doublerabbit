package com.comba.web.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.comba.server.dao.common.treeService.TreeHierarchyService;





/**
 * 构建树形对象
 * @author wengzhonghui
 *
 * @param <T>
 * @param <V>
 */
public class TreeGridBuilder<T, V extends TreeNode> {

	private static final Logger logger = Logger.getLogger(TreeGridBuilder.class);
    private List<T> list;
    private List<V> result = new ArrayList<V>();

    public TreeGridBuilder(List<T> list) {
        super();
        this.list = list;
    }

    public List<V> build(Comparator<T> comparator, TreeNodeConverter<T, V> converter) {
        return this.build(comparator, converter, true);
    }

    /**
     * 
     * @param comparator
     * @param converter
     * @param fullTree <ul><li>True:所有的节点除了根节点之外，都必须与父节点一并出现或一并隐藏</li> 
     *                 <li>False: 当某一个节点的父节点被过滤隐藏时，此节点将被放置于第一层</li></ul>
     * @return 
     */
    public List<V> build(Comparator<T> comparator, TreeNodeConverter<T, V> converter, boolean fullTree) {
        Collections.sort(this.list, comparator);
        Iterator<T> it = this.list.iterator();
        while (it.hasNext()) {
            T t = it.next();
            V treeNode = converter.convert(t);
            TreeNode parentNode = this.findTreeNode(this.result, treeNode.getParentId());
            if (parentNode != null) {
                parentNode.setState(TreeNode.STATE_CLOSE);
                parentNode.getChildren().add(treeNode);
            } else if ((fullTree && treeNode.getParentId().equals("-1")) || !fullTree) {
                this.result.add(treeNode);
            }
        }

        return this.result;
    }
    
    public List<V> buildAsyncTree(Comparator<T> comparator, TreeNodeConverter<T, V> converter, 
    			TreeHierarchyService<T> treeService, boolean isRootExpanded) {
    	try {
    		  Collections.sort(this.list, comparator);
    	        Iterator<T> it = this.list.iterator();
    	        List<T> children = null;
    	        while (it.hasNext()) {
    	            T t = it.next();
    	            V treeNode = converter.convert(t);
    	            children = treeService.getChildrenById(treeNode.getId());
    	    		if(!isRootExpanded && children != null && children.size()>0) {
    	    			treeNode.setState(TreeNode.STATE_CLOSE);
    	    		}
    	    		else if(isRootExpanded && children!=null && children.size()>0) {  
    	    			treeNode.setState(TreeNode.STATE_OPEN);
    	    			List<T> descendant = null;
    	    			Collections.sort(children, comparator);
    	    			for(T child: children) {
    	    				V childNode = converter.convert(child);
    	    				descendant = treeService.getChildrenById(childNode.getId());
    	    				if(descendant!=null && descendant.size()>0) {
    	    					childNode.setState(TreeNode.STATE_CLOSE);
    	    				}
    	    				treeNode.getChildren().add(childNode);
    	    			}				
    	    		}
    	    		result.add(treeNode);
    	        }
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
        return this.result;
    }

    private TreeNode findTreeNode(List<? extends TreeNode> treeNodes, String id) {
        for (TreeNode node : treeNodes) {
            if (node.getId().equalsIgnoreCase(id)) {
                return node;
            } else if (node.getChildren() != null && !node.getChildren().isEmpty()) {
                TreeNode foundNode = findTreeNode(node.getChildren(), id);
                if (foundNode != null) {
                    return foundNode;
                }
            }
        }

        return null;
    }
}
