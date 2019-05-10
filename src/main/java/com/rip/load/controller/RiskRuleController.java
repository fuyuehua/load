package com.rip.load.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.rip.load.pojo.Risk;
import com.rip.load.pojo.RiskRule;
import com.rip.load.pojo.nativePojo.Result;
import com.rip.load.service.RiskRuleService;
import com.rip.load.service.RiskService;
import com.rip.load.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author zxh
 * @since 2019-05-09
 */
@Api(tags = {"风控规则表和风控规则的连接控制"})
@RestController
@RequestMapping("api/riskRule")
public class RiskRuleController {

    @Autowired
    private RiskRuleService riskRuleService;
    @Autowired
    private RiskService riskService;

    @ApiOperation("批量增加个性风控规则")
    @PostMapping("/addBatch")
    public Result<Object> addBatch(@ApiParam("个性风控规则列表")
                                   @RequestBody List<RiskRule> list){
        Risk risk = riskService.selectOne(new EntityWrapper<Risk>().eq("id", list.get(0).getRiskId()));
        if(risk == null){
            return new ResultUtil<Object>().setErrorMsg("未创建风控规则表");
        }

        return null;

    }


}
