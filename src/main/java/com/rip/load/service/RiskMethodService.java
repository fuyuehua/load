package com.rip.load.service;

import com.alibaba.fastjson.JSON;
import com.rip.load.otherPojo.idCardElements.Data;
import com.rip.load.otherPojo.idCardElements.IdCardElements;
import com.rip.load.pojo.Item;
import com.rip.load.pojo.Report;
import com.rip.load.pojo.RiskRule;
import com.rip.load.pojo.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

public class RiskMethodService {


    public boolean idCardElements(RiskRule riskRule, Item item){

        String resultJson = item.getResultJson();
        IdCardElements idCardElements = JSON.parseObject(resultJson, IdCardElements.class);
        Data data = idCardElements.getData();
        String key = data.getKey();
        if(key.equals("9998") || key.equals("3")){
            return false;
        }
        return true;
    }

    public boolean useMethod(String method, RiskRule riskRule)throws Exception{
        String className = "com.rip.load.service.RiskMethodService";
        String methodName = method;
        Class clz = Class.forName(className);

        //
        Object obj = clz.newInstance();
        //获取方法
        Method m = obj.getClass().getDeclaredMethod(methodName, String.class);
        //调用方法
        boolean result = (boolean) m.invoke(obj, riskRule);
        return result;
    }
}

