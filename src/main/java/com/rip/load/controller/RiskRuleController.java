package com.rip.load.controller;


import com.rip.load.service.RiskRuleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxh
 * @since 2019-05-09
 */
@RestController
@RequestMapping("api/riskRule")
public class RiskRuleController {

    @Autowired
    private RiskRuleService riskRuleService;

//    @ApiOperation(value = "增加一个规则")
//    public Result<Object>

}
