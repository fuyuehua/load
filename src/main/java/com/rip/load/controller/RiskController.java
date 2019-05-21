package com.rip.load.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.rip.load.pojo.Risk;
import com.rip.load.pojo.RiskRule;
import com.rip.load.pojo.Role;
import com.rip.load.pojo.User;
import com.rip.load.pojo.nativePojo.Result;
import com.rip.load.pojo.nativePojo.UserThreadLocal;
import com.rip.load.service.RiskRuleService;
import com.rip.load.service.RiskService;
import com.rip.load.utils.ResultUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.management.ObjectName;
import java.util.List;

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

    @Autowired
    private RiskRuleService riskRuleService;

    @ApiOperation(value = "增加一个新的风控规则表")
    @PostMapping("/add")
    public Result<Risk> add(@ApiParam(value = "风控规则实体类")
                              @RequestBody Risk risk){

        User user = UserThreadLocal.get();
        risk.setUserId(user.getId());

        Risk name = riskService.selectOne(new EntityWrapper<Risk>().eq("name", risk.getName()));
        if(name != null){
            return new ResultUtil<Risk>().setErrorMsg("已有此名称");
        }
        boolean b = riskService.insert(risk);
        Risk risk1 = riskService.selectOne(new EntityWrapper<Risk>().eq("name", risk.getName()));
        if(b){
            return new ResultUtil<Risk>().setData(risk1);
        }else{
            return new ResultUtil<Risk>().setErrorMsg("储存错误");
        }
    }



    @ApiOperation(value = "查看风控规则表")
    @GetMapping("/listByPage")
    public Result<Page<Risk>> listByPage(@ApiParam(value = "想要请求的页码")
                                       @RequestParam int currentPage,
                                   @ApiParam(value = "一页显示多少数据")
                                       @RequestParam int pageSize){
        User user = UserThreadLocal.get();
        Page<Risk> page = new Page<>(currentPage, pageSize);
        Page<Risk> result = riskService.selectPage(page,new EntityWrapper<Risk>().eq("user_id", user.getId()));
        return new ResultUtil<Page<Risk>>().setData(result);
    }

    @ApiOperation(value = "查看单个风控规则表(渠道商)")
    @GetMapping("/get")
    public Result<Risk> get(@ApiParam(value = "风控规则ID")
                                         @RequestParam int id){
        if(id == 0){
            return new ResultUtil<Risk>().setErrorMsg("ID不能为0");
        }
        User user = UserThreadLocal.get();
        Risk risk = riskService.selectById(id);
        if(risk == null){
            return new ResultUtil<Risk>().setErrorMsg("这个风控表不存在");
        }
        if(!(user.getId().equals(risk.getUserId()))){
            return new ResultUtil<Risk>().setErrorMsg("这个风控表不属于该用户");
        }
        List<RiskRule> list = riskRuleService.selectList(new EntityWrapper<RiskRule>().eq("risk_id", id));
        risk.setRiskRuleList(riskRuleService.setRule4RiskRule(list));
        return new ResultUtil<Risk>().setData(risk);
    }

}
