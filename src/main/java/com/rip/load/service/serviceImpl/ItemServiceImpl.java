package com.rip.load.service.serviceImpl;

import com.rip.load.mapper.ItemMapper;
import com.rip.load.pojo.Item;
import com.rip.load.service.ItemService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zxh
 * @since 2019-04-22
 */
@Service
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements ItemService {

}
