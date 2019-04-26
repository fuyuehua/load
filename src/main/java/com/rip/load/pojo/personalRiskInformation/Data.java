package com.rip.load.pojo.personalRiskInformation;

import java.util.List;

public class Data {

    /**
     *黑名单类型统计
     */
    private List<TotalCounts> totalCounts;

    /**
     *返回结果列表
     */
    private List<ListTemp> list;

    public List<TotalCounts> getTotalCounts() {
        return totalCounts;
    }

    public void setTotalCounts(List<TotalCounts> totalCounts) {
        this.totalCounts = totalCounts;
    }

    public List<ListTemp> getList() {
        return list;
    }

    public void setList(List<ListTemp> list) {
        this.list = list;
    }
}
