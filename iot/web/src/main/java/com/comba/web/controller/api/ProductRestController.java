package com.comba.web.controller.api;

import com.comba.server.common.data.device.Category;
import com.comba.server.common.data.device.Product;
import com.comba.server.common.data.web.utils.UUIDUtils;
import com.comba.server.dao.device.CategoryService;
import com.comba.server.dao.device.ProductService;
import com.comba.server.exception.IoTErrorCode;
import com.comba.server.common.data.rest.ProductRest;
import com.comba.server.common.msg.rest.ResponseEntity;
import com.comba.web.security.jwt.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * ProductRestController
 * 产品开放接口
 * @author maozhihui
 * @create 2017-11-22 14:27
 **/
@Slf4j
@RestController
@RequestMapping("/api/v1")
public class ProductRestController {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private JwtTokenUtil tokenUtil;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping(value = "/product",produces = "application/json")
    public ResponseEntity addProduct(@RequestBody ProductRest reqBody,
                                     @RequestHeader(value = "X-Authorization") String token){
        token = tokenUtil.removeHeader(token);
        if (!tokenUtil.validateToken(token)){
            log.error("token auth failed.",token);
            return new ResponseEntity<>(IoTErrorCode.token_invalid.getErrorCode(),"token is invalid.","{}");
        }
        String tenantId = tokenUtil.getIdFromToken(token);
        if (StringUtils.isBlank(tenantId))
            return new ResponseEntity<>(IoTErrorCode.bad_request_param.getErrorCode(),"tenantId is invalid.","{}");

        // 验证类型编码的合法性
        List<Category> categories = categoryService.findByCode(reqBody.getTypeCode());
        if (categories.isEmpty()){
            log.error("request body typeCode [{}] not found.",reqBody.getTypeCode());
            return new ResponseEntity<>(IoTErrorCode.item_not_found.getErrorCode(),"request body typeCode not exists.","{}");
        }
        Category category = categories.get(0);
        Product product = new Product();
        product.setTenantId(tenantId);
        product.setCategoryId(category.getCategoryId());
        product.setCategoryName(category.getName());
        product.setType(2);
        product.setName(reqBody.getName());
        product.setModel(reqBody.getMode());
        product.setAccessProtocol(reqBody.getAccessType());
        product.setBrief(reqBody.getDescription());
        product.setCreateTime(new Date());

        product = productService.save(product);
        StringBuilder data = new StringBuilder("{");
        data.append("id:").append("\"").append(product.getProductId())
                .append("\"}");
        return new ResponseEntity<>(IoTErrorCode.SUCCESS.getErrorCode(),"success",data.toString());
    }

    @DeleteMapping(value = "/product/{pid}",produces = "application/json")
    public ResponseEntity deleteProduct(@PathVariable(value = "pid") String pid,
                                        @RequestHeader(value = "X-Authorization") String token){
        token = tokenUtil.removeHeader(token);
        if (!tokenUtil.validateToken(token)){
            log.error("token auth failed.",token);
            return new ResponseEntity<>(IoTErrorCode.token_invalid.getErrorCode(),"token is invalid.","{}");
        }
        productService.delete(pid);
        return new ResponseEntity<>(IoTErrorCode.SUCCESS.getErrorCode(),"success","{}");
    }

    @PutMapping(value = "/product/{pid}",produces = "application/json")
    public ResponseEntity updateProduct(@PathVariable(value = "pid") String pid,
                                        @RequestBody ProductRest reqBody,
                                        @RequestHeader(value = "X-Authorization") String token){
        token = tokenUtil.removeHeader(token);
        if (!tokenUtil.validateToken(token)){
            log.error("token auth failed.",token);
            return new ResponseEntity<>(IoTErrorCode.token_invalid.getErrorCode(),"token is invalid.","{}");
        }

        List<Product> products = productService.findByIds(Arrays.asList(pid));
        if (products.isEmpty()){
            log.error("product id [{}] is not exists.",pid);
            return new ResponseEntity<>(IoTErrorCode.bad_request_param.getErrorCode(),"pid is not exists.","{}");
        }

        Product product = products.get(0);
        product.setName(reqBody.getName());
        product.setModel(reqBody.getMode());
        product.setBrief(reqBody.getDescription());
        product.setUpdateTime(new Date());
        productService.save(product);
        return new ResponseEntity<>(IoTErrorCode.SUCCESS.getErrorCode(),"success","{}");
    }

    @GetMapping(value = "/products",produces = "application/json")
    public ResponseEntity getProducts(@RequestHeader(value = "X-Authorization") String token){
        token = tokenUtil.removeHeader(token);
        if (!tokenUtil.validateToken(token)){
            log.error("token auth failed.",token);
            return new ResponseEntity<>(IoTErrorCode.token_invalid.getErrorCode(),"token is invalid.","{}");
        }
        String tenantId = tokenUtil.getIdFromToken(token);
        if (StringUtils.isBlank(tenantId))
            return new ResponseEntity<>(IoTErrorCode.bad_request_param.getErrorCode(),"tenantId is invalid.","{}");
        List<Object[]> res = productService.findAllByTenantId(tenantId);
        List<ProductRest> data = new ArrayList<>();
        for (Object[] row : res){
            ProductRest rest = new ProductRest();
            rest.setId((String) row[0]);
            rest.setName((String) row[1]);
            rest.setTypeCode((String) row[2]);
            rest.setMode((String) row[3]);
            rest.setAccessType((String) row[4]);
            rest.setDescription((String) row[5]);
            rest.setCreateTime(DATE_FORMAT.format((Date) row[6]));
            data.add(rest);
        }
        return new ResponseEntity<>(IoTErrorCode.SUCCESS.getErrorCode(),"success",data);
    }
}
