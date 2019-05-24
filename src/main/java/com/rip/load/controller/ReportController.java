package com.rip.load.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.rip.load.pojo.*;
import com.rip.load.pojo.nativePojo.Result;
import com.rip.load.service.*;
import com.rip.load.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
    private RipService ripService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private RiskService riskService;
    @Autowired
    private RiskRuleService riskRuleService;
    @Autowired
    private  RuleService ruleService;
    @Autowired
    private ReportItemService reportItemService;
    @Autowired
    private ReportService reportService;
    @Autowired
    private RiskRuleItemService riskRuleItemService;
    @Autowired
    private ItemService itemService;

    @ApiOperation(value = "生成弱授权风控报告")
    @GetMapping("/createReport")
    public Result<Object> createReport(int orderId, String remark){

        Order order = orderService.selectById(orderId);
        int id = order.getUid();
        Report report = new Report();
        report.setUserId(id);
        report.setRemark(remark);
        report.setCreatetime(new Date());
        reportService.insert(report);


        Integer productId = order.getProductId();
        Product product = productService.selectById(productId);
        Integer riskId = product.getRiskId();
        Risk risk = riskService.selectById(riskId);
        List<RiskRule> riskRules = riskRuleService.selectList(new EntityWrapper<RiskRule>().eq("risk_id", risk.getId()));
        for(RiskRule riskRule : riskRules){
            Rule rule = ruleService.selectById(riskRule.getRuleId());
            //查询风控
            String method = rule.getMethod();
            RiskMethodService methodService = new RiskMethodService();

            report = reportService.setItem(report);
            List<Item> items = report.getItemList();

            //身份证二要素
            if(method.equals("idCardElements")) {
                String s = ripService.idCardElementsService(id, report.getId());
                if(s.equals("1")) {
                    for (Item item : items) {
                        if (item.getType().equals(1)) {
                            boolean b = methodService.idCardElements(riskRule, item);
                            handleRiskRuleItemFlag(b, riskRule.getId(), item.getId());
                            break;
                        }
                    }
                }
            }

            //年龄限制
            if(method.equals("ageCheck")){
                String s = ripService.ageCheckService(id, report.getId());
                if(s.equals("1")) {
                    for (Item item : items) {
                        if (item.getType().equals("ageCheck")) {
                            boolean b = methodService.ageCheck(riskRule, item);
                            handleRiskRuleItemFlag(b, riskRule.getId(), item.getId());
                            break;
                        }
                    }
                }
            }

            //是否本人号码(直接使用运营商三要素)
            if(method.equals("operatorThreeElements")){
                String s = ripService.operatorThreeElementsService(id, report.getId());
                if(s.equals("1")) {
                    for (Item item : items) {
                        if (item.getType().equals("2")) {
                            boolean b = methodService.operatorThreeElementsCheck(riskRule, item);
                            handleRiskRuleItemFlag(b, riskRule.getId(), item.getId());
                            break;
                        }
                    }
                }
            }
            //紧急联系人A是否本人号码
            if(method.equals("operatorTwoElementsA")){
                String s = ripService.operatorTwoElementsAService(id, report.getId());
                if(s.equals("1")) {
                    for (Item item : items) {
                        if (item.getType().equals("operatorTwoElementsA")) {
                            boolean b = methodService.operatorTwoElementsCheck(riskRule, item);
                            handleRiskRuleItemFlag(b, riskRule.getId(), item.getId());
                            break;
                        }
                    }
                }
            }

            //紧急联系人B是否本人号码
            if(method.equals("operatorTwoElementsB")){
                String s = ripService.operatorTwoElementsBService(id, report.getId());
                if(s.equals("1")) {
                    for (Item item : items) {
                        if (item.getType().equals("operatorTwoElementsB")) {
                            boolean b = methodService.operatorTwoElementsCheck(riskRule, item);
                            handleRiskRuleItemFlag(b, riskRule.getId(), item.getId());
                            break;
                        }
                    }
                }
            }

            //手机在网时长
            if(method.equals("inTheNetworkTime")){
                String s = ripService.inTheNetworkTimeService(id, report.getId());
                if(s.equals("1")) {
                    for (Item item : items) {
                        if (item.getType().equals("3")) {
                            boolean b = methodService.inTheNetworkTimeCheck(riskRule, item);
                            handleRiskRuleItemFlag(b, riskRule.getId(), item.getId());
                            break;
                        }
                    }
                }
            }

            //紧急联系人A手机在网时长
            if(method.equals("inTheNetworkTimeA")){
                String s = ripService.inTheNetworkTimeAService(id, report.getId());
                if(s.equals("1")) {
                    for (Item item : items) {
                        if (item.getType().equals("inTheNetworkTimeA")) {
                            boolean b = methodService.inTheNetworkTimeCheck(riskRule, item);
                            handleRiskRuleItemFlag(b, riskRule.getId(), item.getId());
                            break;
                        }
                    }
                }
            }
            //紧急联系人B手机在网时长
            if(method.equals("inTheNetworkTimeB")){
                String s = ripService.inTheNetworkTimeBService(id, report.getId());
                if(s.equals("1")) {
                    for (Item item : items) {
                        if (item.getType().equals("inTheNetworkTimeB")) {
                            boolean b = methodService.inTheNetworkTimeCheck(riskRule, item);
                            handleRiskRuleItemFlag(b, riskRule.getId(), item.getId());
                            break;
                        }
                    }
                }
            }

            //个人名下关联企业 起诉记录少于10条
            if(method.equals("personalEnterprise")){
                String s = ripService.personalEnterpriseService(id, report.getId());
                if(s.equals("1")) {
                    for (Item item : items) {
                        if (item.getType().equals("6")) {
                            boolean b = methodService.personalEnterpriseCheck(riskRule, item);
                            handleRiskRuleItemFlag(b, riskRule.getId(), item.getId());
                            break;
                        }
                    }
                }
            }

            //个人名下关联企业 行政处罚记录少于10条
            if(method.equals("personalEnterprise2")){
                for (Item item : items) {
                    if (item.getType().equals("6")) {
                        boolean b = methodService.personalEnterprise2Check(riskRule, item);
                        handleRiskRuleItemFlag(b, riskRule.getId(), item.getId());
                        break;
                    }
                }
            }

            //个人黑名单综合查询 名下公司无税务重大违法
            if(method.equals("personalRiskInformation")){
                String s = ripService.personalRiskInformationService(id, report.getId());
                if(s.equals("1")) {
                    for (Item item : items) {
                        if (item.getType().equals("8")) {
                            boolean b = methodService.personalRiskInformationCheck(riskRule, item, "A02");
                            handleRiskRuleItemFlag(b, riskRule.getId(), item.getId());
                            break;
                        }
                    }
                }
            }

            //个人黑名单综合查询 个人无失联
            if(method.equals("personalRiskInformationB01")){
                for (Item item : items) {
                    if (item.getType().equals("personalRiskInformationB01")) {
                        boolean b = methodService.personalRiskInformationCheck(riskRule, item, "B01");
                        handleRiskRuleItemFlag(b, riskRule.getId(), item.getId());
                        break;
                    }
                }
            }
            //个人黑名单综合查询 个人无贷款不良贷款不良（逾期90天以上未还）
            if(method.equals("personalRiskInformationB02")){
                for (Item item : items) {
                    if (item.getType().equals("personalRiskInformationB02")) {
                        boolean b = methodService.personalRiskInformationCheck(riskRule, item, "B02");
                        handleRiskRuleItemFlag(b, riskRule.getId(), item.getId());
                        break;
                    }
                }
            }
            //个人黑名单综合查询 个人短时逾期
            if(method.equals("personalRiskInformationB03")){
                for (Item item : items) {
                    if (item.getType().equals("personalRiskInformationB03")) {
                        boolean b = methodService.personalRiskInformationCheck(riskRule, item, "B03");
                        handleRiskRuleItemFlag(b, riskRule.getId(), item.getId());
                        break;
                    }
                }
            }
            //个人黑名单综合查询 个人无逾期
            if(method.equals("personalRiskInformationB04")){
                for (Item item : items) {
                    if (item.getType().equals("personalRiskInformationB04")) {
                        boolean b = methodService.personalRiskInformationCheck(riskRule, item, "B04");
                        handleRiskRuleItemFlag(b, riskRule.getId(), item.getId());
                        break;
                    }
                }
            }
            //个人黑名单综合查询 个人无失信被执行人
            if(method.equals("personalRiskInformationC01")){
                for (Item item : items) {
                    if (item.getType().equals("personalRiskInformationC01")) {
                        boolean b = methodService.personalRiskInformationCheck(riskRule, item, "C01");
                        handleRiskRuleItemFlag(b, riskRule.getId(), item.getId());
                        break;
                    }
                }
            }
            //个人黑名单综合查询 个人无疑似催收风险
            if(method.equals("personalRiskInformationD01")){
                for (Item item : items) {
                    if (item.getType().equals("personalRiskInformationD01")) {
                        boolean b = methodService.personalRiskInformationCheck(riskRule, item, "D01");
                        handleRiskRuleItemFlag(b, riskRule.getId(), item.getId());
                        break;
                    }
                }
            }
            //个人黑名单综合查询 个人无名下公司存在违规行为（被税务局或者工商局公示）
            if(method.equals("personalRiskInformationD02")){
                for (Item item : items) {
                    if (item.getType().equals("personalRiskInformationD02")) {
                        boolean b = methodService.personalRiskInformationCheck(riskRule, item, "D02");
                        handleRiskRuleItemFlag(b, riskRule.getId(), item.getId());
                        break;
                    }
                }
            }


            //个人黑名单综合查询 个人无来自信贷高风险区域
            if(method.equals("personalRiskInformationD03")){
                for (Item item : items) {
                    if (item.getType().equals("personalRiskInformationD03")) {
                        boolean b = methodService.personalRiskInformationCheck(riskRule, item, "D03");
                        handleRiskRuleItemFlag(b, riskRule.getId(), item.getId());
                        break;
                    }
                }
            }

            //个人黑名单综合查询 个人无其他潜在风险
            if(method.equals("personalRiskInformationD04")){
                for (Item item : items) {
                    if (item.getType().equals("personalRiskInformationD04")) {
                        boolean b = methodService.personalRiskInformationCheck(riskRule, item, "D04");
                        handleRiskRuleItemFlag(b, riskRule.getId(), item.getId());
                        break;
                    }
                }
            }

            //个人黑名单综合查询 个人无7天内多头借贷
            if(method.equals("personalRiskInformationE01")){
                for (Item item : items) {
                    if (item.getType().equals("personalRiskInformationE01")) {
                        boolean b = methodService.personalRiskInformationCheck(riskRule, item, "E01");
                        handleRiskRuleItemFlag(b, riskRule.getId(), item.getId());
                        break;
                    }
                }
            }

            //个人黑名单综合查询 个人无1月内多头借贷
            if(method.equals("personalRiskInformationE02")){
                for (Item item : items) {
                    if (item.getType().equals("personalRiskInformationE02")) {
                        boolean b = methodService.personalRiskInformationCheck(riskRule, item, "E02");
                        handleRiskRuleItemFlag(b, riskRule.getId(), item.getId());
                        break;
                    }
                }
            }

            //个人黑名单综合查询 个人无3月内多头借贷
            if(method.equals("personalRiskInformationE03")){
                for (Item item : items) {
                    if (item.getType().equals("personalRiskInformationE03")) {
                        boolean b = methodService.personalRiskInformationCheck(riskRule, item, "E03");
                        handleRiskRuleItemFlag(b, riskRule.getId(), item.getId());
                        break;
                    }
                }
            }
            //个人黑名单综合查询 个人无疑似多头借贷
            if(method.equals("personalRiskInformationE04")){
                for (Item item : items) {
                    if (item.getType().equals("personalRiskInformationE04")) {
                        boolean b = methodService.personalRiskInformationCheck(riskRule, item, "E04");
                        handleRiskRuleItemFlag(b, riskRule.getId(), item.getId());
                        break;
                    }
                }
            }

            //企业工商数据查询 企业有起诉记录少于10条
            if(method.equals("businessData")){
                String s = ripService.businessDataService(id, report.getId());
                if(s.equals("1")) {
                    for (Item item : items) {
                        if (item.getType().equals("5")) {
                            boolean b = methodService.businessDataCheck(riskRule, item, 1);
                            handleRiskRuleItemFlag(b, riskRule.getId(), item.getId());
                            break;
                        }
                    }
                }
            }

            //企业工商数据查询 企业有行政处罚记录少于10条
            if(method.equals("businessData2")){
                for (Item item : items) {
                    if (item.getType().equals("5")) {
                        boolean b = methodService.businessDataCheck(riskRule, item, 2);
                        handleRiskRuleItemFlag(b, riskRule.getId(), item.getId());
                        break;
                    }
                }
            }

            //企业工商数据查询 企业有严重违法记录少于10条
            if(method.equals("businessData3")){
                for (Item item : items) {
                    if (item.getType().equals("5")) {
                        boolean b = methodService.businessDataCheck(riskRule, item, 3);
                        handleRiskRuleItemFlag(b, riskRule.getId(), item.getId());
                        break;
                    }
                }
            }

            //个人涉诉少于10条
            if(method.equals("personalComplaintInquiryC")){
                String s = ripService.personalComplaintInquiryCService(id, report.getId());
                if(s.equals("1")) {
                    for (Item item : items) {
                        if (item.getType().equals("9")) {
                            boolean b = methodService.personalComplaintInquiryCCheck(riskRule, item);
                            handleRiskRuleItemFlag(b, riskRule.getId(), item.getId());
                            break;
                        }
                    }
                }
            }

        }
        Report kk = reportService.setItem(report);
        return new ResultUtil<Object>().setData(kk);
    }

    @ApiOperation(value = "拿到弱授权风控报告")
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
    }

    private boolean handleRiskRuleItemFlag(boolean b, int riskRuleId, int itemId){
        RiskRuleItem riskRuleItem = new RiskRuleItem();
        riskRuleItem.setRiskRuleId(riskRuleId);
        riskRuleItem.setItemId(itemId);
        if (b)
            riskRuleItem.setFlag(1);
        else
            riskRuleItem.setFlag(0);
        boolean insert = riskRuleItemService.insert(riskRuleItem);
        return insert;
    }
}
