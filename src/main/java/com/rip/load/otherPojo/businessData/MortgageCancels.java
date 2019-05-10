package com.rip.load.otherPojo.businessData;

/**
 *动产抵押-注销信息
 */
public class MortgageCancels {

    /**
     * 注销日期
     */
    private String cancelDate;

    /**
     * 注销原因
     */
    private String cancelReason;

    /**
     * 登记编号
     */
    private String regNo;

    public String getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(String cancelDate) {
        this.cancelDate = cancelDate;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }
}
