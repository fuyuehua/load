package com.rip.load.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.rip.load.pojo.*;
import com.rip.load.pojo.nativePojo.Result;
import com.rip.load.pojo.nativePojo.UserThreadLocal;
import com.rip.load.service.*;
import com.rip.load.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Api(tags = {"查询征信控制"})
@RestController
@RequestMapping("api/rip")
public class RipController {

    @Autowired
    private RipService ripService;
    @Autowired
    private UserCustomerService userCustomerService;

    /** 运营商信用报告高级版  start**/

    /**
     * 运营商信用报告采集任务提交（立木）

     * @return
     */
    @ApiOperation(value = "提交报告")
    @GetMapping("/operatorCreditReports/result")
    public Result<Object> operatorCreditReportsResult(int id, @ApiParam(value = "运营商密码") @RequestParam  String password) {

        if(StringUtils.isEmpty(password)) {
            return new ResultUtil<Object>().setErrorMsg("params are null");
        }


        Map<String, Object> map = new HashMap();
        map.put("password", password);

        String suffixUrl = "/operatorCreditReports/result";
        Map<String, String> map1 = ripService.operatorCreditReportsService(id, map, suffixUrl,null);
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
    public Result<Object> operatorCreditReportsStatus(int id, String token) {

        if(StringUtils.isEmpty(token)) {
            return new ResultUtil<Object>().setErrorMsg("params are null");
        }
        Map<String, Object> map = new HashMap();
        map.put("token", token);

        String suffixUrl = "/operatorCreditReports/status";
        Map<String, String> map1 = ripService.operatorCreditReportsService(id, map, suffixUrl,null);
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
    public Result<Object> operatorCreditReportsInput(int id, String token, String input) {

        if(StringUtils.isEmpty(token) || StringUtils.isEmpty(input)) {
            return new ResultUtil<Object>().setErrorMsg("params are null");
        }
        Map<String, Object> map = new HashMap();
        map.put("token", token);
        map.put("input", input);

        String suffixUrl = "/operatorCreditReports/input";
        Map<String, String> map1 = ripService.operatorCreditReportsService(id, map, suffixUrl, null);
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
    public Result<Object> operatorCreditReportsReport(int id, String token, String rip_id, Integer reportId) {

        if(StringUtils.isEmpty(token) || StringUtils.isEmpty(rip_id)) {
            return new ResultUtil<Object>().setErrorMsg("params are null");
        }
        Map<String, Object> map = new HashMap();
        map.put("token", token);
        map.put("rip_id", rip_id);

        String suffixUrl = "/operatorCreditReports/report";
        Map<String, String> map1 = ripService.operatorCreditReportsService(id, map, suffixUrl,reportId);
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
    public Result<Object> idCardElements(int id) {

        String s = ripService.idCardElementsService(id, null);
        if(!s.equals("1")){
            return new ResultUtil<Object>().setErrorMsg(s);
        }
        return new ResultUtil<Object>().set();
    }
    @ApiOperation(value = "运营商三要素")
    @GetMapping("/operatorThreeElements")
    public Result<Object> operatorThreeElements(int id) {

        String s = ripService.operatorThreeElementsService(id, null);
        if(!s.equals("1")){
            return new ResultUtil<Object>().setErrorMsg(s);
        }
        return new ResultUtil<Object>().set();
    }
    @ApiOperation(value = "运营商二要素")
    @GetMapping("/operatorTwoElements")
    public Result<Object> operatorTwoElements(int id) {

        String s = ripService.operatorTwoElementsService(id,null);
        if(!s.equals("1")){
            return new ResultUtil<Object>().setErrorMsg(s);
        }
        return new ResultUtil<Object>().set();
    }
    @ApiOperation(value = "手机在网时长")
    @GetMapping("/inTheNetworkTime")
    public Result<Object> inTheNetworkTime(int id) {

        String s = ripService.inTheNetworkTimeService(id, null);
        if(!s.equals("1")){
            return new ResultUtil<Object>().setErrorMsg(s);
        }
        return new ResultUtil<Object>().set();
    }
    @ApiOperation(value = "车辆详情核验")
    @GetMapping("/vehicleDetailsEnquiry")
    public Result<Object> vehicleDetailsEnquiry(int id) {

        String s = ripService.vehicleDetailsEnquiryService(id,null);
        if(!s.equals("1")){
            return new ResultUtil<Object>().setErrorMsg(s);
        }
        return new ResultUtil<Object>().set();
    }
    @ApiOperation(value = "企业工商数据查询")
    @GetMapping("/businessData")
    public Result<Object> businessData(int id) {
        String s = ripService.businessDataService(id, null);
        if(!s.equals("1")){
            return new ResultUtil<Object>().setErrorMsg(s);
        }
        return new ResultUtil<Object>().set();
    }
    @ApiOperation(value = "个人名下关联企业")
    @GetMapping("/personalEnterprise")
    public Result<Object> personalEnterprise(int id) {

        String s = ripService.personalEnterpriseService(id,null);
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
    public Result<Object> personalRiskInformation(int id) {

        String s = ripService.personalRiskInformationService(id,null);
        if(!s.equals("1")){
            return new ResultUtil<Object>().setErrorMsg(s);
        }
        return new ResultUtil<Object>().set();
    }

    @ApiOperation(value = "个人涉诉报告高级版")
    @GetMapping("/personalComplaintInquiryC")
    public Result<Object> personalComplaintInquiryC(int id) {

        String s = ripService.personalComplaintInquiryCService(id,null);
        if(!s.equals("1")){
            return new ResultUtil<Object>().setErrorMsg(s);
        }
        return new ResultUtil<Object>().set();
    }

    @ApiOperation(value = "银行卡四要素")
    @GetMapping("/fourElementsOfBankCard")
    public Result<Object> fourElementsOfBankCard(int id) {
        //fourElementsOfBankCard/result
        String s = ripService.fourElementsOfBankCardService(id);
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
    public Result<Object> taoBaoGet(int id) {
        Map<String, Object> map = new HashMap();
        String suffixUrl = "/taoBao/get";
        Map<String, String> map1 = ripService.taoBaoService(id, map, suffixUrl,null);
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
    public Result<Object> getStatus(int id, String token, String ripId, String biztype) {
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(ripId) || StringUtils.isEmpty(biztype))
            return new ResultUtil<Object>().setErrorMsg("参数不足");

        Map<String, Object> map = new HashMap();
        map.put("token", token);
        map.put("rip_id", ripId);
        map.put("biztype", biztype);

        String suffixUrl = "/limu/validate/getStatus";
        Map<String, String> map1 = ripService.taoBaoService(id, map, suffixUrl,null);
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
    public Result<Object> getInput(int id, String token, String sign) {
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(sign))
            return new ResultUtil<Object>().setErrorMsg("参数不足");

        Map<String, Object> map = new HashMap();
        map.put("token", token);
        map.put("sign", sign);

        String suffixUrl = "/limu/validate/getInput";
        Map<String, String> map1 = ripService.taoBaoService(id, map, suffixUrl, null);
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
    public Result<Object> getResult(int id ,String token, String ripId, String biztype, Integer reportId) {
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(ripId) || StringUtils.isEmpty(biztype))
            return new ResultUtil<Object>().setErrorMsg("参数不足");

        Map<String, Object> map = new HashMap();
        map.put("token", token);
        map.put("rip_id", ripId);
        map.put("biztype", biztype);

        String suffixUrl = "/limu/validate/getResult";
        Map<String, String> map1 = ripService.taoBaoService(id, map, suffixUrl, reportId);
        String result = map1.get("result");
        if(result.equals("1"))
            return new ResultUtil<Object>().setData(map1.get("data"));
        else
            return new ResultUtil<Object>().setData(result);
    }

    /**
     * 身份证照片识别
     *
     * @return
     */
    @ApiOperation(value = "身份证照片识别")
    @PostMapping("/idcardPhoto")
    public Result<Object> idcardPhoto(int id, String idCardSide, String base64Data) {
        if (StringUtils.isEmpty(idCardSide) || StringUtils.isEmpty(base64Data))
            return new ResultUtil<Object>().setErrorMsg("参数不足");

        Map<String, Object> map = new HashMap();
        map.put("idCardSide", idCardSide);
        map.put("base64Data", base64Data);

        String suffixUrl = "/identificationbd/result";
        Map<String, String> map1 = ripService.idcardPhotoService(id, map, suffixUrl);
        String result = map1.get("result");
        if(result.equals("1"))
            return new ResultUtil<Object>().setData(map1);
        else
            return new ResultUtil<Object>().setData(result);
    }

    /**
     * 身份证照片识别
     *
     * @return
     */
    @ApiOperation(value = "完善照片识别的所有信息")
    @GetMapping("/AllInfoBack")
    public Result<Object> AllInfoBack(Integer id,
                                          String idcardName,
                                          String idcardSex,
                                          String idcardNation,
                                          String idcardBirthday,
                                          String idcardAddress,
                                          String idcardIdcard,
                                          String idcardPhotoa,
                                          String idcardExpiration,
                                          String idcardLocation,
                                          String idcardSign,
                                          String idcardPhotob,

                                          String bankcardCardnumber,
                                          String bankcardCardtype,
                                          String bankcardCardname,
                                          String bankcardPhoto,

                                          String facePhotoa,
                                          String facePhotob,
                                          String facePhotoc
                                          ) {
        if (StringUtils.isEmpty(idcardName) ||
                StringUtils.isEmpty(idcardSex) ||
                StringUtils.isEmpty(idcardNation) ||
                StringUtils.isEmpty(idcardBirthday) ||
                StringUtils.isEmpty(idcardAddress) ||
                StringUtils.isEmpty(idcardIdcard) ||
                StringUtils.isEmpty(idcardPhotoa) ||
                StringUtils.isEmpty(idcardExpiration) ||
                StringUtils.isEmpty(idcardLocation) ||
                StringUtils.isEmpty(idcardSign) ||
                StringUtils.isEmpty(idcardPhotob) ||
                StringUtils.isEmpty(bankcardCardnumber) ||
                StringUtils.isEmpty(bankcardCardtype) ||
                StringUtils.isEmpty(bankcardCardname) ||
                StringUtils.isEmpty(bankcardPhoto) ||
                StringUtils.isEmpty(facePhotoa) ||
                StringUtils.isEmpty(facePhotob) ||
                StringUtils.isEmpty(facePhotoc)
        )
            return new ResultUtil<Object>().setErrorMsg("参数不足");

        if(id == null || id == 0){
            User user = UserThreadLocal.get();
            id = user.getId();
        }
        UserCustomer userCustomer = userCustomerService.selectOne(new EntityWrapper<UserCustomer>().eq("userId", id));
        if(userCustomer == null){
            return new ResultUtil<Object>().setErrorMsg("客户不存在");
        }
        //正面
        userCustomer.setIdcardName(idcardName);
        userCustomer.setIdcardSex(idcardSex);
        userCustomer.setIdcardNation(idcardNation);
        userCustomer.setIdcardBirthday(idcardBirthday);
        userCustomer.setIdcardAddress(idcardAddress);
        userCustomer.setIdcardIdcard(idcardIdcard);
        userCustomer.setIdcardPhotoa(idcardPhotoa);
        //反面
        userCustomer.setIdcardExpiration(idcardExpiration);
        userCustomer.setIdcardLocation(idcardLocation);
        userCustomer.setIdcardSign(idcardSign);
        userCustomer.setIdcardPhotob(idcardPhotob);

        //银行卡
        userCustomer.setBankcardCardnumber(bankcardCardnumber);
        userCustomer.setBankcardCardtype(bankcardCardtype);
        userCustomer.setBankcardCardname(bankcardCardname);
        userCustomer.setBankcardPhoto(bankcardPhoto);

        //人脸识别
        userCustomer.setFacePhotoa(facePhotoa);
        userCustomer.setFacePhotob(facePhotob);
        userCustomer.setFacePhotoC(facePhotoc);

        userCustomer.setInfoStatus("2");
        boolean b = userCustomerService.update(userCustomer, new EntityWrapper<UserCustomer>().eq("userId", id));
        if(b)
            return new ResultUtil<Object>().set();
        else
            return new ResultUtil<Object>().setErrorMsg("存储错误");
    }

    /**
     * 银行卡照片识别
     *
     * @return
     */
    @ApiOperation(value = "银行卡照片识别")
    @PostMapping("/bankcardPhoto")
    public Result<Object> bankcardPhoto(int id, String base64Data) {
        if (StringUtils.isEmpty(base64Data))
            return new ResultUtil<Object>().setErrorMsg("参数不足");

        Map<String, Object> map = new HashMap();
        map.put("base64Data", base64Data);

        String suffixUrl = "/cardIdentificationbd/result";
        Map<String, String> map1 = ripService.bankcardPhotoService(id, map, suffixUrl);
        String result = map1.get("result");
        if(result.equals("1"))
            return new ResultUtil<Object>().setData(map1);
        else
            return new ResultUtil<Object>().setErrorMsg(result);
    }



}
