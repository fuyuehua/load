package com.rip.load.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.rip.load.pojo.Config;
import com.rip.load.pojo.Product;
import com.rip.load.pojo.Risk;
import com.rip.load.pojo.User;
import com.rip.load.pojo.nativePojo.Result;
import com.rip.load.pojo.nativePojo.UserThreadLocal;
import com.rip.load.service.ConfigService;
import com.rip.load.service.ProductService;
import com.rip.load.service.RiskService;
import com.rip.load.utils.DateUtil;
import com.rip.load.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxh
 * @since 2019-05-09
 */
@Api(tags = {"贷款产品接口"})
@RestController
@RequestMapping("api/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ConfigService configService;
    @Autowired
    private RiskService riskService;

    @ApiOperation(value = "新增一个贷款产品")
    @PostMapping("/add")
    public Result<Object> add(
            @ApiParam(value = "贷款产品实体类")
            @Valid  @RequestBody Product product) {

        User user = UserThreadLocal.get();
        product.setUserId(user.getId());

        boolean b = productService.insert(product);
        if (b) {
            return new ResultUtil<Object>().set();
        } else {
            return new ResultUtil<Object>().setErrorMsg("储存错误");
        }
    }

    @ApiOperation(value = "查看贷款产品")
    @GetMapping("/get")
    public Result<Object> get(
            @ApiParam(value = "产品ID")
            @RequestParam Integer productId) {
        if(productId == null){
            return new ResultUtil<Object>().setErrorMsg("产品ID为空");
        }
        Product product = productService.selectById(productId);
        List<Product> list = new ArrayList<>();
        list.add(product);
        list = productService.settleConfigRisk(list);
        return new ResultUtil<Object>().setData(list.get(0));
    }

    @ApiOperation(value = "查看渠道商的产品")
    @GetMapping("/listByDistributorId")
    public Result<Page<Product>>  listByDistributorId(@ApiParam(value = "渠道商ID")
                                                          @RequestParam int id,
                                                      @ApiParam(value = "想要请求的页码")
                                                      @RequestParam int currentPage,
                                                      @ApiParam(value = "一页显示多少数据")
                                                          @RequestParam int pageSize,
                                                      @ApiParam(value = "产品名称模糊查询")
                                                      @RequestParam String name){
        Page<Product> page = new Page<>(currentPage, pageSize);
        Page<Product> pages = productService.selectPage(page, new EntityWrapper<Product>().eq("user_id", id)
                .like(!StringUtils.isEmpty(name),"name", name));
        List<Product> list = pages.getRecords();
        if(list.size() == 0){
            return new ResultUtil<Page<Product>>().setData(pages);
        }
        list = productService.settleConfigRisk(list);
        pages.setRecords(list);
        return new ResultUtil<Page<Product>>().setData(pages);
    }

    @ApiOperation(value = "修改一个贷款产品的基本信息")
    @PostMapping("/update")
    public Result<Object> update(
            @ApiParam(value = "贷款产品实体类")
            @Valid @RequestBody Product product) {

        product.setConfigId(null);
        product.setRiskId(null);

        boolean b = productService.updateById(product);
        if (b) {
            return new ResultUtil<Object>().set();
        } else {
            return new ResultUtil<Object>().setErrorMsg("储存错误");
        }
    }



}
