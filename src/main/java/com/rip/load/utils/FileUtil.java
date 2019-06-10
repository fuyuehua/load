package com.rip.load.utils;

import com.alibaba.fastjson.JSON;
import com.rip.load.controller.FileController;
import com.rip.load.pojo.nativePojo.Result;
import com.sun.corba.se.spi.orbutil.fsm.Guard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileUtil {

    private final static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    private static final String username = "zxh";
    private static final String password = "233123";

    /**
     * 文件生成网络url
     * @param filepath 生成哪个文件夹里
     * @param file 文件
     * @param b 是否完成后删除
     * @return
     */
    public static String getOnlineUrl(String filepath, File file, Boolean b){

        Map<String, Object> map = new HashMap<>();
        map.put("name", username);
        map.put("password", password);
        map.put("filepath", filepath);

        String url = "http://106.13.58.157:9898/api/file/getUrl";
        String json = "";
        try {
            json = HttpUtil.httpPostForm(url, "file", file, map);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("文件转换错误");
        }finally {
            if(b) {
                //如果不需要File文件可删除
                if (file.exists()) {
                    file.delete();
                }
            }
        }
        Result result = JSON.parseObject(json, Result.class);
        Map<String, String> mapResult = (Map<String, String>) result.getResult();
        String result1 = mapResult.get("url");
        return result1;

    }
}
