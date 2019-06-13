package com.rip.load.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.rip.load.pojo.*;
import com.rip.load.pojo.nativePojo.Result;
import com.rip.load.service.*;
import com.rip.load.utils.FileUtil;
import com.rip.load.utils.ResultUtil;
import com.rip.load.utils.pdfUtils.OperatorReportPdfUtil;
import com.rip.load.utils.pdfUtils.TaoBaoReportPdfUtil;
import com.rip.load.utils.pdfUtils.WindControlReportUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxh
 * @since 2019-04-30
 */
@Api(tags = {"报告所有接口"})
@RestController
@RequestMapping("api/report")
public class ReportController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ReportService reportService;

    @ApiOperation(value = "生成首次风控报告")
    @GetMapping("/createReport")
    public Result<Object> createReport(@ApiParam("订单ID") @RequestParam int orderId, @ApiParam("报告备注") @RequestParam String remark){
        remark = "订单ID： " + orderId + " 生成首次风控报告, 备注： " + remark;
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

    @ApiOperation(value = "拿到首次风控报告")
    @GetMapping("/takeReport")
    public Result<Object> takeReport(@ApiParam("订单ID") @RequestParam int orderId){
        Order order = orderService.selectById(orderId);
        if(order == null){
            return new ResultUtil<Object>().setErrorMsg("此订单不存在");
        }
        Report report = reportService.takeFirstReport(order);
        if(report == null){
            return new ResultUtil<Object>().setErrorMsg("无风控报告");
        }

        //生成pdf
        Map<String, Object> map = new HashMap<>();
        Result<Object> result = new ResultUtil<Object>().setData(report);
        String json = JSON.toJSONString(result);
        String filePath = "../img";
        File file = new File(filePath);
        if(!file.exists()){
            file.mkdir();
        }
        Boolean aBoolean = WindControlReportUtil.windControlReport(json, filePath+"/1.pdf");
        Boolean aBoolean1 = OperatorReportPdfUtil.operatorReport(json, filePath+"/2.pdf");
        Boolean aBoolean2 = TaoBaoReportPdfUtil.taoBaoReport(json,filePath+"/3.pdf");
        if(!StringUtils.isEmpty(report.getBaseUrl())){
            aBoolean = false;
        }
        if(!StringUtils.isEmpty(report.getOperatorUrl())){
            aBoolean1 = false;
        }
        if(!StringUtils.isEmpty(report.getTaobaoUrl())){
            aBoolean2 = false;
        }
        if(aBoolean){
            String onlineUrl = FileUtil.getOnlineUrl("pdfPackage", new File(filePath + "/1.pdf"), true);
            report.setBaseUrl(onlineUrl);
        }
        if(aBoolean1){
            String onlineUrl = FileUtil.getOnlineUrl("pdfPackage", new File(filePath + "/2.pdf"),true);
            report.setOperatorUrl(onlineUrl);
        }
        if(aBoolean2){
            String onlineUrl = FileUtil.getOnlineUrl("pdfPackage", new File(filePath + "/3.pdf"),true);
            report.setTaobaoUrl(onlineUrl);
        }
        boolean b = reportService.updateById(report);
        if(!b){
            return new ResultUtil<Object>().setErrorMsg("报告存储失败，请重试");
        }
        return new ResultUtil<Object>().setData(report);
    }

    public static void main(String[] args) {

    }
}
