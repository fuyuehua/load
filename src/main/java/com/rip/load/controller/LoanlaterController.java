package com.rip.load.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.rip.load.pojo.Loanlater;
import com.rip.load.pojo.User;
import com.rip.load.pojo.UserDistributorSubordinate;
import com.rip.load.pojo.nativePojo.Result;
import com.rip.load.pojo.nativePojo.UserThreadLocal;
import com.rip.load.service.LoanlaterService;
import com.rip.load.service.UserCustomerService;
import com.rip.load.service.UserDistributorSubordinateService;
import com.rip.load.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxh
 * @since 2019-06-11
 */
@Api(tags = {"贷后管理接口"})
@RestController
@RequestMapping("api/loanlater")
public class LoanlaterController {

    @Autowired
    private LoanlaterService loanlaterService;
    @Autowired
    private UserDistributorSubordinateService userDistributorSubordinateService;

    @ApiOperation("查看贷后记录")
    @GetMapping("/listRecord")
    public Result<List<Loanlater>> listRecord(
            @ApiParam("订单ID")
                    @RequestParam
            Integer orderId){
        List<Loanlater> order_id = loanlaterService.selectList(new EntityWrapper<Loanlater>().eq("order_id", orderId));
        return new ResultUtil<List<Loanlater>>().setData(order_id);
    }

    @ApiOperation("新增贷后记录")
    @PostMapping("/setRecord")
    public Result<Loanlater> setRecord(
            @ApiParam("贷后记录实体类, 只传订单号和备注")
            @RequestBody Loanlater loanlater){
        if(loanlater.getOrderId() == null || loanlater.getOrderId() == 0 || StringUtils.isEmpty(loanlater.getRemark())){
            return  new ResultUtil<Loanlater>().setErrorMsg("参数不足");
        }
        User user = UserThreadLocal.get();
        UserDistributorSubordinate subordinate = userDistributorSubordinateService.selectOne(new EntityWrapper<UserDistributorSubordinate>()
                .eq("user_id", user.getId()));
        if(subordinate == null){
            return  new ResultUtil<Loanlater>().setErrorMsg("此用户不是贷后人员");
        }
        loanlater.setOpertor(subordinate.getRealName());
        loanlater.setOpertorId(subordinate.getUserId());
        loanlater.setCreatetime(new Date());
        loanlaterService.insertOrUpdate(loanlater);
        return new ResultUtil<Loanlater>().setData(loanlater);
    }
}
