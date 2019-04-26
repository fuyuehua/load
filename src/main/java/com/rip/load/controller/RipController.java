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

        //获取渠道商秘钥
        User user = UserThreadLocal.get();
        UserCustomer userCustomer = userCustomerService.selectById(user.getId());
        if(userCustomer == null || userCustomer.getFatherId() == null){
            return new ResultUtil<Object>().setErrorMsg("客户未完善信息");
        }
        UserDistributor userDistributor = userDistributorService.selectById(userCustomer.getFatherId());
        if(userDistributor == null){
            return new ResultUtil<Object>().setErrorMsg("客户没有上级");
        }
        if(StringUtils.isEmpty(userDistributor.getUsername()) || StringUtils.isEmpty(userDistributor.getAccessToken())){
            return new ResultUtil<Object>().setErrorMsg("缺少征信秘钥");
        }
        String username = userDistributor.getUsername();
        String accessToken = userDistributor.getAccessToken();

        Map<String, Object> map = new HashMap();
        map.put("username", username);
        map.put("accessToken", accessToken);
        map.put("name", userCustomer.getCellphone());
        map.put("password", password);
        map.put("identityCardNo", userCustomer.getIdcard());
        map.put("identityName", userCustomer.getRealname());

//        if(!StringUtils.isEmpty(contentType))
//            map.put("contentType", contentType);
//        if(!StringUtils.isEmpty(otherInfo))
//            map.put("otherInfo", otherInfo);
        if(!StringUtils.isEmpty(userCustomer.getaRealname()))
            map.put("contactName1st", userCustomer.getaRealname());
        if(!StringUtils.isEmpty(userCustomer.getaPhone()))
            map.put("contactMobile1st", userCustomer.getaPhone());
//        if(!StringUtils.isEmpty(contactIdentityNo1st))
//            map.put("contactIdentityNo1st", contactIdentityNo1st);
        if(!StringUtils.isEmpty(userCustomer.getaRelation()))
            map.put("contactRelationship1st", userCustomer.getaRelation());
        if(!StringUtils.isEmpty(userCustomer.getbRealname()))
            map.put("contactName2nd", userCustomer.getbRealname());
        if(!StringUtils.isEmpty(userCustomer.getbPhone()))
            map.put("contactMobile2nd", userCustomer.getbPhone());
//        if(!StringUtils.isEmpty(contactIdentityNo2nd))
//            map.put("contactIdentityNo2nd", contactIdentityNo2nd);
        if(!StringUtils.isEmpty(userCustomer.getbRelation()))
            map.put("contactRelationship2nd", userCustomer.getbRelation());
//        if(!StringUtils.isEmpty(contactName3rd))
//            map.put("contactName3rd", contactName3rd);
//        if(!StringUtils.isEmpty(contactMobile3rd))
//            map.put("contactMobile3rd", contactMobile3rd);
//        if(!StringUtils.isEmpty(contactIdentityNo3rd))
//            map.put("contactIdentityNo3rd", contactIdentityNo3rd);
//        if(!StringUtils.isEmpty(contactRelationship3rd))
//            map.put("contactRelationship3rd", contactRelationship3rd);
//        if(!StringUtils.isEmpty(score))
//            map.put("score", score);

        String suffixUrl = "/operatorCreditReports/result";
        String json = ripService.ripSetTokenService(map, suffixUrl);
        return new ResultUtil<Object>().setData(json);
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
        String json = ripService.ripSetTokenService(map, suffixUrl);
        return new ResultUtil<Object>().setData(json);
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
        String json = ripService.ripSetTokenService(map, suffixUrl);
        return new ResultUtil<Object>().setData(json);
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
        String json = ripService.ripSetTokenService(map, suffixUrl);
        if(json == null){
            return new ResultUtil<Object>().setErrorMsg("运营商报告获取失败");
        }

        User user = UserThreadLocal.get();

        OperatorReport report = new OperatorReport();
        report.setResultJson(json);
        report.setTime(new Date());
        report.setUserId(user.getId());
        report.setType("10");
        boolean insert = operatorReportService.insert(report);
        if(insert){
            return new ResultUtil<Object>().set();
        }else{
            return new ResultUtil<Object>().setErrorMsg("存储数据库失败");
        }
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



}
