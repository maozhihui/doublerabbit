package com.comba.web.controller.device;

import com.comba.server.common.data.device.Category;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.common.data.web.utils.Constants;
import com.comba.server.dao.common.utils.StringUtils;
import com.comba.server.dao.device.CategoryService;
import com.comba.server.dao.device.ProductService;
import com.comba.server.dao.device.template.DeviceTemplateService;
import com.comba.web.common.annotation.SystemControllerLog;
import com.comba.web.common.export.ExportUtils;
import com.comba.web.response.ResponseBean;
import com.comba.web.ui.CategoryTreeNode;
import com.comba.web.ui.TreeGridBuilder;
import com.comba.web.ui.TreeNode;
import com.comba.web.utils.CommonProperties;
import com.comba.web.utils.GenerateCodeUtils;
import org.hibernate.internal.util.StringHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;


/**
 * 设备类别管理
 * @author wengzhonghui
 *
 */
@Controller
@RequestMapping("/category")
public class CategoryController {
	
	@Resource
	private CategoryService categoryService;

	@Resource
	private ProductService productService;

	@Resource
	private DeviceTemplateService deviceTemplateService;
	
	@RequestMapping("list")
	 public String list( Model model)throws Exception {
		
		return "device/category/category_list";
	 }
	
	@RequestMapping("category_edit")
	public String edit(@RequestParam Map<String,Object> parameterMap, Model model) throws Exception{
		String categoryId= StringUtils.getString(parameterMap.get("categoryId"));
		String parentId = StringUtils.getString(parameterMap.get("parentId"));
		Category category = null;
		if(StringHelper.isNotEmpty(categoryId)){
			category = categoryService.getOne(categoryId);
		}
		model.addAttribute("category", category);
		model.addAttribute("parentId", parentId);
		return "device/category/category_edit";
	}

