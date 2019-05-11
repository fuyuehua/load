package com.rip.load.controller;

import com.rip.load.pojo.nativePojo.Result;
import com.rip.load.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Api(tags = {"文件系统"})
@RestController
@RequestMapping("api/file")
public class FileController {

    private final static Logger logger = LoggerFactory.getLogger(FileController.class);

    //服务器中文件储存的地址
    private final static String FILE_PATH = "/usr/local/tomcatfangdifile";

    @ApiOperation("上传文件获得URL")
    @PostMapping("/getUrl")
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
}
