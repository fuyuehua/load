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

import java.util.ArrayList;
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

    /**
     * 新增风控(测试)
     * @return
     */
//    @ApiOperation("caozuoqilai")
//    @GetMapping("/test")
    public Result<Object> test(){
        List<Rule> list = new ArrayList<>();
        /*list.add(setTest("客户运营商三要素","客户身份证，手机号，姓名互相匹配","operatorThreeElements","1000",null,null));
        list.add(setTest("紧急联系人A运营商三要素","紧急联系人A身份证，手机号，姓名互相匹配","operatorTwoElementsA","1000",null,null));
        list.add(setTest("紧急联系人B运营商三要素","紧急联系人B身份证，手机号，姓名互相匹配","operatorTwoElementsB","1000",null,null));
        list.add(setTest("手机在网时长","客户手机在网时长大于*param*个月","inTheNetworkTime","1000","12",null));
        list.add(setTest("紧急联系人A手机在网时长","紧急联系人A手机在网时长大于*param*个月","inTheNetworkTimeA","100","6",null));
        list.add(setTest("紧急联系人B手机在网时长","紧急联系人B手机在网时长大于*param*个月","inTheNetworkTimeB","100","6",null));
        list.add(setTest("个人名下关联企业起诉","客户名下企业起诉记录少于10条","personalEnterprise","1000","10",null));
        list.add(setTest("个人名下关联企业行政处罚","客户名下企业行政处罚记录少于*param*条","personalEnterprise2","1000","10",null));
        list.add(setTest("企业工商数据查询起诉","客户在职企业有起诉记录少于*param*条","businessData","1000","10",null));
        list.add(setTest("企业工商数据查询行政处罚","客户在职企业有行政处罚记录少于*param*条","businessData2","1000","10",null));
        list.add(setTest("企业工商数据查询严重违法","客户在职企业有严重违法记录少于*param*条","businessData3","1000","10",null));
        list.add(setTest("个人涉诉","个人涉诉记录少于*param*条","personalComplaintInquiryC","1000","10",null));
*/
        boolean b = ruleService.insertBatch(list);
        if(!b){
            return new ResultUtil<Object>().setErrorMsg("失败");
        }
        return new ResultUtil<Object>().setData(list);
    }

    private Rule setTest(String name, String info, String method, String grade,String paramA,String paramB){
        Rule rule = new Rule();
        rule.setUserId(1);
        rule.setName(name);
        rule.setInfo(info);
        rule.setMethod(method);
        rule.setGrade(grade);
        if(paramA!= null){
            rule.setParamA(paramA);
        }
        if(paramB!=null){
            rule.setParamB(paramB);
        }
        return rule;
    }

}
