package com.rip.load.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.rip.load.pojo.Config;
import com.rip.load.pojo.User;
import com.rip.load.pojo.nativePojo.Result;
import com.rip.load.pojo.nativePojo.UserThreadLocal;
import com.rip.load.service.ConfigService;
import com.rip.load.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxh
 * @since 2019-04-02
 */
@Api(tags = {"产品的利率配置"})
@RestController
@RequestMapping("api/config")
public class ConfigController {

    @Autowired
    private ConfigService configService;


    @ApiOperation("各个渠道商自己新增委贷利息等配置")
    @PostMapping("/add")
    public Result<Object> add(
            @ApiParam("系统配置实体类,除了ID，都不能为空")
            @RequestBody Config config
    ){
        User user = UserThreadLocal.get();
        config.setUserId(user.getId());

        boolean b = configService.insert(config);
        b = configService.updateById(config);

        if (b) {
            return new ResultUtil<Object>().set();
        } else {
            return new ResultUtil<Object>().setErrorMsg("数据库错误");
        }
    }

    @ApiOperation("查看自身所有的配置")
    @GetMapping("/getself")
    public Result<List<Config>> getself(){
        User user = UserThreadLocal.get();
        List<Config> uid = configService.selectList(new EntityWrapper<Config>().eq("uid", user.getId()));
        return new ResultUtil<List<Config>>().setData(uid);
    }

//    @ApiOperation("查看配置")
//    @GetMapping("/getAllInPage")
    public Result<Page<Config>> getAllInPage(
            @ApiParam(value = "想要请求的页码")
            @RequestParam int currentPage,
            @ApiParam(value = "一页显示多少数据")
            @RequestParam int pageSize,
            @ApiParam("根据订单状态查找")
            @RequestParam(required = false) Integer status
    ){
        Page<Config> page = new Page<>(currentPage, pageSize);
        Page<Config> configPage = configService.selectPage(page);
        return new ResultUtil<Page<Config>>().setData(configPage);
    }
}
