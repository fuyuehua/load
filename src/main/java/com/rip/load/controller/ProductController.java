package com.rip.load.controller;


import com.rip.load.pojo.Product;
import com.rip.load.pojo.User;
import com.rip.load.pojo.nativePojo.Result;
import com.rip.load.pojo.nativePojo.UserThreadLocal;
import com.rip.load.service.ProductService;
import com.rip.load.utils.ResultUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

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
}
