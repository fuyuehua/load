package com.rip.load.controller;


import com.rip.load.pojo.BankRecord;
import com.rip.load.pojo.User;
import com.rip.load.pojo.nativePojo.Result;
import com.rip.load.pojo.nativePojo.UserThreadLocal;
import com.rip.load.service.BankFundService;
import com.rip.load.service.BankRecordService;
import com.rip.load.service.UserService;
import com.rip.load.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxh
 * @since 2019-05-29
 */
@Api(tags = {"资金相关接口"})
@RestController
@RequestMapping("api/bankFund")
public class BankFundController {

    @Autowired
    private UserService userService;
    @Autowired
    private BankRecordService bankRecordService;

    @ApiOperation("平台商划拨手续费给渠道商")
    @GetMapping("/platformTransferDistributor")
    public synchronized Result<Object>  platformTransferDistributor(@ApiParam("交易金额")
                                                            @RequestParam String money,
                                                       @ApiParam("收款渠道商ID")
                                                            @RequestParam int payeeId){
        if(StringUtils.isEmpty(money) || payeeId == 0){
            return new ResultUtil<Object>().setErrorMsg("参数不完整");
        }
        User user = userService.selectById(payeeId);
        if(user == null || user.getType() !=5){
            return new ResultUtil<Object>().setErrorMsg("渠道商不存在");
        }
        User platform = UserThreadLocal.get();
        BankRecord bankRecord = new BankRecord();
        bankRecord.setMoney(new BigDecimal(money));
        bankRecord.setCreatetime(new Date());
        bankRecord.setDraweeId(platform.getId());
        bankRecord.setPayeeId(user.getId());
        bankRecord.setOperatorId(platform.getId());
        bankRecord.setType(5);
        bankRecord = bankRecordService.createTransferRecord2Distributor(bankRecord);
        if(bankRecord == null){
            return new ResultUtil<Object>().setErrorMsg("存储失败");
        }
        return new ResultUtil<Object>().setData(bankRecord);

    }
}
