package com.rip.load.pojo.businessData;

/**
 * 股东信息
 */
public class Shareholder {

    /**
     * 股东名称
     */
    private String shaName;

    /**
     * 认缴出资额(万元)
     */
    private String subConAm;

    /**
     *认缴出资币种
     */
    private String regCapCur;

    /**
     *出资方式
     */
    private String conForm;

    /**
     *出资比例
     */
    private String fundedRatio;

    /**
     *出资日期
     */
    private String conDate;

    /**
     * 国别
     */
    private String country;

    /**
     * 股东总数量
     */
    private String invaMount;

    /**
     * 信用卡号
     */
    private String creditNo;

    public String getShaName() {
        return shaName;
    }

    public void setShaName(String shaName) {
        this.shaName = shaName;
    }

    public String getSubConAm() {
        return subConAm;
    }

    public void setSubConAm(String subConAm) {
        this.subConAm = subConAm;
    }

    public String getRegCapCur() {
        return regCapCur;
    }

    public void setRegCapCur(String regCapCur) {
        this.regCapCur = regCapCur;
    }

    public String getConForm() {
        return conForm;
    }

    public void setConForm(String conForm) {
        this.conForm = conForm;
    }

    public String getFundedRatio() {
        return fundedRatio;
    }

    public void setFundedRatio(String fundedRatio) {
        this.fundedRatio = fundedRatio;
    }

    public String getConDate() {
        return conDate;
    }

    public void setConDate(String conDate) {
        this.conDate = conDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getInvaMount() {
        return invaMount;
    }

    public void setInvaMount(String invaMount) {
        this.invaMount = invaMount;
    }

    public String getCreditNo() {
        return creditNo;
    }

    public void setCreditNo(String creditNo) {
        this.creditNo = creditNo;
    }
}
