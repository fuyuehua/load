package com.rip.load.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.rip.load.pojo.User;
import com.rip.load.pojo.UserCustomer;
import com.rip.load.pojo.nativePojo.Result;
import com.rip.load.service.UserCustomerService;
import com.rip.load.service.UserService;
import com.rip.load.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxh
 * @since 2019-04-16
 */
@Api(tags = "借贷客户控制")
@RestController
@RequestMapping("api/userCustomer")
public class UserCustomerController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserCustomerService userCustomerService;

    @ApiOperation(value = "完善客户信息")
    @PostMapping("/improveInfo")
    public Result<Object> improveInfo(@ApiParam(value = "借贷客户客户信息实体类")
                                          @RequestBody UserCustomer userCustomer){
        if(userCustomer.getUserId() == 0){
            return new ResultUtil<Object>().setErrorMsg("用户ID不能为空");
        }
        userCustomer.setStatus(null);
        User user = userService.selectById(userCustomer.getUserId());
        if(user == null){
            return new ResultUtil<Object>().setErrorMsg("该用户不存在");
        }
        String aRelation = userCustomer.getaRelation();
        String bRelation = userCustomer.getbRelation();
        if(!(aRelation.equals("FATHER") || aRelation.equals("MOTHER") || aRelation.equals("SPOUSE") ||
                aRelation.equals("CHILD") ||aRelation.equals("OTHER_RELATIVE") ||aRelation.equals("FRIEND") ||
                aRelation.equals("COWORKER") ||aRelation.equals("OTHERS"))){
            return new ResultUtil<Object>().setErrorMsg("紧急联系人关系A输入错误");
        }
        if(!(bRelation.equals("FATHER") || bRelation.equals("MOTHER") || bRelation.equals("SPOUSE") ||
                bRelation.equals("CHILD") ||bRelation.equals("OTHER_RELATIVE") ||bRelation.equals("FRIEND") ||
                bRelation.equals("COWORKER") ||bRelation.equals("OTHERS"))){
            return new ResultUtil<Object>().setErrorMsg("紧急联系人关系B输入错误");
        }
        if(userCustomer.getInfoStatus().equals("3")){
            userCustomer.setInfoStatus("4");
        }
        boolean b = userCustomerService.update(userCustomer,new EntityWrapper<UserCustomer>().eq("userId", userCustomer.getUserId()));
        if (b) {
            return new ResultUtil<Object>().set();
        } else {
            return new ResultUtil<Object>().setErrorMsg("数据库错误");
        }
    }

    @ApiOperation(value = "完善客户其他信息（车牌号之类的）")
    @GetMapping("/improveOtherInfo")
    public Result<Object> improveOtherInfo(
                                      @RequestParam int userId,
                                       String plateNumber,
                                       String workUnit,
                                       String workAddress,
                                       String socialSecurity,
                                      @RequestParam String aRealname,
                                      @RequestParam String aPhone,
                                      @RequestParam String aRelation,
                                      @RequestParam String bRealname,
                                      @RequestParam String bPhone,
                                      @RequestParam String bRelation
                                      ){
        if(
                StringUtils.isEmpty(aRealname) ||
                StringUtils.isEmpty(aPhone) ||
                StringUtils.isEmpty(aRelation) ||
                StringUtils.isEmpty(bRealname) ||
                StringUtils.isEmpty(bPhone) ||
                StringUtils.isEmpty(bRelation)
        )
            return new ResultUtil<Object>().setErrorMsg("必填项为空");
        if(!(aRelation.equals("FATHER") || aRelation.equals("MOTHER") || aRelation.equals("SPOUSE") ||
                aRelation.equals("CHILD") ||aRelation.equals("OTHER_RELATIVE") ||aRelation.equals("FRIEND") ||
                aRelation.equals("COWORKER") ||aRelation.equals("OTHERS"))){
            return new ResultUtil<Object>().setErrorMsg("紧急联系人关系A输入错误");
        }
        if(!(bRelation.equals("FATHER") || bRelation.equals("MOTHER") || bRelation.equals("SPOUSE") ||
                bRelation.equals("CHILD") ||bRelation.equals("OTHER_RELATIVE") ||bRelation.equals("FRIEND") ||
                bRelation.equals("COWORKER") ||bRelation.equals("OTHERS"))){
            return new ResultUtil<Object>().setErrorMsg("紧急联系人关系B输入错误");
        }
        if(userId == 0){
            return new ResultUtil<Object>().setErrorMsg("用户ID不能为空");
        }
        UserCustomer customer = userCustomerService.selectOne(new EntityWrapper<UserCustomer>().eq("userId", userId));
        if(customer == null){
            return new ResultUtil<Object>().setErrorMsg("该用户不存在");
        }
        if(customer.getInfoStatus().equals("2"))
            customer.setInfoStatus("3");
        customer.setPlateNumber(plateNumber);
        customer.setWorkAddress(workAddress);
        customer.setWorkUnit(workUnit);
        customer.setSocialSecurity(socialSecurity);
        customer.setaRealname(aRealname);
        customer.setaPhone(aPhone);
        customer.setaRelation(aRelation);
        customer.setbRealname(bRealname);
        customer.setbPhone(bPhone);
        customer.setbRelation(bRelation);
        boolean b = userCustomerService.update(customer,new EntityWrapper<UserCustomer>().eq("userId", userId));
        if (b) {
            return new ResultUtil<Object>().set();
        } else {
            return new ResultUtil<Object>().setErrorMsg("数据库错误");
        }
    }

    @ApiOperation(value = "得到客户信息")
    @PostMapping("/getInfo")
    public Result<Object> getInfo(@ApiParam(value = "客户ID")
                                      @RequestParam int userId){
        if(userId == 0){
            return new ResultUtil<Object>().setErrorMsg("用户ID不能为0");
        }
        UserCustomer customer = userCustomerService.selectOne(new EntityWrapper<UserCustomer>().eq("userId", userId));

        if(customer == null){
            return new ResultUtil<Object>().setErrorMsg("该用户不存在");
        }
        /*if(customer.getInfoStatus().equals("3")){
            customer.setInfoStatus("4");
            userCustomerService.updateById(customer);
        }*/
        return new ResultUtil<Object>().setData(customer);
    }

    @ApiOperation("通过手机号查询客户")
    @GetMapping("/getCustomerByPhone")
    public Result<User> getCustomerByPhone(
            @ApiParam(value = "手机号码查询")
            @RequestParam(required = true) String phone
    ){
        List<User> users = userService.selectList(
                new EntityWrapper<User>()
                        .eq("onoff", 1)
                        .eq(!(StringUtils.isEmpty(phone)), "username", phone)
        );
        users = userService.setRoleAndInfo(users);
        return new ResultUtil<User>().setData(users.get(0));
    }


    @ApiOperation(value = "加入黑名单")
    @GetMapping("/sendBlackList")
    public Result<Object> sendBlackList(@ApiParam(value = "客户ID")
                                      @RequestParam int userId,
                                        @ApiParam(value = "是否加入黑名单 1：加入 0：拿出黑名单")
                                        @RequestParam int type){
        if(userId == 0){
            return new ResultUtil<Object>().setErrorMsg("用户ID不能为空");
        }
        UserCustomer customer = userCustomerService.selectOne(new EntityWrapper<UserCustomer>().eq("userId", userId));
        if(customer == null){
            return new ResultUtil<Object>().setErrorMsg("该用户不存在");
        }
        if(type == 1) {
            customer.setStatus(7);
        }else {
            customer.setStatus(0);
        }
        boolean b = userCustomerService.update(customer, new EntityWrapper<UserCustomer>().eq("userId",customer.getUserId()));
        if (b) {
            return new ResultUtil<Object>().set();
        } else {
            return new ResultUtil<Object>().setErrorMsg("数据库错误");
        }
    }
}
