package com.rip.load.service;

import com.rip.load.pojo.Permission;
import com.rip.load.pojo.Role;
import com.rip.load.pojo.User;
import com.baomidou.mybatisplus.service.IService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zxh
 * @since 2019-03-29
 */
public interface UserService extends IService<User> {

    /**
     * 拿到角色和所有权限url
     * @param userId
     * @return map:role,list<Role>
     *     permission,list<Permission>
     */
    public Map<String, Object> getRolePermission(int userId);
}
