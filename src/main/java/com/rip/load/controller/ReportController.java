package com.rip.load.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.rip.load.pojo.*;
import com.rip.load.pojo.nativePojo.Result;
import com.rip.load.service.*;
import com.rip.load.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxh
 * @since 2019-04-30
 */
@RestController
@RequestMapping("api/report")
public class ReportController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ReportService reportService;

    @ApiOperation(value = "生成首次风控报告")
    @GetMapping("/createReport")
    public Result<Object> createReport(int orderId, String remark){
        Order order = orderService.selectById(orderId);
        if(order == null){
            return new ResultUtil<Object>().setErrorMsg("此订单不存在");
        }
        List<Report> reportsInDB = reportService.selectList(new EntityWrapper<Report>().eq("order_id", orderId).eq("type",1));
        Report report = null;
        if(reportsInDB.size() == 0){
            report = reportService.handleFirstReport(order, remark);
        }else{
            return new ResultUtil<Object>().setData(reportService.takeFirstReport(order));
        }
        return new ResultUtil<Object>().setData(report);
    }

   /* @ApiOperation(value = "拿到首次风控报告")
    @GetMapping("/takeReport")
    public Result<Object> takeReport(int orderId, int reportId){

        Order order = orderService.selectById(orderId);
        int userId = order.getUid();
        List<Report> reports = reportService.selectList(new EntityWrapper<Report>().eq("user_id", userId));
        for(Report report : reports){
            List<RiskRuleItem> result = new ArrayList<>();
            report = reportService.setItem(report);
            for(Item item : report.getItemList()) {
                List<RiskRuleItem> linkList = riskRuleItemService.selectList(new EntityWrapper<RiskRuleItem>().eq("item_id", item.getId()));
                result = riskRuleItemService.setRiskRule(linkList);
            }
            report.setRiskRuleItems(result);
        }
        return new ResultUtil<Object>().setData(reports);
    }*/
}
