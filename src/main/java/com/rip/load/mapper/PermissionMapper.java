package com.rip.load.mapper;

import com.rip.load.pojo.Permission;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zxh
 * @since 2019-03-29
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    List<Permission> getUserPermission(int userId);

    List<Permission> getByRoleId(int id);
}
