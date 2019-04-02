package com.rip.load.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    public static String setNow4liantong(){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String time = null;
        try {
            time = simpleDateFormat.format(date);
        } catch (Exception e) {
            logger.error("方法名setNow4liantong报错：" + e);
        }
        return time;
    }
}
