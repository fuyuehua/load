package com.rip.load.service.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.rip.load.pojo.*;
import com.rip.load.mapper.UserMapper;
import com.rip.load.pojo.nativePojo.UserThreadLocal;
import com.rip.load.service.*;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private UserCustomerService userCustomerService;
    @Autowired
    private UserPlatformService userPlatformService;
    @Autowired
    private UserPlatformSubordinateService userPlatformSubordinateService;
    @Autowired
    @Lazy
    private UserDistributorService userDistributorService;
    @Autowired
    private UserDistributorSubordinateService userDistributorSubordinateService;

    @Override
    public Map<String, Object> getRolePermission(int userId) {
        Map<String, Object> resultMap = new HashMap<>();
        List<Role> roleList = roleService.getUserRole(userId);
        resultMap.put("role", roleList);
        List<Permission> permissionList = permissionService.getUserPermission(userId);
        resultMap.put("permission",permissionList);
        return resultMap;
    }

    @Override
    public boolean createAndSetRole(User readyUser) {
        User user = UserThreadLocal.get();

        UserRole userRole = new UserRole();
        Integer type = readyUser.getType();
        userRole.setRid(type);
        if(!(type == 6 || type == 7 || type == 8 || type == 9)){
            return false;
        }

        boolean k = insert(readyUser);
        userRole.setUid(readyUser.getId());
        if(!k){
            return false;
        }
        boolean k1 = userRoleService.insert(userRole);
        if(!k1){
            return false;
        }
        UserDistributorSubordinate user1 = new UserDistributorSubordinate();
        user1.setUserId(readyUser.getId());
        user1.setFatherId(user.getId());
        userDistributorSubordinateService.insert(user1);
        return true;
    }

    @Override
    public List<User> setRoleAndInfo(List<User> allUser) {
        //客户
        List<Integer> list2 = new ArrayList<>();
        //平台商
        List<Integer> list3 = new ArrayList<>();
        //平台商下属
        List<Integer> list4 = new ArrayList<>();
        //渠道商
        List<Integer> list5 = new ArrayList<>();
        //渠道商下属
        List<Integer> list6 = new ArrayList<>();

        for(User user : allUser){
            if(user.getType() == 2)
                list2.add(user.getId());
            if(user.getType() == 3)
                list3.add(user.getId());
            if(user.getType() == 4)
                list4.add(user.getId());
            if(user.getType() == 5)
                list5.add(user.getId());
            if(user.getType() == 6 || user.getType() == 7 || user.getType() == 8 ||user.getType() == 9)
                list6.add(user.getId());
            List<Role> role = roleService.getUserRole(user.getId());
            user.setRoleList(role);
        }
        if(list2.size() > 0) {
            List<UserCustomer> userCustomers = userCustomerService.selectBatchIds(list2);
            for (UserCustomer k : userCustomers) {
                for (User user : allUser) {
                    if (k.getUserId().equals(user.getId())) {
                        user.setInfoObj(k);
                    }
                }
            }
        }
        if(list3.size() > 0) {
            List<UserPlatform> userPlatforms = userPlatformService.selectBatchIds(list3);
            for (UserPlatform k : userPlatforms) {
                for (User user : allUser) {
                    if (k.getUserId().equals(user.getId())) {
                        user.setInfoObj(k);
                    }
                }
            }
        }

        if(list4.size() > 0) {
            List<UserPlatformSubordinate> userPlatformSubordinates = userPlatformSubordinateService.selectBatchIds(list4);
            for (UserPlatformSubordinate k : userPlatformSubordinates) {
                for (User user : allUser) {
                    if (k.getUserId().equals(user.getId())) {
                        user.setInfoObj(k);
                    }
                }
            }
        }

        if(list5.size() > 0) {
            List<UserDistributor> userDistributors = userDistributorService.selectBatchIds(list5);
            for (UserDistributor k : userDistributors) {
                for (User user : allUser) {
                    if (k.getUserId().equals(user.getId())) {
                        user.setInfoObj(k);
                    }
                }
            }
        }

        if(list6.size() > 0) {
            List<UserDistributorSubordinate> userDistributorSubordinates = userDistributorSubordinateService.selectBatchIds(list6);
            for (UserDistributorSubordinate k : userDistributorSubordinates) {
                for (User user : allUser) {
                    if (k.getUserId().equals(user.getId())) {
                        user.setInfoObj(k);
                    }
                }
            }
        }

        return allUser;
    }

    @Override
    public List<Integer> getSon4Platform(User user) {
        List<Integer> resultList= new ArrayList<>();
        List<UserPlatformSubordinate> subordinates = userPlatformSubordinateService.selectList(new EntityWrapper<UserPlatformSubordinate>()
                .setSqlSelect("user_id")
                .eq("father_id", user.getId()));
        for(UserPlatformSubordinate s: subordinates){
            resultList.add(s.getUserId());
        }
        List<UserDistributor> userDistributors = userDistributorService.selectList(new EntityWrapper<UserDistributor>()
                .setSqlSelect("user_id")
                .eq("father_id", user.getId()));
        for(UserDistributor s: userDistributors){
            resultList.add(s.getUserId());
        }
        List<UserDistributorSubordinate> userDistributorSubordinates = userDistributorSubordinateService.selectList(new EntityWrapper<UserDistributorSubordinate>()
                .setSqlSelect("user_id")
                .eq("father_id", user.getId()));
        for(UserDistributorSubordinate s: userDistributorSubordinates){
            resultList.add(s.getUserId());
        }

        return resultList;
    }

    @Override
    public List<Integer> getSon4Distributor(User user) {
        List<Integer> resultList= new ArrayList<>();
        List<UserDistributorSubordinate> userDistributorSubordinates = userDistributorSubordinateService.selectList(new EntityWrapper<UserDistributorSubordinate>()
                .setSqlSelect("user_id as userId")
                .eq("father_id", user.getId())
        );

        for(UserDistributorSubordinate s: userDistributorSubordinates){
            resultList.add(s.getUserId());
        }

        return resultList;
    }
}
