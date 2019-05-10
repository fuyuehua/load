package com.rip.load.otherPojo.businessData;

/**
 *动产抵押-被担保主债权信息
 */
public class MortgageDebtors {

    /**
     * 履行债务开始日期
     */
    private String startDate;

    /**
     * 履行债务结束日期
     */
    private String endtDate;

    /**
     * 数额
     */
    private String amount;

    /**
     * 担保范围
     */
    private String range;

    /**
     *备注
     */
    private String remark;

    /**
     *种类
     */
    private String type;
    /**
     *编号
     */
    private String regNo;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndtDate() {
        return endtDate;
    }

    public void setEndtDate(String endtDate) {
        this.endtDate = endtDate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }
}
