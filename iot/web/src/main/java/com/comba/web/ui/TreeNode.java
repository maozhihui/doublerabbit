package com.comba.web.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;




/**
 * 树形树点
 * @author wengzhonghui
 *
 */
public class TreeNode {
	
	private static final Logger logger = Logger.getLogger(TreeNode.class);  
	
	private String id;
	private String text;
	private String iconCls;
	// open or closed
	private String state;
	private boolean checked;
	private Map<String, Object> attributes = new HashMap<String, Object>();
	private List<TreeNode> children = new ArrayList<TreeNode>();
	private String parentId;
	private Integer depth;
	public final static String STATE_OPEN = "open";
	public final static String STATE_CLOSE = "closed";

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	/**
	 * @see STATE_OPEN
	 * @see STATE_CLOSE
	 * @param state
	 */
	public void setState(String state) {
		this.state = state;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}
	
	
	  
    public Integer getDepth() {
		return depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}
	//添加孩子的方法  
	public void addChild(TreeNode node){  
       if(this.children == null){  
           children= new ArrayList<TreeNode>();  
           children.add(node);  
       }else{  
           children.add(node);  
       }  
    }  
     
	
	
	public static void expandNodes(List<? extends TreeNode> list) {
        for (TreeNode node : list) {
            if (!node.getChildren().isEmpty()) {
                node.setState(TreeNode.STATE_OPEN);
            }else{
            	node.setState(TreeNode.STATE_CLOSE);
            }
            expandNodes(node.getChildren());
        }
    }
	
	/**
	 * 根据深度，展开树
	 * @param list
	 */
	public static void expandNodesByDepth(List<? extends TreeNode> list,int depth) {
        for (TreeNode node : list) {
            if(node.getChildren().isEmpty()){
            	node.setState(TreeNode.STATE_OPEN);
            }else if (node.getDepth()!=null && node.getDepth()<=depth) {
                node.setState(TreeNode.STATE_OPEN);
            }else{
            	node.setState(TreeNode.STATE_CLOSE);
            }
            expandNodesByDepth(node.getChildren(),depth);
        }
    }
	
	/**
	 * 展开所有结点
	 * @param list
	 */
	public static void expandAllNodes(List<? extends TreeNode> list) {
        for (TreeNode node : list) {
        	node.setState(TreeNode.STATE_OPEN);
            expandAllNodes(node.getChildren());
        }
    }
	
	/**
	 * 从整个树中截取出当前用户拥有权限的子树
	 * @param list　整个树
	 * @param authIdMap 拥有的树ID
	 * @param authTreeNodes 拥有的子树放到这里
	 */
	public static void getAuthNodes(List<? extends TreeNode> list,Map<Integer,String> authIdMap
			,List authTreeNodes) {
		for (TreeNode node : list) {
			if(authIdMap.containsKey(node.getId())){
				authTreeNodes.add(node);
			}else{
				getAuthNodes(node.getChildren(),authIdMap, authTreeNodes);
			}
        }
    }
	
	/**
	 * 把树形数据模型转成List 
	 * @param list
	 * @param listNodes
	 */
	public static void treeToList(List<? extends TreeNode> list,List listNodes) {
		for (TreeNode node : list) {
			listNodes.add(node);
			if(node.getChildren().size()>0){
				treeToList(node.getChildren(), listNodes);
			}
        }
    }
	
	/**
	 * 让树形结点选中
	 * @param list
	 */
	public static void checkedNodes(List<? extends TreeNode> list,Map<Integer,String> checkMap) {
        for (TreeNode node : list) {
        	if(checkMap!=null && checkMap.containsKey(node.getId())){
        		node.setChecked(true);
        	}
            if (!node.getChildren().isEmpty()) {
                node.setState(TreeNode.STATE_OPEN);
            }
            checkedNodes(node.getChildren(),checkMap);
        }
    }
	
	/**
	 * 设置树形标签
	 * @param list
	 * @param type:organization,role,area,pri
	 * @param depth
	 */
	public static void setNodeIcons(List<? extends TreeNode> list,String type,int depth){
		if(type==null || list==null) return;
		depth++;
		for(TreeNode node :list){
			node.setIconCls(getIconsByDepthAndType(depth, type));
			setNodeIcons(node.getChildren(), type, depth);
		}
		
	}
	public static String getIconsByDepthAndType(int depth,String type){
		if(type.equalsIgnoreCase("organization")){
			return getIconsByDepth_organ(depth);
		}else if(type.equalsIgnoreCase("role")){
			return getIconsByDepth_role(depth);
		}else if(type.equalsIgnoreCase("area")){
			return getIconsByDepth_area(depth);
		}else if(type.equalsIgnoreCase("pri")){
			return getIconsByDepth_pri(depth);
		}else if(type.equalsIgnoreCase("p")){
			return getIconsByDepth_organ(depth);
		}
		return "";
	}
	
	/**
	 * 获取机构图标
	 * @param depth
	 * @return
	 */
	public static String getIconsByDepth_organ(int depth){
		if(depth>5) depth=5;
		return "tree-building" + depth;
	}
	/**获取角色图标
	 * @param depth
	 * @return
	 */
	public static String getIconsByDepth_role(int depth){
		if(depth>5) depth=5;
		return "tree-role" + depth;
	}
	/**获取区域图标
	 * @param depth
	 * @return
	 */
	public static String getIconsByDepth_area(int depth){
		if(depth>5) depth=5;
		return "tree-place" + depth;
	}
	public static String getIconsByDepth_pri(int depth){
		if(depth>5) depth=5;
		return "tree-pri" + depth;
	}
	
	 /**
	  * 构建树形结构数据
	 * @param nodeList 把对象转成的treeNode结构
	 * @return
	 */
	public static List<TreeNode> buildTree(List<TreeNode> nodeList){  
		  
       List<TreeNode> list = new ArrayList<TreeNode>();  
       Map<String, TreeNode> map = new HashMap<String, TreeNode>();  
       try {  
           for (TreeNode nodeT : nodeList) {  
               map.put(nodeT.getId(), nodeT);  
           }  
  
           for (TreeNode nodeT : nodeList) {  
        	   
        	   String pId = nodeT.getParentId();
        	   if(pId!=null && map.containsKey(pId)){
        		   TreeNode pnode = (TreeNode)map.get(pId);  
                   pnode.addChild(nodeT);  
        	   }else{
        		   list.add(nodeT);
        	   }
           }           
       }catch (Exception e) {  
           e.printStackTrace();  
           logger.error(e.getMessage(),e);
       }  
        return list;  
    }
	
	
}
