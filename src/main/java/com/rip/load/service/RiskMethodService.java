package com.rip.load.service;

import com.alibaba.fastjson.JSON;
import com.rip.load.otherPojo.InTheNetworkTime.InTheNetworkTime;
import com.rip.load.otherPojo.businessData.BreakLaw;
import com.rip.load.otherPojo.businessData.BusinessData;
import com.rip.load.otherPojo.businessData.PunishBreaks;
import com.rip.load.otherPojo.idCardElements.Data;
import com.rip.load.otherPojo.idCardElements.IdCardElements;
import com.rip.load.otherPojo.operatorThreeElements.OperatorThreeElements;
import com.rip.load.otherPojo.operatorTwoElements.OperatorTwoElements;
import com.rip.load.otherPojo.personalComplaintInquiryC.PageData;
import com.rip.load.otherPojo.personalComplaintInquiryC.PersonalComplaintInquiryC;
import com.rip.load.otherPojo.personalEnterprise.CaseInfos;
import com.rip.load.otherPojo.personalEnterprise.PersonalEnterprise;
import com.rip.load.otherPojo.personalEnterprise.Punished;
import com.rip.load.otherPojo.personalRiskInformation.ListTemp;
import com.rip.load.otherPojo.personalRiskInformation.PersonalRiskInformation;
import com.rip.load.pojo.Item;
import com.rip.load.pojo.Report;
import com.rip.load.pojo.RiskRule;
import com.rip.load.pojo.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RiskMethodService {

    private final static Logger logger  = LoggerFactory.getLogger(RiskMethodService.class);


    public boolean idCardElements(RiskRule riskRule, Item item){
        try{
            String resultJson = item.getResultJson();
            IdCardElements idCardElements = JSON.parseObject(resultJson, IdCardElements.class);
            Data data = idCardElements.getData();
            String key = data.getKey();
            if(key.equals("0000")){
                return true;
            }
        }catch (Exception e){
            logger.error("RiskMethodService: idCardElements");
            e.printStackTrace();
        }
        return false;
    }

    public boolean ageCheck(RiskRule riskRule, Item item) {
        String paramA = riskRule.getParamA();
        String paramB = riskRule.getParamB();
        String json = item.getResultJson();
        String age = json.substring(5, 14);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        int ageByBirth = 0;
        try {
            ageByBirth = getAgeByBirth(sdf.parse(age));
        } catch (Exception e) {
            logger.error("RiskMethodService: ageCheck");
            e.printStackTrace();
        }
        int A = Integer.parseInt(paramA);
        int B = Integer.parseInt(paramB);
        if(A<=ageByBirth && ageByBirth <= B){
            return true;
        }
        return false;
    }


    private int getAgeByBirth(Date birthDay) throws ParseException {
        int age = 0;
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) { //出生日期晚于当前时间，无法计算
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);  //当前年份
        int monthNow = cal.get(Calendar.MONTH);  //当前月份
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        age = yearNow - yearBirth;   //计算整岁数
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
            } else {
                age--;//当前月份在生日之前，年龄减一
            }
        }
        return age;
    }

    public boolean operatorTwoElementsCheck(RiskRule riskRule, Item item) {
        try{
            String resultJson = item.getResultJson();
            OperatorTwoElements operatorTwoElements = JSON.parseObject(resultJson, OperatorTwoElements.class);
            com.rip.load.otherPojo.operatorTwoElements.Data data = operatorTwoElements.getData();
            String key = data.getKey();
            if(key.equals("0000")){
                return true;
            }
        }catch (Exception e){
            logger.error("RiskMethodService: operatorTwoElementsCheck");
            e.printStackTrace();
        }
        return false;
    }

    public boolean operatorThreeElementsCheck(RiskRule riskRule, Item item) {
        try{
            String resultJson = item.getResultJson();
            OperatorThreeElements operatorThreeElements = JSON.parseObject(resultJson, OperatorThreeElements.class);
            com.rip.load.otherPojo.operatorThreeElements.Data data = operatorThreeElements.getData();
            String key = data.getKey();
            if(key.equals("0000")){
                return true;
            }
        }catch (Exception e){
            logger.error("RiskMethodService: operatorThreeElements");
            e.printStackTrace();
        }
        return false;
    }

    public boolean inTheNetworkTimeCheck(RiskRule riskRule, Item item) {
        try{
            String paramA = riskRule.getParamA();
            int line = Integer.parseInt(paramA);

            String resultJson = item.getResultJson();
            InTheNetworkTime inTheNetworkTime = JSON.parseObject(resultJson, InTheNetworkTime.class);
            com.rip.load.otherPojo.InTheNetworkTime.Data data = inTheNetworkTime.getData();
            String opt = data.getOUTPUT1();
            String sub = opt.substring(1, opt.length() - 1);
            String[] split = sub.split(",");
            int i = Integer.parseInt(split[0]);
            if(i>=line){
                return true;
            }

        }catch (Exception e){
            logger.error("RiskMethodService: inTheNetworkTimeCheck");
            e.printStackTrace();
        }
        return false;
    }

    public boolean personalEnterpriseCheck(RiskRule riskRule, Item item) {
        try{
            String paramA = riskRule.getParamA();
            int num = Integer.parseInt(paramA);

            String resultJson = item.getResultJson();
            PersonalEnterprise personalEnterprise = JSON.parseObject(resultJson, PersonalEnterprise.class);
            com.rip.load.otherPojo.personalEnterprise.Data data = personalEnterprise.getData();
            if(data.getStatus().equals("NO_DATA"))
                return true;
            List<Punished> punished = data.getPunished();
            if(num > punished.size()){
                return true;
            }
        }catch (Exception e){
            logger.error("RiskMethodService: personalEnterpriseCheck");
            e.printStackTrace();
        }
        return false;
    }

    public boolean personalEnterprise2Check(RiskRule riskRule, Item item) {
        try{
            String paramA = riskRule.getParamA();
            int num = Integer.parseInt(paramA);

            String resultJson = item.getResultJson();
            PersonalEnterprise personalEnterprise = JSON.parseObject(resultJson, PersonalEnterprise.class);
            com.rip.load.otherPojo.personalEnterprise.Data data = personalEnterprise.getData();
            if(data.getStatus().equals("NO_DATA"))
                return true;
            List<CaseInfos> caseInfos = data.getCaseInfos();
            if(caseInfos == null)
                return true;
            if(num > caseInfos.size()){
                return true;
            }
        }catch (Exception e){
            logger.error("RiskMethodService: personalEnterprise2Check");
            e.printStackTrace();
        }
        return false;
    }

    public boolean personalRiskInformationCheck(RiskRule riskRule, Item item, String blackRiskType) {
        try{
            String resultJson = item.getResultJson();
            PersonalRiskInformation personalRiskInformation = JSON.parseObject(resultJson, PersonalRiskInformation.class);
            com.rip.load.otherPojo.personalRiskInformation.Data data = personalRiskInformation.getData();
            List<ListTemp> list = data.getList();
            for(ListTemp temp : list){
                if(temp.getBlackRiskType().equals(blackRiskType))
                    return false;
            }
            return true;
        }catch (Exception e){
            logger.error("RiskMethodService: personalRiskInformationCheck" + blackRiskType);
            e.printStackTrace();
        }

        return false;
    }

    public boolean businessDataCheck(RiskRule riskRule, Item item, int type) {
        try{
            String paramA = riskRule.getParamA();
            int num = Integer.parseInt(paramA);

            String resultJson = item.getResultJson();
            BusinessData businessData = JSON.parseObject(resultJson, BusinessData.class);
            com.rip.load.otherPojo.businessData.Data data = businessData.getData();
            if(data.getStatus().equals("NO_DATA"))
                return true;
            if(type == 1) {
                List<com.rip.load.otherPojo.businessData.Punished> punished = data.getPunished();
                if (punished == null)
                    return true;
                if (num > punished.size()) {
                    return true;
                }
            }else if(type == 2){
                List<com.rip.load.otherPojo.businessData.CaseInfos> caseInfos = data.getCaseInfos();
                if (caseInfos == null)
                    return true;
                if (num > caseInfos.size()) {
                    return true;
                }
            }else if(type == 3){
                List<BreakLaw> breakLaw = data.getBreakLaw();
                if (breakLaw == null)
                    return true;
                if (num > breakLaw.size()) {
                    return true;
                }
            }
        }catch (Exception e){
            logger.error("RiskMethodService: personalEnterprise2Check");
            e.printStackTrace();
        }
        return false;
    }

    public boolean personalComplaintInquiryCCheck(RiskRule riskRule, Item item) {
        try{
            String paramA = riskRule.getParamA();
            int num = Integer.parseInt(paramA);

            String resultJson = item.getResultJson();
            PersonalComplaintInquiryC personalComplaintInquiryC = JSON.parseObject(resultJson, PersonalComplaintInquiryC.class);
            com.rip.load.otherPojo.personalComplaintInquiryC.Data data = personalComplaintInquiryC.getData();
            if(data.getCheckStatus().equals("NO_DATA"))
                return true;
            List<PageData> pageData = data.getPageData();
            if(pageData == null){
                return true;
            }
            if(num > pageData.size()){
                return true;
            }
        }catch (Exception e){
            logger.error("RiskMethodService: personalEnterpriseCheck");
            e.printStackTrace();
        }
        return false;
    }
}

