package com.rip.load.service.serviceImpl;

import com.rip.load.pojo.Permission;
import com.rip.load.pojo.Role;
import com.rip.load.pojo.User;
import com.rip.load.mapper.UserMapper;
import com.rip.load.service.PermissionService;
import com.rip.load.service.RoleService;
import com.rip.load.service.UserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zxh
 * @since 2019-03-29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    @Override
    public Map<String, Object> getRolePermission(int userId) {
        Map<String, Object> resultMap = new HashMap<>();
        List<Role> roleList = roleService.getUserRole(userId);
        resultMap.put("role", roleList);
        List<Permission> permissionList = permissionService.getUserPermission(userId);
        resultMap.put("permission",permissionList);
        return resultMap;
    }
}
