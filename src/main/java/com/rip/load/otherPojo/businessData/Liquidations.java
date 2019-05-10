package com.rip.load.otherPojo.businessData;
/**
 *清算信息
 */
public class Liquidations {

    /**
     * 清算责任人
     */
    private String ligEntity;
    /**
     *清算负责人
     */
    private String ligPrincipal;
    /**
     *清算组成员
     */
    private String liQMen;
    /**
     *清算完结情况
     */
    private String liGSt;
    /**
     *清算完结日期
     */
    private String ligEndDate;
    /**
     *债务承接人
     */
    private String debtTranee;
    /**
     *债权承接人
     */
    private String claimTranee;
    /**
     *电话
     */
    private String mobile;
    /**
     *地址
     */
    private String address;


    public String getLigEntity() {
        return ligEntity;
    }

    public void setLigEntity(String ligEntity) {
        this.ligEntity = ligEntity;
    }

    public String getLigPrincipal() {
        return ligPrincipal;
    }

    public void setLigPrincipal(String ligPrincipal) {
        this.ligPrincipal = ligPrincipal;
    }

    public String getLiQMen() {
        return liQMen;
    }

    public void setLiQMen(String liQMen) {
        this.liQMen = liQMen;
    }

    public String getLiGSt() {
        return liGSt;
    }

    public void setLiGSt(String liGSt) {
        this.liGSt = liGSt;
    }

    public String getLigEndDate() {
        return ligEndDate;
    }

    public void setLigEndDate(String ligEndDate) {
        this.ligEndDate = ligEndDate;
    }

    public String getDebtTranee() {
        return debtTranee;
    }

    public void setDebtTranee(String debtTranee) {
        this.debtTranee = debtTranee;
    }

    public String getClaimTranee() {
        return claimTranee;
    }

    public void setClaimTranee(String claimTranee) {
        this.claimTranee = claimTranee;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
