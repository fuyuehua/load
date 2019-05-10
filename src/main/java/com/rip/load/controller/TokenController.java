package com.rip.load.controller;


import com.rip.load.pojo.Token;
import com.rip.load.pojo.nativePojo.Result;
import com.rip.load.service.TokenService;
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
 * @since 2019-03-20
 */
@Api(value = "秘钥控制器", tags = {"秘钥控制"})
@RestController
@RequestMapping("api/token")
public class TokenController {

    /*@Autowired
    private TokenService tokenService;

    @ApiOperation(value = "新增一个秘钥")
    @PostMapping("/add")
    public Result<Object> add(
            @ApiParam(value = "秘钥实体类")
            @RequestBody Token token){

        if(StringUtils.isEmpty(token.getAccessToken()) ||StringUtils.isEmpty(token.getUsername()))
            return new ResultUtil<Object>().setErrorMsg("参数不足，除了ID都要传");
        token.setId(null);
        boolean b = tokenService.insert(token);
        if(b){
            return new ResultUtil<Object>().set();
        }else{
            return new ResultUtil<Object>().setErrorMsg("储存错误");
        }
    }

    @ApiOperation("查询所有秘钥")
    @GetMapping("/getAll")
    public Result<List<Token>> getAll(
    ){
        List<Token> list = tokenService.selectList(null);
        return new ResultUtil<List<Token>>().setData(list);
    }*/
}
