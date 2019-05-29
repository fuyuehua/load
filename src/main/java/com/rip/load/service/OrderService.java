package com.rip.load.service;

import com.rip.load.pojo.Order;
import com.baomidou.mybatisplus.service.IService;
import com.rip.load.pojo.UserDistributor;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zxh
 * @since 2019-03-29
 */
public interface OrderService extends IService<Order> {

    String createOrderAndRepayPlan(Order order);

    List<Order> makeupInfo(List<Order> records, List<UserDistributor> distributors);
}
