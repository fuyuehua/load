package com.rip.load.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.rip.load.pojo.User;
import com.rip.load.pojo.nativePojo.Result;
import com.rip.load.pojo.nativePojo.UserThreadLocal;
import com.rip.load.service.UserService;
import com.rip.load.utils.MD5Util;
import com.rip.load.utils.RedisUtil;
import com.rip.load.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

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

    @ApiOperation(value = "新建一个用户")
    @PostMapping("/add")
    public Result<Object> add(@ApiParam(value = "用户实体类") @RequestBody User user){
        if (StringUtils.isEmpty(user.getUsername())|| StringUtils.isEmpty(user.getPassword())
                || StringUtils.isEmpty(user.getNickname())){
            return new ResultUtil<Object>().setErrorMsg("参数不足");
        }
        //密码处理
        Map<String, String> map = MD5Util.getSecert(user.getPassword());

        User readyUser = new User();
        readyUser.setUsername(user.getUsername());
        readyUser.setPassword(map.get("secert"));
        readyUser.setNickname(user.getNickname());
        readyUser.setSalt(map.get("salt"));

        boolean b = userService.insert(readyUser);
        if(b){
            return new ResultUtil<Object>().set();
        }else{
            return new ResultUtil<Object>().setErrorMsg("注册失败");
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
//        map.put("role", Integer.toString(user.getRole()));
        return new ResultUtil<Object>().setData(map);
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
        Map<String, String> map = MD5Util.getSecert(user.getPassword());
        user1.setPassword(map.get("secert"));
        user1.setSalt(map.get("salt"));

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
        Map<String, String> map = MD5Util.getSecert(user.getPassword());
        user1.setPassword(map.get("secert"));
        user1.setSalt(map.get("salt"));

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


    @ApiOperation("得到用户列表")
    @GetMapping("/getAllInPage")
    public Result<Page<User>> getAllInPage(
            @ApiParam(value = "想要请求的页码")
            @RequestParam int currentPage,
            @ApiParam(value = "一页显示多少数据")
            @RequestParam int pageSize){
        Page<User> page = new Page<>(currentPage, pageSize);
        Page<User> userPage = userService.selectPage(page);
        return new ResultUtil<Page<User>>().setData(userPage);
    }
}

