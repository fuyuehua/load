package com.rip.load.controller;


import com.rip.load.pojo.*;
import com.rip.load.pojo.nativePojo.Result;
import com.rip.load.service.RegionAreaService;
import com.rip.load.service.UserPlatformSubordinateService;
import com.rip.load.service.UserService;
import com.rip.load.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.management.ObjectName;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxh
 * @since 2019-05-18
 */
@Api(tags = {"平台商的下属：终审人员"})
@RestController
@RequestMapping("api/userPlatformSubordinate")
public class UserPlatformSubordinateController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserPlatformSubordinateService userPlatformSubordinateService;

    @Autowired
    private RegionAreaService regionAreaService;

    @ApiOperation("新增和修改终审人员信息")
    @PostMapping("/addOrUpdate")
    public Result<UserPlatformSubordinate> addOrUpdate(
            @ApiParam("终审人员实体类")
            @Valid @RequestBody UserPlatformSubordinate userPlatformSubordinate){

        User user = userService.selectById(userPlatformSubordinate.getUserId());
        if(user == null){
            return new ResultUtil<UserPlatformSubordinate>().setErrorMsg("该用户不存在");
        }

        boolean b = userPlatformSubordinateService.insertOrUpdate(userPlatformSubordinate);
        if(b){
            return new ResultUtil<UserPlatformSubordinate>().setData(userPlatformSubordinate);
        }else{
            return new ResultUtil<UserPlatformSubordinate>().setErrorMsg("创建或修改失败");
        }
    }

    @ApiOperation("终审人员绑定管理区域")
    @PostMapping("/bindArea")
    public Result<Object> bindArea(@Valid @RequestBody RegionBase regionBase){
        try {
            if (regionBase.getProvinceList().get(0).getCityList().get(0).getAreaList().size() == 0){
                return new ResultUtil<Object>().setErrorMsg("未选择任何地区");
            }
        }catch (Exception e){
            return new ResultUtil<Object>().setErrorMsg("未选择任何地区");
        }

        List<RegionArea> listInDB = regionAreaService.selectList(null);

        List<RegionProvince> provinceList = regionBase.getProvinceList();
        for (RegionProvince province : provinceList){
            List<RegionCity> cityList = province.getCityList();
            for(RegionCity city : cityList){
                List<RegionArea> areaList = city.getAreaList();
                List<RegionArea> list = new ArrayList<>();
                for(RegionArea area : areaList){
                    RegionArea newArea = new RegionArea();
                    newArea.setId(area.getId());
                    newArea.setUserId(area.getUserId());
                    for (RegionArea areaInDB : listInDB){
                        if(areaInDB.getId().equals(newArea.getId())){
                            Integer userId = areaInDB.getUserId();
                            if(userId == null || userId.equals(0)){
                                list.add(newArea);
                            }
                        }
                    }
                }
                regionAreaService.insertBatch(list);
            }
        }
        return new ResultUtil<Object>().set();
    }
}
