package com.rip.load.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.rip.load.pojo.User;
import com.rip.load.pojo.UserDistributorSubordinate;
import com.rip.load.pojo.nativePojo.Result;
import com.rip.load.pojo.nativePojo.UserThreadLocal;
import com.rip.load.service.UserDistributorSubordinateService;
import com.rip.load.service.UserService;
import com.rip.load.utils.MD5Util;
import com.rip.load.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxh
 * @since 2019-04-09
 */
@Api(tags = {"渠道商下属控制"})
@RestController
@RequestMapping("api/userDistributorSubordinate")
public class UserDistributorSubordinateController {

    @Autowired
    private UserDistributorSubordinateService subordinateService;
    @Autowired
    private UserService userService;

    @ApiOperation(value = "渠道商创建下属")
    @PostMapping("/addByDistributor")
    public Result<User> addByDistributor(@ApiParam(value = "白户实体类,type传6：入件；7：审核；8：复审；9：贷后")
                                      @Valid  @RequestBody User user){
        Integer type = user.getType();
        if(type == null || !(type == 6 ||type==7 ||type==8||type==9)){
            return new ResultUtil<User>().setErrorMsg("未输入角色");
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
        readyUser.setType(type);
        boolean b = userService.createAndSetRole(readyUser);
        if(b){
            return new ResultUtil<User>().setData(readyUser);
        }else{
            return new ResultUtil<User>().setErrorMsg("注册失败");
        }
    }

    @ApiOperation(value = "渠道商修改下属信息")
    @PostMapping("/updateByDistributor")
    public Result<Object> updateByDistributor(@ApiParam(value = "渠道商下属实体类")
                                  @RequestBody UserDistributorSubordinate subordinate){
        if(subordinate.getUserId() == null || subordinate.getUserId() == 0 || subordinate.getFatherId() == null || subordinate.getFatherId() == 0){
            return new ResultUtil<Object>().setErrorMsg("参数为空");
        }
        User user = userService.selectById(subordinate.getUserId());
        if(user == null){
            return new ResultUtil<Object>().setErrorMsg("该渠道商下属不存在");
        }
        User distributor = UserThreadLocal.get();
        if (distributor.getId() != subordinate.getFatherId()){
            return new ResultUtil<Object>().setErrorMsg("请设置自身ID为此渠道商下属上级ID");
        }
        boolean b = subordinateService.insertOrUpdate(subordinate);
        if(b){
            return new ResultUtil<Object>().set();
        }else{
            return new ResultUtil<Object>().setErrorMsg("完善失败");
        }
    }

    @ApiOperation(value = "管理员修改下属信息")
    @PostMapping("/updateByAdmin")
    public Result<Object> updateByAdmin(@ApiParam(value = "渠道商下属实体类")
                                              @RequestBody UserDistributorSubordinate subordinate){
        if(subordinate.getUserId() == null || subordinate.getUserId() == 0 || subordinate.getFatherId() == null || subordinate.getFatherId() == 0){
            return new ResultUtil<Object>().setErrorMsg("参数为空");
        }
        User user = userService.selectById(subordinate.getUserId());
        if(user == null){
            return new ResultUtil<Object>().setErrorMsg("该渠道商下属不存在");
        }
        boolean b = subordinateService.insertOrUpdate(subordinate);
        if(b){
            return new ResultUtil<Object>().set();
        }else{
            return new ResultUtil<Object>().setErrorMsg("修改失败");
        }
    }

    @ApiOperation(value = "渠道商下属自己修改基本信息")
    @PostMapping("/improveself")
    public Result<Object> improveself(@ApiParam(value = "渠道商下属实体类") @RequestBody UserDistributorSubordinate subordinate){
        User user = UserThreadLocal.get();
        if(user.getId() != subordinate.getUserId()){
            return new ResultUtil<Object>().setErrorMsg("被修改用户不是本人");
        }
        UserDistributorSubordinate userSubordinate = subordinateService.selectById(subordinate.getUserId());

        if(userSubordinate == null){
            return new ResultUtil<Object>().setErrorMsg("没有上级，请联系平台商或管理员");
        }
        userSubordinate.setRealName(subordinate.getRealName());
        boolean b = subordinateService.updateById(userSubordinate);
        if(b){
            return new ResultUtil<Object>().set();
        }else{
            return new ResultUtil<Object>().setErrorMsg("修改失败");
        }
    }

    @ApiOperation("得到渠道商下属列表")
    @GetMapping("/getAllInPage")
    public Result<Page<UserDistributorSubordinate>> getAllInPage(
            @ApiParam(value = "想要请求的页码")
            @RequestParam int currentPage,
            @ApiParam(value = "一页显示多少数据")
            @RequestParam int pageSize,
            @ApiParam(value = "渠道商ID")
            @RequestParam int id){
        Page<UserDistributorSubordinate> page = new Page<>(currentPage, pageSize);
        Page<UserDistributorSubordinate> userPage = subordinateService.selectPage(page, new EntityWrapper<UserDistributorSubordinate>()
                .eq("father_id", id));
        return new ResultUtil<Page<UserDistributorSubordinate>>().setData(userPage);
    }

    @ApiOperation("渠道商下属得到自身信息")
    @GetMapping("/getSelf")
    public Result<UserDistributorSubordinate> getSelf(
    ){
        User user = UserThreadLocal.get();
        UserDistributorSubordinate userPlatform = subordinateService.selectById(user.getId());
        return new ResultUtil<UserDistributorSubordinate>().setData(userPlatform);
    }
}
