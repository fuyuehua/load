package com.rip.load.pojo.businessData;

/**
 *动产抵押物信息
 */
public class MorguaInfos {

    /**
     * 综合信息（数量、质量、状况、所在地等情况）
     */
    private String comprehensiveDetail;
    /**
     * 抵押物名称
     */
    private String guaName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 登记编号
     */
    private String regNo;

    /**
     * 所有权或使用权归属
     */
    private String owner;

    public String getComprehensiveDetail() {
        return comprehensiveDetail;
    }

    public void setComprehensiveDetail(String comprehensiveDetail) {
        this.comprehensiveDetail = comprehensiveDetail;
    }

    public String getGuaName() {
        return guaName;
    }

    public void setGuaName(String guaName) {
        this.guaName = guaName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
