package com.rip.load.service.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.rip.load.pojo.*;
import com.rip.load.mapper.OrderMapper;
import com.rip.load.service.OrderService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.rip.load.service.ProductService;
import com.rip.load.service.RepayplanService;
import com.rip.load.service.UserCustomerService;
import com.rip.load.utils.AverageCapitalPlusInterestUtils;
import com.rip.load.utils.AverageCapitalUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zxh
 * @since 2019-03-29
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private ProductService productService;
    @Autowired
    private RepayplanService repayplanService;

    @Autowired
    private UserCustomerService userCustomerService;

    @Override
    public String createOrderAndRepayPlan(Order order) {

        Integer productId = order.getProductId();
        Product product = productService.selectById(productId);
        if(product == null){
            return "产品不存在";
        }
        List<Product> products = new ArrayList<>();
        products.add(product);
        products = productService.settleConfig(products);
        product = products.get(0);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String now = sdf.format(date);

        Config config = product.getConfig();
        Repayplan repayplan = new Repayplan();
        repayplan.setOrderId(order.getId());
        repayplan.setType(config.getRepayType());
        repayplan.setStartDate(now);
        //设置结束时间
        String limitType = config.getLimitType();
        String limit = config.getLimit();
        if(limitType.equals("Y")) {
            Date date1 = DateUtils.addYears(date, Integer.parseInt(limit));
            repayplan.setEndDate(sdf.format(date1));
            repayplan.setTotalTimes(String.valueOf(Integer.parseInt(limit)*12));
        }else if(limitType.equals("M")){
            Date date1 = DateUtils.addMonths(date, Integer.parseInt(limit));
            repayplan.setEndDate(sdf.format(date1));
            repayplan.setTotalTimes(String.valueOf(Integer.parseInt(limit)));
        }
        Date date1 = DateUtils.addMonths(date, 1);
        repayplan.setNextDealDate(sdf.format(date1));
        repayplan.setDealDay(sdf.format(date).substring(6,8));

        String totalPri = order.getBorrowMoney().toString();
        String totalInt = null;

        if(repayplan.getType() == 1){
            double principalInterestCount = AverageCapitalPlusInterestUtils.getPrincipalInterestCount(Double.valueOf(order.getBorrowMoney().toString()), Double.valueOf(config.getInterest()) * 0.01, Integer.parseInt(repayplan.getTotalTimes()));
            repayplan.setTotalAmount(Double.toString(principalInterestCount));
            double interestCount = principalInterestCount - Double.valueOf(order.getBorrowMoney().toString());
            totalInt = Double.toString(interestCount);
        }
        if(repayplan.getType() == 2){
            double interestCount = AverageCapitalUtils.getInterestCount(Double.valueOf(order.getBorrowMoney().toString()), Double.valueOf(config.getInterest()) * 0.01, Integer.parseInt(repayplan.getTotalTimes()));
            double total = interestCount + Double.valueOf(order.getBorrowMoney().toString());
            repayplan.setTotalAmount(Double.toString(total));
            totalInt = Double.toString(interestCount);
        }

        for (int i = 0; i < 3; i++) {
            if(i == 0){
                repayplan.setMoneyType("PRI");
                repayplan.setAmount(totalPri);
            }
            if(i == 1){
                repayplan.setMoneyType("INT");
                repayplan.setAmount(totalInt);
            }
            if(i == 2){
                repayplan.setMoneyType("ODP");
                repayplan.setAmount("0");
            }
            repayplanService.insert(repayplan);
        }
        updateById(order);
        return "1";
    }

    @Override
    public List<Order> makeupInfo(List<Order> records, List<UserDistributor> distributors) {
        List<Integer> list = new ArrayList<>();
        for(Order o : records){
            list.add(o.getUid());
        }
        List<UserCustomer> customers = userCustomerService.selectList(new EntityWrapper<UserCustomer>()
                .setSqlSelect("userId, realname, idcard, cellphone, father_id as fatherId")
                .in("userId", list));
        for(Order o : records){
            for(UserCustomer uc : customers){
                if(uc.getUserId().equals(o.getUid())){
                    o.setUserCustomer(uc);
                }
                for(UserDistributor ud : distributors) {
                    if (uc.getFatherId().equals(ud.getUserId())){
                        uc.setUserDistributor(ud);
                    }
                }
            }
        }
        return records;
    }

    public static void main(String[] args) {
    }
}
