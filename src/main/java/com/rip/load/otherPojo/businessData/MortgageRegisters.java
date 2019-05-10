package com.rip.load.otherPojo.businessData;
/**
 *动产抵押-登记信息
 */
public class MortgageRegisters {

    /**
     * 履行债务开始日期
     */
    private String startDate;

    /**
     * 履行债务结束日期
     */
    private String endDate;

    /**
     * 被担保债权数额
     */
    private String amount;

    /**
     * 担保范围
     */
    private String range;

    /**
     * 被担保债券种类
     */
    private String type;
    /**
     * 登记编号
     */
    private String regNo;

    /**
     * 登记日期
     */
    private String registerDate;

    /**
     * 登记机关
     */
    private String registerOrg;

    /**
     * 省份代码
     */
    private String provinceNo;

    /**
     * 状态
     */
    private String status;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getRegisterOrg() {
        return registerOrg;
    }

    public void setRegisterOrg(String registerOrg) {
        this.registerOrg = registerOrg;
    }

    public String getProvinceNo() {
        return provinceNo;
    }

    public void setProvinceNo(String provinceNo) {
        this.provinceNo = provinceNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
