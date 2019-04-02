package com.rip.load.mapper;

import com.rip.load.pojo.Role;
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
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> getUserRole(int userId);
}
