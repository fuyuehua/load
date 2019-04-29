package com.rip.load.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rip.load.pojo.OperatorReport;
import com.rip.load.pojo.User;
import com.rip.load.pojo.UserCustomer;
import com.rip.load.pojo.UserDistributor;
import com.rip.load.pojo.nativePojo.Result;
import com.rip.load.pojo.nativePojo.UserThreadLocal;
import com.rip.load.service.*;
import com.rip.load.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Api(tags = {"查询征信控制"})
@RestController
@RequestMapping("api/rip")
public class RipController {

    @Autowired
    private UserCustomerService userCustomerService;
    @Autowired
    private UserDistributorService userDistributorService;
    @Autowired
    private RipService ripService;
    @Autowired
    private OperatorReportService operatorReportService;

    /** 运营商信用报告高级版  start**/

    /**
     * 运营商信用报告采集任务提交（立木）

     * @return
     */
    @ApiOperation(value = "提交报告")
    @GetMapping("/operatorCreditReports/result")
    public Result<Object> operatorCreditReportsResult(@ApiParam(value = "运营商密码") @RequestParam  String password) {

        if(StringUtils.isEmpty(password)) {
            return new ResultUtil<Object>().setErrorMsg("params are null");
        }


        Map<String, Object> map = new HashMap();
        map.put("password", password);

        String suffixUrl = "/operatorCreditReports/result";
        Map<String, String> map1 = ripService.operatorCreditReportsService(0, map, suffixUrl);
        String result = map1.get("result");
        if(result.equals("1"))
            return new ResultUtil<Object>().setData(map1.get("data"));
        else
            return new ResultUtil<Object>().setData(result);
    }
    /**
     * 轮询运营商报告采集状态（立木轮询）
     * @param token
     * @return
     */
    @ApiOperation(value = "轮询查询状态")
    @GetMapping("/operatorCreditReports/status")
    public Result<Object> operatorCreditReportsStatus(String token) {

        if(StringUtils.isEmpty(token)) {
            return new ResultUtil<Object>().setErrorMsg("params are null");
        }
        Map<String, Object> map = new HashMap();
        map.put("token", token);

        String suffixUrl = "/operatorCreditReports/status";
        Map<String, String> map1 = ripService.operatorCreditReportsService(0, map, suffixUrl);
        String result = map1.get("result");
        if(result.equals("1"))
            return new ResultUtil<Object>().setData(map1.get("data"));
        else
            return new ResultUtil<Object>().setData(result);
    }

    /**
     * 运营商报告验证码输入(立木轮询)
     * @param token
     * @param input
     * @return
     */
    @ApiOperation(value = "输入验证码")
    @GetMapping("/operatorCreditReports/input")
    public Result<Object> operatorCreditReportsInput(String token, String input) {

        if(StringUtils.isEmpty(token) || StringUtils.isEmpty(input)) {
            return new ResultUtil<Object>().setErrorMsg("params are null");
        }
        Map<String, Object> map = new HashMap();
        map.put("token", token);
        map.put("input", input);

        String suffixUrl = "/operatorCreditReports/input";
        Map<String, String> map1 = ripService.operatorCreditReportsService(0, map, suffixUrl);
        String result = map1.get("result");
        if(result.equals("1"))
            return new ResultUtil<Object>().setData(map1.get("data"));
        else
            return new ResultUtil<Object>().setData(result);
    }

    /**
     * 运营商报告结果获取（立木）
     * @param token
     * @param rip_id
     * @return
     */
    @ApiOperation(value = "获取报告")
    @GetMapping("/operatorCreditReports/report")
    public Result<Object> operatorCreditReportsReport(String token, String rip_id) {

        if(StringUtils.isEmpty(token) || StringUtils.isEmpty(rip_id)) {
            return new ResultUtil<Object>().setErrorMsg("params are null");
        }
        Map<String, Object> map = new HashMap();
        map.put("token", token);
        map.put("rip_id", rip_id);

        String suffixUrl = "/operatorCreditReports/report";
        Map<String, String> map1 = ripService.operatorCreditReportsService(0, map, suffixUrl);
        String result = map1.get("result");
        if(result.equals("1"))
            return new ResultUtil<Object>().setData(map1.get("data"));
        else
            return new ResultUtil<Object>().setData(result);
    }
    /** 运营商信用报告高级版  end**/

