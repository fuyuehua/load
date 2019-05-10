package com.rip.load.service.serviceImpl;

import com.rip.load.pojo.Product;
import com.rip.load.mapper.ProductMapper;
import com.rip.load.service.ProductService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zxh
 * @since 2019-05-09
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

}
