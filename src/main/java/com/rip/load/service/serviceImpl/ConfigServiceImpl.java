package com.rip.load.service.serviceImpl;

import com.rip.load.pojo.Config;
import com.rip.load.mapper.ConfigMapper;
import com.rip.load.service.ConfigService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zxh
 * @since 2019-04-02
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService {

}
