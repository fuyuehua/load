package com.rip.load.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.rip.load.pojo.Order;
import com.rip.load.pojo.User;
import com.rip.load.pojo.nativePojo.Result;
import com.rip.load.pojo.nativePojo.UserThreadLocal;
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

import java.math.BigDecimal;

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

    @ApiOperation("初审人员审核")
    @GetMapping("/firstCheck")
    public Result<Object> firstCheck(
            @ApiParam("1:通过 0：不通过 2:资料待完善")
            @RequestParam int result,
            @ApiParam("填驳回原因")
            String reason,
            @ApiParam("订单ID")
            @RequestParam int orderId){
        if(orderId == 0){
            return new ResultUtil<Object>().setErrorMsg("订单ID没有");
        }
        Order order = orderService.selectById(orderId);
        if(order == null){
            return new ResultUtil<Object>().setErrorMsg("订单不存在");
        }
        User user = UserThreadLocal.get();
        order.setFirstRejectMan(user.getId());
        if(result == 1){
            order.setStatus(1);
        }else if(result == 0){
            order.setStatus(3);
            order.setFirstRejectReason(reason);
        }else if(result == 2){
            order.setStatus(2);
        }
        boolean insert = orderService.insert(order);
        if (insert) {
            return new ResultUtil<Object>().set();
        } else {
            return new ResultUtil<Object>().setErrorMsg("数据库错误");
        }
    }


    @ApiOperation("复审人员审核")
    @GetMapping("/secondCheck")
    public Result<Object> secondCheck(
            @ApiParam("1:通过 0：不通过") @RequestParam int result,
            @ApiParam("填驳回原因") String reason,
            @ApiParam("订单ID") @RequestParam int orderId,
            @ApiParam("允许放贷多少钱") String money
            ){
        if(orderId == 0){
            return new ResultUtil<Object>().setErrorMsg("订单ID没有");
        }
        Order order = orderService.selectById(orderId);
        if(order == null){
            return new ResultUtil<Object>().setErrorMsg("订单不存在");
        }
        User user = UserThreadLocal.get();
        order.setSecondRejectMan(user.getId());
        if(result == 1){
            order.setStatus(4);
            order.setBorrowMoney(new BigDecimal(money));
        }else if(result == 0){
            order.setStatus(5);
            order.setFirstRejectReason(reason);
        }
        String resultStr = orderService.createOrderAndRepayPlan(order);
        if (resultStr.equals("1")) {
            return new ResultUtil<Object>().set();
        } else {
            return new ResultUtil<Object>().setErrorMsg(resultStr);
        }
    }

}
