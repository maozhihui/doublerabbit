package com.comba.web.controller.device;

import com.comba.server.common.data.device.Category;
import com.comba.server.common.data.device.Product;
import com.comba.server.common.data.product.ProductTypeEnum;
import com.comba.server.common.data.web.page.Page;
import com.comba.server.common.data.web.utils.Constants;
import com.comba.server.dao.common.utils.StringUtils;
import com.comba.server.dao.device.*;
import com.comba.server.dao.device.alarm.ActiveAlarmService;
import com.comba.server.dao.rule.RuleJpaService;
import com.comba.web.common.annotation.SystemControllerLog;
import com.comba.web.common.export.ExportUtils;
import com.comba.web.controller.BaseController;
import com.comba.web.response.ResponseBean;
import com.comba.web.security.SpringSecurityUtils;
import com.comba.web.utils.CommonProperties;
import com.google.common.collect.Lists;
import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 产品管理
 * @author wengzhonghui
 *
 */
@Controller
@RequestMapping("/product")
public class ProductController extends BaseController{
	@Resource
	private ProductService productService;
	@Resource
	private DeviceService deviceService;
	@Resource
	private RuleJpaService ruleService;
	@Resource
	private HistoryDataService historyDataService;
	@Resource
    private ActiveAlarmService activeAlarmService;

	@Autowired
	private CategoryService categoryService;

	/**
	 * 产品首页
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model,String productId,HttpSession session) {
		Product product = null;
		if (productId != null && productId.length() > 0) {
			product = productService.getOne(productId);
		}else if (session.getAttribute(Constants.CUR_PRODUCT_ID) != null){
			product = productService.getOne(session.getAttribute(Constants.CUR_PRODUCT_ID).toString());
		}

		if (product == null) {
			List<Product> list = productService.findByTenantId(SpringSecurityUtils.getCurrentUserTenantId());
			if (list != null && list.size() > 0) {
				product = list.get(0);
			}else{
				model.addAttribute("product", product);
				return "product/product_index";
			}
		}
		productId = product.getProductId();
		productId = productId==null?"-1":productId;
		session.removeAttribute(Constants.CUR_PRODUCT_ID);
		session.setAttribute(Constants.CUR_PRODUCT_ID, productId);
		product = product==null?new Product():product;
		model.addAttribute("product", product);
		return "product/product_index";
	}
	
	/**
	 * 产品首页统计
	 * 
	 * @author wengzhonghui 2017年7月3日
	 * @return devNum_notGateWay:非网关设备的数量，devNum_gateWay：网关设备数量 ,userNum:用户数统计,ruleNum:规则数统计
	 * @throws Exception
	 */
	@RequestMapping(value="product_statis")
	@ResponseBody
	public Map<String,Object> product_statis(String productId) throws Exception{
		//设备数量统计
		Map<String,Object> staticsDatas = deviceService.deviceStatisByIsGateWayOfProduct(productId);
		staticsDatas = staticsDatas==null?new HashMap<>():staticsDatas;

		//规则数统计
        Long ruleNum = activeAlarmService.countByProductId(productId);
        staticsDatas.put("ruleNum", ruleNum);
		return staticsDatas;
	}

    /**
     * 统计产品的遥测数据
     *
     * @param productId 产品id
     * @return
     */
    @RequestMapping(value="productDeviceData")
    @ResponseBody
	public Map<String,Object> productDeviceData(String productId){
	    Map<String,Object> datas = new HashMap<>();
        Long dataNum = historyDataService.countByProductId(productId);
        datas.put("dataNum", dataNum);
        return datas;
    }
	
	@RequestMapping("/product_edit")
	public String edit(String productId, Model model) {
		Product product = null;
		if(StringHelper.isNotEmpty(productId)){
			product = productService.getOne(productId);
			if (product != null && StringHelper.isNotEmpty(product.getCategoryId())){
				Category category = categoryService.getOne(product.getCategoryId());
				if (category.getParentId() != null){
					Category parentCategory = categoryService.getOne(category.getParentId());
					model.addAttribute("parentCategory",parentCategory);
				}
				model.addAttribute("category",category);
			}
		}
		model.addAttribute("product", product);
		return "product/product_edit";
	}
	
	@RequestMapping("/product_list")
	public String list() {
		return "product/product_list";
	}
	
		
	@RequestMapping("/product_view")
	public String userCheck(String productId, Model model) throws Exception{
		Product product = null;
		if(StringHelper.isNotEmpty(productId)){
			product = productService.getOne(productId);
		}
		model.addAttribute("product", product);
		
		return "product/product_view";
	}
	 
