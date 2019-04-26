package com.rip.load.pojo.businessData;

/**
 *动产抵押-变更信息
 */
public class MortgageAlter {

    /**
     * 变更日期
     */
    private String alterDate;
    /**
     * 变更内容
     */
    private String alterDetail;
    /**
     * 登记编号
     */
    private String RegNo;

    public String getAlterDate() {
        return alterDate;
    }

    public void setAlterDate(String alterDate) {
        this.alterDate = alterDate;
    }

    public String getAlterDetail() {
        return alterDetail;
    }

    public void setAlterDetail(String alterDetail) {
        this.alterDetail = alterDetail;
    }

    public String getRegNo() {
        return RegNo;
    }

    public void setRegNo(String regNo) {
        RegNo = regNo;
    }
}
