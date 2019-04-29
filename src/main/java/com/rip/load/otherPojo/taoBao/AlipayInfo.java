package com.rip.load.otherPojo.taoBao;
/**
 *绑定的支付宝信息
 */
public class AlipayInfo {

    /**
     *支付账户名
     */
    private String username;

    /**
     *绑定邮箱
     */
    private String email;

    /**
     *绑定手机
     */
    private String mobile;

    /**
     *实名认证姓名
     */
    private String realName;

    /**
     *实名认证省份证号
     */
    private String identityNo;

    /**
     *实名认证状态
     */
    private String identityStatus;

    /**
     *账户余额
     */
    private String accBal;

    /**
     *余额宝余额
     */
    private String yuebaoBal;

    /**
     *余额宝历史累计收益
     */
    private String yuebaoHisIncome;

    /**
     *花呗消费额度
     */
    private String huabeiLimit;

    /**
     *花呗可用额度
     */
    private String huabeiAvailableLimit;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public String getIdentityStatus() {
        return identityStatus;
    }

    public void setIdentityStatus(String identityStatus) {
        this.identityStatus = identityStatus;
    }

    public String getAccBal() {
        return accBal;
    }

    public void setAccBal(String accBal) {
        this.accBal = accBal;
    }

    public String getYuebaoBal() {
        return yuebaoBal;
    }

    public void setYuebaoBal(String yuebaoBal) {
        this.yuebaoBal = yuebaoBal;
    }

    public String getYuebaoHisIncome() {
        return yuebaoHisIncome;
    }

    public void setYuebaoHisIncome(String yuebaoHisIncome) {
        this.yuebaoHisIncome = yuebaoHisIncome;
    }

    public String getHuabeiLimit() {
        return huabeiLimit;
    }

    public void setHuabeiLimit(String huabeiLimit) {
        this.huabeiLimit = huabeiLimit;
    }

    public String getHuabeiAvailableLimit() {
        return huabeiAvailableLimit;
    }

    public void setHuabeiAvailableLimit(String huabeiAvailableLimit) {
        this.huabeiAvailableLimit = huabeiAvailableLimit;
    }
}

