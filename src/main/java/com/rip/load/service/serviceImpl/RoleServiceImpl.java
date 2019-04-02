package com.rip.load.service.serviceImpl;

import com.rip.load.pojo.Role;
import com.rip.load.mapper.RoleMapper;
import com.rip.load.service.RoleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {


    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> getUserRole(int userId) {
        List<Role> userRole = roleMapper.getUserRole(userId);
        return userRole;
    }
}
