package com.rip.load.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.rip.load.pojo.User;
import com.rip.load.pojo.nativePojo.Result;
import com.rip.load.pojo.nativePojo.UserThreadLocal;
import com.rip.load.service.UserService;
import com.rip.load.utils.MD5Util;
import com.rip.load.utils.MessageUtil;
import com.rip.load.utils.RedisUtil;
import com.rip.load.utils.ResultUtil;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

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
/**
 *
 *
 * 李瑞池 start**/
    @ApiOperation(value = "禁用/启用用户")
    @GetMapping("/prohibitAndnable")
    public Result<Object>prohibiteAndnable(
            @ApiParam("用户ID")
            @RequestParam Integer id){
        User user=userService.selectById(id);
        if(id != user.getId()){
            return new ResultUtil<Object>().setErrorMsg("该用户不存在");
        }
        if(id == user.getId()){
            if (user.getOnoff() == 1){
                 user.setOnoff(0);
            }else{
                user.setOnoff(1);
            }
        }
       return new ResultUtil<Object>().set();
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
    public Result<Object>sendValidate(@ApiParam(value="手机号") @RequestParam String phone){
        if(StringUtils.isEmpty(phone)){
            return new ResultUtil<Object>().setErrorMsg("传入号码为空");
        }
        User user = userService.selectOne(new EntityWrapper<User>().eq("phone", phone));
        if(user == null){
            return new ResultUtil<Object>().setErrorMsg("该用户不存在");
        }
        //假装发验证码
        int i = (int)(Math.random()*900000 + 100000);
        List<String> list = new ArrayList<>();
        list.add(Integer.toString(i));
        String s = MessageUtil.messageHttp(list, phone, null, null);
        redisUtil.set(phone, Integer.toString(i));
        redisUtil.expire(phone, 300);
        return new ResultUtil<Object>().setData(s);
    }

    @ApiOperation(value="验证手机验证码")
    @GetMapping("/validateMessage")
    public Result<Object>validateMessage(@ApiParam(value="手机号") @RequestParam String phone,
                                         @ApiParam(value="验证码") @RequestParam String str){
        if(StringUtils.isEmpty(phone)|| StringUtils.isEmpty(str)){
            return new ResultUtil<Object>().setErrorMsg("传入参数为空");
        }
        User user = userService.selectOne(new EntityWrapper<User>().eq("phone", phone));
        if(user == null){
            return new ResultUtil<Object>().setErrorMsg("该用户不存在");
        }
        boolean b = redisUtil.hasKey(phone);
        if(!b){
            return new ResultUtil<Object>().setErrorMsg("验证码过时或未发送");
        }
        String o = (String)redisUtil.get(phone);
        if(o.equals(str)){
            int i = (int)(Math.random()*900000 + 100000);
            redisUtil.set(phone+"Secret", Integer.toString(i));
            redisUtil.expire(phone+"Secret", 300);
            return new ResultUtil<Object>().setData(Integer.toString(i));

        }else {
            return new ResultUtil<Object>().setErrorMsg("验证失败");
        }
    }

        @ApiOperation(value="重置密码")
        @GetMapping("/resetPassword")
        public Result<Object>resetPassword(@ApiParam(value="手机号") @RequestParam String phone,
                                           @ApiParam(value="新密码") @RequestParam String password,
                                           @ApiParam(value="验证码") @RequestParam String str){
            if(StringUtils.isEmpty(password)|| StringUtils.isEmpty(str)||StringUtils.isEmpty(phone)){
                return new ResultUtil<Object>().setErrorMsg("传入参数为空");
            }
            User user = userService.selectOne(new EntityWrapper<User>().eq("phone", phone));
            if(user == null){
                return new ResultUtil<Object>().setErrorMsg("该用户不存在");
            }
            boolean b = redisUtil.hasKey(phone+"Secret");
            if(!b){
                return new ResultUtil<Object>().setErrorMsg("验证码过时");
            }

            String o = (String)redisUtil.get(phone+"Secret");
            if(o.equals(str)){
                Map<String, String> map = MD5Util.getSecert(password);
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
    /**
     *
     * 李瑞池 end**/


    @ApiOperation(value = "修改一个用户信息")
    @PostMapping("/update")
    public Result<Object> update(@ApiParam(value = "用户实体类") @RequestBody User user){
        if(user.getId() == null || user.getId() == 0 ){
            return new ResultUtil<Object>().setErrorMsg("没传ID");
        }

        User user1 = new User();
        user1.setId(user.getId());
        user1.setNickname(user.getNickname());
//        Map<String, String> map = MD5Util.getSecert(user.getPassword());
//        user1.setPassword(map.get("secert"));
//        user1.setSalt(map.get("salt"));

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
//        Map<String, String> map = MD5Util.getSecert(user.getPassword());
//        user1.setPassword(map.get("secert"));
//        user1.setSalt(map.get("salt"));

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

