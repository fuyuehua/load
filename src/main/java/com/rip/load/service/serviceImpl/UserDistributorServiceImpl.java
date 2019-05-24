package com.rip.load.service.serviceImpl;

import com.rip.load.pojo.User;
import com.rip.load.pojo.UserDistributor;
import com.rip.load.mapper.UserDistributorMapper;
import com.rip.load.service.UserDistributorService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.rip.load.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zxh
 * @since 2019-04-09
 */
@Service
public class UserDistributorServiceImpl extends ServiceImpl<UserDistributorMapper, UserDistributor> implements UserDistributorService {

    @Autowired
    private UserService userService;

    @Override
    public boolean insertUpdateAndSetType(UserDistributor distributor, User user) {
        boolean b = insertOrUpdate(distributor);
        //如果没有存用户类型，存一下
        if(user.getType() ==null || user.getType() == 0){
            user.setType(5);
            boolean b1 = userService.updateById(user);
            if(!b1){
                return false;
            }
        }
        return b;
    }
}
