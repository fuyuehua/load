package com.rip.load.controller;

import com.alibaba.fastjson.JSON;
import com.rip.load.otherPojo.city.*;
import com.rip.load.pojo.RegionArea;
import com.rip.load.pojo.RegionCity;
import com.rip.load.pojo.RegionProvince;
import com.rip.load.pojo.nativePojo.Result;
import com.rip.load.service.RegionAreaService;
import com.rip.load.service.RegionCityService;
import com.rip.load.service.RegionProvinceService;
import com.rip.load.utils.HttpUtil;
import com.rip.load.utils.ResultUtil;
import com.rip.load.utils.Test;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.apache.commons.io.FileUtils;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Api(tags = {"文件系统"})
@RestController
@RequestMapping("api/file")
public class FileController {

    private final static Logger logger = LoggerFactory.getLogger(FileController.class);

    //服务器中文件储存的地址
    private final static String FILE_PATH = "/usr/local/tomcatfangdifile";

//    @ApiOperation("上传文件获得URL（先弃用）")
//    @PostMapping("/getUrl")
    public Result<Object> getUrl(@RequestParam(required = false) MultipartFile file, HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        File targetFile=null;
        String url="";//返回存储路径
        int code=1;
        String fileName = file.getOriginalFilename();//获取文件名加后缀
        logger.info(fileName.toString());
        if(fileName!= null && fileName !=""){
            String returnUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() +"/file/";//存储路径
            String path = FILE_PATH; //文件存储位置

            String fileF = fileName.substring(fileName.lastIndexOf("."), fileName.length());//文件后缀
            fileName=new Date().getTime()+"_"+new Random().nextInt(1000)+fileF;//新的文件名

            //先判断文件是否存在
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String fileAdd = sdf.format(new Date());
            //获取文件夹路径
            File file1 =new File(path+"/"+fileAdd);
            //如果文件夹不存在则创建
            if(!file1.exists() && !file1.isDirectory()){
                file1.mkdir();
            }
            //将图片存入文件夹
            targetFile = new File(file1, fileName);
            try {
                //将上传的文件写到服务器上指定的文件。
                file.transferTo(targetFile);
                url=returnUrl+fileAdd+"/"+fileName;
                map.put("url", url);
                map.put("fileName", fileName);
                logger.info(url);
                return new ResultUtil<Object>().setData(map);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResultUtil<Object>().setErrorMsg("存储错误");
            }
        }
        return new ResultUtil<Object>().setErrorMsg("文件上传失败");
    }


    @ApiOperation("上传文件获得URL（外接线上）")
    @PostMapping("/getOnlineUrl")
    public Result getOnlineUrl(MultipartFile file, HttpServletRequest request){

        if(file.isEmpty()){
            return new ResultUtil<Object>().setErrorMsg("传入的文件为空");
        }

        String name = "zxh";
        String password = "233123";
        String filepath = "img";

        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("password", password);
        map.put("filepath", filepath);

        String url = "http://106.13.58.157:9898/api/file/getUrl";
        //转换成file
        //在指定目录，生成临时文件，然后再转换
        //在根目录下创建一个tempfileDir的临时文件夹
        String contextpath = request.getContextPath()+"/tempfileDir";
        File f = new File(contextpath);
        if(!f.exists()){
            f.mkdirs();
        }
        String fileName = file.getOriginalFilename();
        String finalfilepath = contextpath+"/"+fileName;
        File finalfile = new File(finalfilepath);
        String json = "";
        try {
            FileUtils.copyInputStreamToFile(file.getInputStream(), finalfile);
            json = HttpUtil.httpPostForm(url, "file", finalfile, map);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("文件转换错误");
        }finally {
            //如果不需要File文件可删除
            if(finalfile.exists()){
                finalfile.delete();
            }
        }
        Result result = JSON.parseObject(json, Result.class);

        return result;

    }


    @Autowired
    RegionProvinceService regionProvinceService;


    /**
     * 测试存储一手省市区信息，顺便测试一手回退
     * @return
     */
//    @GetMapping("/test")
    public Result<Object> test(){

        String s = regionProvinceService.inserttest();
      return new ResultUtil<Object>().set();
    }
}
