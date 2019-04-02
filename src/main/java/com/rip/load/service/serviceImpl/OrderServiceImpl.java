package com.rip.load.service.serviceImpl;

import com.rip.load.pojo.Order;
import com.rip.load.mapper.OrderMapper;
import com.rip.load.service.OrderService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
