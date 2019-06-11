package com.rip.load.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.rip.load.pojo.*;
import com.rip.load.pojo.nativePojo.Result;
import com.rip.load.pojo.nativePojo.UserThreadLocal;
import com.rip.load.service.*;
import com.rip.load.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.StringBufferInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxh
 * @since 2019-05-29
 */
@Api(tags = {"资金记录相关接口"})
@RestController
@RequestMapping("api/bankRecord")
public class BankRecordController {

    @Autowired
    private BankRecordService bankRecordService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserDistributorSubordinateService userDistributorSubordinateService;
    @Autowired
    private UserDistributorService userDistributorService;
    @Autowired
    private UserCustomerService userCustomerService;
    @Autowired
    private UserPlatformService userPlatformService;
    @Autowired
    private BankFundService bankFundService;

    @ApiOperation("查询自己资金余额")
    @GetMapping("/balance")
    public synchronized Result<Object> balance(

    ){
        User user = UserThreadLocal.get();
        BankFund user_id = bankFundService.selectOne(new EntityWrapper<BankFund>().eq("user_id", user.getId()));
        return new ResultUtil<Object>().setData(user_id);
    }

    @ApiOperation("管理员查询所有平台商和渠道商余额")
    @GetMapping("/balanceAll")
    public synchronized Result<Object> balanceAll(

    ){
        List<Integer> list = new ArrayList<>();
        List<UserDistributor> distributors = userDistributorService.selectList(new EntityWrapper<UserDistributor>().setSqlSelect("user_id AS userId"));
        List<UserPlatform> platforms = userPlatformService.selectList(new EntityWrapper<UserPlatform>().setSqlSelect("user_id AS userId"));
        for (UserDistributor ud : distributors){
            list.add(ud.getUserId());
        }
        for (UserPlatform up : platforms){
            list.add(up.getUserId());
        }
        BankFund user_id = bankFundService.selectOne(new EntityWrapper<BankFund>().in("user_id", list));
        return new ResultUtil<Object>().setData(user_id);
    }

    @ApiOperation("平台商查询自己下级渠道商各种余额")
    @GetMapping("/balancePlatform")
    public synchronized Result<Object> balancePlatform(

    ){
        User user = UserThreadLocal.get();
        List<Integer> list = new ArrayList<>();
        List<UserDistributor> distributors = userDistributorService.selectList(new EntityWrapper<UserDistributor>()
                .setSqlSelect("user_id AS userId")
                .eq("father_id", user.getId())
        );
        for (UserDistributor ud : distributors){
            list.add(ud.getUserId());
        }
        BankFund user_id = bankFundService.selectOne(new EntityWrapper<BankFund>().in("user_id", list));
        return new ResultUtil<Object>().setData(user_id);
    }

    @ApiOperation("模拟银行给渠道商充值")
    @GetMapping("/recharge")
    public synchronized Result<Object> recharge(
            @ApiParam("充值金额")
            @RequestParam String money,
            @ApiParam("渠道商ID")
            @RequestParam int userId
    ){
        BankRecord bankRecord = new BankRecord();
        bankRecord.setMoney(new BigDecimal(money));
        bankRecord.setCreatetime(new Date());
        bankRecord.setDrawee("上游银行");
        bankRecord.setPayeeId(userId);
        bankRecord.setOperator("上游银行");
        bankRecord.setType(3);

        bankRecord = bankRecordService.createFundRecharge(bankRecord);

        if(bankRecord == null){
            return new ResultUtil<Object>().setErrorMsg("存储失败");
        }
        return new ResultUtil<Object>().setData(bankRecord);
    }

