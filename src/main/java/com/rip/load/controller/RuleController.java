package com.rip.load.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.rip.load.pojo.Rule;
import com.rip.load.pojo.nativePojo.Result;
import com.rip.load.service.RuleService;
import com.rip.load.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxh
 * @since 2019-05-10
 */
@Api(tags = {"风控规则库"})
@RestController
@RequestMapping("api/rule")
public class RuleController {

    @Autowired
    private RuleService ruleService;

    @ApiOperation("列出平台商的所有风控规则")
    @GetMapping("/listAll")
    public Result<List<Rule>> listAll(){

        List<Rule> list = ruleService.selectList(new EntityWrapper<Rule>().eq("onOff", 1));
        return new ResultUtil<List<Rule>>().setData(list);
    }


}
