package com.rip.load.service.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.rip.load.mapper.UserCustomerMapper;
import com.rip.load.pojo.*;
import com.rip.load.mapper.ReportMapper;
import com.rip.load.service.*;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.rip.load.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zxh
 * @since 2019-04-30
 */
@Service
public class ReportServiceImpl extends ServiceImpl<ReportMapper, Report> implements ReportService {

    @Autowired
    private RipService ripService;
    @Autowired
    private ReportItemService reportItemService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private RiskRuleItemService riskRuleItemService;
    @Autowired
    private UserCustomerMapper userCustomerMapper;

    @Override
    public Report setItem(Report report) {
        List<ReportItem> reportItems = reportItemService.selectList(new EntityWrapper<ReportItem>().eq("report_id", report.getId()));
        List<Integer> list = new ArrayList<>();
        for(ReportItem reportItem : reportItems){
            list.add(reportItem.getItemId());
        }
        List<Item> items = itemService.selectBatchIds(list);
        report.setItemList(items);
        return report;
    }

    @Override
    public Report handleFirstReport(Order order, String remark) {
        int id = order.getUid();
        Report report = new Report();
        report.setUserId(id);
        report.setRemark(remark);
        report.setCreatetime(new Date());
        report.setOrderId(order.getId());
        report.setType(1);
        insert(report);

        Integer productId = order.getProductId();
        Product product = productService.selectById(productId);
        List<Product> productsTemp = new ArrayList<>();
        productsTemp.add(product);
        productsTemp = productService.settleRisk(productsTemp);
        product = productsTemp.get(0);
        List<RiskRule> riskRules = product.getRisk().getRiskRuleList();

        //为了不重复查询
        int personalEnterprise = 0;
        int businessData = 0;
        for(RiskRule riskRule : riskRules) {
            Rule rule = riskRule.getRule();
            //查询风控
            String method = rule.getMethod();

            //身份证二要素
            if (method.equals("idCardElements")) {
                String s = ripService.idCardElementsService(id, report.getId());
            }

            //年龄限制
            if (method.equals("ageCheck")) {
                String s = ripService.ageCheckService(id, report.getId());
            }

            //是否本人号码(直接使用运营商三要素)
            if (method.equals("operatorThreeElements")) {
                String s = ripService.operatorThreeElementsService(id, report.getId());
            }
            //紧急联系人A是否本人号码
            if (method.equals("operatorTwoElementsA")) {
                String s = ripService.operatorTwoElementsAService(id, report.getId());
            }

            //紧急联系人B是否本人号码
            if (method.equals("operatorTwoElementsB")) {
                String s = ripService.operatorTwoElementsBService(id, report.getId());
            }

            //手机在网时长
            if (method.equals("inTheNetworkTime")) {
                String s = ripService.inTheNetworkTimeService(id, report.getId());
            }

            //紧急联系人A手机在网时长
            if (method.equals("inTheNetworkTimeA")) {
                String s = ripService.inTheNetworkTimeAService(id, report.getId());
            }
            //紧急联系人B手机在网时长
            if (method.equals("inTheNetworkTimeB")) {
                String s = ripService.inTheNetworkTimeBService(id, report.getId());
            }

            //个人名下关联企业 起诉记录少于10条
            if (method.equals("personalEnterprise")) {
                if(personalEnterprise == 0) {
                    String s = ripService.personalEnterpriseService(id, report.getId());
                    personalEnterprise += 1;
                }
            }

            //个人名下关联企业 行政处罚记录少于10条
            if (method.equals("personalEnterprise2")) {
                if(personalEnterprise == 0) {
                    String s = ripService.personalEnterpriseService(id, report.getId());
                    personalEnterprise += 1;
                }
            }

            //企业工商数据查询 企业有起诉记录少于10条
            if (method.equals("businessData")) {
                if(businessData == 0) {
                    String s = ripService.businessDataService(id, report.getId());
                    businessData += 1;
                }
            }

            //企业工商数据查询 企业有行政处罚记录少于10条
            if (method.equals("businessData2")) {
                if(businessData == 0) {
                    String s = ripService.businessDataService(id, report.getId());
                    businessData += 1;
                }
            }

            //企业工商数据查询 企业有严重违法记录少于10条
            if (method.equals("businessData3")) {
                if(businessData == 0) {
                    String s = ripService.businessDataService(id, report.getId());
                    businessData += 1;
                }
            }
            //个人涉诉少于10条
            if (method.equals("personalComplaintInquiryC")) {
                String s = ripService.personalComplaintInquiryCService(id, report.getId());
            }
        }

        report = setItem(report);
        List<Item> items = report.getItemList();
        for(RiskRule riskRule : riskRules) {
            Rule rule = riskRule.getRule();
            //查询风控
            String method = rule.getMethod();
            RiskMethodService methodService = new RiskMethodService();
            for (Item item : items) {
                //身份证二要素
                if (method.equals("idCardElements") && item.getType().equals("1")) {
                    boolean b = methodService.idCardElements(riskRule, item);
                    handleRiskRuleItemFlag(b, riskRule.getId(), item.getId());
                    break;
                }
                //年龄限制
                if (method.equals("ageCheck") && item.getType().equals("ageCheck")) {
                    boolean b = methodService.ageCheck(riskRule, item);
                    handleRiskRuleItemFlag(b, riskRule.getId(), item.getId());
                    break;
                }
                //是否本人号码(直接使用运营商三要素)
                if (method.equals("operatorThreeElements") && item.getType().equals("2")) {
                    boolean b = methodService.operatorThreeElementsCheck(riskRule, item);
                    handleRiskRuleItemFlag(b, riskRule.getId(), item.getId());
                    break;
                }
                //紧急联系人A是否本人号码
                if (method.equals("operatorTwoElementsA") && item.getType().equals("operatorTwoElementsA")) {
                    boolean b = methodService.operatorTwoElementsCheck(riskRule, item);
                    handleRiskRuleItemFlag(b, riskRule.getId(), item.getId());
                    break;
                }
                //紧急联系人B是否本人号码
                if (method.equals("operatorTwoElementsB")&&item.getType().equals("operatorTwoElementsB")) {
                    boolean b = methodService.operatorTwoElementsCheck(riskRule, item);
                    handleRiskRuleItemFlag(b, riskRule.getId(), item.getId());
                    break;
                }
                //手机在网时长
                if (method.equals("inTheNetworkTime")&&item.getType().equals("3")) {
                    boolean b = methodService.inTheNetworkTimeCheck(riskRule, item);
                    handleRiskRuleItemFlag(b, riskRule.getId(), item.getId());
                    break;
                }
                //紧急联系人A手机在网时长
                if (method.equals("inTheNetworkTimeA") && item.getType().equals("inTheNetworkTimeA")) {
                    boolean b = methodService.inTheNetworkTimeCheck(riskRule, item);
                    handleRiskRuleItemFlag(b, riskRule.getId(), item.getId());
                    break;
                }
                //紧急联系人B手机在网时长
                if (method.equals("inTheNetworkTimeB")&&item.getType().equals("inTheNetworkTimeB")) {
                    boolean b = methodService.inTheNetworkTimeCheck(riskRule, item);
                    handleRiskRuleItemFlag(b, riskRule.getId(), item.getId());
                    break;
                }
                //个人名下关联企业 起诉记录少于10条
                if (method.equals("personalEnterprise") && item.getType().equals("6")) {
                    boolean b = methodService.personalEnterpriseCheck(riskRule, item);
                    handleRiskRuleItemFlag(b, riskRule.getId(), item.getId());
                    break;
                }
                //个人名下关联企业 行政处罚记录少于10条
                if (method.equals("personalEnterprise2")&&item.getType().equals("6")) {
                    boolean b = methodService.personalEnterprise2Check(riskRule, item);
                    handleRiskRuleItemFlag(b, riskRule.getId(), item.getId());
                    break;
                }
                //企业工商数据查询 企业有起诉记录少于10条
                if(method.equals("businessData")&&item.getType().equals("5")){
                    boolean b = methodService.businessDataCheck(riskRule, item, 1);
                    handleRiskRuleItemFlag(b, riskRule.getId(), item.getId());
                    break;
                }
                //企业工商数据查询 企业有行政处罚记录少于10条
                if(method.equals("businessData2")&&item.getType().equals("5")){
                    boolean b = methodService.businessDataCheck(riskRule, item, 2);
                    handleRiskRuleItemFlag(b, riskRule.getId(), item.getId());
                    break;
                }
                //企业工商数据查询 企业有严重违法记录少于10条
                if (method.equals("businessData3")&&item.getType().equals("5")) {
                    boolean b = methodService.businessDataCheck(riskRule, item, 3);
                    handleRiskRuleItemFlag(b, riskRule.getId(), item.getId());
                    break;
                }
                //个人涉诉少于10条
                if (method.equals("personalComplaintInquiryC")&&item.getType().equals("9")) {
                    boolean b = methodService.personalComplaintInquiryCCheck(riskRule, item);
                    handleRiskRuleItemFlag(b, riskRule.getId(), item.getId());
                    break;
                }
            }
        }

        List<Integer> listTemp = new ArrayList<>();
        for(Item item : items){
            listTemp.add(item.getId());
        }
        List<RiskRuleItem> linkList = riskRuleItemService.selectList(new EntityWrapper<RiskRuleItem>().in("item_id", listTemp));
        linkList = riskRuleItemService.setRiskRule(linkList);
        report.setRiskRuleItems(linkList);
        List<UserCustomer> userId = userCustomerMapper.selectList(new EntityWrapper<UserCustomer>().eq("userId", id));
        report.setUserCustomer(userId.get(0));
        return report;
    }

    @Override
    public Report takeFirstReport(Order order) {
        Report report = selectOne(new EntityWrapper<Report>().eq("order_id", order.getId()).eq("type",1));
        report = setItem(report);
        List<Item> items = report.getItemList();
        List<Integer> listTemp = new ArrayList<>();
        for(Item item : items){
            listTemp.add(item.getId());
        }
        List<RiskRuleItem> linkList = riskRuleItemService.selectList(new EntityWrapper<RiskRuleItem>().in("item_id", listTemp));
        linkList = riskRuleItemService.setRiskRule(linkList);
        report.setRiskRuleItems(linkList);
        List<UserCustomer> userId = userCustomerMapper.selectList(new EntityWrapper<UserCustomer>().eq("userId", order.getUid()));
        report.setUserCustomer(userId.get(0));
        return report;
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
