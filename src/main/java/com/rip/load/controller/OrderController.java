package com.rip.load.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.rip.load.pojo.*;
import com.rip.load.pojo.nativePojo.Result;
import com.rip.load.pojo.nativePojo.UserThreadLocal;
import com.rip.load.service.*;
import com.rip.load.utils.ResultUtil;
import com.sun.org.apache.xpath.internal.operations.Or;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    @Autowired
    private UserCustomerService userCustomerService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserDistributorService userDistributorService;
    @Autowired
    private UserDistributorSubordinateService userDistributorSubordinateService;

    @ApiOperation("生成订单")
    @GetMapping("/add")
    public Result<Object> add(
            @ApiParam("客户Id")
            @RequestParam Integer userId,
            @ApiParam("产品Id")
            @RequestParam Integer productId
            ){
        if(userId == 0){
            return new ResultUtil<Object>().setErrorMsg("用户ID不能为0");
        }
        UserCustomer customer = userCustomerService.selectOne(new EntityWrapper<UserCustomer>().eq("userId", userId));

        if(customer == null){
            return new ResultUtil<Object>().setErrorMsg("该用户不存在");
        }
        if(!customer.getInfoStatus().equals("4"))
            return new ResultUtil<Object>().setErrorMsg("身份证信息未完善");

        Order order = new Order();
        order.setUid(userId);
        order.setProductId(productId);
        order.setRealname(customer.getRealname());
        order.setPhone(customer.getCellphone());
        boolean b = orderService.insert(order);
        if (b) {
            return new ResultUtil<Object>().setData(order);
        } else {
            return new ResultUtil<Object>().setErrorMsg("数据库错误");
        }
    }

    @ApiOperation("查看订单(渠道商)")
    @GetMapping("/getAllInPageByDistributor")
    public Result<Page<Order>> getAllInPageByDistributor(
            @ApiParam(value = "想要请求的页码")
            @RequestParam int currentPage,
            @ApiParam(value = "一页显示多少数据")
            @RequestParam int pageSize,
            @ApiParam("根据订单状态查找 0:待审核，1：通过 2：拒绝")
            @RequestParam Integer status
    ){

        if(status == null){
            return new ResultUtil<Page<Order>>().setErrorMsg("状态为空");
        }
        List<Integer> temp =  new ArrayList<>();
        if(status == 0){
            temp.add(0);
            temp.add(1);
            temp.add(2);
        }else if(status == 1){
            temp.add(4);
        }else if(status == 2){
            temp.add(3);
            temp.add(5);
        } else{
            return new ResultUtil<Page<Order>>().setErrorMsg("参数错误");
        }
        User user = UserThreadLocal.get();
        List<Integer> intList = userService.getCustomer4Distributor(user);
        Page<Order> page = new Page<>(currentPage, pageSize);
        Page<Order> orderPage = orderService.selectPage(page, new EntityWrapper<Order>().in("uid", intList).in("status", temp));
        List<Order> records = orderPage.getRecords();
        List<UserDistributor> distributors = userDistributorService.selectList(new EntityWrapper<UserDistributor>().eq("user_id", user.getId()));
        records = orderService.makeupInfo(records, distributors);
        orderPage.setRecords(records);
        return new ResultUtil<Page<Order>>().setData(orderPage);
    }

    @ApiOperation("查看订单(渠道商下属)")
    @GetMapping("/getAllInPageByDistributorSubordinate")
    public Result<Page<Order>> getAllInPageByDistributorSubordinate(
            @ApiParam(value = "想要请求的页码")
            @RequestParam int currentPage,
            @ApiParam(value = "一页显示多少数据")
            @RequestParam int pageSize,
            @ApiParam("根据订单状态查找 0:待审核，1：通过 2：拒绝")
            @RequestParam Integer status
    ){

        if(status == null){
            return new ResultUtil<Page<Order>>().setErrorMsg("状态为空");
        }
        List<Integer> temp =  new ArrayList<>();
        if(status == 0){
            temp.add(0);
            temp.add(1);
            temp.add(2);
        }else if(status == 1){
            temp.add(4);
        }else if(status == 2){
            temp.add(3);
            temp.add(5);
        } else{
            return new ResultUtil<Page<Order>>().setErrorMsg("参数错误");
        }
        User user = UserThreadLocal.get();
        UserDistributorSubordinate ds = userDistributorSubordinateService.selectOne(new EntityWrapper<UserDistributorSubordinate>().eq("user_id", user.getId()));
        user = new User();
        user.setId(ds.getFatherId());
        List<Integer> intList = userService.getCustomer4Distributor(user);
        Page<Order> page = new Page<>(currentPage, pageSize);
        Page<Order> orderPage = orderService.selectPage(page, new EntityWrapper<Order>().in("uid", intList).in("status", temp));
        List<Order> records = orderPage.getRecords();
        List<UserDistributor> distributors = userDistributorService.selectList(new EntityWrapper<UserDistributor>().eq("user_id", user.getId()));
        records = orderService.makeupInfo(records, distributors);
        orderPage.setRecords(records);
        return new ResultUtil<Page<Order>>().setData(orderPage);
    }

    @ApiOperation("查看订单(平台商)")
    @GetMapping("/getAllInPageByPlatform")
    public Result<Page<Order>> getAllInPageByPlatform(
            @ApiParam(value = "想要请求的页码")
            @RequestParam int currentPage,
            @ApiParam(value = "一页显示多少数据")
            @RequestParam int pageSize,
            @ApiParam("根据订单状态查找 0:待审核，1：通过 2：拒绝")
            @RequestParam Integer status
    ){
        if(status == null){
            return new ResultUtil<Page<Order>>().setErrorMsg("状态为空");
        }
        List<Integer> temp =  new ArrayList<>();
        if(status == 0){
            temp.add(0);
            temp.add(1);
            temp.add(2);
        }else if(status == 1){
            temp.add(4);
        }else if(status == 2){
            temp.add(3);
            temp.add(5);
        } else{
            return new ResultUtil<Page<Order>>().setErrorMsg("参数错误");
        }
        User user = UserThreadLocal.get();
        List<Integer> intList = userService.getCustomer4Platform(user);
        Page<Order> page = new Page<>(currentPage, pageSize);
        Page<Order> orderPage = orderService.selectPage(page, new EntityWrapper<Order>().in("uid", intList).in("status", temp));
        List<Order> records = orderPage.getRecords();
        List<UserDistributor> distributors = userDistributorService.selectList(new EntityWrapper<UserDistributor>().eq("father_id", user.getId()));
        records = orderService.makeupInfo(records, distributors);
        orderPage.setRecords(records);
        return new ResultUtil<Page<Order>>().setData(orderPage);
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
        //查询客户
        int userId = order.getUid();
        if(userId == 0){
            return new ResultUtil<Object>().setErrorMsg("用户ID不能为0");
        }
        UserCustomer customer = userCustomerService.selectOne(new EntityWrapper<UserCustomer>().eq("user_id", userId));
        if(customer == null){
            return new ResultUtil<Object>().setErrorMsg("该客户不存在");
        }
        //查询客户
        User user = UserThreadLocal.get();
        order.setFirstRejectMan(user.getId());
        if(result == 1){
            customer.setStatus(1);
            order.setStatus(1);
        }else if(result == 0){
            customer.setStatus(3);
            order.setStatus(3);
            order.setFirstRejectReason(reason);
        }else if(result == 2){
            customer.setStatus(2);
            order.setStatus(2);
        }
        boolean insert = orderService.insert(order);
        userCustomerService.updateById(customer);
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
        //查询客户
        int userId = order.getUid();
        if(userId == 0){
            return new ResultUtil<Object>().setErrorMsg("用户ID不能为0");
        }
        UserCustomer customer = userCustomerService.selectOne(new EntityWrapper<UserCustomer>().eq("user_id", userId));
        if(customer == null){
            return new ResultUtil<Object>().setErrorMsg("该客户不存在");
        }
        //查询客户

        User user = UserThreadLocal.get();
        order.setSecondRejectMan(user.getId());
        if(result == 1){
            customer.setStatus(4);
            order.setStatus(4);
            order.setBorrowMoney(new BigDecimal(money));
        }else if(result == 0){
            customer.setStatus(5);
            order.setStatus(5);
            order.setFirstRejectReason(reason);
        }
        String resultStr = orderService.createOrderAndRepayPlan(order);
        userCustomerService.updateById(customer);

        if (resultStr.equals("1")) {
            return new ResultUtil<Object>().set();
        } else {
            return new ResultUtil<Object>().setErrorMsg(resultStr);
        }
    }

}
