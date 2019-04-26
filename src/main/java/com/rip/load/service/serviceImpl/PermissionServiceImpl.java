package com.rip.load.service.serviceImpl;

import com.rip.load.pojo.Permission;
import com.rip.load.mapper.PermissionMapper;
import com.rip.load.service.PermissionService;
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
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> getUserPermission(int userId) {
        return permissionMapper.getUserPermission(userId);
    }

    @Override
    public boolean hasPermissionUrl(String url, List<Permission> list) {
        for(Permission p : list){

            // geturl = /rip/**
            String[] split = p.getUrl().split("\\*\\*");
            if(split.length == 0){
                if(p.getUrl().equals(url)){
                    return true;
                }
            }else {
                // prefix = /rip/
                String prefix = split[0];
                // url = /rip/kkk
                String[] split1 = url.split(prefix);
                if(split1 == null ||split1.length == 0 ||split1[0].equals("")){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<Permission> getByRoleId(int id) {
        return permissionMapper.getByRoleId(id);
    }
}
