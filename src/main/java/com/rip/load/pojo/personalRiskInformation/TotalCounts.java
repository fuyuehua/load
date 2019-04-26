package com.rip.load.pojo.personalRiskInformation;

public class TotalCounts {

    /**
     *黑名单类型 A-严重违法;B-信贷逾期;C-法院失信;D-潜在风险;E-多头借贷
     */
    private String blackType;
    /**
     *黑名单类型数量
     */
    private String blackCount;

    public String getBlackType() {
        return blackType;
    }

    public void setBlackType(String blackType) {
        this.blackType = blackType;
    }

    public String getBlackCount() {
        return blackCount;
    }

    public void setBlackCount(String blackCount) {
        this.blackCount = blackCount;
    }
}