    @ApiOperation("模拟银行放款接口（复审人员）")
    @GetMapping("/sendOrder")
    public synchronized Result<Object> sendOrder(
            @ApiParam("订单ID") @RequestParam int orderId
    ){
        User user = UserThreadLocal.get();
        UserDistributorSubordinate subordinate = userDistributorSubordinateService.selectOne(new EntityWrapper<UserDistributorSubordinate>().eq("user_id", user.getId()));
        if(subordinate == null || subordinate.getFatherId() == 0)
        {
            return new ResultUtil<Object>().setErrorMsg("无上级");
        }
        UserDistributor distributor = userDistributorService.selectOne(new EntityWrapper<UserDistributor>().eq("user_id", subordinate.getFatherId()));
        UserPlatform platform = userPlatformService.selectOne(new EntityWrapper<UserPlatform>().eq("user_id", distributor.getFatherId()));
        Order order = orderService.selectById(orderId);
        if(order.getStatus() != 4){
            return new ResultUtil<Object>().setErrorMsg("订单未复审通过");
        }
        UserCustomer customer = userCustomerService.selectOne(new EntityWrapper<UserCustomer>().eq("userId", order.getUid()));
        //先查看有没有放款放过了
        BankRecord bankRecord1 = bankRecordService.selectOne(new EntityWrapper<BankRecord>().eq("payee_id", order.getUid())
                .eq("drawee_id", subordinate.getFatherId())
                .eq("operator", "上游银行").eq("type", 1));
        if(bankRecord1 != null){
            return new ResultUtil<Object>().setErrorMsg("已经放款成功");
        }
        Date now = new Date();
        BankRecord bankRecord = new BankRecord();
        bankRecord.setMoney(order.getBorrowMoney());
        bankRecord.setCreatetime(now);
        bankRecord.setDraweeId(subordinate.getFatherId());
        bankRecord.setDrawee(distributor.getCorporateName());
        bankRecord.setPayeeId(order.getUid());
        bankRecord.setPayee(customer.getRealname());
        bankRecord.setOperator("上游银行");
        bankRecord.setType(1);
        bankRecord.setUserId(order.getUid());
        bankRecord.setOrderId(orderId);
        bankRecord.setPoundageRate(distributor.getPoundage());

        //渠道商资金账户够不够扣钱
        BankFund distributorBank = bankFundService.selectOne(new EntityWrapper<BankFund>().eq("user_id", distributor.getUserId()));
        BigDecimal subtract = distributorBank.getFund().subtract(bankRecord.getMoney());
        if(subtract.signum() == -1){
            return new ResultUtil<Object>().setErrorMsg("渠道商资金账户钱不够");
        }
        distributorBank.setFund(subtract);

        BankRecord poundageRecord = new BankRecord();
        poundageRecord.setMoney(order.getBorrowMoney().multiply(new BigDecimal(distributor.getPoundage())).multiply(new BigDecimal("0.01")));
        poundageRecord.setCreatetime(now);
        poundageRecord.setDraweeId(distributor.getUserId());
        poundageRecord.setDrawee(distributor.getCorporateName());
        poundageRecord.setPayeeId(platform.getUserId());
        poundageRecord.setPayee(platform.getCorporateName());
        poundageRecord.setOperatorId(platform.getUserId());
        poundageRecord.setOperator(platform.getCorporateName());
        poundageRecord.setType(4);
        poundageRecord.setUserId(order.getUid());
        poundageRecord.setOrderId(orderId);
        poundageRecord.setPoundageRate(distributor.getPoundage());

        //渠道商手续费账户够不够扣钱
        BigDecimal subtract2 = distributorBank.getPoundage().subtract(poundageRecord.getMoney());
        if(subtract2.signum() == -1){
            return new ResultUtil<Object>().setErrorMsg("渠道商手续费账户钱不够");
        }
        distributorBank.setPoundage(subtract2);

        Boolean b = bankRecordService.createSendFundAndGetPoundage(bankRecord, poundageRecord, distributorBank);
        if(b){
            return new ResultUtil<Object>().set();
        }
        return new ResultUtil<Object>().setErrorMsg("存储错误，重试就好了，会自动回退，钱不会扣除");
    }

    @Autowired
    private RepayplanService repayplanService;
    @Autowired
    private RepayplanItemService repayplanItemService;

    @ApiOperation("模拟客户给渠道商还钱")
    @GetMapping("/payback")
    public synchronized Result<Object> payback(
            @ApiParam("订单ID") @RequestParam int orderId
    ){
        Order order = orderService.selectById(orderId);
        List<Repayplan> plans = repayplanService.selectList(new EntityWrapper<Repayplan>().eq("order_id", orderId));
        List<RepayplanItem> result = new ArrayList<>();
        for(Repayplan plan : plans) {
            if (plan.getMoneyType().equals("PRI") || plan.getMoneyType().equals("INT")) {
                List<RepayplanItem> items = repayplanItemService.selectList(new EntityWrapper<RepayplanItem>().eq("plan_id", plan.getId()));
                List<RepayplanItem> itemsTemp = new ArrayList<>();
                for (RepayplanItem item : items) {
                    for (int j = 1; j <= items.size(); j++) {
                        if (item.getTime().equals(j))
                            itemsTemp.add(j - 1, item);
                    }
                }
                items = itemsTemp;
                for (int j = 0; j < items.size(); j++) {
                    RepayplanItem item = items.get(j);
                    if (item.getEndDate() == null) {
                        item.setInAmount(item.getPlanAmount());
                        item.setEndDate(items.get(j + 1).getStartDate());
                        repayplanItemService.updateById(item);
                        result.add(item);
                        break;
                    }
                }
            }
        }
        return new ResultUtil<Object>().setData(result);
    }