	/**
	 * 查询设备类别数据
	 *
	 * @param category
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/datasByPage")
	public Page getAuditDataByPage(Category category, Page page)throws Exception{
		page = page == null ? new Page() : page;
		List<String> orderBysList = page.getOrderBys();
		return categoryService.pagedQuery(page.getPageNo(), page.getPageSize(), category, orderBysList);
	}

	@RequestMapping("category_view")
	public String check(String categoryId, Model model) throws Exception{
		Category category = null;
		Category categoryParent = null;
		if(StringHelper.isNotEmpty(categoryId)){
			category =categoryService.getOne(categoryId);
			String categoryParentId = category.getParentId();
			if(categoryParentId != null && categoryParentId.length()>0 ){
				categoryParent = categoryService.getOne(categoryParentId);
			}
		}
		model.addAttribute("category", category);
		model.addAttribute("categoryParent", categoryParent);
		return "device/category/category_view";
	}
	
	@RequestMapping(value="addCategory",method=RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog()
	public ResponseBean addCategory(Category category,HttpServletRequest request) throws Exception{
		ResponseBean responseBean = new ResponseBean();
		if(StringHelper.isEmpty(category.getName())){
			responseBean.setFlag_fail();
			responseBean.setMessage("添加设备类别失败，名称不能为空");
			return responseBean;
		}

        if(categoryService.countByName(category.getName()) > 0){
            responseBean.setFlag_fail();
            responseBean.setMessage("该设备类别已存在");
            return responseBean;
        }

        String parentCode = "";
		if (category.getParentId() != null) {
			Category parentRole = categoryService.getOne(category.getParentId());
			if (parentRole != null && parentRole.getLevelFlag() != null) {
				if (parentRole.getLevelFlag() == 4) {
					responseBean.setFlag_fail();
					responseBean.setMessage("添加的设备类别级数超过了4级");
					return responseBean;
				}
				parentCode = parentRole.getCode();
				category.setLevelFlag(parentRole.getLevelFlag() + 1);
			} else {
				category.setLevelFlag(1);
				category.setParentId("-1");
			}
		}

		// add by mzh 2017-11-01 生成类别编码
		if (category.getParentId() != null){
			Optional<String> lastCode = categoryService.findLastCodeByParentId(category.getParentId());
			String code = GenerateCodeUtils.generateCategoryCode(lastCode,parentCode);
			category.setCode(code);
		}

		categoryService.save(category);
		request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "添加设备类别【"+category.getName()+"】");
		responseBean.setFlag_success();
		return responseBean;
	}
	
	@RequestMapping(value="updateCategory",method=RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "更新设备类别")
	public ResponseBean updateCategory(Category category,HttpServletRequest request) throws Exception{
		ResponseBean responseBean = new ResponseBean();
		if(StringHelper.isEmpty(category.getName())||
                StringHelper.isEmpty(category.getCategoryId())){
			responseBean.setFlag_fail();
			responseBean.setMessage("添加设备类别失败，名称和Id不能为空");
			return responseBean;
		}
		if(category.getParentId()!=null){
			Category parentRole = categoryService.getOne(category.getParentId());
			if (parentRole!=null && parentRole.getLevelFlag()!=null) {
			    if (parentRole.getLevelFlag() == 4){
                    responseBean.setFlag_fail();
                    responseBean.setMessage("添加的设备类别级数超过了4级");
                    return responseBean;
                }
                category.setLevelFlag(parentRole.getLevelFlag() + 1);
			}

		}
		categoryService.save(category);
		request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "更新设备类别【"+category.getName()+"】");
		responseBean.setFlag_success();
		return responseBean;
	}
	
	@PostMapping("deleteCategory")
	@SystemControllerLog()
	@ResponseBody
	public  ResponseBean deleteOrg(@RequestParam(value = "categoryId") String categoryId
			,HttpServletRequest request) throws Exception{
		ResponseBean responseBean = new ResponseBean();
		Category category = categoryService.getOne(categoryId);

		if (category == null) {
			responseBean.setFlag_fail();
			responseBean.setMessage("没有该设备类别，无法删除");
			return responseBean;
		}

		if (productService.countByCategoryId(categoryId) > 0){
			responseBean.setFlag_fail();
			responseBean.setMessage("该设备类别有关联的设备产品，无法删除");
			return responseBean;
		}

		if(deviceTemplateService.countByCategoryId(categoryId) > 0){
			responseBean.setFlag_fail();
			responseBean.setMessage("该设备类别有关联的设备模板，无法删除");
			return responseBean;
		}
		request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "删除设备类别【" + category.getName() + "】");
		categoryService.delete(categoryId);
		responseBean.setFlag_success();
		return responseBean;
	}
	
	@RequestMapping(value="/validateCode",method=RequestMethod.POST)
	@ResponseBody
	public  int validateCode( String code,String id) throws Exception{
		
		if(StringHelper.isNotEmpty(code)){
			List<Category> categoryList = categoryService.findByCode(code);
			if(categoryList.size()>0){
				Category category = categoryList.get(0);
				if(category!=null && !category.getCategoryId().equalsIgnoreCase(id)) return -1;
			}
		}
		return 1;	
	}
	
	/**
	 * 获取设备类别树形列表数据
	 * @param category
	 * @param isExpand
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
    @RequestMapping( value = "/getTreeData")
    public List<TreeNode> getTreeData(Category category,String isExpand) throws Exception{
		 
        List<Category> datas = this.categoryService.findByCategory(category);
        if(datas==null) return null;
        List<TreeNode> nodes = new ArrayList<TreeNode>();
        if(datas!=null && datas.size()>0){ 
        	for(Category categoryT:datas){
        		TreeNode node = new TreeNode();
        		node.setId(categoryT.getCategoryId());
        		node.setText(categoryT.getName());
        		node.setParentId(categoryT.getParentId());
        		nodes.add(node);
        	}
        }
        List<TreeNode> treeDatas = TreeNode.buildTree(nodes);
        TreeNode.setNodeIcons(treeDatas, "category", 0);
    	return treeDatas;
    }
	
	/**
	 * 获取设备类别树形列表数据
	 * @param category
	 * @param isExpand
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
    @RequestMapping( value = "/getTreeGridData")
    public List<CategoryTreeNode> getTreeGridData(Category category,String isExpand) throws Exception{
		 
        List<Category> data = this.categoryService.findByCategory(category);
        if(data==null){
        	return null;
        }
        List<CategoryTreeNode> list = new TreeGridBuilder<Category, CategoryTreeNode>(data).build(new CategoryComparator(),
                new CategoryTreeNode(), false);
        
        expandNoses(list, isExpand);
    	TreeNode.setNodeIcons(list, "category", 0);
    	return list;
    }
	
	 @ResponseBody
	 @RequestMapping("/datasByExport")
	 public ResponseBean getAuditDataByPageExport(Category category,String isExpand
			 ,String exportFlag,HttpSession session,@RequestParam Map<String, Object> parameterMap) throws Exception{
		Page dataPage = new Page();
		dataPage.setPageSize(CommonProperties.maxAllowExportNum);
		List<CategoryTreeNode> treeDatas = getTreeGridData(category, isExpand);
		List<CategoryTreeNode> datas = new ArrayList<CategoryTreeNode>();
		TreeNode.treeToList(treeDatas, datas);
		dataPage.setResult(datas);
		return ExportUtils.exportDatasByExcel(dataPage, exportFlag, session, parameterMap);
	 }
	 
	
	/**
	 * 打开树结点
	 * @param list
	 * @param isExpand
	 */
	public void expandNoses(List<CategoryTreeNode> list,String isExpand){
		if ("1".equals(isExpand)) {
        	TreeNode.expandAllNodes(list);
        }else{
        	 TreeNode.expandNodesByDepth(list,4);
        }
	}
	
	
	 private class CategoryComparator implements Comparator<Category> {
        @Override
        public int compare(Category o1, Category o2) {
            if (o1.getLevelFlag().intValue() == o2.getLevelFlag().intValue()) {
                return -1;
            } else {
                return o1.getLevelFlag() - o2.getLevelFlag();
            }
        }
    }


	/**
	 * 校验设备类别名称
	 *
	 * @param categoryId 设备类别ID
	 * @param name 名称
	 * @return
	 */
	@PostMapping("/validateCategoryName")
	@ResponseBody
	public ResponseBean validateCategoryName(String categoryId,String name){
		ResponseBean response = new ResponseBean(ResponseBean.FAIL);

		if (StringHelper.isNotEmpty(categoryId)){
			Category categoryDB = categoryService.getOne(categoryId);
			if (!categoryDB.getName().equals(name) && categoryService.countByName(name) > 0){
				response.setMessage("该设备类别已存在");
				return response;
			}
		}else if(categoryService.countByName(name) > 0){
			response.setMessage("该设备类别已存在");
			return response;
		}

		response.setFlag_success();
		return response;
	}
}
