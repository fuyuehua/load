package com.rip.load.controller;


import com.rip.load.pojo.Risk;
import com.rip.load.pojo.User;
import com.rip.load.pojo.nativePojo.Result;
import com.rip.load.pojo.nativePojo.UserThreadLocal;
import com.rip.load.service.RiskService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.management.ObjectName;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxh
 * @since 2019-05-09
 */
@RestController
@RequestMapping("api/risk")
public class RiskController {

    @Autowired
    private RiskService riskService;

    @ApiOperation(value = "增加一个新的")
    @PostMapping("/add")
    public Result<Object> add(@ApiParam(value = "风控规则实体类")
                              @RequestBody Risk risk){

        User user = UserThreadLocal.get();
        risk.setUserId(user.getId());
        return null;

    }
}
