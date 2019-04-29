package com.rip.load.otherPojo.taoBao;
/**
 *收货地址
 */
public class Addresses {

    /**
     *收货姓名
     */
    private String name;

    /**
     *收货地址
     */
    private String address;

    /**
     *收货联系手机号或电话
     */
    private String mobile;

    /**
     *邮编
     */
    private String zipCode;

    /**
     *是否未默认收货地址（是，不是）
     */
    private String defaultAddr;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getDefaultAddr() {
        return defaultAddr;
    }

    public void setDefaultAddr(String defaultAddr) {
        this.defaultAddr = defaultAddr;
    }
}
