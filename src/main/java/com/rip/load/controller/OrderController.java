package com.rip.load.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.rip.load.pojo.Order;
import com.rip.load.pojo.nativePojo.Result;
import com.rip.load.service.OrderService;
import com.rip.load.utils.ResultUtil;
import com.sun.org.apache.xpath.internal.operations.Or;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxh
 * @since 2019-03-29
 */
@Api(tags = {"订单控制"})
@RestController
@RequestMapping("api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation("生成订单")
    @GetMapping("/add")
    public Result<Object> add(
            @ApiParam("客户Id")
            @RequestParam Integer userId,
            @ApiParam("产品Id")
            @RequestParam Integer productId
            ){
        Order order = new Order();
        order.setUid(userId);
        order.setProductId(productId);

        boolean b = orderService.insert(order);
        if (b) {
            return new ResultUtil<Object>().setData(order);
        } else {
            return new ResultUtil<Object>().setErrorMsg("数据库错误");
        }
    }

    @ApiOperation("查看订单")
    @GetMapping("/getAllInPage")
    public Result<Page<Order>> getAllInPage(
            @ApiParam(value = "想要请求的页码")
            @RequestParam int currentPage,
            @ApiParam(value = "一页显示多少数据")
            @RequestParam int pageSize,
            @ApiParam("根据订单状态查找")
            @RequestParam(required = false) Integer status
    ){
        Page<Order> page = new Page<>(currentPage, pageSize);
        Page<Order> result = null;
        if(status != null ) {
            result = orderService.selectPage(page, new EntityWrapper<Order>().eq("status", status));
        }else
            result = orderService.selectPage(page);
        return new ResultUtil<Page<Order>>().setData(result);
    }

    @ApiOperation("修改备注和驳回原因")
    @PostMapping("/updateRemark")
    public Result<Object> updateRemark(
            @ApiParam("只填备注和驳回原因")
            @RequestBody Order order
            ){
        if(order.getId() == null || order.getId() == 0 ||
                StringUtils.isEmpty(order.getRemark()) ||
                StringUtils.isEmpty(order.getRejectReason()) )
            return new ResultUtil<Object>().setErrorMsg("参数不足");
        Order order1 = new Order();
        order1.setId(order.getId());
        order1.setRemark(order.getRemark());
        order1.setRejectReason(order.getRejectReason());
        boolean b = orderService.updateById(order1);
        if (b) {
            return new ResultUtil<Object>().set();
        } else {
            return new ResultUtil<Object>().setErrorMsg("数据库错误");
        }
    }




}
