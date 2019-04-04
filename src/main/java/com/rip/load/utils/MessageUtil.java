package com.rip.load.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageUtil {

    private static Logger log = LoggerFactory.getLogger(MessageUtil.class);

    private static final String host = "https://feginesms.market.alicloudapi.com";//【1】请求地址  支持http 和 https 及 WEBSOCKET
    private static final String path = "/codeNotice";                              //【2】后缀
    private static final String appcode = "f0d75d7fa9174bd09b43e982de54898d";     //【3】AppCode  你自己的AppCode 在买家中心查看


    public static String messageHttp(List<String> list, String phone, String sign, String skin){
        StringBuffer sb = new StringBuffer();
        for(String str : list){
            sb.append(str).append("|");
        }
        String param = sb.substring(0, sb.lastIndexOf("|"));
        log.debug(param);
        Map<String, Object> map = new HashMap<>(4);
        map.put("param", param);
        map.put("phone", phone);
        map.put("sign", sign);
        map.put("skin", skin);
        Map<String, Object> mapHeader = new HashMap<>(1);
        mapHeader.put("Authorization", "APPCODE " + appcode);
        String s = null;
        try {
            s = HttpUtil.httpGet(host + path, mapHeader, map);
        } catch (Exception e) {
            log.error(e.toString() +"时间： "+new Date()+ "这个发短信失败的手机号是" + phone);
            e.printStackTrace();
        }

        return s;
    }



}
