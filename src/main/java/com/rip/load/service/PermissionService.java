package com.rip.load.service;

import com.rip.load.pojo.Permission;
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
public interface PermissionService extends IService<Permission> {


    /**
     * 得到List<Permission>
     * @param userId
     * @return List<Permission>
     */
    List<Permission> getUserPermission(int userId);

    /**
     *判断给定的List中是否有这个url
     * @param url
     * @param list
     * @return
     */
    boolean hasPermissionUrl(String url, List<Permission> list);

    /**
     *  根据角色ID查询其绑定的权限
     * @param id 角色ID
     * @return
     */
    List<Permission> getByRoleId(int id);

}
