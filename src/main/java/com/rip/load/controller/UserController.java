package com.rip.load.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.rip.load.pojo.User;
import com.rip.load.pojo.UserCustomer;
import com.rip.load.pojo.UserRole;
import com.rip.load.pojo.nativePojo.Result;
import com.rip.load.pojo.nativePojo.UserThreadLocal;
import com.rip.load.service.UserCustomerService;
import com.rip.load.service.UserRoleService;
import com.rip.load.service.UserService;
import com.rip.load.utils.*;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.management.ObjectName;
import javax.servlet.ServletRequest;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxh
 * @since 2019-03-29
 */
@Api(tags = {"用户控制"})
@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private UserCustomerService userCustomerService;

    @ApiOperation(value = "新建一个用户（管理员）")
    @PostMapping("/add")
    public Result<User> add(@ApiParam(value = "用户实体类") @RequestBody User user){
        if (StringUtils.isEmpty(user.getUsername())|| StringUtils.isEmpty(user.getPassword())
                || StringUtils.isEmpty(user.getNickname()) || StringUtils.isEmpty(user.getPhone())){
            return new ResultUtil<User>().setErrorMsg("参数不足");
        }

        //账户名去重
        User username = userService.selectOne(new EntityWrapper<User>().eq("username", user.getUsername()));
        if(username != null){
            return new ResultUtil<User>().setErrorMsg("用户名重复");
        }

        //密码处理
        Map<String, String> map = MD5Util.getSecert(user.getPassword());
        User readyUser = new User();
        readyUser.setUsername(user.getUsername());
        readyUser.setPassword(map.get("secert"));
        readyUser.setNickname(user.getNickname());
        readyUser.setSalt(map.get("salt"));
        readyUser.setPhone(user.getPhone());
        readyUser.setOnoff(1);
        readyUser.setCreatetime(new Date());

        boolean b = userService.insert(readyUser);
        if(b){
            return new ResultUtil<User>().setData(readyUser);
        }else{
            return new ResultUtil<User>().setErrorMsg("注册失败");
        }
    }

    @ApiOperation(value = "登录")
    @GetMapping("/login")
    public Result<Object> login(
            @ApiParam(value = "用户名") @RequestParam String username,
            @ApiParam(value = "密码") @RequestParam  String password){
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            return new ResultUtil<Object>().setErrorMsg("参数不足");
        }
        User user = userService.selectOne(new EntityWrapper<User>().eq("username", username));
        if(user == null){
            return new ResultUtil<Object>().setErrorMsg("用户不存在");
        }
        boolean b = MD5Util.verifyPassword(password, user.getPassword(), user.getSalt());
        if(!b){
            return new ResultUtil<Object>().setErrorMsg("密码错误");
        }
        String json = JSON.toJSONString(user);
        String token = MD5Util.getToken(json);
        redisUtil.set(token, json);
        redisUtil.expire(token, 28800);
        Map<String, String> map = new HashMap<>();
        map.put("authorization", token);
        map.put("nickname", user.getNickname());
        return new ResultUtil<Object>().setData(map);
    }

    @ApiOperation(value = "登出")
    @GetMapping("/loginOut")
    public Result<Object> loginOut(@RequestHeader(value="authorization") String authorization){
        UserThreadLocal.remove();
        redisUtil.del(authorization);
        return new ResultUtil<Object>().set();
    }

    @ApiOperation(value = "修改一个用户信息")
    @PostMapping("/update")
    public Result<Object> update(@ApiParam(value = "用户实体类") @RequestBody User user){
        if(user.getId() == null || user.getId() == 0 ){
            return new ResultUtil<Object>().setErrorMsg("没传ID");
        }

        User user1 = new User();
        user1.setId(user.getId());
        user1.setNickname(user.getNickname());
        user1.setPhone(user.getPhone());
        boolean b = userService.updateById(user1);
        if(b){
            return new ResultUtil<Object>().set();
        }else{
            return new ResultUtil<Object>().setErrorMsg("修改失败");
        }
    }

    @ApiOperation(value = "用户修改自身信息")
    @PostMapping("/updateSelf")
    public Result<Object> updateSelf(@ApiParam(value = "用户实体类") @RequestBody User user){
        if(user.getId() == null || user.getId() == 0 ){
            return new ResultUtil<Object>().setErrorMsg("没传ID");
        }

        User user1 = UserThreadLocal.get();
        user1.setNickname(user.getNickname());
        user1.setPhone(user.getPhone());
        boolean b = userService.updateById(user1);
        if(b){
            return new ResultUtil<Object>().set();
        }else{
            return new ResultUtil<Object>().setErrorMsg("修改失败");
        }
    }

    @ApiOperation("通过用户ID得到角色以及权限")
    @GetMapping("/getRolePermission")
    public Result<Map<String, Object>> getRolePermission(
            @ApiParam(value = "用户ID")
            @RequestParam int id
    ){
        Map<String, Object> rolePermission = userService.getRolePermission(id);
        return new ResultUtil<Map<String, Object>>().setData(rolePermission);
    }

    @ApiOperation("用户得到自己角色以及权限")
    @GetMapping("/getSelfRolePermission")
    public Result<Map<String, Object>> getSelfRolePermission(
    ){
        User user = UserThreadLocal.get();
        Map<String, Object> rolePermission = userService.getRolePermission(user.getId());
        return new ResultUtil<Map<String, Object>>().setData(rolePermission);
    }

    @ApiOperation("用户得到自身信息")
    @GetMapping("/getSelf")
    public Result<User> getSelf(
    ){
        User user = UserThreadLocal.get();
        return new ResultUtil<User>().setData(user);
    }


    @ApiOperation("得到用户列表")
    @GetMapping("/getAllInPage")
    public Result<Page<User>> getAllInPage(
            @ApiParam(value = "想要请求的页码")
            @RequestParam int currentPage,
            @ApiParam(value = "一页显示多少数据")
            @RequestParam int pageSize,
            @ApiParam(value = "模糊公司名称")
            @RequestParam String nickname){
        Page<User> page = new Page<>(currentPage, pageSize);
        Page<User> userPage = userService.selectPage(page, new EntityWrapper<User>()
                .like("nickname", nickname));
        return new ResultUtil<Page<User>>().setData(userPage);
    }

    /** 李瑞池 start**/
    @ApiOperation(value = "禁用/启用用户")
    @GetMapping("/prohibitAndnable")
    public Result<Object>prohibiteAndnable(
            @ApiParam("用户ID")
            @RequestParam Integer id){
        if(id == null || id == 0){
            return new ResultUtil<Object>().setErrorMsg("参数不足");
        }
        User user=userService.selectById(id);
        if(UserThreadLocal.get().getId().equals(id)){
            return new ResultUtil<Object>().setErrorMsg("不能禁用自己");
        }
        if(user == null){
            return new ResultUtil<Object>().setErrorMsg("该用户不存在");
        }
        if(user.getUsername().equals("admin")){
            return new ResultUtil<Object>().setErrorMsg("无法禁用管理员");
        }

        if (user.getOnoff().equals(1)){
            user.setOnoff(0);
        }else{
            user.setOnoff(1);
        }
        boolean b = userService.updateById(user);
        if(b){
            return new ResultUtil<Object>().set();
        }else{
            return new ResultUtil<Object>().setErrorMsg("修改失败");
        }
    }



    @ApiOperation(value = "已知密码重置")
    @GetMapping("/knowpasswordReset")
    public Result<Object> knowpasswordReset(@ApiParam(value = "用户名") @RequestParam String username,
                                            @ApiParam(value = "密码") @RequestParam  String password,
                                            @ApiParam(value = "新密码")@RequestParam String newPassword){
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            return new ResultUtil<Object>().setErrorMsg("参数不足");
        }
        User user = userService.selectOne(new EntityWrapper<User>().eq("username", username));
        if(user == null){
            return new ResultUtil<Object>().setErrorMsg("用户不存在");
        }
        //知道原密码的前提下修改密码
        boolean b = MD5Util.verifyPassword(password, user.getPassword(), user.getSalt());
        if(!b){
            return new ResultUtil<Object>().setErrorMsg("密码错误");
        }else{
            Map<String, String> map = MD5Util.getSecert(newPassword);
            user.setPassword(map.get("secert"));
            user.setSalt(map.get("salt"));
        }
        boolean b1 = userService.updateById(user);
        if(b1){
            return new ResultUtil<Object>().set();
        }else{
            return new ResultUtil<Object>().setErrorMsg("修改失败");
        }
    }



    @ApiOperation(value="发送手机验证码,以最后一个验证码为准,时效5分钟")
    @GetMapping("/sendValidate")
    public Result<Object>sendValidate(@ApiParam(value="手机号") @RequestParam String phone,
                                      @ApiParam(value="用户名") @RequestParam String username){
        if(StringUtils.isEmpty(phone) || StringUtils.isEmpty(username)){
            return new ResultUtil<Object>().setErrorMsg("传入参数为空");
        }
        User user = userService.selectOne(new EntityWrapper<User>().eq("username", username));
        if(user == null){
            return new ResultUtil<Object>().setErrorMsg("该用户不存在");
        }
        if(!phone.equals(user.getPhone())){
            return new ResultUtil<Object>().setErrorMsg("手机号与用户不匹配");
        }
        //发验证码
        int i = (int)(Math.random()*900000 + 100000);
        List<String> list = new ArrayList<>();
        list.add(Integer.toString(i));
        String s = MessageUtil.messageHttp(list, phone, "500067", "900394");
        redisUtil.set(username, Integer.toString(i));
        redisUtil.expire(username, 300);
        return new ResultUtil<Object>().setData(s);
    }

    @ApiOperation(value="验证手机验证码")
    @GetMapping("/validateMessage")
    public Result<Object>validateMessage(@ApiParam(value="用户名") @RequestParam String username,
                                         @ApiParam(value="验证码") @RequestParam String str,
                                         @ApiParam(value="新密码") @RequestParam String password){
        if(StringUtils.isEmpty(username)|| StringUtils.isEmpty(str) || StringUtils.isEmpty(password)){
            return new ResultUtil<Object>().setErrorMsg("传入参数为空");
        }
        User user = userService.selectOne(new EntityWrapper<User>().eq("username", username));
        if(user == null){
            return new ResultUtil<Object>().setErrorMsg("该用户不存在");
        }
        boolean b = redisUtil.hasKey(username);
        if(!b){
            return new ResultUtil<Object>().setErrorMsg("验证码过时");
        }
        String o = (String)redisUtil.get(username);
        if(o.equals(str)){
            Map<String, String> map = MD5Util.getSecert(password);
            user.setPassword(map.get("secert"));
            user.setSalt(map.get("salt"));
            boolean b1 = userService.updateById(user);
            if(b1){
                return new ResultUtil<Object>().set();
            }else{
                return new ResultUtil<Object>().setErrorMsg("修改失败");
            }
        }else {
            return new ResultUtil<Object>().setErrorMsg("验证失败");
        }
    }
    /** 李瑞池 end**/
    @ApiOperation(value = "客户通过手机号码注册,1.发送手机验证码")
    @GetMapping("/registerByPhone1")
    public Result<Object> registerByPhone1(
            @ApiParam(value = "手机号码")
            @RequestParam  String phone){
        if(StringUtils.isEmpty(phone)){
            return new ResultUtil<Object>().setErrorMsg("未传入手机号");
        }

        //账户名去重
        User username = userService.selectOne(new EntityWrapper<User>().eq("username", phone));
        if(username != null){
            return new ResultUtil<Object>().setErrorMsg("此用户已注册");
        }

        int i = (int)(Math.random()*900000 + 100000);
        List<String> list = new ArrayList<>();
        list.add(Integer.toString(i));
        String s = MessageUtil.messageHttp(list, phone, "500067", "900394");
        redisUtil.set(phone, Integer.toString(i));
        redisUtil.expire(phone, 300);

        return new ResultUtil<Object>().set();
    }

    @ApiOperation(value = "客户通过手机号码注册，2.校验验证码并注册")
    @GetMapping("/registerByPhone2")
    public Result<Object> registerByPhone2(
            @ApiParam(value = "手机号码")
            @RequestParam  String phone,
            @ApiParam(value = "验证码")
            @RequestParam String str,
            @ApiParam("传一个标识这个客户属于某个渠道商的渠道商ID")
            @RequestParam Integer distributorId){
        if(StringUtils.isEmpty(phone)){
            return new ResultUtil<Object>().setErrorMsg("未传入手机号");
        }
        boolean b = redisUtil.hasKey(phone);
        if(!b){
            return new ResultUtil<Object>().setErrorMsg("此手机号验证码失效");
        }
        String secret = (String)redisUtil.get(phone);
        if(!secret.equals(str)){
            return new ResultUtil<Object>().setErrorMsg("验证码错误");
        }
        //密码处理(目前设验证码为其密码)，设置其手机号为账户
        Map<String, String> map = MD5Util.getSecert(str);
        User readyUser = new User();
        readyUser.setUsername(phone);
        readyUser.setPassword(map.get("secert"));
        readyUser.setNickname("待修改个人信息客户");
        readyUser.setSalt(map.get("salt"));
        readyUser.setPhone(phone);
        readyUser.setOnoff(1);
        readyUser.setCreatetime(new Date());
        readyUser.setType(2);

        boolean b1 = userService.insert(readyUser);
        if(!b1){
            return new ResultUtil<Object>().setErrorMsg("产生基础用户失败");
        }

        //给客户绑定客户角色，让其有权限
        UserRole userRole = new UserRole();
        userRole.setUid(readyUser.getId());
        //固定角色：  1.管理员    2.客户角色
        userRole.setRid(2);
        boolean insert = userRoleService.insert(userRole);
        if(!insert){
            return new ResultUtil<Object>().setErrorMsg("赋予角色失败");
        }
        UserCustomer userCustomer = new UserCustomer();
        userCustomer.setUserId(readyUser.getId());
        userCustomer.setFatherId(distributorId);
        boolean insert1 = userCustomerService.insert(userCustomer);
        if(insert1){
            return new ResultUtil<Object>().setData(readyUser);
        }else{
            return new ResultUtil<Object>().setErrorMsg("注册失败");
        }
    }

    @ApiOperation(value = "客户通过手机号码登录，发送验证码")
    @GetMapping("/loginByPhone1")
    public Result<Object> loginByPhone1(
            @ApiParam(value = "手机号码")
            @RequestParam  String phone){
        if(StringUtils.isEmpty(phone)){
            return new ResultUtil<Object>().setErrorMsg("未传入手机号");
        }
        //账户名去重
        User username = userService.selectOne(new EntityWrapper<User>().eq("username", phone));
        if(username == null){
            return new ResultUtil<Object>().setErrorMsg("此用户未注册");
        }

        int i = (int)(Math.random()*900000 + 100000);
        List<String> list = new ArrayList<>();
        list.add(Integer.toString(i));
        String s = MessageUtil.messageHttp(list, phone, "500067", "900394");
        redisUtil.set(phone, Integer.toString(i));
        redisUtil.expire(phone, 300);

        return new ResultUtil<Object>().set();
    }

    @ApiOperation(value = "客户通过手机号登录，校验验证码")
    @GetMapping("/loginByPhone2")
    public Result<Object> loginByPhone2(
            @ApiParam(value = "手机号码")
            @RequestParam  String phone,
            @ApiParam(value = "验证码")
            @RequestParam String str) {
        if (StringUtils.isEmpty(phone)) {
            return new ResultUtil<Object>().setErrorMsg("未传入手机号");
        }
        boolean b = redisUtil.hasKey(phone);
        if (!b) {
            return new ResultUtil<Object>().setErrorMsg("此手机号验证码失效");
        }
        String secret = (String) redisUtil.get(phone);
        if (!secret.equals(str)) {
            return new ResultUtil<Object>().setErrorMsg("验证码错误");
        }
        User user = userService.selectOne(new EntityWrapper<User>().eq("username", phone).eq("phone", phone));

        String json = JSON.toJSONString(user);
        String token = MD5Util.getToken(json);
        redisUtil.set(token, json);
        redisUtil.expire(token, 28800);
        Map<String, String> map = new HashMap<>();
        map.put("authorization", token);
        map.put("nickname", user.getNickname());
        return new ResultUtil<Object>().setData(map);
    }


    /** 全部用户相关接口 **/
    @ApiOperation("查到所有的内部工作人员")
    @GetMapping("/getAllInUser")
    public Result<Page<User>> getAllInUser(@ApiParam(value = "想要请求的页码")
                                               @RequestParam int currentPage,
                                           @ApiParam(value = "一页显示多少数据")
                                               @RequestParam int pageSize,
                                           @ApiParam(value = "用户名模糊查询")
                                               @RequestParam(required = false) String username,
                                           @ApiParam(value = "手机号精确查询")
                                               @RequestParam(required = false) String phone,
                                           @ApiParam(value = "选择角色（3：平台商，4：平台商员工，5：渠道商，6：入件，7：审核，8：复审，9：贷后）")
                                               @RequestParam(required = false) Integer type
                                           ){
        List<Integer> list = new ArrayList<>();
        if(type == null){
            list.add(3);list.add(4);list.add(5);
            list.add(6);list.add(7);list.add(8);
            list.add(9);
        }else if(type == 3 || type == 4 || type == 5 || type == 6 || type == 7 || type == 8 || type == 9 ){
            list.add(type);
        }else{
            list.add(3);list.add(4);list.add(5);
            list.add(6);list.add(7);list.add(8);
            list.add(9);
        }
        Page<User> page = new Page<>(currentPage, pageSize);
        Page<User> userPage = userService.selectPage(page, 
                new EntityWrapper<User>()
                        .eq("onoff", 1)
                        .in("type", list)
                        .like(!(StringUtils.isEmpty(username)), "nickname", username)
                        .eq(!(StringUtils.isEmpty(phone)), "phone", phone)
        );
        List<User> allUser = userPage.getRecords();
        allUser = userService.setRoleAndInfo(allUser);
        userPage.setRecords(allUser);
        return new ResultUtil<Page<User>>().setData(userPage);
    }

    @ApiOperation("平台商查到所有的内部工作人员")
    @GetMapping("/getAllInUserByPlatform")
    public Result<Page<User>> getAllInUserByPlatform(@ApiParam(value = "想要请求的页码")
                                           @RequestParam int currentPage,
                                           @ApiParam(value = "一页显示多少数据")
                                           @RequestParam int pageSize,
                                           @ApiParam(value = "用户名模糊查询")
                                           @RequestParam(required = false) String username,
                                           @ApiParam(value = "手机号精确查询")
                                           @RequestParam(required = false) String phone,
                                           @ApiParam(value = "选择角色（4：平台商员工，5：渠道商，6：入件，7：审核，8：复审，9：贷后）")
                                           @RequestParam(required = false) Integer type
    ){
        List<Integer> list = new ArrayList<>();
        if(type == null){
            list.add(4);list.add(5);
            list.add(6);list.add(7);list.add(8);
            list.add(9);
        }else if(type == 4 || type == 5 || type == 6 || type == 7 || type == 8 || type == 9 ){
            list.add(type);
        }else{
            list.add(4);list.add(5);
            list.add(6);list.add(7);list.add(8);
            list.add(9);
        }
        //平台商看到的下级
        User user = UserThreadLocal.get();
        List<Integer> sonList = userService.getSon4Platform(user);
        Page<User> page = new Page<>(currentPage, pageSize);
        Page<User> userPage = userService.selectPage(page,
                new EntityWrapper<User>()
                        .eq("onoff", 1)
                        .in("type", list)
                        .in("id", sonList)
                        .like(!(StringUtils.isEmpty(username)), "nickname", username)
                        .eq(!(StringUtils.isEmpty(phone)), "phone", phone)

        );
        List<User> allUser = userPage.getRecords();
        allUser = userService.setRoleAndInfo(allUser);
        userPage.setRecords(allUser);
        return new ResultUtil<Page<User>>().setData(userPage);
    }

    @ApiOperation("渠道商查到所有的内部工作人员")
    @GetMapping("/getAllInUserByDistributor")
    public Result<Page<User>> getAllInUserByDistributor(@ApiParam(value = "想要请求的页码")
                                                     @RequestParam int currentPage,
                                                     @ApiParam(value = "一页显示多少数据")
                                                     @RequestParam int pageSize,
                                                     @ApiParam(value = "用户名模糊查询")
                                                     @RequestParam(required = false) String username,
                                                     @ApiParam(value = "手机号精确查询")
                                                     @RequestParam(required = false) String phone,
                                                     @ApiParam(value = "选择角色（6：入件，7：审核，8：复审，9：贷后）")
                                                     @RequestParam(required = false) Integer type
    ){
        List<Integer> list = new ArrayList<>();
        if(type == null){
            list.add(6);list.add(7);list.add(8);
            list.add(9);
        }else if(type == 6 || type == 7 || type == 8 || type == 9 ){
            list.add(type);
        }else{
            list.add(6);list.add(7);list.add(8);
            list.add(9);
        }
        //渠道商看到的下级
        User user = UserThreadLocal.get();
        List<Integer> sonList = userService.getSon4Distributor(user);
        Page<User> page = new Page<>(currentPage, pageSize);
        Page<User> userPage = userService.selectPage(page,
                new EntityWrapper<User>()
                        .eq("onoff", 1)
                        .in("type", list)
                        .in("id", sonList)
                        .like(!(StringUtils.isEmpty(username)), "nickname", username)
                        .eq(!(StringUtils.isEmpty(phone)), "phone", phone)

        );
        List<User> allUser = userPage.getRecords();
        allUser = userService.setRoleAndInfo(allUser);
        userPage.setRecords(allUser);
        return new ResultUtil<Page<User>>().setData(userPage);
    }




}