    @ApiOperation("查看划拨记录(渠道商)")
    @GetMapping("/listTransferByDistributor")
    public Result<Page<BankRecord>> listTransferByDistributor(
            @ApiParam(value = "想要请求的页码")
            @RequestParam int currentPage,
            @ApiParam(value = "一页显示多少数据")
            @RequestParam int pageSize,
            @ApiParam(value = "平台商名称模糊查询")
            @RequestParam(required = false) String username
    ){
        User user = UserThreadLocal.get();
        Page<BankRecord> page = new Page<>(currentPage, pageSize);
        Page<BankRecord> recordPage = bankRecordService.selectPage(page, new EntityWrapper<BankRecord>()
                .like(!StringUtils.isEmpty(username),"drawee_", username)
                .eq("payee_id",user.getId())
                .eq("type", 5));
        return new ResultUtil<Page<BankRecord>>().setData(recordPage);
    }

    @ApiOperation("查看划拨记录(平台商)")
    @GetMapping("/listTransferByPlatform")
    public Result<Page<BankRecord>> listTransferByPlatform(
            @ApiParam(value = "想要请求的页码")
            @RequestParam int currentPage,
            @ApiParam(value = "一页显示多少数据")
            @RequestParam int pageSize,
            @ApiParam(value = "渠道商名称模糊查询")
            @RequestParam(required = false) String username
    ){
        User user = UserThreadLocal.get();
        Page<BankRecord> page = new Page<>(currentPage, pageSize);
        Page<BankRecord> recordPage = bankRecordService.selectPage(page, new EntityWrapper<BankRecord>()
                .like(!StringUtils.isEmpty(username),"payee", username)
                .eq("drawee_id", user.getId())
                .eq("type", 5));
        return new ResultUtil<Page<BankRecord>>().setData(recordPage);
    }

    @ApiOperation("查看手续费记录(渠道商)")
    @GetMapping("/listPoundageByDistributor")
    public Result<Page<BankRecord>> listPoundageByDistributor(
            @ApiParam(value = "想要请求的页码")
            @RequestParam int currentPage,
            @ApiParam(value = "一页显示多少数据")
            @RequestParam int pageSize,
            @ApiParam(value = "平台商名称模糊查询")
            @RequestParam(required = false) String username
    ){
        User user = UserThreadLocal.get();
        Page<BankRecord> page = new Page<>(currentPage, pageSize);
        Page<BankRecord> recordPage = bankRecordService.selectPage(page, new EntityWrapper<BankRecord>()
                .like(!StringUtils.isEmpty(username),"payee", username)
                .eq("drawee_id",user.getId())
                .eq("type", 4));
        return new ResultUtil<Page<BankRecord>>().setData(recordPage);
    }

    @ApiOperation("查看手续费记录(平台商)")
    @GetMapping("/listPoundageByPlatform")
    public Result<Page<BankRecord>> listPoundageByPlatform(
            @ApiParam(value = "想要请求的页码")
            @RequestParam int currentPage,
            @ApiParam(value = "一页显示多少数据")
            @RequestParam int pageSize,
            @ApiParam(value = "渠道商名称模糊查询")
            @RequestParam(required = false) String username
    ){
        User user = UserThreadLocal.get();
        Page<BankRecord> page = new Page<>(currentPage, pageSize);
        Page<BankRecord> recordPage = bankRecordService.selectPage(page, new EntityWrapper<BankRecord>()
                .like(!StringUtils.isEmpty(username),"drawee_", username)
                .eq("payee_id",user.getId())
                .eq("type", 4));
        return new ResultUtil<Page<BankRecord>>().setData(recordPage);
    }


