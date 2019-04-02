package com.rip.load.service;

import com.rip.load.pojo.Role;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zxh
 * @since 2019-03-29
 */
public interface RoleService extends IService<Role> {

    List<Role> getUserRole(int id);
}
