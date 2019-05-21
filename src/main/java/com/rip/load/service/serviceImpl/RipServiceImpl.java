package com.rip.load.service.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.rip.load.otherPojo.bankcardPhoto.BankcardPhoto;
import com.rip.load.otherPojo.idcardPhoto.IdcardPhoto;
import com.rip.load.otherPojo.idcardPhoto.Words_result;
import com.rip.load.pojo.*;
import com.rip.load.pojo.nativePojo.UserThreadLocal;
import com.rip.load.otherPojo.personalComplaintInquiryC.PersonalComplaintInquiryC;
import com.rip.load.service.*;
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
    private ItemService itemService;
    @Autowired
    private ReportItemService reportItemService;

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
    public Map<String, String> operatorCreditReportsService(int id, Map<String, Object> map, String suffixUrl) {
        Map<String, String> resultMap = new HashMap<>();
        if(id == 0){
            User user = UserThreadLocal.get();
            id = user.getId();
        }
        Map<String, Object> mapReturn = setToken(id);
        String result = (String) mapReturn.get("result");
        if(!result.equals("1")){
            resultMap.put("result", result);
            return resultMap;
        }
        UserCustomer userCustomer = (UserCustomer) mapReturn.get("customer");
        Map<String, Object> mapToken = (Map)mapReturn.get("map");
        map.put("username", mapToken.get("username"));
        map.put("accessToken", mapToken.get("accessToken"));
        if(suffixUrl.equals("/operatorCreditReports/result")){
            map.put("name", userCustomer.getCellphone());
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
        }

        String json = ripSetTokenService(map, suffixUrl);
        if(json == null){
            resultMap.put("result", "接口调用失败");
            return resultMap;
        }
        if(suffixUrl.equals("/operatorCreditReports/report)")){

            Item item = new Item();
            item.setResultJson(json);
            item.setTime(new Date());
            item.setUserId(id);
            item.setType("10");
            boolean insert = itemService.insert(item);
            if(insert){
                resultMap.put("result", "1");
                return resultMap;
            }else{
                resultMap.put("result", "储存错误");
                return resultMap;
            }
        }
        resultMap.put("result", "1");
        resultMap.put("data", json);
        return resultMap;
    }

    @Override
    public Map<String, String> taoBaoService(int id, Map<String, Object> map, String suffixUrl) {
        Map<String, String> resultMap = new HashMap<>();
        if(id == 0){
            User user = UserThreadLocal.get();
            id = user.getId();
        }
        Map<String, Object> mapReturn = setToken(id);
        String result = (String) mapReturn.get("result");
        if(!result.equals("1")){
            resultMap.put("result", result);
            return resultMap;
        }
        UserCustomer userCustomer = (UserCustomer) mapReturn.get("customer");
        Map<String, Object> mapToken = (Map)mapReturn.get("map");
        map.put("username", mapToken.get("username"));
        map.put("accessToken", mapToken.get("accessToken"));
        if(suffixUrl.equals("/operatorCreditReports/result")){

        }

        String json = ripSetTokenService(map, suffixUrl);
        if(json == null){
            resultMap.put("result", "接口调用失败");
            return resultMap;
        }
        if(suffixUrl.equals("/limu/validate/getResult")){

            Item item = new Item();
            item.setResultJson(json);
            item.setTime(new Date());
            item.setUserId(id);
            item.setType("11");
            boolean insert = itemService.insert(item);
            if(insert){
                resultMap.put("result", "1");
                return resultMap;
            }else{
                resultMap.put("result", "储存错误");
                return resultMap;
            }
        }
        resultMap.put("result", "1");
        resultMap.put("data", json);
        return resultMap;
    }
    @Override
    public String idCardElementsService(int id, Integer reportId) {

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
            return "征信信息获取失败";
        }

        Item item = new Item();
        item.setResultJson(json);
        item.setTime(new Date());
        item.setUserId(id);
        item.setType("1");
        boolean insert = itemService.insert(item);

        if(reportId != null && reportId != 0){
            ReportItem reportItem = new ReportItem();
            reportItem.setItemId(item.getId());
            reportItem.setReportId(reportId);
            reportItemService.insert(reportItem);
        }
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

        Item item = new Item();
        item.setResultJson(json);
        item.setTime(new Date());
        item.setUserId(id);
        item.setType("2");
        boolean insert = itemService.insert(item);
        if(insert){
            return "1";
        }else{
            return "储存错误";
        }
    }

    @Override
    public String operatorTwoElementsService(int id) {

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

        String suffixUrl = "/operatorTwoElements/result";
        String json = ripSetTokenService(map, suffixUrl);
        if(json == null){
            return "征信信息获取失败";
        }

        Item item = new Item();
        item.setResultJson(json);
        item.setTime(new Date());
        item.setUserId(id);
        item.setType("14");
        boolean insert = itemService.insert(item);
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

        Item item = new Item();
        item.setResultJson(json);
        item.setTime(new Date());
        item.setUserId(id);
        item.setType("3");
        boolean insert = itemService.insert(item);
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

        Item item = new Item();
        item.setResultJson(json);
        item.setTime(new Date());
        item.setUserId(id);
        item.setType("4");
        boolean insert = itemService.insert(item);
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

        Item item = new Item();
        item.setResultJson(json);
        item.setTime(new Date());
        item.setUserId(id);
        item.setType("5");
        boolean insert = itemService.insert(item);
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

        Item item = new Item();
        item.setResultJson(json);
        item.setTime(new Date());
        item.setUserId(id);
        item.setType("6");
        boolean insert = itemService.insert(item);
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

        Item item = new Item();
        item.setResultJson(json);
        item.setTime(new Date());
        item.setUserId(id);
        item.setType("8");
        boolean insert = itemService.insert(item);
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

        Item item = new Item();
        item.setResultJson(json);
        item.setTime(new Date());
        item.setUserId(id);
        item.setType("9");
        boolean insert = itemService.insert(item);
        if(insert){
            return "1";
        }else{
            return "储存错误";
        }
    }

    @Override
    public Map<String, String> idcardPhotoService(int id, Map<String, Object> map, String suffixUrl, String image) {
        Map<String, String> resultMap = new HashMap<>();
        if(id == 0){
            User user = UserThreadLocal.get();
            id = user.getId();
        }
        Map<String, Object> mapReturn = setToken(id);
        String result = (String) mapReturn.get("result");
        if(!result.equals("1")){
            resultMap.put("result", result);
            return resultMap;
        }
        UserCustomer userCustomer = (UserCustomer) mapReturn.get("customer");
        Map<String, Object> mapToken = (Map)mapReturn.get("map");
        map.put("username", mapToken.get("username"));
        map.put("accessToken", mapToken.get("accessToken"));
        String json = ripSetTokenService(map, suffixUrl);
        if(json == null){
            resultMap.put("result", "接口调用失败");
            return resultMap;
        }

        Item item = new Item();
        item.setResultJson(json);
        item.setTime(new Date());
        item.setUserId(id);
        item.setType("12");
        boolean insert = itemService.insert(item);

        IdcardPhoto idcardPhoto = JSON.parseObject(json, IdcardPhoto.class);
        Words_result words_result = idcardPhoto.getWords_result();
            //正面
        if(((String)map.get("idCardSide")).equals("front")) {
            userCustomer.setIdcardName(words_result.get姓名().getWords());
            userCustomer.setIdcardSex(words_result.get性别().getWords());
            userCustomer.setIdcardNation(words_result.get民族().getWords());
            userCustomer.setIdcardBirthday(words_result.get出生().getWords());
            userCustomer.setIdcardAddress(words_result.get住址().getWords());
            userCustomer.setIdcardIdcard(words_result.get公民身份号码().getWords());
            userCustomer.setIdcardPhotoa(image);
        }else {
            //反面
            userCustomer.setIdcardExpiration(words_result.get失效日期().getWords());
            userCustomer.setIdcardLocation(words_result.get签发机关().getWords());
            userCustomer.setIdcardSign(words_result.get签发日期().getWords());
            userCustomer.setIdcardPhotob(image);
        }

        boolean b = userCustomerService.updateById(userCustomer);

        if(insert && b){
            resultMap.put("result", "1");
            return resultMap;
        }else{
            resultMap.put("result", "储存错误");
            return resultMap;
        }
    }

    @Override
    public Map<String, String> bankcardPhotoService(int id, Map<String, Object> map, String suffixUrl, String image) {
        Map<String, String> resultMap = new HashMap<>();
        if(id == 0){
            User user = UserThreadLocal.get();
            id = user.getId();
        }
        Map<String, Object> mapReturn = setToken(id);
        String result = (String) mapReturn.get("result");
        if(!result.equals("1")){
            resultMap.put("result", result);
            return resultMap;
        }
        UserCustomer userCustomer = (UserCustomer) mapReturn.get("customer");
        Map<String, Object> mapToken = (Map)mapReturn.get("map");
        map.put("username", mapToken.get("username"));
        map.put("accessToken", mapToken.get("accessToken"));
        String json = ripSetTokenService(map, suffixUrl);
        if(json == null){
            resultMap.put("result", "接口调用失败");
            return resultMap;
        }

        Item item = new Item();
        item.setResultJson(json);
        item.setTime(new Date());
        item.setUserId(id);
        item.setType("13");
        boolean insert = itemService.insert(item);

        BankcardPhoto bankcardPhoto = JSON.parseObject(json, BankcardPhoto.class);
        userCustomer.setBankcardCardnumber(bankcardPhoto.getResult().getCardnumber());
        userCustomer.setBankcardCardtype(bankcardPhoto.getResult().getCardtype());
        userCustomer.setBankcardCardname(bankcardPhoto.getResult().getCardname());
        userCustomer.setBankcardPhoto(image);

        boolean b = userCustomerService.updateById(userCustomer);

        if(insert && b){
            resultMap.put("result", "1");
            return resultMap;
        }else{
            resultMap.put("result", "储存错误");
            return resultMap;
        }
    }

    @Override
    public String fourElementsOfBankCardService(int id) {
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
        map.put("custName", userCustomer.getRealname());
        map.put("idNo", userCustomer.getIdcard());
        map.put("phoneNo", userCustomer.getCellphone());
        map.put("idType", 01);
        map.put("bankCardno", userCustomer.getBankcard());

        String suffixUrl = "/fourElementsOfBankCard/result";
        String json = ripSetTokenService(map, suffixUrl);
        if(json == null){
            return "征信信息获取失败";
        }

        Item item = new Item();
        item.setResultJson(json);
        item.setTime(new Date());
        item.setUserId(id);
        item.setType("11");
        boolean insert = itemService.insert(item);
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
