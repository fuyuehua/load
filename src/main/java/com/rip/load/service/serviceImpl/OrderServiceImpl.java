package com.rip.load.service.serviceImpl;

import com.rip.load.pojo.Config;
import com.rip.load.pojo.Order;
import com.rip.load.mapper.OrderMapper;
import com.rip.load.pojo.Product;
import com.rip.load.service.OrderService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.rip.load.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        Config config = product.getConfig();


        return null;
    }
}
