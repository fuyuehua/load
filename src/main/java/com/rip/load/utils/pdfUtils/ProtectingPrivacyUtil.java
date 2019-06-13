package com.rip.load.utils.pdfUtils;

import org.apache.http.util.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 用户基本信息脱敏
 * @Author: FYH
 * @Date: Created in 15:47 2019/6/13
 * @Modified:
 */
public class ProtectingPrivacyUtil {

    /**
     * 姓名脱敏
     * @param name
     * @return
     */
    public static String nameEncrypt( String name ) {
        // 获取姓名长度
        String custName = name;
        int length = custName.length();
        String reg = ".{1}";
        StringBuffer sb = new StringBuffer();
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(name);
        int i = 0;
        while (m.find()) {
            i++;
            if (i == length)
                continue;
            m.appendReplacement(sb, "*");
        }
        m.appendTail(sb);
        return sb.toString();
    }

    /**
     * 手机号脱敏
     * @param mobile
     * @return
     */
    public static String mobileEncrypt(String mobile){
        if(mobile.length() == 11){
            return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
        }
        return mobile;
    }

    /**
     * 身份证脱敏
     * @param id
     * @return
     */
    public static String idEncrypt(String id) {
        if (TextUtils.isEmpty(id) || (id.length() < 8)) {
            return id;
        }
        return id.replaceAll("(?<=\\w{3})\\w(?=\\w{4})", "*");
    }

}
