package com.rip.load.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MD5Util {

    /**
     * MD5方法
     *
     * @param text 明文
     * @param key 密钥
     * @return 密文
     * @throws Exception
     */
    public static String md5(String text, String key){
        //加密后的字符串
        String encodeStr= DigestUtils.md5Hex(text + key);
        return encodeStr;
    }

    /**
     * MD5验证方法
     *
     * @param text 明文
     * @param key 密钥
     * @param md5 密文
     * @return true/false
     * @throws Exception
     */
    public static boolean verify(String text, String key, String md5){
        //根据传入的密钥进行验证
        String md5Text = md5(text, key);
        if(md5Text.equalsIgnoreCase(md5))
        {return true;}
        return false;
    }


    /**
     * md5BySingleParam方法（以32个字符的十六进制字符串小写）
     *
     * @param text 明文
     * @return 密文
     * @throws Exception
     */
    public static String md5BySingleParam(String text){
        //加密后的字符串
        String encodeStr= DigestUtils.md5Hex(text);
        return encodeStr;
    }

    /**
     * md5BySingleParamda方法（以32个字符的十六进制字符串大写）
     *用于联通加密
     * @param text 明文
     * @return 密文
     * @throws Exception
     */
    public static String md5BySingleParamda(String text){
        if(text == null){
            return null;
        }
        //加密后的字符串
        String encodeStr= DigestUtils.md5Hex(text);
        return encodeStr.toUpperCase();
    }

    /**
     * 通过salt生成加密密码
     * @param password
     * @param salt
     * @return
     */
    public static String password2Secert(String password, String salt){
        return md5BySingleParam(md5(password, salt)).toUpperCase();
    }

    /**
     * 密码加密,当前时间字符串加随机三个数再MD5生成salt,然后带上密码MD5，再自我MD5
     * @param password
     * @return
     */
    public static Map<String, String> getSecert(String password){
        Map<String, String> map = new HashMap<>();
        int i = (int)(Math.random()*900 + 100);
        String now = DateFormatUtils.format(new Date(), "yyyy-MM-dd hh:mm:ss");
        String salt = md5BySingleParam(now + i);
        String secert = md5BySingleParam(md5(password, salt)).toUpperCase();
        map.put("salt", salt);
        map.put("secert", secert);
        return map;
    }

    /**
     * 验证密码
     * @param testPassword 测试密码
     * @param realPassword 真实密码
     * @param salt 盐
     * @return
     */
    public static boolean verifyPassword(String testPassword, String realPassword, String salt){
        String secert = md5BySingleParam(md5(testPassword, salt)).toUpperCase();
        if(secert.equals(realPassword)){
            return true;
        }
        return false;
    }

    /**
     * 乱JB搞一个不容易重复的弱智token
     * @param json user的json
     * @return
     */
    public static String getToken(String json){
        int i = (int)(Math.random()*900 + 100);
        String now = DateFormatUtils.format(new Date(), "yyyy-MM-dd hh:mm:ss");
        String salt = md5BySingleParam(now + i);
        String token = md5BySingleParam(md5(json, salt)).toUpperCase();
        return token;
    }

    public static void main(String[] args) {
        //测试实现密码加密,当前时间字符串加随机三个数作为key,然后两次MD5，一次带KEY，一次不带
        int i = (int)(Math.random()*900 + 100);
        String now = DateFormatUtils.format(new Date(), "yyyy-MM-dd hh:mm:ss");
        String key = md5BySingleParam(now + i);
        String result = md5BySingleParam(md5("123456", key)).toUpperCase();
        System.out.println("key: " + key + " , token: " + result);

    }
}