    @ApiOperation("查看资金记录(渠道商)")
    @GetMapping("/listFundRecordByDistributor")
    public Result<Page<BankRecord>> listFundRecordByDistributor(
            @ApiParam(value = "想要请求的页码")
            @RequestParam int currentPage,
            @ApiParam(value = "一页显示多少数据")
            @RequestParam int pageSize,
            @ApiParam(value = "客户真实姓名模糊查询")
            @RequestParam(required = false) String realname,
            @ApiParam(value = "订单ID精确查询")
            @RequestParam(required = false) Integer order,
            @ApiParam(value = "类型 1：放款记录，3：充值记录")
            @RequestParam(required = false) Integer type

    ){
        List<Integer> list = new ArrayList<>();
        if(!StringUtils.isEmpty(realname)){
            List<UserCustomer> userCustomers = userCustomerService.selectList(new EntityWrapper<UserCustomer>().setSqlSelect("userId AS userId").like("realname", realname));
            for (UserCustomer uc : userCustomers){
                list.add(uc.getUserId());
            }
        }

        User user = UserThreadLocal.get();
        Page<BankRecord> page = new Page<>(currentPage, pageSize);
        Page<BankRecord> recordPage = null;
        if(type != null && type == 1){
            recordPage = bankRecordService.selectPage(page, new EntityWrapper<BankRecord>()
                    .eq("drawee_id", user.getId()).eq("type",1)
                    .eq(order!= null && order!= 0,"order_id",order)
            );
        }else if(type != null && type == 3){
            recordPage = bankRecordService.selectPage(page, new EntityWrapper<BankRecord>()
                    .eq("payee_id", user.getId()).eq("type",3)
                    .in(list.size()>0,"user_id",list)
                    .eq(order!= null && order!= 0,"order_id",order)
            );
        }else{
            recordPage = bankRecordService.selectPage(page, new EntityWrapper<BankRecord>()
                    .eq("drawee_id", user.getId()).eq("type",1)
                    .orNew()
                    .eq("payee_id", user.getId()).eq("type",3)
                    .in(list.size()>0,"user_id",list)
                    .andNew()
                    .eq(order!= null && order!= 0,"order_id",order)
            );
        }
        return new ResultUtil<Page<BankRecord>>().setData(recordPage);
    }

    @ApiOperation("查看资金记录(平台商)")
    @GetMapping("/listFundRecordByPlatform")
    public Result<Page<BankRecord>> listFundRecordByPlatform(
            @ApiParam(value = "想要请求的页码")
            @RequestParam int currentPage,
            @ApiParam(value = "一页显示多少数据")
            @RequestParam int pageSize,
            @ApiParam(value = "渠道商公司名称模糊查询")
            @RequestParam(required = false) String realname,
            @ApiParam(value = "客户真实姓名模糊查询")
            @RequestParam(required = false) String dName,
            @ApiParam(value = "订单ID精确查询")
            @RequestParam(required = false) Integer order,
            @ApiParam(value = "类型 1：放款记录，3：充值记录")
            @RequestParam(required = false) Integer type

    ){
        User user = UserThreadLocal.get();
        Page<BankRecord> page = new Page<>(currentPage, pageSize);
        //筛选出渠道商，模糊查询公司名称，平台商下级
        List<Integer> list1 = new ArrayList<>();
        List<UserDistributor> distributors = userDistributorService.selectList(new EntityWrapper<UserDistributor>().setSqlSelect("user_id AS userId")
                .eq("father_id", user.getId())
        .eq(!StringUtils.isEmpty(dName),"corporate_name",dName));
        for (UserDistributor ud : distributors){
            list1.add(ud.getUserId());
        }
        if(list1.size() == 0) {
            return new ResultUtil<Page<BankRecord>>().setData(page);
        }
        //平台商下属渠道商搜索客户
        List<Integer> list = new ArrayList<>();
        if(!StringUtils.isEmpty(realname)){
            List<UserCustomer> userCustomers = userCustomerService.selectList(new EntityWrapper<UserCustomer>().setSqlSelect("userId AS userId")
                    .like("realname", realname)
            .in("father_id",list1));
            for (UserCustomer uc : userCustomers){
                list.add(uc.getUserId());
            }
        }

        Page<BankRecord> recordPage = null;
        if(type != null && type == 1){
            recordPage = bankRecordService.selectPage(page, new EntityWrapper<BankRecord>()
                    .in("drawee_id", list1).eq("type",1)
                    .eq(order!= null && order!= 0,"order_id",order)
            );
        }else if(type != null && type == 3){
            recordPage = bankRecordService.selectPage(page, new EntityWrapper<BankRecord>()
                    .in("payee_id", list1).eq("type",3)
                    .in(list.size()>0,"user_id",list)
                    .eq(order!= null && order!= 0,"order_id",order)
            );
        }else{
            recordPage = bankRecordService.selectPage(page, new EntityWrapper<BankRecord>()
                    .in("drawee_id", list1).eq("type",1)
                    .orNew()
                    .in("payee_id", list1).eq("type",3)
                    .in(list.size()>0,"user_id",list)
                    .andNew()
                    .eq(order!= null && order!= 0,"order_id",order)
            );
        }
        return new ResultUtil<Page<BankRecord>>().setData(recordPage);
    }


}
