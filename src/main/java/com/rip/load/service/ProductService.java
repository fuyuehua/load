package com.rip.load.service;

import com.rip.load.pojo.Product;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zxh
 * @since 2019-05-09
 */
public interface ProductService extends IService<Product> {

    List<Product> settleConfigRisk(List<Product> list);

    List<Product> settleConfig(List<Product> list);

    List<Product> settleRisk(List<Product> list);
}
