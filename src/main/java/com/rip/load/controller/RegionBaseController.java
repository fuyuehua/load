package com.rip.load.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.rip.load.pojo.RegionArea;
import com.rip.load.pojo.RegionBase;
import com.rip.load.pojo.RegionCity;
import com.rip.load.pojo.RegionProvince;
import com.rip.load.pojo.nativePojo.Result;
import com.rip.load.service.RegionAreaService;
import com.rip.load.service.RegionCityService;
import com.rip.load.service.RegionProvinceService;
import com.rip.load.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxh
 * @since 2019-05-18
 */
@Api(tags = {"省市区控制"})
@RestController
@RequestMapping("api/regionBase")
public class RegionBaseController {

    @Autowired
    private RegionProvinceService regionProvinceService;
    @Autowired
    private RegionCityService regionCityService;
    @Autowired
    private RegionAreaService regionAreaService;

    @ApiOperation("拿到所有的省市区信息")
    @GetMapping("getAll")
    public Result<RegionBase> getAll(){
        List<RegionProvince> provinceList = regionProvinceService.selectList(null);
        for(RegionProvince province : provinceList){
            List<RegionCity> cityList = regionCityService.selectList(new EntityWrapper<RegionCity>().eq("super_id", province.getId()));
            province.setCityList(cityList);
            for(RegionCity city : cityList){
                List<RegionArea> areaList = regionAreaService.selectList(new EntityWrapper<RegionArea>().eq("super_id", city.getId()));
                city.setAreaList(areaList);
            }
        }
        RegionBase regionBase = new RegionBase();
        regionBase.setProvinceList(provinceList);
        return new ResultUtil<RegionBase>().setData(regionBase);
    }
}