	 @ResponseBody
	 @RequestMapping("/datasByPage")
	 public Page getAuditDataByPage(Product product,Page page)throws Exception{
		 page = page==null?new Page():page;
		 Page returnPage = null;
		 page.setPageSize(CommonProperties.maxAllowExportNum);//取全部
		 List<String> orderBysList = page.getOrderBys();
		String tenantId = SpringSecurityUtils.getCurrentUserTenantId();
		if (StringHelper.isNotEmpty(tenantId)){
			product.setTenantId(tenantId);
		}
		 returnPage = productService.pagedQuery(page.getPageNo(), page.getPageSize(), product,orderBysList);
		 return returnPage;
	 }
	
	
	@ResponseBody
	 @RequestMapping("/datasByExport")
	 public ResponseBean  datasByExport(Product product,Page page,String exportFlag,HttpSession session,@RequestParam Map<String, Object> parameterMap
			 )throws Exception{
		page.setPageSize(CommonProperties.maxAllowExportNum);
		Page dataPage = getAuditDataByPage(product, page);
		return ExportUtils.exportDatasByExcel(dataPage, exportFlag, session, parameterMap);
	 }
	
	
	
	
	@RequestMapping(value="addProduct",method=RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "添加产品")
	public ResponseBean addProduct(Product product,HttpServletRequest request) throws Exception{
		ResponseBean responseBean = new ResponseBean(ResponseBean.FAIL);
		Product productDB = productService.findByNameAndTenantId(product.getName(), SpringSecurityUtils.getCurrentUserTenantId());
		if (productDB != null) {
			responseBean.setMessage("产品名称已存在");
			return responseBean;
		}

		product.setCreateTime(new Date());
		product.setUpdateTime(new Date());
		product.setTenantId(SpringSecurityUtils.getCurrentUserTenantId());
		product.setType(ProductTypeEnum.USER_PRODUCT.getType());
		productService.save(product);
		request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "添加产品【" + product.getName() + "】");
		responseBean.setFlag_success();
		return responseBean;
		
	}
	
	@RequestMapping(value="updateProduct",method=RequestMethod.POST)
	@ResponseBody
	@SystemControllerLog(description = "更新产品")
	public ResponseBean updateProduct(Product product,HttpServletRequest request) throws Exception{
		ResponseBean responseBean = new ResponseBean(ResponseBean.FAIL);
		product.setUpdateTime(new Date());
		if (product.getType() == null ){
			product.setType(ProductTypeEnum.USER_PRODUCT.getType());
		}

		Product productDB = productService.getOne(product.getProductId());
		if (productDB == null){
		    responseBean.setMessage("产品不存在");
		    return responseBean;
        }

        product.setCreateTime(productDB.getCreateTime());

		if (!product.getName().equals(productDB.getName())) {
			Product productCount = productService.findByNameAndTenantId(product.getName(), SpringSecurityUtils.getCurrentUserTenantId());
			if (productCount != null) {
				responseBean.setMessage("产品名称已存在");
				return responseBean;
			}
		}

		productService.save(product);
		product.setTenantId(SpringSecurityUtils.getCurrentUserTenantId());
		request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "添加产品【"+product.getName()+"】");
		if(product!=null && product.getName()!=null){
			request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "修改产品信息"+product.getName()+"】");
		}
		responseBean.setFlag_success();
		return responseBean;
	}
	@RequestMapping(value="deleteProduct",method=RequestMethod.POST)
	@SystemControllerLog(description = "删除产品")
	public @ResponseBody  ResponseBean deleteGuser(@RequestParam(value = "ids[]")  String[] productIds
			,HttpServletRequest request) throws Exception{
		ResponseBean responseBean = new ResponseBean();

		List<String> productIdList = Lists.newArrayList(productIds);

        StringBuilder logDesc = new StringBuilder();
		List<Product> userList = productService.findByIds(productIdList);
		if(userList!=null && userList.size()>0){
			for(Product product : userList){
				if(product!=null && product.getName()!=null){
					if(logDesc.length()>0) logDesc.append(",");
					logDesc.append(product.getName());
				}
			}
			request.setAttribute(Constants.AOP_LOG_DESCRIPTION, "删除产品【"+logDesc.toString()+"】");
		}
		productService.deleteByIds(productIdList);
		responseBean.setFlag_success();
		return responseBean;
	}
	
}
