package com.rip.load.service.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.rip.load.pojo.OperatorReport;
import com.rip.load.pojo.User;
import com.rip.load.pojo.UserCustomer;
import com.rip.load.pojo.UserDistributor;
import com.rip.load.pojo.nativePojo.UserThreadLocal;
import com.rip.load.otherPojo.personalComplaintInquiryC.PersonalComplaintInquiryC;
import com.rip.load.service.OperatorReportService;
import com.rip.load.service.RipService;
import com.rip.load.service.UserCustomerService;
import com.rip.load.service.UserDistributorService;
import com.rip.load.utils.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class RipServiceImpl implements RipService {

    private static final Logger logger = LoggerFactory.getLogger(RipServiceImpl.class);

    private static final String LINRICO_PREFIX_URL = "https://rip.linrico.com";

    @Autowired
    private UserCustomerService userCustomerService;
    @Autowired
    private UserDistributorService userDistributorService;
    @Autowired
    private OperatorReportService operatorReportService;


    @Override
    public Map<String, Object> getOperatorCreditReports(Map<String, Object> map) {
        return null;
    }

    @Override
    public String ripSetTokenService(Map<String, Object> map, String suffixUrl) {
        String resultJson = null;
        try {
            resultJson = HttpUtil.httpGet(LINRICO_PREFIX_URL + suffixUrl, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultJson;
    }
    @Override
    public String idCardElementsService(int id) {

        if(id == 0){
            User user = UserThreadLocal.get();
            id = user.getId();
        }
        Map<String, Object> mapReturn = setToken(id);
        String result = (String) mapReturn.get("result");
        if(!result.equals("1")){
            return result;
        }
        UserCustomer userCustomer = (UserCustomer) mapReturn.get("customer");
        Map<String, Object> map = (Map)mapReturn.get("map");
        map.put("name", userCustomer.getRealname());
        map.put("idNumber", userCustomer.getIdcard());

        String suffixUrl = "/idCardElements/result";
        String json = ripSetTokenService(map, suffixUrl);
        if(json == null){
            return "身份证二要素信息获取失败";
        }

        OperatorReport report = new OperatorReport();
        report.setResultJson(json);
        report.setTime(new Date());
        report.setUserId(id);
        report.setType("1");
        boolean insert = operatorReportService.insert(report);
        if(insert){
            return "1";
        }else{
            return "储存错误";
        }
    }

    @Override
    public String operatorThreeElementsService(int id) {

        if(id == 0){
            User user = UserThreadLocal.get();
            id = user.getId();
        }
        Map<String, Object> mapReturn = setToken(id);
        String result = (String) mapReturn.get("result");
        if(!result.equals("1")){
            return result;
        }
        UserCustomer userCustomer = (UserCustomer) mapReturn.get("customer");
        Map<String, Object> map = (Map)mapReturn.get("map");
        map.put("name", userCustomer.getRealname());
        map.put("mobile", userCustomer.getCellphone());
        map.put("idNumber", userCustomer.getIdcard());

        String suffixUrl = "/operatorThreeElements/result";
        String json = ripSetTokenService(map, suffixUrl);
        if(json == null){
            return "征信信息获取失败";
        }

        OperatorReport report = new OperatorReport();
        report.setResultJson(json);
        report.setTime(new Date());
        report.setUserId(id);
        report.setType("2");
        boolean insert = operatorReportService.insert(report);
        if(insert){
            return "1";
        }else{
            return "储存错误";
        }
    }

    @Override
    public String inTheNetworkTimeService(int id) {

        if(id == 0){
            User user = UserThreadLocal.get();
            id = user.getId();
        }
        Map<String, Object> mapReturn = setToken(id);
        String result = (String) mapReturn.get("result");
        if(!result.equals("1")){
            return result;
        }
        UserCustomer userCustomer = (UserCustomer) mapReturn.get("customer");
        Map<String, Object> map = (Map)mapReturn.get("map");
        map.put("mobile", userCustomer.getCellphone());
        String suffixUrl = "/inTheNetworkTime/result";
        String json = ripSetTokenService(map, suffixUrl);
        if(json == null){
            return "征信信息获取失败";
        }

        OperatorReport report = new OperatorReport();
        report.setResultJson(json);
        report.setTime(new Date());
        report.setUserId(id);
        report.setType("3");
        boolean insert = operatorReportService.insert(report);
        if(insert){
            return "1";
        }else{
            return "储存错误";
        }
    }

    @Override
    public String vehicleDetailsEnquiryService(int id) {

        if(id == 0){
            User user = UserThreadLocal.get();
            id = user.getId();
        }
        Map<String, Object> mapReturn = setToken(id);
        String result = (String) mapReturn.get("result");
        if(!result.equals("1")){
            return result;
        }
        UserCustomer userCustomer = (UserCustomer) mapReturn.get("customer");
        Map<String, Object> map = (Map)mapReturn.get("map");
        map.put("name", userCustomer.getRealname());
        if(StringUtils.isEmpty(userCustomer.getPlateNumber())){
            return "没有车牌号 ";
        }
        map.put("licensePlate", userCustomer.getPlateNumber());
        //小型车辆
        map.put("licensePlateType", 02);

        String suffixUrl = "/vehicleDetailsEnquiry/result";
        String json = ripSetTokenService(map, suffixUrl);
        if(json == null){
            return "征信信息获取失败";
        }

        OperatorReport report = new OperatorReport();
        report.setResultJson(json);
        report.setTime(new Date());
        report.setUserId(id);
        report.setType("4");
        boolean insert = operatorReportService.insert(report);
        if(insert){
            return "1";
        }else{
            return "储存错误";
        }
    }

    @Override
    public String businessDataService(int id) {

        if(id == 0){
            User user = UserThreadLocal.get();
            id = user.getId();
        }
        Map<String, Object> mapReturn = setToken(id);
        String result = (String) mapReturn.get("result");
        if(!result.equals("1")){
            return result;
        }
        UserCustomer userCustomer = (UserCustomer) mapReturn.get("customer");
        Map<String, Object> map = (Map)mapReturn.get("map");
        if(StringUtils.isEmpty(userCustomer.getWorkUnit())){
            return "工作单位为空";
        }
        map.put("key", userCustomer.getWorkUnit());
        map.put("keyType", 1);

        String suffixUrl = "/businessData/result";
        String json = ripSetTokenService(map, suffixUrl);
        if(json == null){
            return "征信信息获取失败";
        }

        OperatorReport report = new OperatorReport();
        report.setResultJson(json);
        report.setTime(new Date());
        report.setUserId(id);
        report.setType("5");
        boolean insert = operatorReportService.insert(report);
        if(insert){
            return "1";
        }else{
            return "储存错误";
        }
    }

    @Override
    public String personalEnterpriseService(int id) {

        if(id == 0){
            User user = UserThreadLocal.get();
            id = user.getId();
        }
        Map<String, Object> mapReturn = setToken(id);
        String result = (String) mapReturn.get("result");
        if(!result.equals("1")){
            return result;
        }
        UserCustomer userCustomer = (UserCustomer) mapReturn.get("customer");
        Map<String, Object> map = (Map)mapReturn.get("map");
        map.put("key", userCustomer.getIdcard());

        String suffixUrl = "/personalEnterprise/result";
        String json = ripSetTokenService(map, suffixUrl);
        if(json == null){
            return "征信信息获取失败";
        }

        OperatorReport report = new OperatorReport();
        report.setResultJson(json);
        report.setTime(new Date());
        report.setUserId(id);
        report.setType("6");
        boolean insert = operatorReportService.insert(report);
        if(insert){
            return "1";
        }else{
            return "储存错误";
        }
    }

    @Override
    public String personalRiskInformationService(int id) {

        if(id == 0){
            User user = UserThreadLocal.get();
            id = user.getId();
        }
        Map<String, Object> mapReturn = setToken(id);
        String result = (String) mapReturn.get("result");
        if(!result.equals("1")){
            return result;
        }
        UserCustomer userCustomer = (UserCustomer) mapReturn.get("customer");
        Map<String, Object> map = (Map)mapReturn.get("map");
        map.put("name", userCustomer.getRealname());
        map.put("idCard", userCustomer.getIdcard());
        map.put("mobile", userCustomer.getCellphone());

        String suffixUrl = "/personalRiskInformation/result";
        String json = ripSetTokenService(map, suffixUrl);
        if(json == null){
            return "征信信息获取失败";
        }

        OperatorReport report = new OperatorReport();
        report.setResultJson(json);
        report.setTime(new Date());
        report.setUserId(id);
        report.setType("8");
        boolean insert = operatorReportService.insert(report);
        if(insert){
            return "1";
        }else{
            return "储存错误";
        }
    }

    @Override
    public String personalComplaintInquiryCService(int id) {

        if(id == 0){
            User user = UserThreadLocal.get();
            id = user.getId();
        }
        Map<String, Object> mapReturn = setToken(id);
        String result = (String) mapReturn.get("result");
        if(!result.equals("1")){
            return result;
        }
        UserCustomer userCustomer = (UserCustomer) mapReturn.get("customer");
        Map<String, Object> map = (Map)mapReturn.get("map");
        map.put("name", userCustomer.getRealname());
        map.put("idCard", userCustomer.getIdcard());
        map.put("pageIndex", 1);
//        if (!StringUtils.isEmpty(pageIndex))
//            map.put("pageIndex", pageIndex);

        String suffixUrl = "/personalComplaintInquiryC/result";
        String json = ripSetTokenService(map, suffixUrl);
        if(json == null){
            return "征信信息获取失败";
        }

        PersonalComplaintInquiryC base = JSON.parseObject(json, PersonalComplaintInquiryC.class);
        int resultSize = Integer.parseInt(base.getData().getPageInfo().getResultSize());
        int pageSize = Integer.parseInt(base.getData().getPageInfo().getPageSize());
        int i = (resultSize / pageSize ) + 1;
        StringBuffer sb = new StringBuffer();
        sb.append(json).append("fengexian");
        for (int j = 2; j<= i; j++){
            map.put("pageIndex", j);
            String jsonTemp = ripSetTokenService(map, suffixUrl);
            sb.append(jsonTemp).append("fengexian");
        }

        OperatorReport report = new OperatorReport();
        report.setResultJson(sb.toString());
        report.setTime(new Date());
        report.setUserId(id);
        report.setType("9");
        boolean insert = operatorReportService.insert(report);
        if(insert){
            return "1";
        }else{
            return "储存错误";
        }
    }
    private Map<String, Object> setToken(int id){
        Map<String, Object> map = new HashMap(3);
        if(id == 0){
            map.put("result", "客户唯一标识符为0");
            return map;
        }
        //获取渠道商秘钥
        UserCustomer userCustomer = userCustomerService.selectById(id);
        if(userCustomer == null || userCustomer.getFatherId() == null){
            map.put("result", "客户未完善信息");
            return map;
        }
        UserDistributor userDistributor = userDistributorService.selectById(userCustomer.getFatherId());
        if(userDistributor == null){
            map.put("result", "客户没有上级");
            return map;
        }
        if(StringUtils.isEmpty(userDistributor.getUsername()) || StringUtils.isEmpty(userDistributor.getAccessToken())){
            map.put("result", "缺少征信秘钥");
            return map;
        }
        String username = userDistributor.getUsername();
        String accessToken = userDistributor.getAccessToken();
        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("username", username);
        map1.put("accessToken", accessToken);
        map.put("map", map1);
        map.put("result", "1");
        map.put("customer", userCustomer);
        return map;
    }

}
