package com.rip.load.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.rip.load.pojo.User;
import com.rip.load.pojo.UserPlatform;
import com.rip.load.pojo.nativePojo.Result;
import com.rip.load.pojo.nativePojo.UserThreadLocal;
import com.rip.load.service.UserPlatformService;
import com.rip.load.service.UserService;
import com.rip.load.utils.MD5Util;
import com.rip.load.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxh
 * @since 2019-04-09
 */
@Api(tags = {"平台控制"})
@RestController
@RequestMapping("api/userPlatform")
public class UserPlatformController {

    @Autowired
    private UserPlatformService userPlatformService;

    @ApiOperation(value = "平台用户自己完善和修改信息")
    @PostMapping("/improve")
    public Result<Object> add(@ApiParam(value = "平台实体类") @RequestBody UserPlatform platform){
        if(platform.getUserId() == null || platform.getUserId() == 0){
            return new ResultUtil<Object>().setErrorMsg("用户ID为空");
        }
        User user = UserThreadLocal.get();
        if(user.getId() != platform.getUserId()){
            return new ResultUtil<Object>().setErrorMsg("被修改用户不是本人");
        }
        boolean b = userPlatformService.insertOrUpdate(platform);
        if(b){
            return new ResultUtil<Object>().set();
        }else{
            return new ResultUtil<Object>().setErrorMsg("完善失败");
        }
    }

    @ApiOperation("得到平台列表")
    @GetMapping("/getAllInPage")
    public Result<Page<UserPlatform>> getAllInPage(
            @ApiParam(value = "想要请求的页码")
            @RequestParam int currentPage,
            @ApiParam(value = "一页显示多少数据")
            @RequestParam int pageSize){
        Page<UserPlatform> page = new Page<>(currentPage, pageSize);
        Page<UserPlatform> userPage = userPlatformService.selectPage(page);
        return new ResultUtil<Page<UserPlatform>>().setData(userPage);
    }

    @ApiOperation("平台得到自身信息")
    @GetMapping("/getSelf")
    public Result<UserPlatform> getSelf(
    ){
        User user = UserThreadLocal.get();
        UserPlatform userPlatform = userPlatformService.selectById(user.getId());
        return new ResultUtil<UserPlatform>().setData(userPlatform);
    }

}
