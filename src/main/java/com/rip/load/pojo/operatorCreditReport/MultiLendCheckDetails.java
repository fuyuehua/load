package com.rip.load.pojo.operatorCreditReport;

public class MultiLendCheckDetails {
    /**
     * 借贷平台类型
     */
    private String lendType;
    /**
     * 借贷次数
     */
    private String lendCnt;

    public String getLendType() {
        return lendType;
    }

    public void setLendType(String lendType) {
        this.lendType = lendType;
    }

    public String getLendCnt() {
        return lendCnt;
    }

    public void setLendCnt(String lendCnt) {
        this.lendCnt = lendCnt;
    }
}