    /** 身份证二要素 **/
    @ApiOperation("身份证二要素")
    @GetMapping("/idCardElements")
    public Result<Object> idCardElements() {

        String s = ripService.idCardElementsService(0);
        if(!s.equals("1")){
            return new ResultUtil<Object>().setErrorMsg(s);
        }
        return new ResultUtil<Object>().set();
    }
    @ApiOperation(value = "运营商三要素")
    @GetMapping("/operatorThreeElements")
    public Result<Object> operatorThreeElements() {

        String s = ripService.operatorThreeElementsService(0);
        if(!s.equals("1")){
            return new ResultUtil<Object>().setErrorMsg(s);
        }
        return new ResultUtil<Object>().set();
    }
    @ApiOperation(value = "手机在网时长")
    @GetMapping("/inTheNetworkTime")
    public Result<Object> inTheNetworkTime() {

        String s = ripService.inTheNetworkTimeService(0);
        if(!s.equals("1")){
            return new ResultUtil<Object>().setErrorMsg(s);
        }
        return new ResultUtil<Object>().set();
    }
    @ApiOperation(value = "车辆详情核验")
    @GetMapping("/vehicleDetailsEnquiry")
    public Result<Object> vehicleDetailsEnquiry() {

        String s = ripService.vehicleDetailsEnquiryService(0);
        if(!s.equals("1")){
            return new ResultUtil<Object>().setErrorMsg(s);
        }
        return new ResultUtil<Object>().set();
    }
    @ApiOperation(value = "企业工商数据查询")
    @GetMapping("/businessData")
    public Result<Object> businessData() {

        String s = ripService.businessDataService(0);
        if(!s.equals("1")){
            return new ResultUtil<Object>().setErrorMsg(s);
        }
        return new ResultUtil<Object>().set();
    }
    @ApiOperation(value = "个人名下关联企业")
    @GetMapping("/personalEnterprise")
    public Result<Object> personalEnterprise() {

        String s = ripService.personalEnterpriseService(0);
        if(!s.equals("1")){
            return new ResultUtil<Object>().setErrorMsg(s);
        }
        return new ResultUtil<Object>().set();
    }
//    @ApiOperation(value = "公安不良信息")
//    @GetMapping("/operatorThreeElements")
//    public Result<Object> operatorThreeElements() {
//
//        String s = ripService.idCardElementsService(0);
//        if(!s.equals("1")){
//            return new ResultUtil<Object>().setErrorMsg(s);
//        }
//        return new ResultUtil<Object>().set();
//    }
    @ApiOperation(value = "综合风险查询")
    @GetMapping("/personalRiskInformation")
    public Result<Object> personalRiskInformation() {

        String s = ripService.personalRiskInformationService(0);
        if(!s.equals("1")){
            return new ResultUtil<Object>().setErrorMsg(s);
        }
        return new ResultUtil<Object>().set();
    }

    @ApiOperation(value = "个人涉诉报告高级版")
    @GetMapping("/personalComplaintInquiryC")
    public Result<Object> personalComplaintInquiryC() {

        String s = ripService.personalComplaintInquiryCService(0);
        if(!s.equals("1")){
            return new ResultUtil<Object>().setErrorMsg(s);
        }
        return new ResultUtil<Object>().set();
    }

    @ApiOperation(value = "银行卡四要素")
    @GetMapping("/fourElementsOfBankCard")
    public Result<Object> fourElementsOfBankCard() {
        //fourElementsOfBankCard/result
        String s = ripService.fourElementsOfBankCardService(0);
        if(!s.equals("1")){
            return new ResultUtil<Object>().setErrorMsg(s);
        }
        return new ResultUtil<Object>().set();
    }

    /** 淘宝报告 **/
    /**
     * 淘宝查询
     *
     * @return
     */
    @ApiOperation(value = "淘宝查询")
    @GetMapping("/taoBaoGet")
    public Result<Object> taoBaoGet() {
        Map<String, Object> map = new HashMap();
        String suffixUrl = "/taoBao/get";
        Map<String, String> map1 = ripService.taoBaoService(0, map, suffixUrl);
        String result = map1.get("result");
        if(result.equals("1"))
            return new ResultUtil<Object>().setData(map1.get("data"));
        else
            return new ResultUtil<Object>().setData(result);
    }

    /**
     * 轮询查看状态
     *
     * @param token
     * @return
     */
    @ApiOperation(value = "轮询查看状态")
    @GetMapping("/getStatus")
    public Result<Object> getStatus(String token, String ripId, String biztype) {
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(ripId) || StringUtils.isEmpty(biztype))
            return new ResultUtil<Object>().setErrorMsg("参数不足");

        Map<String, Object> map = new HashMap();
        map.put("token", token);
        map.put("rip_id", ripId);
        map.put("biztype", biztype);

        String suffixUrl = "/limu/validate/getStatus";
        Map<String, String> map1 = ripService.taoBaoService(0, map, suffixUrl);
        String result = map1.get("result");
        if(result.equals("1"))
            return new ResultUtil<Object>().setData(map1.get("data"));
        else
            return new ResultUtil<Object>().setData(result);
    }

    /**
     * 输入
     *
     * @param token
     * @param sign
     * @return
     */
    @ApiOperation(value = "输入输入")
    @GetMapping("/getInput")
    public Result<Object> getInput(String token, String sign) {
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(sign))
            return new ResultUtil<Object>().setErrorMsg("参数不足");

        Map<String, Object> map = new HashMap();
        map.put("token", token);
        map.put("sign", sign);

        String suffixUrl = "/limu/validate/getInput";
        Map<String, String> map1 = ripService.taoBaoService(0, map, suffixUrl);
        String result = map1.get("result");
        if(result.equals("1"))
            return new ResultUtil<Object>().setData(map1.get("data"));
        else
            return new ResultUtil<Object>().setData(result);
    }

    /**
     * 得到结果
     *
     * @param token
     * @param ripId
     * @param biztype
     * @return
     */
    @ApiOperation(value = "得到结果")
    @GetMapping("/getResult")
    public Result<Object> getResult(String token, String ripId, String biztype) {
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(ripId) || StringUtils.isEmpty(biztype))
            return new ResultUtil<Object>().setErrorMsg("参数不足");

        Map<String, Object> map = new HashMap();
        map.put("token", token);
        map.put("rip_id", ripId);
        map.put("biztype", biztype);

        String suffixUrl = "/limu/validate/getResult";
        Map<String, String> map1 = ripService.taoBaoService(0, map, suffixUrl);
        String result = map1.get("result");
        if(result.equals("1"))
            return new ResultUtil<Object>().setData(map1.get("data"));
        else
            return new ResultUtil<Object>().setData(result);
    }

}
