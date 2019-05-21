package com.rip.load.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.rip.load.pojo.*;
import com.rip.load.pojo.nativePojo.Result;
import com.rip.load.pojo.nativePojo.UserThreadLocal;
import com.rip.load.service.TokenService;
import com.rip.load.service.UserDistributorService;
import com.rip.load.service.UserPlatformService;
import com.rip.load.service.UserService;
import com.rip.load.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javafx.scene.effect.Light;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxh
 * @since 2019-04-09
 */
@Api(tags = {"渠道商控制"})
@RestController
@RequestMapping("api/userDistributor")
public class UserDistributorController {


    @Autowired
    private UserDistributorService userDistributorService;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserPlatformService userPlatformService;

    @ApiOperation(value = "渠道商用户自己修改基本信息")
    @PostMapping("/improveself")
    public Result<Object> improveself(@ApiParam(value = "渠道商实体类") @RequestBody UserDistributor distributor){
        User user = UserThreadLocal.get();
        if(user.getId() != distributor.getUserId()){
            return new ResultUtil<Object>().setErrorMsg("被修改用户不是本人");
        }
        UserDistributor userDistributor = userDistributorService.selectById(distributor.getUserId());
        if(userDistributor == null){
            return new ResultUtil<Object>().setErrorMsg("未生成渠道商抽成额度，请联系平台商或管理员");
        }
        userDistributor.setCorporateName(distributor.getCorporateName());
        userDistributor.setCorporateLocation(distributor.getCorporateLocation());
        userDistributor.setCorporatePhone(distributor.getCorporatePhone());
        boolean b = userDistributorService.updateById(userDistributor);
        if(b){
            return new ResultUtil<Object>().set();
        }else{
            return new ResultUtil<Object>().setErrorMsg("完善失败");
        }
    }

//    @ApiOperation(value = "平台完善渠道商信息")
//    @PostMapping("/improve")
    public Result<Object> improve(@ApiParam(value = "渠道商实体类") @Valid @RequestBody UserDistributor distributor){

        User user = userService.selectById(distributor.getUserId());
        if(user == null){
            return new ResultUtil<Object>().setErrorMsg("该渠道商不存在");
        }
        User platform = UserThreadLocal.get();
        if (platform.getId() != distributor.getFatherId()){
            return new ResultUtil<Object>().setErrorMsg("请设置自身ID为此渠道商上级ID");
        }
        boolean b = userDistributorService.insertOrUpdate(distributor);
        if(b){
            return new ResultUtil<Object>().set();
        }else{
            return new ResultUtil<Object>().setErrorMsg("完善失败");
        }
    }

    @ApiOperation(value = "管理员创建渠道商信息(也可以修改)")
    @PostMapping("/adminAdd")
    public Result<Object> adminAdd(@ApiParam(value = "渠道商实体类") @Valid @RequestBody UserDistributor distributor){

        User user = userService.selectById(distributor.getUserId());
        if(user == null){
            return new ResultUtil<Object>().setErrorMsg("该渠道商不存在");
        }
        List<UserPlatform> list = userPlatformService.selectList(null);
        distributor.setFatherId(list.get(0).getUserId());
        boolean b = userDistributorService.insertOrUpdate(distributor);
        if(b){
            return new ResultUtil<Object>().set();
        }else{
            return new ResultUtil<Object>().setErrorMsg("创建失败");
        }
    }

    @ApiOperation("得到渠道商列表")
    @GetMapping("/getAllInPage")
    public Result<Page<UserDistributor>> getAllInPage(
            @ApiParam(value = "想要请求的页码")
            @RequestParam int currentPage,
            @ApiParam(value = "一页显示多少数据")
            @RequestParam int pageSize,
            @ApiParam(value = "平台商ID")
            @RequestParam int id){
        if(id == 0){
            return new ResultUtil<Page<UserDistributor>>().setErrorMsg("id不能为空");
        }
        Page<UserDistributor> page = new Page<>(currentPage, pageSize);
        Page<UserDistributor> userPage = userDistributorService.selectPage(page, new EntityWrapper<UserDistributor>().eq("father_id", id));
        return new ResultUtil<Page<UserDistributor>>().setData(userPage);
    }

    @ApiOperation("得到渠道商列表(管理员)")
    @GetMapping("/getAllInPageAdmin")
    public Result<Page<UserDistributor>> getAllInPageAdmin(
            @ApiParam(value = "想要请求的页码")
            @RequestParam int currentPage,
            @ApiParam(value = "一页显示多少数据")
            @RequestParam int pageSize){
        Page<UserDistributor> page = new Page<>(currentPage, pageSize);
        Page<UserDistributor> userPage = userDistributorService.selectPage(page);
        return new ResultUtil<Page<UserDistributor>>().setData(userPage);
    }

    @ApiOperation("渠道商得到自身信息")
    @GetMapping("/getSelf")
    public Result<UserDistributor> getSelf(
    ){
        User user = UserThreadLocal.get();
        UserDistributor userPlatform = userDistributorService.selectById(user.getId());
        return new ResultUtil<UserDistributor>().setData(userPlatform);
    }

//    @ApiOperation("管理员新增和修改秘钥给渠道商")
//    @PostMapping("/bindToken")
    public Result<Object> bindToken(@ApiParam(value = "渠道商实体类") @RequestBody UserDistributor distributor
    ){

        if(distributor.getUserId() == null || distributor.getUserId() == 0 || distributor.getFatherId() == null || distributor.getFatherId() == 0
                || StringUtils.isEmpty(distributor.getUsername()) || StringUtils.isEmpty(distributor.getAccessToken())){
            return new ResultUtil<Object>().setErrorMsg("参数为空");
        }
        User user = userService.selectById(distributor.getUserId());
        if(user == null){
            return new ResultUtil<Object>().setErrorMsg("该渠道商不存在");
        }

        UserDistributor distributor1 = new UserDistributor();
        distributor1.setUserId(distributor.getUserId());
        distributor1.setUsername(distributor.getUsername());
        distributor1.setAccessToken(distributor.getAccessToken());
        boolean b = userDistributorService.updateById(distributor1);
        if(b){
            return new ResultUtil<Object>().set();
        }else{
            return new ResultUtil<Object>().setErrorMsg("绑定失败");
        }
    }

}
