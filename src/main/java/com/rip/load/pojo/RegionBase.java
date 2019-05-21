package com.rip.load.pojo;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class RegionBase {
    @NotEmpty
    private List<RegionProvince> provinceList;

    public List<RegionProvince> getProvinceList() {
        return provinceList;
    }

    public void setProvinceList(List<RegionProvince> provinceList) {
        this.provinceList = provinceList;
    }
}
