package com.rip.load.service.serviceImpl;

import com.rip.load.pojo.Token;
import com.rip.load.mapper.TokenMapper;
import com.rip.load.service.TokenService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zxh
 * @since 2019-04-16
 */
@Service
public class TokenServiceImpl extends ServiceImpl<TokenMapper, Token> implements TokenService {

}
