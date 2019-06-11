package com.rip.load.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.rip.load.pojo.Order;
import com.rip.load.pojo.Repayplan;
import com.rip.load.pojo.RepayplanItem;
import com.rip.load.pojo.nativePojo.Result;
import com.rip.load.service.OrderService;
import com.rip.load.service.RepayplanItemService;
import com.rip.load.service.RepayplanService;
import com.rip.load.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxh
 * @since 2019-05-24
 */
@Api(tags = {"还款计划接口"})
@RestController
@RequestMapping("api/repayplan")
public class RepayplanController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private RepayplanService repayplanService;
    @Autowired
    private RepayplanItemService repayplanItemService;


    @ApiOperation(value = "拿到还款情况表")
    @GetMapping("/takeRepayplan")
    public Result<Object> takeRepayplan(int orderId){
        Order order = orderService.selectById(orderId);
        List<Repayplan> repayplans = repayplanService.selectList(new EntityWrapper<Repayplan>().eq("order_id", orderId));
        for(Repayplan repayplan : repayplans){
            List<RepayplanItem> items = repayplanItemService.selectList(new EntityWrapper<RepayplanItem>().eq("plan_id", repayplan.getId()));
            repayplan.setRepayplanItemList(items);
        }
        return new ResultUtil<Object>().setData(repayplans);
    }
}
