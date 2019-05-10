package com.rip.load.controller;


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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation(value = "完善征信信息")
    @PostMapping("/improveInfo")
    public Result<Object> improveInfo(@ApiParam(value = "借贷客户客户信息实体类")
                                          @RequestBody UserCustomer userCustomer){
        if(userCustomer.getUserId() == null || userCustomer.getUserId() == 0 ||
                StringUtils.isEmpty(userCustomer.getRealname()) ||
                StringUtils.isEmpty(userCustomer.getIdcard()) ||
            StringUtils.isEmpty(userCustomer.getCellphone())||
                StringUtils.isEmpty(userCustomer.getBankcard())){
            return new ResultUtil<Object>().setErrorMsg("用户名不能为空");
        }
        User user = userService.selectById(userCustomer.getUserId());
        if(user == null){
            return new ResultUtil<Object>().setErrorMsg("该用户不存在");
        }
        boolean b = userCustomerService.insert(userCustomer);
        if (b) {
            return new ResultUtil<Object>().set();
        } else {
            return new ResultUtil<Object>().setErrorMsg("数据库错误");
        }
    }



}
