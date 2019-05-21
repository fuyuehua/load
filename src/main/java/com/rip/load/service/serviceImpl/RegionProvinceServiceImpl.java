package com.rip.load.service.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.rip.load.otherPojo.city.*;
import com.rip.load.pojo.RegionArea;
import com.rip.load.pojo.RegionCity;
import com.rip.load.pojo.RegionProvince;
import com.rip.load.mapper.RegionProvinceMapper;
import com.rip.load.service.RegionAreaService;
import com.rip.load.service.RegionCityService;
import com.rip.load.service.RegionProvinceService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zxh
 * @since 2019-05-18
 */
@Service
public class RegionProvinceServiceImpl extends ServiceImpl<RegionProvinceMapper, RegionProvince> implements RegionProvinceService {

    @Autowired
    RegionCityService regionCityService;
    @Autowired
    RegionAreaService regionAreaService;

    @Override
    public String inserttest() {
        Base base = JSON.parseObject(CityJson.cityJson, Base.class);
        List<Province> baseList = base.getBase();

        for(Province province : baseList) {
            List<City> cityList = province.getCityList();

            RegionProvince regionProvince = new RegionProvince();
            regionProvince.setCode(province.getCode());
            regionProvince.setName(province.getName());
            boolean insert = insert(regionProvince);

            for (City city : cityList) {
                RegionCity regionCity = new RegionCity();
                regionCity.setCode(city.getCode());
                regionCity.setName(city.getName());
                regionCity.setSuperId(regionProvince.getId());
                boolean insert1 = regionCityService.insert(regionCity);
                List<RegionArea> list = new ArrayList<>();
                for (Area area : city.getAreaList()) {
                    RegionArea regionArea = new RegionArea();
                    regionArea.setCode(area.getCode());
                    regionArea.setName(area.getName());
                    regionArea.setSuperId(regionCity.getId());
                    list.add(regionArea);
                }
                regionAreaService.insertBatch(list);
            }
        }
        return "ok";
    }
}
