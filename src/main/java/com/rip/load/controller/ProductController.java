package com.rip.load.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
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
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxh
 * @since 2019-05-09
 */
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
            @RequestBody Product product) {

        User user = UserThreadLocal.get();
        product.setUserId(user.getId());

        boolean b = productService.insert(product);
        if (b) {
            return new ResultUtil<Object>().set();
        } else {
            return new ResultUtil<Object>().setErrorMsg("储存错误");
        }
    }

    @ApiOperation(value = "查看渠道商的产品")
    @GetMapping("/listByDistributorId")
    public Result<List<Product>>  listByDistributorId(@ApiParam(value = "渠道商ID")
                                                @RequestParam
                                                           int id){
        List<Product> list = productService.selectList(new EntityWrapper<Product>().eq("user_id", id));
        for (Product product: list) {
            Config config = configService.selectById(product.getConfigId());
            product.setConfig(config);
            Risk risk = riskService.selectById(product.getRiskId());
            product.setRisk(risk);
        }
        return new ResultUtil<List<Product>>().setData(list);
    }

    @ApiOperation(value = "修改一个贷款产品的基本信息")
    @PostMapping("/update")
    public Result<Object> update(
            @ApiParam(value = "贷款产品实体类")
            @RequestBody Product product) {

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
